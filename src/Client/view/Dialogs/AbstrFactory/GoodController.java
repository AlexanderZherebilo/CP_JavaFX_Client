package Client.view.Dialogs.AbstrFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GoodController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonOk;

    @FXML
    private Label label;

    @FXML
    private AnchorPane Pane;

    GoodAnswerFactory answ = new GoodAnswerFactory();
    Button Ok = answ.createButton();
    Label answerLabel = answ.createLabel();

    @FXML
    void OnButtonOkPressed(ActionEvent event) {
        Stage stage = (Stage) ButtonOk.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }
}
