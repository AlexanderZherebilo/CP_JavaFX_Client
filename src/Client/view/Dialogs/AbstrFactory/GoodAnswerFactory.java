package Client.view.Dialogs.AbstrFactory;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GoodAnswerFactory extends AnswerFactory {

    public Label createLabel() {
        return new Label("Успешно");
    }

    public Button createButton() {
        return new Button("Ок");
    }
}
