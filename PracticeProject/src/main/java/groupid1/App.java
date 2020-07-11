package groupid1;

import Controller.Controller;
import Model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * JavaFX App
 */
public class App extends Application {

    private static UIController uiController;
    private static Model model;
    private static Controller controller;
    private static Stage _stage;

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
        root = loader.load();
        stage.setScene(new Scene(root));

        model = new Model();
        controller = new Controller(model,uiController);
        uiController.setiController(controller);
        uiController.init();
        model.setVisualisator(uiController);
        stage.setResizable(false);
        stage.setTitle("A* visualisation");

        InputStream iconStream;
        Image image;

        iconStream = getClass().getResourceAsStream("/Astern512.png");
        image = new Image(iconStream);
        stage.getIcons().add(image);
        iconStream = getClass().getResourceAsStream("/Astern256.png");
        image = new Image(iconStream);
        stage.getIcons().add(image);
        iconStream = getClass().getResourceAsStream("/Astern128.png");
        image = new Image(iconStream);
        stage.getIcons().add(image);
        iconStream = getClass().getResourceAsStream("/Astern64.png");
        image = new Image(iconStream);
        stage.getIcons().add(image);


        DialogController dialogController = new DialogController(this);
        _stage = stage;
        //stage.show();
    }

    public void newField(int width, int height, UIController.FieldType fieldType) {
        _stage.show();
        uiController.newField(width, height, fieldType);
    }

    public static void main(String[] args) {
        launch();
    }

}