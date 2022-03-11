/**
 * Sample Skeleton for 'PersonOverview.fxml' Controller Class
 */

package com.iesebre.provascenebuilder.controller;

import com.iesebre.provascenebuilder.MainApp;
import com.iesebre.provascenebuilder.controller.PersonEditDialogController;
import com.iesebre.provascenebuilder.persistence.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import com.iesebre.provascenebuilder.model.Person;
import com.iesebre.provascenebuilder.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PersonOverviewController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, Float> souColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label souLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        //Les columnes no String se'ls hi passa un Factory de la següent forma
        souColumn.setCellValueFactory(new PropertyValueFactory<>("sou"));
//        PropertyValueFactory factory = new PropertyValueFactory<>("firstName");
//        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        // Clear person details
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }

    public void setPersonData(ObservableList<Person> personData) {


        // Add observable list data to the table
        personTable.setItems(personData);
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    public void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            souLabel.setText(Float.toString(person.getSou()));
            cityLabel.setText(person.getCity().getName());

            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            souLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() throws DAOException {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            mainApp.getDb().remove(personTable.getItems().get(selectedIndex));
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
//            Dialogs.create()
//                    .title("No Selection")
//                    .masthead("No Person Selected")
//                    .message("Please select a person in the table.")
//                    .showWarning();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText("No person selected");
            alert.setContentText("Please select a person in the table");

            alert.showAndWait();

        }
    }

    /**
     * Called when the user clicks on the new button.
     */
    @FXML
    private void handleNewPerson() {
        showEditPerson(null);
    }

    /**
     * Called when the user clicks on the edit button.
     */
    @FXML
    private void handleEditPerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            showEditPerson(personTable.getSelectionModel().getSelectedItem());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText("No person selected");
            alert.setContentText("Please select a person in the table");

            alert.showAndWait();

        }
    }

    /**
     * Called when the user clicks on the edit button and there is a selected person.
     */
    private void showEditPerson(Person selectedItem) {
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));

            AnchorPane pane=(AnchorPane) loader.load();
            mainApp.getRootLayout().setCenter(pane);

            PersonEditDialogController pedc=loader.getController();
            pedc.setMainApp(mainApp);
            pedc.setPersonData(selectedItem);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
