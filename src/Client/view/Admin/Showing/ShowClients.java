package Client.view.Admin.Showing;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Client.Main;
import Client.view.Admin.Editing.EditClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Clients;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Currency;

public class ShowClients {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private ObservableList<Clients> ClientsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Clients> Clients = new TableView<>(ClientsData);

    @FXML
    private TableColumn<Clients, Integer> ClientID = new TableColumn<>();

    @FXML
    private TableColumn<Clients, String> ClientSurname = new TableColumn<>();

    @FXML
    private TableColumn<Clients, String> ClientName = new TableColumn<>();

    @FXML
    private TableColumn<Clients, String> ClientLastname = new TableColumn<>();

    @FXML
    private TableColumn<Clients, Date> ClientBirthday = new TableColumn<>();

    @FXML
    private TableColumn<Clients, String> ClientCity = new TableColumn<>();

    @FXML
    private TableColumn<Clients, String> ClientStreet = new TableColumn<>();

    @FXML
    private TableColumn<Clients, Integer> ClientHouse = new TableColumn<>();

    @FXML
    private TableColumn<Clients, Integer> ClientFlat = new TableColumn<>();

    @FXML
    private TableColumn<Clients, Float> ClientSalary = new TableColumn<>();

    @FXML
    private TableColumn<Clients, String> ClientCurrencyID = new TableColumn<>();

    @FXML
    private Button EditButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button CloseButton;

    @FXML
    private ComboBox<String> Criteries;

    @FXML
    private TextField CriterieValue;

    @FXML
    private Button SearchButton;

    @FXML
    private TextField MinSalary;

    @FXML
    private ComboBox<String> Currency;

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
        stage.showAndWait();
    }

    @FXML
    void OnDeleteButtonPressed(ActionEvent event) throws ClassNotFoundException {
        Clients clientID = Clients.getSelectionModel().getSelectedItem();
        if (clientID.getClientID() != 0) {
            if (clientID != null) {
                Main.client.setMessage("DeleteOneClient");
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
                    int selInd = Clients.getSelectionModel().getSelectedIndex();
                    Clients.getItems().remove(selInd); // удаление только что выбранной строки из таблицы
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
                } else if (serveranswer.equals("cannotdeleteclient")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotDelClient.fxml"));

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
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotAdmin.fxml"));

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
    void OnEditButtonPressed(ActionEvent event) throws ClassNotFoundException, IOException {
        if (Clients.getSelectionModel().getSelectedItem() != null) {
            Clients dlg = new Clients();
            dlg.setClientID(Clients.getSelectionModel().getSelectedItem().getClientID());
            dlg.setClientSurname(Clients.getSelectionModel().getSelectedItem().getClientSurname());
            dlg.setClientName(Clients.getSelectionModel().getSelectedItem().getClientName());
            dlg.setClientLastname(Clients.getSelectionModel().getSelectedItem().getClientLastname());
            dlg.setClientBirthday(Clients.getSelectionModel().getSelectedItem().getClientBirthday());
            dlg.setClientCity(Clients.getSelectionModel().getSelectedItem().getClientCity());
            dlg.setClientStreet(Clients.getSelectionModel().getSelectedItem().getClientStreet());
            dlg.setClientHouse(Clients.getSelectionModel().getSelectedItem().getClientHouse());
            dlg.setClientFlat(Clients.getSelectionModel().getSelectedItem().getClientFlat());
            dlg.setClientSalary(Clients.getSelectionModel().getSelectedItem().getClientSalary());
            dlg.setCurrencyID(Clients.getSelectionModel().getSelectedItem().getCurrencyID());
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\editclient.dat"));
            file.writeObject(dlg);
            file.close();

            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Admin/Editing/EditClient.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
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
    void OnSearchButtonPressed(ActionEvent event) throws ClassNotFoundException {
        boolean error = false;
        float m1=0;
        try {
            m1 = Integer.parseInt(MinSalary.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        if (error == false && m1 >= 0) {
            if (CriterieValue.getLength() != 0 && MinSalary.getLength() != 0) {
                String krit = Criteries.getSelectionModel().getSelectedItem();
                if (krit.equals("Фамилия")) {
                    Main.client.setMessage("SearchClientBySurname");
                    Main.client.SendMess();
                    Clients client1 = new Clients();
                    client1.setClientSurname(CriterieValue.getText());
                    client1.setClientSalary(Float.parseFloat(MinSalary.getText()));
                    client1.setCurrencyID(Currency.getSelectionModel().getSelectedItem());
                    Main.client.setMessage(client1);
                    Main.client.SendMess();
                } else if (krit.equals("Город")) {
                    Main.client.setMessage("SearchClientByCity");
                    Main.client.SendMess();
                    Clients client2 = new Clients();
                    client2.setClientCity(CriterieValue.getText());
                    client2.setClientSalary(Float.parseFloat(MinSalary.getText()));
                    client2.setCurrencyID(Currency.getSelectionModel().getSelectedItem());
                    Main.client.setMessage(client2);
                    Main.client.SendMess();
                }
                int s = 0;
                ArrayList<Clients> answer = (ArrayList<Clients>) Main.client.RecvMess();
                s = answer.size();
                if (s > 0) {
                    Clients.getItems().removeAll(ClientsData);
                    for (int i = 0; i < answer.size(); i++)
                        ClientsData.add(answer.get(i));
                    Clients.setItems(ClientsData);
                } else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotFound.fxml"));

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
    void OnSaveReportPressed(ActionEvent event) throws IOException {
        Date date = new Date();
        String filename = "Клиенты банка - " + date.toString();
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\reports\\" + filename.replace(':', '-') + ".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(String.format("%-20s%n", "----------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
        output.write(String.format("|%-10s | %-15s | %-15s | %-15s | %-15s | %-10s | %-15s | %-5s | %-8s | %-15s | %-10s |%n", "ID клиента",
                "Фамилия клиента", "Имя", "Отчество", "Дата рождения", "Город", "Улица", "Дом", "Квартира", "Размер дохода", "Валюта"));
        output.write(String.format("%-20s%n", "----------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
        for (int i=0; i<ClientsData.size(); i++) {
            output.write(String.format("|%-10s | %-15s | %-15s | %-15s | %-15s | %-10s | %-15s | %-5s | %-8s | %-15s | %-10s |%n", ClientsData.get(i).getClientID(), ClientsData.get(i).getClientSurname(),
                    ClientsData.get(i).getClientName(), ClientsData.get(i).getClientLastname(), ClientsData.get(i).getClientBirthday(), ClientsData.get(i).getClientCity(),
                    ClientsData.get(i).getClientStreet(), ClientsData.get(i).getClientHouse(), ClientsData.get(i).getClientFlat(),
                    ClientsData.get(i).getClientSalary(), ClientsData.get(i).getCurrencyID()));
            output.write(String.format("%-20s%n", "----------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
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
        Main.client.setMessage("ShowAllClients");
        Main.client.SendMess();
        ArrayList<Clients> mas = (ArrayList<Clients>)Main.client.RecvMess();

        for (int i=0; i< mas.size(); i++)
            ClientsData.add(mas.get(i));

        ClientID.setCellValueFactory(new PropertyValueFactory<Clients, Integer>("ClientID"));
        ClientSurname.setCellValueFactory(new PropertyValueFactory<model.Clients, String>("ClientSurname"));
        ClientName.setCellValueFactory(new PropertyValueFactory<model.Clients, String>("ClientName"));
        ClientLastname.setCellValueFactory(new PropertyValueFactory<model.Clients, String>("ClientLastname"));
        ClientBirthday.setCellValueFactory(new PropertyValueFactory<model.Clients, Date>("ClientBirthday"));
        ClientCity.setCellValueFactory(new PropertyValueFactory<model.Clients, String>("ClientCity"));
        ClientStreet.setCellValueFactory(new PropertyValueFactory<model.Clients, String>("ClientStreet"));
        ClientHouse.setCellValueFactory(new PropertyValueFactory<model.Clients, Integer>("ClientHouse"));
        ClientFlat.setCellValueFactory(new PropertyValueFactory<model.Clients, Integer>("ClientFlat"));
        ClientSalary.setCellValueFactory(new PropertyValueFactory<model.Clients, Float>("ClientSalary"));
        ClientCurrencyID.setCellValueFactory(new PropertyValueFactory<model.Clients, String>("CurrencyID"));

        Clients.setItems(ClientsData);

        ArrayList<String> crit = new ArrayList<String>();
        crit.add("Фамилия");
        crit.add("Город");
        Criteries.getItems().addAll(crit);
        Criteries.setValue("Город");

        Main.client.setMessage("CurrencyFor_Add");
        Main.client.SendMess();
        ArrayList<String> cur;
        cur = (ArrayList<String>)Main.client.RecvMess();
        Currency.getItems().addAll(cur);
        Currency.setValue(cur.get(0));
    }
}
