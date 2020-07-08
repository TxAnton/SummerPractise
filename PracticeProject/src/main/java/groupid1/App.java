package groupid1;

import Controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.*;
import javafx.fxml.*;

import java.net.URL;

import Model.*;


/**
 * JavaFX App
 */
public class App extends Application {

    private static UIController uiController;
    private static Model model;
    private static Controller controller;


    @Override
    public void start(Stage stage) throws IOException {
        uiController = new UIController();
        FXMLLoader loader;
        URL xmlUrl;
        Parent root;

        loader = new FXMLLoader();

        xmlUrl = getClass().getResource("/UI.FXML");
        System.out.println(getClass().getResource("/UI.FXML"));
        System.out.println(xmlUrl);
        loader.setLocation(xmlUrl);
        loader.setController(uiController);
        //try {
            root = loader.load();
            stage.setScene(new Scene(root));
        //}catch (IOException e){System.err.println(e.toString()+"FOOO");};



        model = new Model();
        controller = new Controller(model,uiController);
        uiController.setiController(controller);
        uiController.init();
        model.setVisualisator(uiController);
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