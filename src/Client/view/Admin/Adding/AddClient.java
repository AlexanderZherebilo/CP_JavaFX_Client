package Client.view.Admin.Adding;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.sql.Date;
import java.util.ResourceBundle;

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

public class AddClient {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ClientID;

    @FXML
    private TextField Surname;

    @FXML
    private TextField Name;

    @FXML
    private TextField Lastname;

    @FXML
    private DatePicker Birthday;

    @FXML
    private TextField City;

    @FXML
    private TextField Street;

    @FXML
    private TextField House;

    @FXML
    private TextField Flat;

    @FXML
    private TextField Salary;

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
        int clientID = 0, house = 0, flat = 0;
        float salary = 0;
        try {
            clientID = Integer.parseInt(ClientID.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        String surname = Surname.getText();
        String name = Name.getText();
        String lastname = Lastname.getText();
        Date birthday = Date.valueOf(Birthday.getValue());
        String city = City.getText();
        String street = Street.getText();
        try {
            house = Integer.parseInt(House.getText());
            flat = Integer.parseInt(Flat.getText());
            salary = Float.parseFloat(Salary.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        String currency = Currency.getValue();
        if (error == false && house > 0 && flat > 0 && salary > 0) {
            if (ClientID.getLength() != 0 && Surname.getLength() != 0 && Name.getLength() != 0 && Lastname.getLength() != 0
                    && City.getLength() != 0 && Street.getLength() != 0 && House.getLength() != 0 && Flat.getLength() != 0 && Salary.getLength() != 0) {
                Clients client = new Clients(clientID, surname, name, lastname, birthday, city, street, house, flat, salary, currency);
                Main.client.setMessage("AddOneClient");
                Main.client.SendMess();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.client.setMessage(client);
                Main.client.SendMess();
                String serveranswer = (String) Main.client.RecvMess();
                if (!serveranswer.equals("error")) {
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
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/IDExists.fxml"));

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
        Main.client.setMessage("CurrencyFor_Add");//Устанавливаем выбранное действие(вход)
        Main.client.SendMess();//Отправка сообщения
        ArrayList<String> mas;
        mas = (ArrayList<String>)Main.client.RecvMess();
        Currency.getItems().addAll(mas);
        Currency.setValue(mas.get(0));
    }
}
