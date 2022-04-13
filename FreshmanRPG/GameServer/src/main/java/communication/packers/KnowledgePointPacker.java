package communication.packers;

import java.util.ArrayList;

import communication.messages.KnowledgePointPrizeMessage;
import dataDTO.KnowledgePointPrizeDTO;
import model.QualifiedObservableReport;
import model.reports.ChatMessageReceivedReport;
import model.reports.KnowledgePointPrizeReport;

/*
 * @Author: Andrew M, Christian C
 */
public class KnowledgePointPacker extends MessagePacker
{


	ArrayList<KnowledgePointPrizeDTO> dtos = new ArrayList<>();
	KnowledgePointPrizeMessage msg = null;

	/*
	 * pack the message
	 */
	public KnowledgePointPrizeMessage pack(QualifiedObservableReport object)
	{

		if (object.getClass() != KnowledgePointPrizeReport.class)
		{
			throw new IllegalArgumentException("KnowledgePoint Prize Report cannot pack messages of type " + object.getClass());
		}

		KnowledgePointPrizeReport report = (KnowledgePointPrizeReport) object;
		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			msg = new KnowledgePointPrizeMessage(report.getPlayerID(), report.getPrizes());
		}
		return msg;
	}

	/*
	 * listen for report
	 */
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(KnowledgePointPrizeReport.class);
		return result;
	}
}
