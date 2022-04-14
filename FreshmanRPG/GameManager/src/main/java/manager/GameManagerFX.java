package manager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.OptionsManager;
import model.QualifiedObservableConnector;
import model.reports.AllPlayersReport;
import model.reports.AllQuestsAndObjectivesReport;
import model.reports.ObjectListReport;
import model.reports.PlayersUncompletedObjectivesReport;
import model.reports.QuestionListReport;
import ui.fx.UserInterface;
import ui.fx.contentviews.*;
import ui.fx.dialogues.ModifyPlayerStateModal;


/**
 * @author Benjamin Uleau, Josh McMillen
 *
 */

public class GameManagerFX extends Application
{
	private boolean runningLocally = false;
	private String dbIdentifier = null;
	private static String[] theArgs = new String[]{};

	/**
	 * Default Constructor
	 */
	public GameManagerFX()
	{
		this(theArgs);
	}

	/**
	 *
	 * @param args Database Selection Arguments
	 */
	public GameManagerFX(String[] args)
	{
		for (String arg : args)
		{
			String[] splitArg = arg.split("=");
			if (splitArg[0].equals("--localhost"))
			{
				runningLocally = true;
			}
			else if (splitArg[0].equals("--db"))
			{
				dbIdentifier = splitArg[1];
			}
			else
			{
				throw new IllegalArgumentException("Unrecognized Argument:  " + splitArg[0]);
			}
		}

		if (dbIdentifier == null && runningLocally || dbIdentifier != null && !runningLocally)
		{
			throw new IllegalArgumentException("Missing Database Identifier argument");
		}

		OptionsManager.getSingleton().setUsingTestDB(runningLocally);
		OptionsManager.getSingleton().setDbIdentifier(dbIdentifier);

	}

	private static Stage cont;

	/**
	 * @param container - the stage on which the GameManager is now based
	 */
	@Override
	public void start(Stage container)
	{

		Platform.setImplicitExit(true);
		container.setOnCloseRequest((actionEvent) ->
		{
			Platform.exit();
			System.exit(0);
		});

		try
		{
			//Initialize the scene to the UI singleton instance
			BorderPane root = new BorderPane();
			root.setCenter(UserInterface.getInstance());
			Scene scene = new Scene(root, 1160, 640);

			//Styling
			scene.getStylesheets().add(getClass().getResource("/res/game_manager.css").toExternalForm());

			this.registerObservers();

			//Display the scene
			container.setTitle("Ship CS1 Game Manager");
			container.getIcons().add(new Image("res/ship.png"));
			container.setScene(scene);
			cont = container;
			container.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


	/**
	 * Sets all Content Views to Registered Observers
	 */
	private void registerObservers()
	{
		QualifiedObservableConnector.getSingleton().registerObserver(PlayerContentView.getInstance(), AllPlayersReport.class);
		PlayerContentView.getInstance().refresh();
		QualifiedObservableConnector.getSingleton().registerObserver(QuizbotContentView.getInstance(), QuestionListReport.class);
		QuizbotContentView.getInstance().refresh();
		QualifiedObservableConnector.getSingleton().registerObserver(ObjectiveContentView.getInstance(), AllQuestsAndObjectivesReport.class);
		ObjectiveContentView.getInstance().refresh();
		QualifiedObservableConnector.getSingleton().registerObserver(InteractableObjectContentView.getInstance(), ObjectListReport.class);
		InteractableObjectContentView.getInstance().refresh();
		QualifiedObservableConnector.getSingleton().registerObserver(MessageBoardContentView.getInstance(), ObjectListReport.class);
		MessageBoardContentView.getInstance().refresh();
		QualifiedObservableConnector.getSingleton().registerObserver(ModifyPlayerStateModal.getInstance(), PlayersUncompletedObjectivesReport.class);
	}

	/**
	 * @param args - arguments
	 */
	public static void main(String[] args)
	{
		theArgs = args;
		//Calls Application.launch which launches the start method above
		launch(args);
	}

	/**
	 * @return The game manager's stage
	 */
	public static Stage getStage()
	{
		return cont;
	}
}
