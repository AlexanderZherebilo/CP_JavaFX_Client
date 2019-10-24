package Client.view.Admin.Showing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.Date;

import Client.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Clients;
import model.ClientsCredits;

public class ShowGivenCredits {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private ObservableList<model.ClientsCredits> ClientCreditData = FXCollections.observableArrayList();

    @FXML
    private TableView<ClientsCredits> ClientsCredits;

    @FXML
    private TableColumn<ClientsCredits, Integer> ClientID;

    @FXML
    private TableColumn<ClientsCredits, Integer> CreditID;

    @FXML
    private TableColumn<ClientsCredits, String> ClientSurname;

    @FXML
    private TableColumn<ClientsCredits, String> CreditName;

    @FXML
    private TableColumn<ClientsCredits, Float> NominalRate;

    @FXML
    private TableColumn<ClientsCredits, Date> CreditGiven;

    @FXML
    private TableColumn<ClientsCredits, Date> CreditReturned;

    @FXML
    private TableColumn<ClientsCredits, Integer> Frequency;

    @FXML
    private TableColumn<ClientsCredits, Integer> CreditSumm;

    @FXML
    private TableColumn<ClientsCredits, Float> EffectiveRate;

    @FXML
    private TableColumn<ClientsCredits, Float> TotalPayments;

    @FXML
    private TableColumn<ClientsCredits, Float> PiecePayments;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button GiveButton;

    @FXML
    private Button CloseButton;

    @FXML
    private Button SaveReport;

    @FXML
    void OnCloseButtonPressed(ActionEvent event) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
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
    void OnDeleteButtonPressed(ActionEvent event) throws ClassNotFoundException {
        ClientsCredits clientID = ClientsCredits.getSelectionModel().getSelectedItem();
        if (clientID != null){
            int selInd = ClientsCredits.getSelectionModel().getSelectedIndex();
            ClientsCredits.getItems().remove(selInd); // удаление только что выбранной строки из таблицы
            Main.client.setMessage("ReturnCredit");
            Main.client.SendMess();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.client.setMessage(clientID);
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
            }
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotChosen.fxml"));

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
    void OnGiveButtonPressed(ActionEvent event) {
        Stage stage = (Stage) GiveButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/GiveCredit.fxml"));
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
    void OnSaveReportPressed(ActionEvent event) throws IOException {
        java.util.Date date = new java.util.Date();
        String filename = "Выданные кредиты - " + date.toString();
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\reports\\" + filename.replace(':', '-') + ".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(String.format("%-20s%n", "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
        output.write(String.format("|%-10s | %-10s | %-15s | %-20s | %-35s | %-13s | %-13s | %-25s | %-15s | %-35s | %-20s | %-21s |%n", "ID клиента",
                "ID кредита", "Фамилия клиента", "Название кредита", "Номинальная процентная ставка(%)", "Дата выдачи", "Срок выплаты", "Частота выплат (в год)",
                "Сумма кредита", "Эффективная процентная ставка(%)", "Общая сумма выплат", "Размер разовых выплат"));
        output.write(String.format("%-20s%n", "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
        for (int i=0; i<ClientCreditData.size(); i++) {
            output.write(String.format("|%-10s | %-10s | %-15s | %-20s | %-35s | %-13s | %-13s | %-25s | %-15s | %-35s | %-20s | %-21s |%n", ClientCreditData.get(i).getClientID(), ClientCreditData.get(i).getCreditID(),
                    ClientCreditData.get(i).getClientSurname(), ClientCreditData.get(i).getCreditName(), ClientCreditData.get(i).getCreditNominalRate(), ClientCreditData.get(i).getCreditGiveDate(),
                    ClientCreditData.get(i).getCreditReturnDate(), ClientCreditData.get(i).getCreditFrequency(), ClientCreditData.get(i).getCreditSumm(),
                    ClientCreditData.get(i).getCreditEffectiveRate(), ClientCreditData.get(i).getCreditTotalPayments(), ClientCreditData.get(i).getCreditPeicePayment()));
            output.write(String.format("%-20s%n", "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
        }
        output.close();

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
    }

    @FXML
    void initialize() throws ClassNotFoundException {

        Main.client.setMessage("ShowAllClientsCredits");
        Main.client.SendMess();
        ArrayList<ClientsCredits> mas = (ArrayList<ClientsCredits>)Main.client.RecvMess();

        for (int i=0; i< mas.size(); i++)
            ClientCreditData.add(mas.get(i));

        ClientID.setCellValueFactory(new PropertyValueFactory<ClientsCredits, Integer>("ClientID"));
        CreditID.setCellValueFactory(new PropertyValueFactory<ClientsCredits, Integer>("CreditID"));
        ClientSurname.setCellValueFactory(new PropertyValueFactory<ClientsCredits, String>("ClientSurname"));
        CreditName.setCellValueFactory(new PropertyValueFactory<ClientsCredits, String>("CreditName"));
        NominalRate.setCellValueFactory(new PropertyValueFactory<ClientsCredits, Float>("CreditNominalRate"));
        CreditGiven.setCellValueFactory(new PropertyValueFactory<ClientsCredits, Date>("CreditGiveDate"));
        CreditReturned.setCellValueFactory(new PropertyValueFactory<ClientsCredits, Date>("CreditReturnDate"));
        Frequency.setCellValueFactory(new PropertyValueFactory<model.ClientsCredits, Integer>("CreditFrequency"));
        CreditSumm.setCellValueFactory(new PropertyValueFactory<model.ClientsCredits, Integer>("CreditSumm"));
        EffectiveRate.setCellValueFactory(new PropertyValueFactory<model.ClientsCredits, Float>("CreditEffectiveRate"));
        TotalPayments.setCellValueFactory(new PropertyValueFactory<model.ClientsCredits, Float>("CreditTotalPayments"));
        PiecePayments.setCellValueFactory(new PropertyValueFactory<model.ClientsCredits, Float>("CreditPeicePayment"));

        ClientsCredits.setItems(ClientCreditData);
    }
}
