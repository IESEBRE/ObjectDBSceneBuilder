package com.iesebre.provascenebuilder;

import com.iesebre.provascenebuilder.model.Person;
import com.iesebre.provascenebuilder.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PersonEditDialogController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField street;
    @FXML
    private TextField postalCode;
    @FXML
    private ComboBox<Person.City> city;
    @FXML
    private TextField birthday;

    //Dades de la persona a mostrar
    private Person persona;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        ObservableList<Person.City> cityData = FXCollections.observableArrayList();
        Arrays.stream(Person.City.values()).forEach(c -> cityData.add(c));
        city.setItems(cityData);
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param persona
     */
    public void setPersonData(Person persona) {
        //Get link to the person for further modifications
        this.persona = persona;

        // Add person data to the textfields
        firstName.setText(persona.getFirstName());
        lastName.setText(persona.getLastName());
        street.setText(persona.getStreet());
        postalCode.setText(String.valueOf(persona.getPostalCode()));
        city.setValue(persona.getCity());
        birthday.setText(DateUtil.format(persona.getBirthday()));
    }

    /**
     * Called when the user clicks on the OK button.
     */
    public void handleSavePerson() {
        try {
            // Save the person data with the textfields info
            persona.setFirstName(firstName.getText().strip());
            persona.setLastName(lastName.getText().strip());
            persona.setStreet(street.getText().strip());
            persona.setPostalCode(Integer.valueOf(postalCode.getText().strip()));
            persona.setCity(city.getValue());
            persona.setBirthday(DateUtil.parse(birthday.getText().strip()));

            // Check whether the date is correct
            if(persona.getBirthday() == null ) throw new IllegalArgumentException();
            // After pressing the button we must go back to the previous scene, showing the new person values
            mainApp.getPersonOverviewController().showPersonDetails(persona);
            goBackToPersonOverview();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect data");
            alert.setHeaderText("Check postal code");
            alert.setContentText("Please write a numerical postal code");

            alert.showAndWait();
        } catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect data");
            alert.setHeaderText("Check birthday date");
            alert.setContentText("Please write a correct birthday date (dd.mm.yyyy)");

            alert.showAndWait();

        }
    }

    /**
     * Called when the user clicks on the Cancel button.
     */
    public void handleCancelPersonEdition() {
        // After pressing the button we must go back to the previous scene
        goBackToPersonOverview();
    }

    private void goBackToPersonOverview() {
        mainApp.getRootLayout().setCenter(mainApp.getPersonOverviewPane());
    }


}