package to_null_fire_aatte;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayApp extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Play.fxml"));
        primaryStage.setTitle("2048 GAME");  
        primaryStage.setScene(new Scene(parent));
        primaryStage.show(); 
    }

    public static void main(String[] args) {
        launch(PlayApp.class, args);
    }
}
