package communication.packers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import communication.CommunicationException;
import communication.StateAccumulator;
import communication.messages.Message;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.TypeDetector;

/**
 * Holds the set of MessagePackers that should translate notifications from
 * observables into messages to be sent to the other side
 *
 * @author merlin
 *
 */
public class MessagePackerSet extends TypeDetector
{

	private HashMap<Class<?>, ArrayList<MessagePacker>> packers;

	/**
	 *
	 */
	public MessagePackerSet()
	{
		packers = new HashMap<>();
		ArrayList<Class<?>> packerTypes = this.detectAllExtendersInPackage(MessagePacker.class);
		for (Class<?> packerType : packerTypes)
		{
			try
			{
				MessagePacker packer = (MessagePacker) packerType.getConstructor().newInstance();
				registerPacker(packer);
			}
			catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the packer associated with a given type of report
	 *
	 * @param reportWePack the report type we are interested
	 * @return the packer that will handle reports of that type
	 * @throws CommunicationException if we have no handler for that report type
	 */
	public ArrayList<MessagePacker> getPackersFor(Class<?> reportWePack) throws CommunicationException
	{
		if (!packers.containsKey(reportWePack))
		{
			throw new CommunicationException("No MessagePacker for " + reportWePack);
		}
		return packers.get(reportWePack);
	}

	/**
	 * translate the report into the message that should travel over the connection
	 *
	 * @param report the report that we are packing for transport
	 * @return the message describing the event
	 * @throws CommunicationException if there is no packer registered for this type
	 * of event
	 */
	public ArrayList<Message> pack(QualifiedObservableReport report) throws CommunicationException
	{
		ArrayList<Message> results = new ArrayList<>();
		Class<? extends QualifiedObservableReport> classWeArePacking = report.getClass();
		ArrayList<MessagePacker> packers = getPackersFor(classWeArePacking);
		for (MessagePacker packer : packers)
		{
			Message msg = packer.pack(report);
			if (msg != null)
			{
				results.add(msg);
			}
		}
		return results;

	}

	/**
	 * Add a MessagePacker to this set
	 *
	 * @param packer the packer that should interpret this type of notification
	 */
	public void registerPacker(MessagePacker packer)
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportsWePack = packer.getReportTypesWePack();

		for (Class<? extends QualifiedObservableReport> reportWePack : reportsWePack)
		{
			ArrayList<MessagePacker> relevantPackers = packers.get(reportWePack);
			if (relevantPackers == null)
			{
				relevantPackers = new ArrayList<>();
				packers.put(reportWePack, relevantPackers);

			}
			relevantPackers.add(packer);
		}
	}

	/**
	 * Register the given observer with the report types that this message packer
	 * set can pack
	 *
	 * @param obs the state accumulator to be registered
	 */
	public void hookUpObservationFor(StateAccumulator obs)
	{

		for (ArrayList<MessagePacker> packerList : packers.values())
		{
			for (MessagePacker packer : packerList)
			{
				ArrayList<Class<? extends QualifiedObservableReport>> reportTypesWePack = packer.getReportTypesWePack();
				for (Class<? extends QualifiedObservableReport> reportType : reportTypesWePack)
				{
					QualifiedObservableConnector.getSingleton().registerObserver(obs, reportType);
				}
				packer.setAccumulator(obs);
			}
		}
	}

	/**
	 * Removes all current packers from the hashmap.
	 */
	public void destroyAllObservables(StateAccumulator obs)
	{
		for (ArrayList<MessagePacker> packerList : packers.values())
		{
			for (MessagePacker packer : packerList)
			{
				ArrayList<Class<? extends QualifiedObservableReport>> reportTypesWePack = packer.getReportTypesWePack();
				for (Class<? extends QualifiedObservableReport> reportType : reportTypesWePack)
				{
					QualifiedObservableConnector.getSingleton().unregisterObserver(obs, reportType);
				}
				packer.setAccumulator(obs);
			}
		}
		QualifiedObservableConnector.resetSingleton();
		packers.clear();
	}

	/**
	 * Get the count of all MessagePackers
	 * @return size
	 */
	public int getCount()
	{
		return packers.size();
	}
}