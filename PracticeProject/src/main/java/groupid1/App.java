package groupid1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.*;
import javafx.fxml.*;

import java.net.URL;


/**
 * JavaFX App
 */
public class App extends Application {

    private static UIController controller;

    @Override
    public void start(Stage stage) throws IOException {
        controller = new UIController();
        FXMLLoader loader;
        URL xmlUrl;
        Parent root;

        loader = new FXMLLoader();

        xmlUrl = getClass().getResource("/UI.FXML");
        System.out.println(getClass().getResource("/UI.FXML"));
        System.out.println(xmlUrl);
        loader.setLocation(xmlUrl);
        loader.setController(controller);
        //try {
            root = loader.load();
            stage.setScene(new Scene(root));
        //}catch (IOException e){System.err.println(e.toString()+"FOOO");};


        controller.init();
        stage.show();

/*
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        Text text= new Text("Foo");

        ObservableList<Text> os= FXCollections.observableArrayList(text);;
        ChoiceBox<Text> cb = new ChoiceBox<Text>();
        cb.setItems(os);


        var scene = new Scene(new StackPane(cb), 640, 480);
        stage.setTitle("A*");

        stage.setScene(scene);
        stage.show();
        */

    }

    public static void main(String[] args) {
        launch();
    }

}