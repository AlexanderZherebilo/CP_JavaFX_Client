package Client.view.Admin.Adding;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Credits;

public class AddCredit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField CreditID;

    @FXML
    private TextField CreditName;

    @FXML
    private TextField MaxSumm;

    @FXML
    private TextField NominalRate;

    @FXML
    private Button Ready;

    @FXML
    private ComboBox<String> Currency;

    @FXML
    private Button Cancel;

    @FXML
    void OnCancelPressed(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/AdminMenu.fxml"));
        Parent root1 = null;
        try {
            root1=(Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    void OnReadyPressed(ActionEvent event) throws ClassNotFoundException {
        boolean error = false;
        int creditID = 0, creditMaxSumm = 0;
        float creditNominalRate = 0;
        try {
            creditID = Integer.parseInt(CreditID.getText());
            creditMaxSumm = Integer.parseInt(MaxSumm.getText());
            creditNominalRate = Float.parseFloat(NominalRate.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        String creditName = CreditName.getText();
        String currency = Currency.getValue();

        if (error == false && (creditNominalRate > 0 && creditNominalRate < 100) && creditMaxSumm > 0) {
            if (CreditID.getLength() != 0 && CreditName.getLength() != 0 && MaxSumm.getLength() != 0 && NominalRate.getLength() != 0) {
                Credits credit = new Credits(creditID, creditName, creditMaxSumm, currency, creditNominalRate);
                Main.client.setMessage("AddOneCredit");
                Main.client.SendMess();
                try {
                    Thread.sleep(100);//Ничего не делаем секунду, что бы сервер успел принять сообщение и начал прослушивание дальше
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.client.setMessage(credit);
                Main.client.SendMess();
                String serveranswer = (String) Main.client.RecvMess();
                if (!serveranswer.equals("error")) {
                    // выводим уведомление об успешной регистрации пользователя
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/Success.fxml"));

                    try {
                        loader.load();//Загружаем это окно
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();//Указываем путь к нужному файлу
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    OnCancelPressed(event);
                } else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/IDExists.fxml"));

                    try {
                        loader.load();//Загружаем это окно
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();//Указываем путь к нужному файлу
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    OnCancelPressed(event);
                }
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Client/view/Dialogs/EmptyInput.fxml"));

                try {
                    loader.load();//Загружаем это окно
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();//Указываем пуь к нужному файлу
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Dialogs/IncorrectInput.fxml"));

            try {
                loader.load();//Загружаем это окно
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();//Указываем пуь к нужному файлу
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }

    @FXML
    void initialize() throws ClassNotFoundException {
        Main.client.setMessage("CurrencyFor_Add");
        Main.client.SendMess();
        ArrayList<String> mas;
        mas = (ArrayList<String>)Main.client.RecvMess();
        Currency.getItems().addAll(mas);
        Currency.setValue(mas.get(0));
    }
}
