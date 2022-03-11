package com.iesebre.provascenebuilder;

import com.iesebre.provascenebuilder.controller.PersonOverviewController;
import com.iesebre.provascenebuilder.model.Person;
import com.iesebre.provascenebuilder.persistence.daos.DAO;
import com.iesebre.provascenebuilder.persistence.exceptions.DAOException;
import com.iesebre.provascenebuilder.persistence.impls.PersonObjectDBImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane personOverviewPane;
    private PersonOverviewController personOverviewController;

    //ObjectDB DAO Implementation per persistir la informació
    private DAO<Person> db;

    //Per usar dades de la BD o hardcoded
    private boolean realData=true;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        //Poso l'idioma a inglès per no tindre problemes en els decimals
        Locale.setDefault(new Locale("en","US"));

        //Intentem instanciar el DAO de persistència en ObjectDB, si no podem tanquem l'aplicació
        try {
            db = new PersonObjectDBImpl();
        } catch (DAOException e) {
            showDBError(e);
            System.exit(1);
        }
        if (realData) {
            //Recuperem les persones de la BD i les carreguem a la llista de persones de l'aplicació
            try {
                personData.addAll(db.getAll());
            } catch (DAOException e) {
                showDBError(e);
            }
        } else {
            // Add some sample data
            personData.add(new Person("Hans", "Muster"));
            personData.add(new Person("Ruth", "Mueller"));
            personData.add(new Person("Heinz", "Kurz"));
            personData.add(new Person("Cornelia", "Meier"));
            personData.add(new Person("Werner", "Meyer"));
            personData.add(new Person("Lydia", "Kunz"));
            personData.add(new Person("Anna", "Best"));
            personData.add(new Person("Stefan", "Meier"));
            personData.add(new Person("Martin", "Mueller"));

            //Guardo a la BD les persones afegides hardcoded
            personData.stream().forEach(person -> {
                try {
                    db.saveAndCopy(person);
                } catch (DAOException e) {
                    showDBError(e);
                }
            });
        }
    }

    private void showDBError(DAOException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("DB problem");
        alert.setHeaderText(e.getMessage());
        alert.setContentText("Please check your code");

        alert.showAndWait();
    }

    /**
     * Returns the data as an observable list of Persons.
     *
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public AnchorPane getPersonOverviewPane() {
        return personOverviewPane;
    }

    public PersonOverviewController getPersonOverviewController() {
        return personOverviewController;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        //Per tancar la BD al sortir de l'aplicació (https://stackoverflow.com/questions/26619566/javafx-stage-close-handler)
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            try {
                if(realData) {
                    // Save file
                    //Now we save our model data to the database
                    db.saveAll(personData);
                }
                this.db.close();
            } catch (DAOException e) {
                showDBError(e);
            }

        });

        initRootLayout();

        showPersonOverview();
    }

    private void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));

            this.personOverviewPane = (AnchorPane) loader.load();
            rootLayout.setCenter(this.personOverviewPane);

            this.personOverviewController = loader.getController();
            this.personOverviewController.setMainApp(this);
            //poc.setPersonData(this.personData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout, adapting the size at screen resolution
            java.awt.Toolkit screen = java.awt.Toolkit.getDefaultToolkit();
            Scene scene = new Scene(rootLayout,
                    screen.getScreenSize().getWidth()*2/3,
                    screen.getScreenSize().getHeight()*2/3);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DAO<Person> getDb() {
        return db;
    }
}
