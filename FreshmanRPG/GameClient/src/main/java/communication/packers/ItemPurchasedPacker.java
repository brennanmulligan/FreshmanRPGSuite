package communication.packers;

import java.util.ArrayList;

import communication.messages.ItemPurchasedMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ItemPurchasedReport;

/**
 * Packer that transforms ItemPurchasedReport into an ItemPurchasedMessage
 * @author Kevin Marek, Zachary Semanco
 *
 */
public class ItemPurchasedPacker extends MessagePacker
{

	/**
	 * Transform the ItemPurchasedReport into an ItemPurchasedMessage
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != ItemPurchasedReport.class)
		{
			throw new IllegalArgumentException(
					"ItemPurchasedPacker cannot pack messages of type "
							+ object.getClass());
		}
		ItemPurchasedReport report = (ItemPurchasedReport) object;
		Message msg = new ItemPurchasedMessage(report.getPlayerID(), report.getPrice());
		return msg;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(ItemPurchasedReport.class);
		return result;
	}

}
