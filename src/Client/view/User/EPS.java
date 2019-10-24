package Client.view.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EPS {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField NPSData;

    @FXML
    private TextField FrequencyData;

    @FXML
    private TextField SummData;

    @FXML
    private TextField EPSresult;

    @FXML
    private Button GetResult;

    @FXML
    private Label TotalNPSLabel;

    @FXML
    private Label PieceNPSLabel;

    @FXML
    private TextField TotalNPSresult;

    @FXML
    private TextField PieceNPSresult;

    @FXML
    private Label TotalEPSLabel;

    @FXML
    private Label PieceEPSLabel;

    @FXML
    private TextField TotalEPSresult;

    @FXML
    private TextField PieceEPSresult;

    @FXML
    private Label ResultLabel;

    @FXML
    private Button Ready;

    @FXML
    private Button SaveResult;

    @FXML
    private Label EPSLabel;

    @FXML
    void OnGetResultPressed(ActionEvent event) {
        boolean error = false;
        float nominalRate = 0, summ = 0;
        int frequency = 0;
        try {
            nominalRate = Float.parseFloat(NPSData.getText());
            frequency = Integer.parseInt(FrequencyData.getText());
            summ = Float.parseFloat(SummData.getText());
        } catch (NumberFormatException e) {
            error = true;
        }
        if (error == false && nominalRate > 0 && summ > 0 && frequency > 0) {
            if (NPSData.getLength() != 0 && FrequencyData.getLength() != 0 && SummData.getLength() != 0) {
                if (GetResult.isVisible()) {
                    GetResult.setVisible(false);
                    EPSresult.setVisible(true);
                    TotalEPSresult.setVisible(true);
                    PieceNPSresult.setVisible(true);
                    PieceEPSresult.setVisible(true);
                    TotalNPSresult.setVisible(true);
                    EPSresult.setVisible(true);
                    EPSLabel.setVisible(true);
                    ResultLabel.setVisible(true);
                    PieceNPSLabel.setVisible(true);
                    PieceEPSLabel.setVisible(true);
                    SaveResult.setVisible(true);
                    TotalEPSLabel.setVisible(true);
                    TotalNPSLabel.setVisible(true);
                    NPSData.setEditable(false);
                    FrequencyData.setEditable(false);
                    SummData.setEditable(false);
                }
                float effectiveRate = CalculateEffectiveRate(nominalRate, frequency);
                float totalNPS = CalculateTotalPayments(nominalRate, summ);
                float pieceNPS = CalculatePeicePayments(totalNPS, frequency);
                float totalEPS = CalculateTotalPayments(effectiveRate, summ);
                float pieceEPS = CalculatePeicePayments(totalEPS, frequency);

                EPSresult.setText(String.valueOf(effectiveRate));
                TotalNPSresult.setText(String.valueOf(totalNPS));
                TotalEPSresult.setText(String.valueOf(totalEPS));
                PieceEPSresult.setText(String.valueOf(pieceEPS));
                PieceNPSresult.setText(String.valueOf(pieceNPS));
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

    public float CalculateEffectiveRate(float nr, int f) {
        float er;
        er= (float) ((Math.pow((1+(nr/100)/f), f)-1)*100);
        return er;
    }

    public float CalculateTotalPayments(float er, float s) {
        float tp;
        tp=(1+er/100)*s;
        return tp;
    }

    public float CalculatePeicePayments(float tp, int f) {
        float pp;
        pp=tp/f;
        return pp;
    }

    @FXML
    void OnReadyPressed(ActionEvent event) {
        Stage stage = (Stage) Ready.getScene().getWindow();
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
    void OnSaveResultPressed(ActionEvent event) throws IOException {
        float nominalRate = Float.parseFloat(NPSData.getText());
        int frequency = Integer.parseInt(FrequencyData.getText());
        float summ = Float.parseFloat(SummData.getText());
        float effectiveRate = Float.parseFloat(EPSresult.getText());
        float TotalNPS = Float.parseFloat(TotalNPSresult.getText());
        float PieceNPS = Float.parseFloat(PieceNPSresult.getText());
        float TotalEPS = Float.parseFloat(TotalEPSresult.getText());
        float PieceEPS = Float.parseFloat(PieceEPSresult.getText());

        Date date = new Date();
        String filename = "Report EPS - " + date.toString();
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\reports\\" + filename.replace(':', '-') + ".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(String.format("%-20s%n%n", "Вычисление эффективой процентной ставки по кредиту: "));
        output.write(String.format("%-20s%n", "Исходные данные: "));
        output.write(String.format("%-35s%n", "Номинальная процентная ставка: "+nominalRate + "%"));
        output.write(String.format("%-25s%n", "Частота выплат (в год): "+frequency));
        output.write(String.format("%-20s%n%n", "Сумма кредита: "+summ));
        output.write(String.format("%-35s%n", "Полученный результат: "));
        output.write(String.format("%-35s%n", "Эффективная процентная ставка: "+effectiveRate + "%"));
        output.write(String.format("%-35s%n", "Общая сумма выплат (по НПС): "+TotalNPS));
        output.write(String.format("%-35s%n", "Величина разовых выплат (по НПС): "+PieceNPS));
        output.write(String.format("%-35s%n", "Общая сумма выплат (по ЭПС): "+TotalEPS));
        output.write(String.format("%-35s%n", "Величина разовых выплат (по ЭПС): "+PieceEPS));
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

        OnReadyPressed(event);
    }

    @FXML
    void initialize() {

    }
}
