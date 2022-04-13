package model;

/**
 * Cause certificates for each outstanding real life adventure to be
 * output into a given PDF file.
 *
 * @author Merlin
 *
 */
public class CommandPrintAdventures extends Command
{

	private String fileTitle;

	/**
	 * @param fileTitle the file that will be overwritten with the certificates
	 */
	public CommandPrintAdventures(String fileTitle)
	{
		this.fileTitle = fileTitle;
	}

	/**
	 *
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		PDFAdventureWriter writer = new PDFAdventureWriter();
		writer.createPDFOfTriggeredExternalAdventures(fileTitle);
		return true;
	}

}
