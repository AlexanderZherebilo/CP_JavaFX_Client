package Client.Autorization;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Users;

import javax.swing.*;

public class Autorization {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button RegisterButton;

    @FXML
    private TextField loginfield;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button SignUpButton;

    @FXML
    void OnRegisterButtonPressed(ActionEvent event) {
        Stage stage = (Stage) SignUpButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/Registration/Registration.fxml"));
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
    void OnSignUpButtonPressed(ActionEvent event) throws ClassNotFoundException {
        String login = loginfield.getText();
        String password = passwordfield.getText();
        if(login.length()!=0 && password.length()!=0)//Проверяем на пустоту
        {
            Users user = new Users(login, password);
            Main.client.setMessage("Login");//Устанавливаем выбранное действие(вход)
            Main.client.SendMess();//Отправка сообщения
            try {
                Thread.sleep(100);//Ничего не делаем секунду, что бы сервер успел принять сообщение и начал прослушивание дальше
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.client.setMessage(user);
            Main.client.SendMess();
            String serveranswer = (String) Main.client.RecvMess();
            if (serveranswer.equals("User"))
            {
                Stage stage = (Stage) SignUpButton.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/User/UserMenu.fxml"));
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
            else if(serveranswer.equals("Admin"))
            {
                Stage stage = (Stage) SignUpButton.getScene().getWindow();
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
            else//Несовпадение по логину\паролю
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Client/view/Dialogs/LogPasError.fxml") );

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
                passwordfield.setText("");
                loginfield.setText("");
            }
        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Dialogs/EmptyInput.fxml") );

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
    void initialize() {

    }
}
