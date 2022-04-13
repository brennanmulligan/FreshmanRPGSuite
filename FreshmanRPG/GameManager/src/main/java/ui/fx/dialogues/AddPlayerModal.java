package ui.fx.dialogues;

import datatypes.Crew;
import datatypes.Major;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CommandAddPlayerInManager;
import model.ModelFacade;

/**
 * Modal to add a player
 * @author Benjamin Uleau, Chris Boyer
 *
 */
public class AddPlayerModal extends Modal
{

	private static AddPlayerModal instance;
	private TextField nameField;
	private TextField sectionIdField;
	private TextField passwordField;
	private TextField confirmPasswordField;
	private ComboBox<Enum<Major>> playerMajor;
	private ComboBox<Enum<Crew>> playerCrew;

	private AddPlayerModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);

		Label nameLabel = new Label("Name:");
		nameLabel.setPrefWidth(LABEL_WIDTH);
		nameField = new TextField();
		nameField.setId("NameField");
		nameField.setPromptText("Player Name");
		HBox name = new HBox();
		name.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		name.getChildren().addAll(nameLabel, nameField);

		Label sectionIdLabel = new Label("Section ID:");
		sectionIdLabel.setPrefWidth(LABEL_WIDTH);
		sectionIdField = new TextField();
		sectionIdField.setId("SectionIdField");
		sectionIdField.setPromptText("Player Section ID");
		HBox sectionId = new HBox();
		sectionId.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		sectionId.getChildren().addAll(sectionIdLabel, sectionIdField);

		Label passwordLabel = new Label("Password:");
		passwordLabel.setPrefWidth(LABEL_WIDTH);
		passwordField = new TextField();
		passwordField.setId("PasswordField");
		passwordField.setPromptText("Player Password");
		HBox password = new HBox();
		password.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		password.getChildren().addAll(passwordLabel, passwordField);

		Label confirmPasswordLabel = new Label("Confirm Password:");
		confirmPasswordLabel.setPrefWidth(LABEL_WIDTH);
		confirmPasswordField = new TextField();
		confirmPasswordField.setId("ConfirmPasswordField");
		confirmPasswordField.setPromptText("Confirm Player Password");
		HBox confirmPassword = new HBox();
		confirmPassword.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		confirmPassword.getChildren().addAll(confirmPasswordLabel, confirmPasswordField);

		Label majorLabel = new Label("Major:");
		majorLabel.setPrefWidth(LABEL_WIDTH);
		ObservableList<Enum<Major>> majorsList = FXCollections.observableArrayList(Major.values());
		playerMajor = new ComboBox<>(majorsList);
		playerMajor.setId("Major");
		playerMajor.setValue(majorsList.get(0));
		HBox major = new HBox();
		major.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		major.getChildren().addAll(majorLabel, playerMajor);

		Label crewLabel = new Label("Crew:");
		crewLabel.setPrefWidth(LABEL_WIDTH);
		ObservableList<Enum<Crew>> crewsList = FXCollections.observableArrayList(Crew.values());
		playerCrew = new ComboBox<>(crewsList);
		playerCrew.setId("Crew");
		playerCrew.setValue(crewsList.get(0));
		HBox crew = new HBox();
		crew.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		crew.getChildren().addAll(crewLabel, playerCrew);

		VBox center = new VBox();
		center.getChildren().addAll(name, sectionId, major, crew, password, confirmPassword);
		modal.setCenter(center);

	}

	/**
	 * Check if the singleton is instantiated
	 * @return false if it's not instantiated, true otherwise
	 */
	public static boolean isInstantiated()
	{
		return instance == null;
	}

	/**
	 * Get the singleton instance
	 * @return the singleton instance
	 */
	public static AddPlayerModal getInstance()
	{
		if (instance == null)
		{
			instance = new AddPlayerModal("AddPlayer", "Add Player", 400, 400);
		}
		return instance;
	}

	/**
	 * Create and queue the command which persists the data to the DB
	 */
	@Override
	public void save()
	{
		CommandAddPlayerInManager command = new CommandAddPlayerInManager(AddPlayerModal.getInstance().getPlayerName(),
				AddPlayerModal.getInstance().getPlayerPassword(),
				AddPlayerModal.getInstance().getPlayerCrew().ordinal(),
				AddPlayerModal.getInstance().getPlayerMajor().ordinal(),
				Integer.parseInt(AddPlayerModal.getInstance().getPlayerSectionId()));
		ModelFacade.getSingleton().queueCommand(command);

	}

	/**
	 * Cancel addition of a player
	 */
	@Override
	public void cancel()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @return the player's name from the modal
	 */
	public String getPlayerName()
	{
		return nameField.getText();
	}

	/**
	 * @return the player's section id from the modal
	 */
	public String getPlayerSectionId()
	{
		return sectionIdField.getText();
	}

	/**
	 * @return the player's password from the modal
	 */
	public String getPlayerPassword()
	{
		return passwordField.getText();
	}

	/**
	 * @return the player's password confirmation from the modal
	 */
	public String getPlayerConfirmPassword()
	{
		return confirmPasswordField.getText();
	}

	/**
	 * @return the player's major from the modal
	 */
	public Enum<Major> getPlayerMajor()
	{
		return playerMajor.getValue();
	}

	/**
	 * @return the player's crew from the modal
	 */
	public Enum<Crew> getPlayerCrew()
	{
		return playerCrew.getValue();
	}

	/**
	 * Reset the singleton instance for cleanup
	 */
	@Override
	public void reset()
	{
		instance = null;
	}
}
