# JavaFXSceneBuilder + ObjectDB

Exemple del tutorial:

https://code.makery.ch/es/library/javafx-tutorial/

Coses que he tingut que fer i no surten al tutorial:

- Parte 1. Scene Builder:

-- Els fitxers *fxml* els he tingut que posar a la carpeta/paquet *view* però dins dels ***resources*** del projecte, no al *src/main/java* (fonts). Hem de replicar els paquets existents als fonts dins dels resources (nompaquet.view).

-- Al final de la pàgina diu que ja hauria d'anar l'aplicació, però rebo Exceptions, i no són les que comenta de que no troba els fitxers fxml.

Si ens fixem en estos fitxers fxml, si els hem creat en l'IntelliJ (si ho fem en l'Scene Builder no passa), a la primera etiqueta fan referència a una classe que els controla:

	<AnchorPane prefHeight="300.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/17" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.iesebre.provascenebuilder.controller.PersonOverviewController">
	
Si busquem el controlador dins del projecte veiem que no està creat. Se pot fer des de l'Scene Builder, a l'opció de menú View · Show Sample Controller Skeleton.
Marquem totes les opcions i llenguatge Java, i apretem el botó Save as..., guardant el fitxer al directori arrel del projecte.  
  
Evidentment, això ho repetirem per cada fitxer fxml que tinguem.  

-- Quan executo, a la consola apareixen diversos *warnings* com el següent:  
  
	Loading FXML document with JavaFX API of version 17 by JavaFX runtime of version 11.0.2

Per fer-los desaparèixer he editat l'etiqueta primera dels fitxers fxml canviant el valor de la clau xmlns dixant-la de la següent forma:

	xmlns="http://javafx.com/javafx" 


- Parte 3: Interacción con el usuario

-- A la secció ***Gestión de errores*** proposa que ens baixem un fitxer jar i el copiem al directori lib del projecte.

Com estem usant MAVEN, enlloc d'així podem fer-ho més elegantment de la següent forma:

1. Anem a l'opció de menú *File · Project structure*, categoria *Libraries*.
2. Afegim una llibreria/biblioteca *from Maven* i escrivim ***org.controlsfx*** al quadre de text.
3. Apretem a la lupa i ens mostrarà les diferents versions de la llibreria. Seleccionem la que diu al tutorial (marqueu que baixe fonts, javadoc, ..., i que ho copie al repositori local).
4. Accepteu quan pregunte si afegir-ho al Mòdul.

Més avant diu que implementem una finestra de diàleg per quan intentem borrar (botó Delete) i no hem seleccionat una persona de la taula. A mi, el codi que usa i que crida a la classe Dialogs me donava, primer error de compilació, i finalment error en temps d'execució.

El primer error el vaig arreglar modificant el fitxer *module-info.java*, que configura el projecte a nivell de mòdul de Java. En concret li hem d'afegir una línia per a que pugue accedir a les classes del JavaFX ControlsFX:

	module com.iesebre.provascenebuilder {
	    requires javafx.controls;
	    requires javafx.fxml;
	    //Per poder usar la biblioteca ControlsFX
	    requires controlsfx;
	
	    opens com.iesebre.provascenebuilder to javafx.fxml;
	    exports com.iesebre.provascenebuilder;
	    exports com.iesebre.provascenebuilder.util;
	    opens com.iesebre.provascenebuilder.util to javafx.fxml;
	}
	
El segon error el vaig resoldre canviant la classe que proposa al tutorial (*Dialogs*) per una altra que fa la mateixa funció (*Alert*). En concret vaig escriure el següent codi&#x00B9; dins del mètode que tracta les accions sobre el botó *Delete*:

	            // Nothing selected.
	//            Dialogs.create()
	//                    .title("No Selection")
	//                    .masthead("No Person Selected")
	//                    .message("Please select a person in the table.")
	//                    .showWarning();
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("No selection");
	            alert.setHeaderText("No Person selected");
	            alert.setContentText("Please select a person in the table.");
	
	            alert.showAndWait();
	

-- A la secció ***Diálogos para crear y editar contactos*** ho he volgut fer sense seguir el tutorial i enlloc de crear un nou Stage ho he fet en Scene. Podeu fer-ho com vulgueu.

Continuarà...


&#x00B9; Extret de https://o7planning.org/11529/javafx-alert-dialog#a10503338 
