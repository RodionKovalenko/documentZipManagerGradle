package rodionFX;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import rodionFX.document_processor.DocumentListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {
    DocumentListController controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Parent root = loader.load();
        this.controller = loader.getController();

        primaryStage.getIcons().add(new Image(getClass().getResource( "password-eye.png" ).toExternalForm()));

        primaryStage.setTitle("Document ZIP Manager");
        primaryStage.setScene(new Scene(root, 900, 800));
        controller.setStageAndSetupListeners(primaryStage);

        primaryStage.show();

        primaryStage.setMaxHeight(screenBounds.getHeight() - 100);
        primaryStage.centerOnScreen();
    }

    @Override
    public void stop(){
        this.controller.saveUserPreferences();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
