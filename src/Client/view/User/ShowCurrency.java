package Client.view.User;

import Client.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Currency;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowCurrency {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private ObservableList<Currency> CurrencyData = FXCollections.observableArrayList();

    @FXML
    private TableView<Currency> Currency = new TableView<>(CurrencyData);

    @FXML
    private TableColumn<Currency, String> CurrencyID = new TableColumn<Currency, String>();

    @FXML
    private TableColumn<Currency, String> CurrencyName = new TableColumn<Currency, String>();

    @FXML
    private TableColumn<Currency, Double> RoundingStep = new TableColumn<Currency, Double>();

    @FXML
    private TableColumn<Currency, Float> CurrencyRate = new TableColumn<Currency, Float>();

    @FXML
    private BarChart<?, ?> CurrencyGraph;

    @FXML
    private CategoryAxis OtherCurrency;

    @FXML
    private NumberAxis BYRcurrency;

    @FXML
    private Button CloseButton;

    @FXML
    private Button SaveReport;

    @FXML
    private Button Convert;

    @FXML
    void OnCloseButtonPressed(ActionEvent event) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
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

    @FXML
    void OnSaveReportPressed(ActionEvent event) throws IOException {
        Date date = new Date();
        String filename = "Курсы валют - " + date.toString();
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\reports\\" + filename.replace(':', '-') + ".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(String.format("%-20s%n", "-------------------------------------------------------------------------------------------"));
        output.write(String.format("|%-20s | %-20s | %-20s | %-20s|%n", "ID валюты", "Название валюты", "Шаг округления", "Курс валюты"));
        output.write(String.format("%-20s%n", "-------------------------------------------------------------------------------------------"));
        for (int i=0; i<CurrencyData.size(); i++) {
            output.write(String.format("|%-20s | %-20s | %-20s | %-20s|%n", CurrencyData.get(i).getCurrencyID(), CurrencyData.get(i).getCurrencyName(),
                    CurrencyData.get(i).getRoundingStep(), CurrencyData.get(i).getCurrencyRate()));
            output.write(String.format("%-20s%n", "-------------------------------------------------------------------------------------------"));
        }
            output.close();

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

    @FXML
    void OnConvertPressed(ActionEvent event) {
        Stage stage = (Stage) Convert.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/User/Converter.fxml"));
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
    void initialize() throws ClassNotFoundException {

        Main.client.setMessage("ShowAllCurrency");
        Main.client.SendMess();
        ArrayList<Currency> mas = (ArrayList<Currency>)Main.client.RecvMess();

        for (int i=0; i< mas.size(); i++)
        CurrencyData.add(mas.get(i));

        CurrencyID.setCellValueFactory(new PropertyValueFactory<model.Currency, String>("currencyID"));
        CurrencyName.setCellValueFactory(new PropertyValueFactory<model.Currency, String>("currencyName"));
        RoundingStep.setCellValueFactory(new PropertyValueFactory<model.Currency, Double>("roundingStep"));
        CurrencyRate.setCellValueFactory(new PropertyValueFactory<model.Currency, Float>("currencyRate"));

        Currency.setItems(CurrencyData);

        float euro = 0, rub = 0, usd = 0;
        for (int i = 0; i < mas.size(); i++) {
            if (mas.get(i).getCurrencyID().equals("EUR"))
                euro = mas.get(i).getCurrencyRate();
            else if (mas.get(i).getCurrencyID().equals("RUB"))
                rub = mas.get(i).getCurrencyRate() * 100;
            else if (mas.get(i).getCurrencyID().equals("USD"))
                usd = mas.get(i).getCurrencyRate();
        }

        XYChart.Series set1 = new XYChart.Series<>();
        set1.setName("Стоимость в BYN");

        set1.getData().add(new XYChart.Data("1 USD", usd));
        set1.getData().add(new XYChart.Data("1 EUR", euro));
        set1.getData().add(new XYChart.Data("100 RUB", rub));

        CurrencyGraph.getData().add(set1);

        CurrencyGraph.setTitle("Текущий курс валют");
    }
}
