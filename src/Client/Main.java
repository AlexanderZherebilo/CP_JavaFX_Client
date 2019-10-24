package Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public static Client client = new Client();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Autorization/Autorization.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @Override
    public void stop() { // перегружаем функцию остановки работы программы, чтобы сервер разорвал соединение без ошибок
        System.out.println("Отключаемся от сервера");
        Main.client.StopClient();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
