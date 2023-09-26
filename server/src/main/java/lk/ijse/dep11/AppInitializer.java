package lk.ijse.dep11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/ServerView.fxml"));
        Scene MainScene = new Scene(root);
        primaryStage.setScene(MainScene);
        primaryStage.setTitle("Client App");
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
