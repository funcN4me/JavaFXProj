package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static Controller myControllerHandle;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sceneDiary.fxml"));
        Parent root = loader.load();
        myControllerHandle = (Controller)loader.getController();

        Scene scene = new Scene(root, 1047, 627);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello, dear!");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
