package communication.packers;

import java.util.ArrayList;

import communication.messages.DoubloonPrizeMessage;
import dataDTO.DoubloonPrizeDTO;
import model.QualifiedObservableReport;
import model.reports.DoubloonPrizeReport;

/*
 * @Author: Andrew M, Christian C
 */
public class DoubloonPacker extends MessagePacker
{


	ArrayList<DoubloonPrizeDTO> dtos = new ArrayList<>();
	DoubloonPrizeMessage msg = null;

	/*
	 * pack the message
	 */
	public DoubloonPrizeMessage pack(QualifiedObservableReport object)
	{

		if (object.getClass() != DoubloonPrizeReport.class)
		{
			throw new IllegalArgumentException("Doubloon Prize Report cannot pack messages of type " + object.getClass());
		}

		DoubloonPrizeReport report = (DoubloonPrizeReport) object;
		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			msg = new DoubloonPrizeMessage(report.getPlayerID(), report.getPrizes());
		}
		return msg;
	}

	/*
	 * listen for report
	 */
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(DoubloonPrizeReport.class);
		return result;
	}
}
