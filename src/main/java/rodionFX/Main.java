package rodionFX;

import rodionFX.document_processor.DocumentListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    DocumentListController controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();

        primaryStage.setTitle("Document ZIP Manager");
        primaryStage.setScene(new Scene(root, 900, 800));
        controller.setStageAndSetupListeners(primaryStage);

        primaryStage.show();
    }

    @Override
    public void stop(){
        this.controller.saveUserPreferences();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
