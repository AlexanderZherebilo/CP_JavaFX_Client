package Client.view.Dialogs.AbstrFactory;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BadAnswerFactory extends AnswerFactory {

    public Label createLabel() {
        return new Label("Вы ввели не все данные");
    }

    public Button createButton() {
        return new Button("Принять");
    }
}
