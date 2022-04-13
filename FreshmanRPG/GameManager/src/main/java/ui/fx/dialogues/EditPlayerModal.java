package ui.fx.dialogues;

import datatypes.Crew;
import datatypes.Major;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CommandEditPlayer;
import model.ModelFacade;

/**
 *
 *
 */
public class EditPlayerModal extends Modal
{

	private static EditPlayerModal instance;
	private TextField nameField;
	private TextField sectionField;
	private PasswordField newPasswordField;
	private PasswordField confirmPasswordField;
	private ComboBox<Enum<Major>> playerMajor;
	private ComboBox<Enum<Crew>> playerCrew;

	// Extraneous variables
	private String appearance = null;
	private int score = 0;
	private int xp = 0;
	private int playerId = 0;

	private EditPlayerModal(String id, String label, int height, int width)
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

		Label sectionLabel = new Label("Section:");
		sectionLabel.setPrefWidth(LABEL_WIDTH);
		sectionField = new TextField();
		sectionField.setId("SectionIdField");
		sectionField.setPromptText("Section Id");
		HBox section = new HBox();
		section.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		section.getChildren().addAll(sectionLabel, sectionField);

		Label newPasswordLabel = new Label("New Password:");
		newPasswordLabel.setPrefWidth(LABEL_WIDTH);
		newPasswordField = new PasswordField();
		newPasswordField.setId("NewPasswordField");
		newPasswordField.setPromptText("New Password");
		HBox newPassword = new HBox();
		newPassword.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		newPassword.getChildren().addAll(newPasswordLabel, newPasswordField);

		Label confirmPasswordLabel = new Label("Confirm Password:");
		confirmPasswordLabel.setPrefWidth(LABEL_WIDTH);
		confirmPasswordField = new PasswordField();
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
		center.getChildren().addAll(name, section, major, crew, newPassword, confirmPassword);
		modal.setCenter(center);
	}

	/**
	 * @return the only one of these in the system
	 */
	public static EditPlayerModal getInstance()
	{
		if (instance == null)
		{
			instance = new EditPlayerModal("EditPlayer", "Edit Player", 400, 400);
		}

		return instance;
	}

	/**
	 * @see ui.fx.dialogues.Modal#save()
	 */
	@Override
	public void save()
	{
		CommandEditPlayer edit = new CommandEditPlayer(getPlayerId(), getPlayerAppearanceType(), getPlayerQuizScore(),
				getPlayerExperience(), (Crew) getPlayerCrew(), (Major) getPlayerMajor(),
				Integer.valueOf(getPlayerSectionId()), getPlayerName(), getPlayerNewPassword());
		ModelFacade.getSingleton().queueCommand(edit);
	}

	/**
	 * @see ui.fx.dialogues.Modal#cancel()
	 */
	@Override
	public void cancel()
	{
	}

	/**
	 * Gets the player name.
	 *
	 * @return playerName
	 */
	public String getPlayerName()
	{
		return nameField.getText();
	}

	/**
	 * Sets the player name
	 *
	 * @param name
	 *            Name of player
	 */
	public void setPlayerName(String name)
	{
		nameField.setText(name);
	}

	/**
	 * Gets the PlayerSection ID
	 *
	 * @return playerSectionID
	 */
	public String getPlayerSectionId()
	{
		return sectionField.getText();
	}

	/**
	 * Sets the player section ID
	 *
	 * @param id
	 *            The p[layer section ID
	 */
	public void setPlayerSectionId(String id)
	{
		sectionField.setText(id);
	}

	/**
	 * Gets the player's new password
	 *
	 * @return password
	 */
	public String getPlayerNewPassword()
	{
		return newPasswordField.getText();
	}

	/**
	 * Sets the player's new password
	 *
	 * @param pass
	 *            Player's new password
	 */
	public void setPlayerNewPassword(String pass)
	{
		newPasswordField.setText(pass);
	}

	/**
	 * Gets the player's confirm password
	 *
	 * @return Player's confirmed password.
	 */
	public String getPlayerConfirmPassword()
	{
		return confirmPasswordField.getText();
	}

	/**
	 * Sets the player's confirmed password
	 *
	 * @param pass
	 *            The player's confirmed password
	 */
	public void setPlayerConfirmPassword(String pass)
	{
		confirmPasswordField.setText(pass);
	}

	/**
	 * Get player's major
	 *
	 * @return player's major
	 */
	public Enum<Major> getPlayerMajor()
	{
		return playerMajor.getValue();
	}

	/**
	 * Set the player's major
	 *
	 * @param major
	 *            player's major
	 */
	public void setPlayerMajor(Enum<Major> major)
	{
		playerMajor.setValue(major);
	}

	/**
	 * Get the player's crew
	 *
	 * @return Player's crew
	 */
	public Enum<Crew> getPlayerCrew()
	{
		return playerCrew.getValue();
	}

	/**
	 * Set the player's crew
	 *
	 * @param crew
	 *            Player's crew
	 */
	public void setPlayerCrew(Enum<Crew> crew)
	{
		playerCrew.setValue(crew);
	}

	/**
	 * Set extraneous variables that aren't handled by the game manager
	 *
	 * @param appearance
	 *            Whatever the player's appearance already was
	 * @param score
	 *            Whatever the player's quiz score already was
	 * @param xp
	 *            Whatever the player's experience already was
	 * @param playerId
	 *            The unique is of the player we are modifying
	 */
	public void setOther(String appearance, int score, int xp, int playerId)
	{
		this.appearance = appearance;
		this.score = score;
		this.xp = xp;
		this.playerId = playerId;
	}

	/**
	 * @return a description of the player's appearance type
	 */
	public String getPlayerAppearanceType()
	{
		return appearance;
	}

	/**
	 * @return the players quiz bot score
	 */
	public int getPlayerQuizScore()
	{
		return score;
	}

	/**
	 * @return the number of experience points the player has earned
	 */
	public int getPlayerExperience()
	{
		return xp;
	}

	/**
	 * @return the player's unique id
	 */
	public int getPlayerId()
	{
		return playerId;
	}

	/**
	 * @see ui.fx.dialogues.Modal#reset()
	 */
	@Override
	public void reset()
	{
		instance = null;
	}

}
