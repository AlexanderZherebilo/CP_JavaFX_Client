package Client.view.Admin.Editing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

public class EditCredit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TextField CreditID;

    public int getCreditID() {
        return Integer.parseInt(CreditID.getText());
    }

    public void setCreditID(int creditID) {
        CreditID.setText(String.valueOf(creditID));
    }

    public String getCreditName() {
        return CreditName.getText();
    }

    public void setCreditName(String creditName) {
        CreditName.setText(creditName);
    }

    public int getMaxSumm() {
        return Integer.parseInt(MaxSumm.getText());
    }

    public void setMaxSumm(int maxSumm) {
        MaxSumm.setText(String.valueOf(maxSumm));
    }

    public String getCurrency() {
        return Currency.getValue();
    }

    public void setCurrency(String currency) {
        Currency.setValue(currency);
    }

    public float getNominalRate() {
        return Float.parseFloat(NominalRate.getText());
    }

    public void setNominalRate(float nominalRate) {
        NominalRate.setText(String.valueOf(nominalRate));
    }


    @FXML
    void OnCancelPressed(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/Showing/ShowCredits.fxml"));
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
        int creditID = 0, maxSumm = 0;
        float nominalRate = 0;
        try {
            creditID = Integer.parseInt(CreditID.getText());
            maxSumm = Integer.parseInt(MaxSumm.getText());
            nominalRate = Float.parseFloat(NominalRate.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        String creditName = CreditName.getText();
        String currency = Currency.getValue();

        if (error == false && (nominalRate > 0 && nominalRate < 100) && maxSumm > 0) {

            if (CreditID.getLength() != 0 && CreditName.getLength() != 0 && MaxSumm.getLength() != 0 && NominalRate.getLength() != 0) {
                Credits credit = new Credits(creditID, creditName, maxSumm, currency, nominalRate);
                Main.client.setMessage("EditCredit");
                Main.client.SendMess();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.client.setMessage(credit);
                Main.client.SendMess();
                String serveranswer = (String) Main.client.RecvMess();
                if (!serveranswer.equals("error")) {
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
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotEditCredit.fxml"));

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
    void initialize() throws ClassNotFoundException, IOException {
        Main.client.setMessage("CurrencyFor_Add");//Устанавливаем выбранное действие(вход)
        Main.client.SendMess();//Отправка сообщения
        ArrayList<String> mas;
        mas = (ArrayList<String>)Main.client.RecvMess();
        Currency.getItems().addAll(mas);

        ObjectInputStream file = new ObjectInputStream(new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\editcredit.dat"));
        Credits dlg = (Credits) file.readObject();
        file.close();
        setCreditID(dlg.getCreditID());
        setCreditName(dlg.getCreditName());
        setMaxSumm(dlg.getCreditMaxSumm());
        setCurrency(dlg.getCurrencyID());
        setNominalRate(dlg.getCreditNominalRate());
    }
}
