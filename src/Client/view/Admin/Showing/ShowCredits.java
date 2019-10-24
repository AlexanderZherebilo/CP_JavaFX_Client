package Client.view.Admin.Showing;

import java.io.*;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Credits;

public class ShowCredits {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private ObservableList<Credits> CreditData = FXCollections.observableArrayList();

    @FXML
    private TableView<model.Credits> Credits;

    @FXML
    private TableColumn<Credits, Integer> CreditID;

    @FXML
    private TableColumn<Credits, String> CreditName;

    @FXML
    private TableColumn<Credits, Integer> CreditMaxSumm;

    @FXML
    private TableColumn<Credits, String> CreditCurrencyID;

    @FXML
    private TableColumn<Credits, Float> CreditNominalRate;

    @FXML
    private Button EditButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button CloseButton;

    @FXML
    private ComboBox<String> Criteries;

    @FXML
    private TextField min;

    @FXML
    private Button SearchButton;

    @FXML
    private TextField max;

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
        stage.show();
    }

    @FXML
    void OnDeleteButtonPressed(ActionEvent event) throws ClassNotFoundException {
        Credits creditID = Credits.getSelectionModel().getSelectedItem();
        if (creditID != null) {
            Main.client.setMessage("DeleteOneCredit");
            Main.client.SendMess();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.client.setMessage(creditID);
            Main.client.SendMess();
            String serveranswer = (String) Main.client.RecvMess();
            if (serveranswer.equals("good")) {
                int selInd = Credits.getSelectionModel().getSelectedIndex();
                Credits.getItems().remove(selInd); // удаление только что выбранной строки из таблицы
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
            } else if (serveranswer.equals("cannotdeletecredit")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Client/view/Dialogs/NotDelCredit.fxml"));

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
    void OnEditButtonPressed(ActionEvent event) throws IOException {
        if (Credits.getSelectionModel().getSelectedItem() != null) {
            Credits dlg = new Credits();
            dlg.setCreditID(Credits.getSelectionModel().getSelectedItem().getCreditID());
            dlg.setCreditName(Credits.getSelectionModel().getSelectedItem().getCreditName());
            dlg.setCreditMaxSumm(Credits.getSelectionModel().getSelectedItem().getCreditMaxSumm());
            dlg.setCurrencyID(Credits.getSelectionModel().getSelectedItem().getCurrencyID());
            dlg.setCreditNominalRate(Credits.getSelectionModel().getSelectedItem().getCreditNominalRate());
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\editcredit.dat"));
            file.writeObject(dlg);
            file.close();

            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Admin/Editing/EditCredit.fxml"));
            try {
                loader.load();//Загружаем это окно
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();//Указываем путь к нужному файлу
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
        int m1=0, m2=0;
        boolean error = false;
        try {
            m1 = Integer.parseInt(min.getText());
            m2 = Integer.parseInt(max.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        if (error == false && m1 > 0 && m2 > 0) {
            if (min.getLength() != 0 && max.getLength() != 0) {
                String krit = Criteries.getSelectionModel().getSelectedItem();
                if (krit.equals("Максимальная сумма")) {
                    Main.client.setMessage("SearchCreditBySumm");
                    Main.client.SendMess();
                    int minSumm = Integer.parseInt(min.getText());
                    int maxSumm = Integer.parseInt(max.getText());
                    String curr = Currency.getValue();
                    Main.client.setMessage(minSumm);
                    Main.client.SendMess(); //отправляем минимальное значение
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.client.setMessage(maxSumm);
                    Main.client.SendMess(); //отправляем максимальное значение
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.client.setMessage(curr);
                    Main.client.SendMess(); //отправляем значение валюты
                } else if (krit.equals("Номинальная процентная ставка")) {
                    Main.client.setMessage("SearchCreditByRate");
                    Main.client.SendMess();
                    int minRate = Integer.parseInt(min.getText());
                    int maxRate = Integer.parseInt(max.getText());
                    String curr = Currency.getValue();
                    Main.client.setMessage(minRate);
                    Main.client.SendMess(); //отправляем минимальное значение
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.client.setMessage(maxRate);
                    Main.client.SendMess(); //отправляем максимальное значение
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.client.setMessage(curr);
                    Main.client.SendMess(); //отправляем значение валюты
                }
                int s = 0;
                ArrayList<Credits> answer = (ArrayList<Credits>) Main.client.RecvMess();
                s = answer.size();
                if (s > 0) {
                    Credits.getItems().removeAll(CreditData);
                    for (int i = 0; i < answer.size(); i++)
                        CreditData.add(answer.get(i));
                    Credits.setItems(CreditData);
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
        String filename = "Кредиты - " + date.toString();
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\reports\\" + filename.replace(':', '-') + ".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(String.format("%-20s%n", "------------------------------------------------------------------------------------------------------------"));
        output.write(String.format("|%-10s | %-25s | %-20s | %-10s | %-25s|%n", "ID кредита", "Название кредита", "Максимальная сумма", "ID валюты", "Номинальная процентная ставка"));
        output.write(String.format("%-20s%n", "------------------------------------------------------------------------------------------------------------"));
        for (int i=0; i<CreditData.size(); i++) {
            output.write(String.format("|%-10s | %-25s | %-20s | %-10s | %-29s|%n", CreditData.get(i).getCreditID(), CreditData.get(i).getCreditName(),
                    CreditData.get(i).getCreditMaxSumm(), CreditData.get(i).getCurrencyID(), CreditData.get(i).getCreditNominalRate()));
            output.write(String.format("%-20s%n", "------------------------------------------------------------------------------------------------------------"));
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

        Main.client.setMessage("ShowAllCredits");
        Main.client.SendMess();
        ArrayList<Credits> mas = (ArrayList<Credits>)Main.client.RecvMess();

        for (int i=0; i< mas.size(); i++)
            CreditData.add(mas.get(i));

        CreditID.setCellValueFactory(new PropertyValueFactory<model.Credits, Integer>("CreditID"));
        CreditName.setCellValueFactory(new PropertyValueFactory<model.Credits, String>("CreditName"));
        CreditMaxSumm.setCellValueFactory(new PropertyValueFactory<model.Credits, Integer>("CreditMaxSumm"));
        CreditCurrencyID.setCellValueFactory(new PropertyValueFactory<model.Credits, String>("CurrencyID"));
        CreditNominalRate.setCellValueFactory(new PropertyValueFactory<model.Credits, Float>("CreditNominalRate"));

        Credits.setItems(CreditData);

        ArrayList<String> crit = new ArrayList<String>();
        crit.add("Максимальная сумма");
        crit.add("Номинальная процентная ставка");
        Criteries.getItems().addAll(crit);
        Criteries.setValue("Максимальная сумма");

        Main.client.setMessage("CurrencyFor_Add");
        Main.client.SendMess();
        ArrayList<String> cur;
        cur = (ArrayList<String>)Main.client.RecvMess();
        Currency.getItems().addAll(cur);
        Currency.setValue(cur.get(0));
    }
}
