package Client.view.Admin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ClientsCredits;
import model.Currency;

public class AdminMenu {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ClientsMenu;

    @FXML
    private Button CreditsMenu;

    @FXML
    private Button CurrencyMenu;

    @FXML
    private Button UsersMenu;

    @FXML
    private Button ExitButton;

    @FXML
    private Button ListOfClients;

    @FXML
    private Button AddClient;

    @FXML
    private Button ListOfCredits;

    @FXML
    private Button AddCredit;

    @FXML
    private Button GiveCredit;

    @FXML
    private Button EPSButton;

    @FXML
    private Button ShowCurDiagram;

    @FXML
    void OnAddClientPressed(ActionEvent event) {
        Stage stage = (Stage) AddClient.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/Adding/AddClient.fxml"));
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
    void OnAddCreditPressed(ActionEvent event) {
        Stage stage = (Stage) AddCredit.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/Adding/AddCredit.fxml"));
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
    void OnCreditsMenuPressed(ActionEvent event) {
        if (!AddCredit.isVisible())
        {
            AddCredit.setVisible(true);
            ListOfCredits.setVisible(true);
            GiveCredit.setVisible(true);
            ShowCurDiagram.setVisible(true);
        }
        else {
            AddCredit.setVisible(false);
            ListOfCredits.setVisible(false);
            GiveCredit.setVisible(false);
            ShowCurDiagram.setVisible(false);
        }
    }

    @FXML
    void OnCurrencyMenuPressed(ActionEvent event) {
        Stage stage = (Stage) CurrencyMenu.getScene().getWindow();
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
    void OnExitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
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
    void OnGiveCreditPressed(ActionEvent event) {
        Stage stage = (Stage) GiveCredit.getScene().getWindow();
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
    void OnListOfClientsPressed(ActionEvent event) {
        Stage stage = (Stage) ListOfClients.getScene().getWindow();
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
    void OnListOfCreditsPressed(ActionEvent event) {
        Stage stage = (Stage) ListOfCredits.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/Showing/ShowCredits.fxml"));
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
    void OnUsersMenuPressed(ActionEvent event) {
        Stage stage = (Stage) UsersMenu.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/ShowUsers.fxml"));
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
    void onClientsMenuPressed(ActionEvent event) {
        if (!AddClient.isVisible())
        {
            AddClient.setVisible(true);
            ListOfClients.setVisible(true);
        }
        else {
            AddClient.setVisible(false);
            ListOfClients.setVisible(false);
        }
    }

    @FXML
    void OnEPSButtonPressed(ActionEvent event) {
        Stage stage = (Stage) EPSButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/EPS.fxml"));
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
    void OnShowDgPressed(ActionEvent event) throws ClassNotFoundException, IOException {
        Main.client.setMessage("ShowAllClientsCredits");
        Main.client.SendMess();
        ArrayList<ClientsCredits> mas = (ArrayList<ClientsCredits>)Main.client.RecvMess();
        ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\clientscredits(forDG).dat"));
        file.writeObject(mas);
        file.close();
        Main.client.setMessage("ShowAllCurrency");
        Main.client.SendMess();
        ArrayList<Currency> mas2 = (ArrayList<Currency>)Main.client.RecvMess();
        ObjectOutputStream file2 = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\currency(forDG).dat"));
        file2.writeObject(mas2);
        file2.close();

        Stage stage = (Stage) EPSButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/DiagramOfCurrency.fxml"));
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
    void initialize() {

    }
}
