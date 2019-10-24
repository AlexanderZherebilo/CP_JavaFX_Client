package Client.view.Admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
import model.ClientsCredits;
import model.Credits;
import model.Users;

public class ShowUsers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private ObservableList<Users> UsersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Users> table_Users;

    @FXML
    private TableColumn<Users, String> login_Column;

    @FXML
    private TableColumn<Users, String> password_Column;

    @FXML
    private TableColumn<Users, String> Role_Cloumn;

    @FXML
    private TableColumn<Users, Integer> ClientID;

    @FXML
    private Button Close;

    @FXML
    private Button DeleteUser;

    @FXML
    private Button UsersReport;

    @FXML
    void OnClosePressed(ActionEvent event) {
        Stage stage = (Stage) Close.getScene().getWindow();
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
    void OnDeleteUserPressed(ActionEvent event) throws ClassNotFoundException {
        Users user = table_Users.getSelectionModel().getSelectedItem();
        if (user != null) {
            if (!user.getRole().equals("admin")) {
                int selInd = table_Users.getSelectionModel().getSelectedIndex();
                table_Users.getItems().remove(selInd); // удаление только что выбранной строки из таблицы
                Main.client.setMessage("DeleteUser");
                Main.client.SendMess();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.client.setMessage(user);
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
    void OnUsersReportPressed(ActionEvent event) throws IOException {
        Date date = new Date();
        String filename = "Зарегистрированные пользователи - " + date.toString();
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\reports\\" + filename.replace(':', '-') + ".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(String.format("%-20s%n", "-------------------------------------------------------------------------------------------"));
        output.write(String.format("|%-20s | %-20s | %-20s| %-20s |%n", "Логин", "Пароль", "Роль", "ID клиента"));
        output.write(String.format("%-20s%n", "-------------------------------------------------------------------------------------------"));
        for (int i=0; i<UsersData.size(); i++) {
            output.write(String.format("|%-20s | %-20s | %-20s| %-20s |%n", UsersData.get(i).getLogin(), UsersData.get(i).getPassword(),
                    UsersData.get(i).getRole(), UsersData.get(i).getClientID()));
            output.write(String.format("%-20s%n", "-------------------------------------------------------------------------------------------"));
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

        Main.client.setMessage("ShowAllUsers");
        Main.client.SendMess();
        ArrayList<Users> mas = (ArrayList<Users>)Main.client.RecvMess();

        for (int i=0; i< mas.size(); i++)
            UsersData.add(mas.get(i));

        login_Column.setCellValueFactory(new PropertyValueFactory<Users, String>("login"));
        password_Column.setCellValueFactory(new PropertyValueFactory<Users, String>("password"));
        Role_Cloumn.setCellValueFactory(new PropertyValueFactory<Users, String>("Role"));
        ClientID.setCellValueFactory(new PropertyValueFactory<Users, Integer>("ClientID"));

        table_Users.setItems(UsersData);
    }
}
