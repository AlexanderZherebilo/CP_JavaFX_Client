package Client.view.Admin.Editing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Clients;

public class EditClient {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Surname;

    @FXML
    private TextField Name;

    @FXML
    private TextField Lastname;

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
    private TextField ClientID;

    @FXML
    private DatePicker Birthday;

    @FXML
    void OnCancelPressed(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/Showing/ShowClients.fxml"));
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
        clientID = Integer.parseInt(ClientID.getText());
        try {
            clientID = Integer.parseInt(ClientID.getText());
            house = Integer.parseInt(House.getText());
            flat = Integer.parseInt(Flat.getText());
            salary = Float.parseFloat(Salary.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        String surname = Surname.getText();
        String name = Name.getText();
        String lastname = Lastname.getText();
        Date birthday = Date.valueOf(Birthday.getValue());
        String city = City.getText();
        String street = Street.getText();
        String currency = Currency.getValue();

        if (error == false && house > 0 && flat > 0 && salary > 0) {

            if (ClientID.getLength() != 0 && Surname.getLength() != 0 && Name.getLength() != 0 && Lastname.getLength() != 0
                    && City.getLength() != 0 && Street.getLength() != 0 && House.getLength() != 0 && Flat.getLength() != 0 && Salary.getLength() != 0) {
                Clients client = new Clients(clientID, surname, name, lastname, birthday, city, street, house, flat, salary, currency);
                Main.client.setMessage("EditClient");
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
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotEditClient.fxml"));

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

    public String getSurname() {
        return Surname.getText();
    }

    public void setSurname(String surname) {
        Surname.setText(surname);
    }

    public String getName() {
        return Name.getText();
    }

    public void setName(String name) {
        Name.setText(name);
    }

    public String getLastname() {
        return Lastname.getText();
    }

    public void setLastname(String lastname) {
        Lastname.setText(lastname);
    }

    public String getCity() {
        return City.getText();
    }

    public void setCity(String city) {
        City.setText(city);
    }

    public String getStreet() {
        return Street.getText();
    }

    public void setStreet(String street) {
        Street.setText(street);
    }

    public int getHouse() {
        return Integer.parseInt(House.getText());
    }

    public void setHouse(int house) {
        House.setText(String.valueOf(house));
    }

    public int getFlat() {
        return Integer.parseInt(Flat.getText());
    }

    public void setFlat(int flat) {
        Flat.setText(String.valueOf(flat));
    }

    public float getSalary() {
        return Float.parseFloat(Salary.getText());
    }

    public void setSalary(float salary) {
        Salary.setText(String.valueOf(salary));
    }

    public String getCurrency() {
        return Currency.getValue();
    }

    public void setCurrency(String currency) {
        Currency.setValue(currency);
    }

    public int getClientID() {
        return Integer.parseInt(ClientID.getText());
    }

    public void setClientID(int clientID) {
        ClientID.setText(String.valueOf(clientID));
    }

    public Date getBirthday() {
        return Date.valueOf(Birthday.getValue());
    }

    public void setBirthday(Date birthday) {
        Birthday.setValue(birthday.toLocalDate());
    }

    @FXML
    void initialize() throws ClassNotFoundException, IOException {
        Main.client.setMessage("CurrencyFor_Add");
        Main.client.SendMess();
        ArrayList<String> mas;
        mas = (ArrayList<String>)Main.client.RecvMess();
        Currency.getItems().addAll(mas);

        ObjectInputStream file = new ObjectInputStream(new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\editclient.dat"));
        Clients dlg = (Clients) file.readObject();
        file.close();
        setClientID(dlg.getClientID());
        setSurname(dlg.getClientSurname());
        setName(dlg.getClientName());
        setLastname(dlg.getClientLastname());
        setBirthday(dlg.getClientBirthday());
        setCity(dlg.getClientCity());
        setStreet(dlg.getClientStreet());
        setHouse(dlg.getClientHouse());
        setFlat(dlg.getClientFlat());
        setSalary(dlg.getClientSalary());
        setCurrency(dlg.getCurrencyID());
    }
}
