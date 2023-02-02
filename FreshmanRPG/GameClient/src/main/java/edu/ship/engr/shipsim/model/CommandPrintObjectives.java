package edu.ship.engr.shipsim.model;

/**
 * Cause certificates for each outstanding real life objective to be
 * output into a given PDF file.
 *
 * @author Merlin
 */
public class CommandPrintObjectives extends Command
{

    private final String fileTitle;

    /**
     * @param fileTitle the file that will be overwritten with the certificates
     */
    public CommandPrintObjectives(String fileTitle)
    {
        this.fileTitle = fileTitle;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        PDFObjectiveWriter writer = new PDFObjectiveWriter();
        writer.createPDFOfTriggeredExternalObjectives(fileTitle);
    }

}
