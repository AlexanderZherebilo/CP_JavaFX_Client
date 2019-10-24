package Client.view.Admin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.Date;

import Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Clients;
import model.ClientsCredits;
import model.Credits;

public class GiveCredit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Frequency;

    @FXML
    private TextField CreditSumm;

    @FXML
    private DatePicker GivenCredit;

    @FXML
    private DatePicker ReturnedCredit;

    @FXML
    private Button Ready;

    @FXML
    private ComboBox<String> CreditName;

    @FXML
    private Button Cancel;

    @FXML
    private ComboBox<String> ClientSurname;

    @FXML
    private TextField ClientID;

    @FXML
    private TextField NominalRate;

    @FXML
    private TextField CreditID;

    @FXML
    void OnClientSurnameChosen(ActionEvent event) throws ClassNotFoundException {
        Main.client.setMessage("GetIDBySurname");
        Main.client.SendMess();
        Clients cl = new Clients();
        cl.setClientSurname(ClientSurname.getValue());
        Main.client.setMessage(cl);
        Main.client.SendMess();
        int sur = (int)Main.client.RecvMess();
        ClientID.setText(String.valueOf(sur));
    }

    @FXML
    void OnCreditNameChosen(ActionEvent event) throws ClassNotFoundException {
        Main.client.setMessage("GetIDByCredit");
        Main.client.SendMess();
        Credits cr = new Credits();
        cr.setCreditName(CreditName.getValue());
        Main.client.setMessage(cr);
        Main.client.SendMess();
        Credits crN = (Credits) Main.client.RecvMess();
        CreditID.setText(String.valueOf(crN.getCreditID()));
        NominalRate.setText(String.valueOf(crN.getCreditNominalRate()));
    }

    @FXML
    void OnCancelPressed(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/Showing/ShowGivenCredits.fxml"));
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
        int clientID = Integer.parseInt(ClientID.getText());
        String surname = ClientSurname.getValue();
        int creditID = Integer.parseInt(CreditID.getText());
        String creditName = CreditName.getValue();
        float creditNominalRate = Float.parseFloat(NominalRate.getText());
        Date giveCredit = Date.valueOf(GivenCredit.getValue());
        Date returnCredit = Date.valueOf(ReturnedCredit.getValue());
        boolean error = false;
        int frequency = 0, creditSumm = 0;
        try {
            frequency = Integer.parseInt(Frequency.getText());
            creditSumm = Integer.parseInt(CreditSumm.getText());
        } catch (NumberFormatException e) {
            error = true;
        }

        if (error == false && frequency > 0 && creditSumm > 0) {

            if (Frequency.getLength() != 0 && CreditSumm.getLength() != 0) {
                ClientsCredits clientcredit = new ClientsCredits(clientID, creditID, surname, creditName, creditNominalRate, giveCredit, returnCredit, frequency, creditSumm);
                Main.client.setMessage("GiveCredit");
                Main.client.SendMess();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.client.setMessage(clientcredit);
                Main.client.SendMess();
                String serveranswer = (String) Main.client.RecvMess();
                if (serveranswer.equals("good")) {
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
                } else if (serveranswer.equals("hasCredit")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/hasCredit.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                } else if (serveranswer.equals("TooMuch")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/TooMuch.fxml"));

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
    void initialize() throws ClassNotFoundException {
        Main.client.setMessage("Clients_CurrencyFor_Add");
        Main.client.SendMess();
        ArrayList<String> mas1, mas2;
        mas1 = (ArrayList<String>)Main.client.RecvMess();
        ClientSurname.getItems().addAll(mas1);
        mas2 = (ArrayList<String>)Main.client.RecvMess();
        CreditName.getItems().addAll(mas2);
    }
}
