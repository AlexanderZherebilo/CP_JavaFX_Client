package Client.view.Admin.Editing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Currency;

public class EditCurrency {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField CurrencyName;

    @FXML
    private TextField RoundingStep;

    @FXML
    private TextField CurrencyRate;

    @FXML
    private Button Ready;

    @FXML
    private Button Cancel;

    @FXML
    private TextField CurrencyID;

    public String getCurrencyID() {
        return CurrencyID.getText();
    }

    public void setCurrencyID(String currencyID) {
        CurrencyID.setText(currencyID);
    }

    public String getCurrencyName() {
        return CurrencyName.getText();
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName.setText(currencyName);
    }

    public double getRoundingStep() {
        return Double.parseDouble(RoundingStep.getText());
    }

    public void setRoundingStep(double roundingStep) {
        RoundingStep.setText(String.valueOf(roundingStep));
    }

    public float getCuurencyRate() {
        return Float.parseFloat(CurrencyRate.getText());
    }

    public void setCurrencyRate(float currencyRate) {
        CurrencyRate.setText(String.valueOf(currencyRate));
    }

    @FXML
    void OnCancelPressed(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/ShowCurrency.fxml"));
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
        double roundingStep = 0;
        float cuurencyRate = 0;
        String currencyID = CurrencyID.getText();
        String currencyName = CurrencyName.getText();
        try {
            roundingStep = Double.parseDouble(RoundingStep.getText());
            cuurencyRate = Float.parseFloat(CurrencyRate.getText());
        } catch (NumberFormatException e) {
            error = true;
        }

        if (error == false && cuurencyRate > 0 && roundingStep > 0) {

            if (CurrencyID.getLength() != 0 && CurrencyName.getLength() != 0 && RoundingStep.getLength() != 0 && CurrencyRate.getLength() != 0) {
                Currency currency = new Currency(currencyID, currencyName, roundingStep, cuurencyRate);
                Main.client.setMessage("EditCurrency");
                Main.client.SendMess();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.client.setMessage(currency);
                Main.client.SendMess();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Client/view/Dialogs/Success.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

                OnCancelPressed(event);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Client/view/Dialogs/EmptyInput.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Dialogs/IncorrectInput.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        ObjectInputStream file = new ObjectInputStream(new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\editcurrency.dat"));
        Currency dlg = (Currency) file.readObject();
        file.close();
        setCurrencyID(dlg.getCurrencyID());
        setCurrencyName(dlg.getCurrencyName());
        setRoundingStep(dlg.getRoundingStep());
        setCurrencyRate(dlg.getCurrencyRate());
    }
}
