package ui.fx.contentviews;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.InteractableItemActionParameter;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datatypes.Position;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.*;
import model.reports.ObjectListReport;
import ui.fx.dialogues.AddInteractableObjectModal;
import ui.fx.dialogues.EditInteractableObjectModal;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ContentView;
import ui.fx.framework.FileChooserContainer;

import java.util.ArrayList;
import java.util.Optional;

public class MessageBoardContentView extends ContentView implements QualifiedObserver
{
    private static MessageBoardContentView instance;
    private FileChooserContainer container;
    private ArrayList<InteractableItemDTO> items;
    private boolean wipeTable = false;

    private TableView<InteractableItemDTO> itemsTable;

    /**
     * Singleton constructor
     */
    @SuppressWarnings("unchecked")
    private MessageBoardContentView()
    {
        super("MessageBoardContentView", "Message Board");
        //TABLE STUFF HERE
        //int id, string name, Position pos, InteractableItemActionType actionType,
        //InteractableItemActionParameter actionParam, String mapName

        this.itemsTable = new TableView<>();
        this.itemsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.itemsTable.setId("InteractableObjectsTable");

        TableColumn<InteractableItemDTO, Integer> itemID = new TableColumn<>("ID");
        itemID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<InteractableItemDTO, String> name = new TableColumn<>("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<InteractableItemDTO, Position> position = new TableColumn<>("POSITION");
        position.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<InteractableItemDTO, InteractableItemActionType> actionType = new TableColumn<>("ACTION TYPE");
        actionType.setCellValueFactory(new PropertyValueFactory<>("actionType"));

        TableColumn<InteractableItemDTO, InteractableItemActionParameter> actionParam = new TableColumn<>("ACTION PARAMETER");
        actionParam.setCellValueFactory(new PropertyValueFactory<>("actionParam"));

        TableColumn<InteractableItemDTO, String> mapName = new TableColumn<>("MAP");
        mapName.setCellValueFactory(new PropertyValueFactory<>("mapName"));

        this.itemsTable.getColumns().addAll(itemID, name, position, actionType, actionParam, mapName);
        this.setCenter(this.itemsTable);
        this.refresh();
    }

    /**
     * @return the singleton instance of the items content
     */
    public static MessageBoardContentView getInstance()
    {
        if (instance == null)
        {
            instance = new MessageBoardContentView();
        }
        return instance;
    }

    /**
     * @return all the interactable objects in the table
     */
    public TableView<InteractableItemDTO> getInteractableObjectTable()
    {
        return this.itemsTable;
    }


    /**
     * Refresh items
     */
    @Override
    public void refresh()
    {
        this.itemsTable.setItems(null);
        AlertBar.getInstance().receiveMessage("REFRESH MESSAGE BOARD VIEW");
        this.items = new ArrayList<>();
        CommandGetAllInteractableObjects command = new CommandGetAllInteractableObjects();
        ModelFacade.getSingleton().queueCommand(command);
    }

    //importFile

    /**
     * receive report
     * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
     */
    @Override
    public void receiveReport(QualifiedObservableReport report)
    {
        this.itemsTable.setItems(null);
        this.items = ((ObjectListReport) report).getMessageBoards();
        this.itemsTable.setItems(FXCollections.observableArrayList(items));
    }

    /**
     * Edit an interactable object
     */
    @Override
    public void edit()
    {
        AlertBar.getInstance().receiveMessage("EDIT MESSAGE BOARD");
        InteractableItemDTO item = this.itemsTable.getSelectionModel().getSelectedItem();
        EditInteractableObjectModal.getInstance().setName(item.getName());
        EditInteractableObjectModal.getInstance().setPositionX(item.getPosition().getColumn());
        EditInteractableObjectModal.getInstance().setPositionY(item.getPosition().getRow());
        EditInteractableObjectModal.getInstance().setInteractableItemActionType(item.getActionType());
        if (item.getActionType().equals(InteractableItemActionType.MESSAGE)
                || item.getActionType().equals(InteractableItemActionType.BOARD))
        {
            CriteriaStringDTO msg = (CriteriaStringDTO.class.cast(item.getActionParam()));
            EditInteractableObjectModal.getInstance().setMessageSent(msg.getString());
            EditInteractableObjectModal.getInstance().setBuffPoints(0);
        }
        else if (item.getActionType().equals(InteractableItemActionType.BUFF))
        {
            CriteriaIntegerDTO buff = (CriteriaIntegerDTO.class.cast(item.getActionParam()));
            EditInteractableObjectModal.getInstance().setBuffPoints(buff.getTarget());
            EditInteractableObjectModal.getInstance().setMessageSent(null);
        }
        EditInteractableObjectModal.getInstance().setMap(item.getMapName());
        EditInteractableObjectModal.getInstance().setId(item.getId());
        EditInteractableObjectModal.getInstance().show();
    }

    /**
     * Filter interactable objects
     */
    @Override
    public void filter(String filter)
    {
        AlertBar.getInstance().receiveMessage("FILTER INTERACTABLE OBJECTS BY \"" + filter.toUpperCase() + "\"");
    }

    /**
     * Import CSV file for interactable objects
     */
    @Override
    public void importFile()
    {
        AlertBar.getInstance().receiveMessage("IMPORT INTERACTABLE OBJECTS");
        container = new FileChooserContainer(new FileChooser(), "Open Interactable Object Import File");
        container.load();
        if (container.getSelectedFile() != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setDialogPane(new DialogPane()
            {
                @Override
                protected Node createDetailsButton()
                {
                    CheckBox optOut = new CheckBox();
                    optOut.setText("Wipe all existing objects?");
                    optOut.selectedProperty().addListener((ov, old_val, new_val) -> wipeTable = optOut.isSelected());
                    return optOut;
                }
            });

            alert.setTitle("Confirm Import");
            alert.setContentText("Are you sure you want to import this file?");
            alert.getDialogPane().setExpandableContent(new Group());
            alert.getDialogPane().setExpanded(true);
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();


            if (result.get() == ButtonType.YES)
            {
                /**
                 * If check box to wipe table is called, clear the table before importing.
                 */
                if (wipeTable == true)
                {
                    System.out.println("Check box to clear table selected");
                }
                // Continue with import
                AlertBar.getInstance().receiveMessage("INTERACTABLE OBJECTS IMPORTED");

            }
            else
            {
                // Cancel import
            }
        }
    }
}
