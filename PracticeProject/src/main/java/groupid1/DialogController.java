package groupid1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DialogController {
    public UIController uiController;
    @FXML
    public TextField textWidth;
    @FXML
    public TextField textHeight;
    @FXML
    public RadioButton radioBlank;
    @FXML
    public RadioButton radioFilled;
    @FXML
    public RadioButton radioRandom;
    @FXML
    public RadioButton radioLabyrinth;
    @FXML
    public Button buttonOk;

    public DialogController(UIController uiController1) {
        uiController = uiController1;
        Parent root;
        URL xmlUrl;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        try {
            xmlUrl = getClass().getResource("/NewDialog.fxml");
            loader.setLocation(xmlUrl);
            loader.setController(this);
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void OnOkClicked(ActionEvent event) {
        int width = Integer.parseInt(textWidth.getCharacters().toString());
        int height = Integer.parseInt(textHeight.getCharacters().toString());
        UIController.FieldType fieldType;
        if (radioBlank.isSelected()) {
            fieldType = UIController.FieldType.BLANK;
        } else if (radioFilled.isSelected()) {
            fieldType = UIController.FieldType.FILLED;
        } else if (radioLabyrinth.isSelected()) {
            fieldType = UIController.FieldType.LABYRINTH;
        } else if (radioRandom.isSelected()) {
            fieldType = UIController.FieldType.RANDOM;
        } else {
            fieldType = UIController.FieldType.BLANK;
        }
        uiController.newField(width, height, fieldType);
        // Hide this current window (if this is what you want)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
