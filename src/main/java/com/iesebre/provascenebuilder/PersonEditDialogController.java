package com.iesebre.provascenebuilder;

import com.iesebre.provascenebuilder.model.Person;
import com.iesebre.provascenebuilder.persistence.exceptions.DAOException;
import com.iesebre.provascenebuilder.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.LocalDate;
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

    //Booleà que indica si estem creant (New) o editant (Edit) una persona
    private boolean edit=true;

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
        //Miro si estem editant o creant una persona
        edit=persona!=null;

        //Get link to the person for further modifications
        this.persona = (edit?persona:new Person());

        if(edit) {
            // Add person data to the textfields
            firstName.setText(persona.getFirstName());
            lastName.setText(persona.getLastName());
            street.setText(persona.getStreet());
            postalCode.setText(String.valueOf(persona.getPostalCode()));
            city.setValue(persona.getCity());
            birthday.setText(DateUtil.format(persona.getBirthday()));
        }else{
            // Add empty person data to the textfields
            firstName.setText("");
            lastName.setText("");
            street.setText("");
            postalCode.setText("");
            city.setValue(Person.City.values()[0]);
            birthday.setText(DateUtil.format(LocalDate.now()));

        }
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

            //Si estem creant una nova persona l'hem de passar a la BD i actualitzar el grid
            if(!edit){
                mainApp.getDb().saveAndCopy(persona);
                mainApp.getPersonData().add(persona);
            }
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

        } catch (DAOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("General error");
            alert.setHeaderText("DB problem");
            alert.setContentText("Please tell the programmer...");

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
