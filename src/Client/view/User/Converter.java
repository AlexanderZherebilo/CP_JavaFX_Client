package Client.view.User;

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
import model.Currency;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Converter {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ReadyButton;

    @FXML
    private ComboBox<String> CurrencyOne;

    @FXML
    private ComboBox<String> CurrencyTwo;

    @FXML
    private TextField Data;

    @FXML
    private TextField Result;

    @FXML
    private Button GetConvert;

    @FXML
    void OnGetConvertPressed(ActionEvent event) throws ClassNotFoundException {
        boolean error = false;
        float data = 0;
        try {
            data = Float.parseFloat(Data.getText());
        } catch (NumberFormatException e)
        {
            error = true;
        }
        if (error == false && data > 0) {
            if (Data.getLength() != 0) {
                Main.client.setMessage("ShowAllCurrency");
                Main.client.SendMess();
                ArrayList<Currency> mas = (ArrayList<Currency>) Main.client.RecvMess();

                float byr = 0, euro = 0, rub = 0, usd = 0;
                for (int i = 0; i < mas.size(); i++) {
                    if (mas.get(i).getCurrencyID().equals("BYN"))
                        byr = mas.get(i).getCurrencyRate();
                    else if (mas.get(i).getCurrencyID().equals("EUR"))
                        euro = mas.get(i).getCurrencyRate();
                    else if (mas.get(i).getCurrencyID().equals("RUB"))
                        rub = mas.get(i).getCurrencyRate();
                    else if (mas.get(i).getCurrencyID().equals("USD"))
                        usd = mas.get(i).getCurrencyRate();
                }

                String Name1 = CurrencyOne.getValue();
                String Name2 = CurrencyTwo.getValue();
                float result = 0;
                if (Name1.equals("BYN")) {
                    if (Name2.equals("BYN"))
                        result = (data * byr) / byr;
                    else if (Name2.equals("EUR"))
                        result = (data * byr) / euro;
                    else if (Name2.equals("USD"))
                        result = (data * byr) / usd;
                    else if (Name2.equals("RUB"))
                        result = (data * byr) / rub;
                } else if (Name1.equals("EUR")) {
                    if (Name2.equals("BYN"))
                        result = (data * euro) / byr;
                    else if (Name2.equals("EUR"))
                        result = (data * euro) / euro;
                    else if (Name2.equals("USD"))
                        result = (data * euro) / usd;
                    else if (Name2.equals("RUB"))
                        result = (data * euro) / rub;
                } else if (Name1.equals("RUB")) {
                    if (Name2.equals("BYN"))
                        result = (data * rub) / byr;
                    else if (Name2.equals("EUR"))
                        result = (data * rub) / euro;
                    else if (Name2.equals("USD"))
                        result = (data * rub) / usd;
                    else if (Name2.equals("RUB"))
                        result = (data * rub) / rub;
                } else if (Name1.equals("USD")) {
                    if (Name2.equals("BYN"))
                        result = (data * usd) / byr;
                    else if (Name2.equals("EUR"))
                        result = (data * usd) / euro;
                    else if (Name2.equals("USD"))
                        result = (data * usd) / usd;
                    else if (Name2.equals("RUB"))
                        result = (data * usd) / rub;
                }
                Result.setText(String.valueOf(result));
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
    void OnReadyButtonPressed(ActionEvent event) {
        Stage stage = (Stage) ReadyButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/User/ShowCurrency.fxml"));
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
    void initialize() throws ClassNotFoundException {
        Main.client.setMessage("CurrencyFor_Add");
        Main.client.SendMess();
        ArrayList<String> cur;
        cur = (ArrayList<String>)Main.client.RecvMess();
        CurrencyOne.getItems().addAll(cur);
        CurrencyOne.setValue(cur.get(0));
        CurrencyTwo.getItems().addAll(cur);
        CurrencyTwo.setValue(cur.get(1));
    }
}
