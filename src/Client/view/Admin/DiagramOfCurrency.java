package Client.view.Admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ClientsCredits;
import model.Currency;

public class DiagramOfCurrency {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart DiagramOfCurrency;

    @FXML
    private Button Ok;

    @FXML
    void OkPressed(ActionEvent event) {
        Stage stage = (Stage) Ok.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/view/Admin/AdminMenu.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        ObjectInputStream file = new ObjectInputStream(new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\clientscredits(forDG).dat"));
        ArrayList<ClientsCredits> mas = (ArrayList<ClientsCredits>) file.readObject();
        file.close();
        ObjectInputStream file2 = new ObjectInputStream(new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\currency(forDG).dat"));
        ArrayList<Currency> mas2 = (ArrayList<Currency>) file2.readObject();
        file2.close();

        float byrSumm=0, usdSumm=0, rubSumm=0, eurSumm=0;
        float byrRate=0, usdRate=0, rubRate=0, eurRate=0;
        for (int i=0; i<mas2.size(); i++)
        {
            if (mas2.get(i).getCurrencyID().equals("EUR"))
                eurRate = mas2.get(i).getCurrencyRate();
            else if (mas2.get(i).getCurrencyID().equals("RUB"))
                rubRate = mas2.get(i).getCurrencyRate();
            else if (mas2.get(i).getCurrencyID().equals("USD"))
                usdRate = mas2.get(i).getCurrencyRate();
            else if (mas2.get(i).getCurrencyID().equals("BYN"))
                byrRate = mas2.get(i).getCurrencyRate();
        }
        for (int i=0; i<mas.size(); i++) {
            if (mas.get(i).getCreditName().lastIndexOf("USD") != -1)
                usdSumm += mas.get(i).getCreditSumm();
            else if (mas.get(i).getCreditName().lastIndexOf("BYN") != -1)
                byrSumm += mas.get(i).getCreditSumm();
            else if (mas.get(i).getCreditName().lastIndexOf("EUR") != -1)
                eurSumm += mas.get(i).getCreditSumm();
            else if (mas.get(i).getCreditName().lastIndexOf("RUB") != -1)
                rubSumm += mas.get(i).getCreditSumm();
        }

        float resEUR = eurSumm * eurRate;
        float resUSD = usdSumm * usdRate;
        float resRUB = rubSumm * rubRate;
        float resBYR = byrSumm * byrRate;
        float TotalRes = resEUR + resUSD + resRUB + resBYR;

        float usd = resUSD/TotalRes;
        float eur = resEUR/TotalRes;
        float byr = resBYR/TotalRes;
        float rub = resRUB/TotalRes;

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(new PieChart.Data("BYN", byr),
                new PieChart.Data("USD", usd),
                new PieChart.Data("EUR", eur),
                new PieChart.Data("RUB", rub));
        DiagramOfCurrency.setData(pieChartData);
    }
}
