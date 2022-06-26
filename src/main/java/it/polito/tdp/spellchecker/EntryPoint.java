package it.polito.tdp.spellchecker;

import javafx.application.Application;

import it.polito.tdp.spellchecker.model.Dictionary;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	
    	Dictionary model = new Dictionary();
    	FXMLController controller;
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        controller = loader.getController();
        controller.setModel(model);
        
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        //
        //Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
