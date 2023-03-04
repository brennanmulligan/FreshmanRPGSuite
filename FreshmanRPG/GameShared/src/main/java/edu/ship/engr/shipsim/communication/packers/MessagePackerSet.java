package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.CommunicationException;
import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.TypeDetector;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Holds the set of MessagePackers that should translate notifications from
 * observables into messages to be sent to the other side
 *
 * @author merlin
 */
public class MessagePackerSet extends TypeDetector
{

    private final HashMap<Class<?>, ArrayList<MessagePacker>> packers;

    /**
     *
     */
    public MessagePackerSet()
    {
        packers = new HashMap<>();
        ArrayList<Class<?>> packerTypes =
                this.detectAllExtendersInPackage(MessagePacker.class);
        for (Class<?> packerType : packerTypes)
        {
            try
            {
                MessagePacker packer =
                        (MessagePacker) packerType.getConstructor().newInstance();
                registerPacker(packer);
            }
            catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                   InvocationTargetException e)
            {
                e.printStackTrace();
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
                ArrayList<Class<? extends SendMessageReport>> reportTypesWePack =
                        packer.getReportTypesWePack();
                for (Class<? extends SendMessageReport> reportType : reportTypesWePack)
                {
                    ReportObserverConnector.getSingleton()
                            .unregisterObserver(obs, reportType);
                }
                packer.setAccumulator(obs);
            }
        }
        packers.clear();
    }

    /**
     * Get the count of all MessagePackers
     *
     * @return size
     */
    public int getCount()
    {
        return packers.size();
    }

    /**
     * Get the packer associated with a given type of report
     *
     * @param reportWePack the report type we are interested
     * @return the packer that will handle reports of that type
     */
    public ArrayList<MessagePacker> getPackersFor(Class<?> reportWePack)
    {
        return packers.get(reportWePack);
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
                ArrayList<Class<? extends SendMessageReport>> reportTypesWePack =
                        packer.getReportTypesWePack();
                for (Class<? extends Report> reportType : reportTypesWePack)
                {
                    ReportObserverConnector.getSingleton()
                            .registerObserver(obs, reportType);
                }
                packer.setAccumulator(obs);
            }
        }
    }

    /**
     * translate the report into the message that should travel over the connection
     *
     * @param report the report that we are packing for transport
     * @return the message describing the event
     * @throws CommunicationException if there is no packer registered for this type
     *                                of event
     */
    public ArrayList<Message> pack(SendMessageReport report)
            throws CommunicationException
    {
        ArrayList<Message> results = new ArrayList<>();
        Class<? extends SendMessageReport> classWeArePacking = report.getClass();
        ArrayList<MessagePacker> packers = getPackersFor(classWeArePacking);
        if (packers != null)
        {
            for (MessagePacker packer : packers)
            {
                Message msg = packer.pack(report);
                if (msg != null)
                {
                    results.add(msg);
                }
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
        ArrayList<Class<? extends SendMessageReport>> reportsWePack =
                packer.getReportTypesWePack();

        for (Class<? extends SendMessageReport> reportWePack : reportsWePack)
        {
            ArrayList<MessagePacker> relevantPackers =
                    packers.computeIfAbsent(reportWePack, k -> new ArrayList<>());
            relevantPackers.add(packer);
        }
    }
}