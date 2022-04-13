package ui.fx.framework;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Contains all of the content needed for a fileChooser
 * @author Jordan Long and Abe Loscher
 *
 */
public class FileChooserContainer
{
	private FileChooser fileChooser;
	private File selectedFile;
	private boolean active;
	private String title;

	/**
	 * Constructor for FileChooserContainer
	 * @param fileChooser File Chooser Object
	 * @param title Title of the File Chooser Window
	 */
	public FileChooserContainer(FileChooser fileChooser, String title)
	{
		this.fileChooser = fileChooser;
		this.title = title;
	}

	/**
	 * Loads the FileChooser
	 */
	public void load()
	{
		fileChooser.setTitle(title);
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("CSV Files", "*.csv"));
		this.active = true;
		File result = fileChooser.showOpenDialog(new Stage());
		selectedFile = result == null ? selectedFile : result;
		this.active = false;
	}

	/**
	 * Getter for fileChooser
	 * @return
	 *        fileChooser
	 */
	public FileChooser getFileChooser()
	{
		return fileChooser;
	}

	/**
	 * Getter for selectedFile
	 * @return
	 *        File selected by the fileChooser
	 */
	public File getSelectedFile()
	{
		return selectedFile;
	}

	/**
	 * Getter for active
	 * @return
	 *        Status as to whether the fileChooser is actually active or not
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * Set the selected file
	 * @param
	 *        file that we are now selecting
	 */
	public void setSelectedFile(File file)
	{
		this.selectedFile = file;
	}


}
