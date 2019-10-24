package Client.Registration;

import java.io.IOException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Clients;
import model.Users;

public class Registration {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newloginfield;

    @FXML
    private Button ReadyRegistrationButton;

    @FXML
    private Button CancelButton;

    @FXML
    private PasswordField newpasswordfield;

    @FXML
    private PasswordField repeatnewpassword;

    @FXML
    private ComboBox<String> Surname;

    @FXML
    private TextField ID;

    @FXML
    void OnSurnamePressed(ActionEvent event) throws ClassNotFoundException {
        Main.client.setMessage("GetIDBySurname");
        Main.client.SendMess();
        Clients cl = new Clients();
        cl.setClientSurname(Surname.getValue());
        Main.client.setMessage(cl);
        Main.client.SendMess();
        int clN = (int) Main.client.RecvMess();
        ID.setText(String.valueOf(clN));
    }

    @FXML
    void OnCancelButtonPressed(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/Autorization/Autorization.fxml"));
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
    void OnReadyButtonPressed(ActionEvent event) throws ClassNotFoundException {
        String login = newloginfield.getText();
        String password = newpasswordfield.getText();
        String repeat = repeatnewpassword.getText();
        if(newloginfield.getLength() !=0 && newpasswordfield.getLength() !=0 && repeatnewpassword.getLength() != 0 && Surname.getValue() != null)
        {
            if (password.equals(repeat)) {
                int clientID = Integer.parseInt(ID.getText());
                Users user = new Users(login, password, clientID);
                Main.client.setMessage("Registration");
                Main.client.SendMess();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.client.setMessage(user);
                Main.client.SendMess();
                String serveranswer = (String) Main.client.RecvMess();
                if (!serveranswer.equals("error")) {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/RegSuccess.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    OnCancelButtonPressed(event);
                }
                else
                {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Client/view/Dialogs/LogExists.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    OnCancelButtonPressed(event);
                }
            }
            else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotEquals.fxml") );

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
    void initialize() throws ClassNotFoundException {
        Main.client.setMessage("ShowAllClients");
        Main.client.SendMess();
        ArrayList<Clients> mas = (ArrayList<Clients>)Main.client.RecvMess();
        for (int i=0; i<mas.size(); i++)
        Surname.getItems().addAll(mas.get(i).getClientSurname());
    }
}
