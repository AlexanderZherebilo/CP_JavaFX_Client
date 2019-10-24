package Client.view.Dialogs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonOk;

    @FXML
    void OnButtonOkPressed(ActionEvent event) {
        Stage stage = (Stage) ButtonOk.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }
}
