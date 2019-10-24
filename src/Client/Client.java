package Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client {

    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Object message;
    public Client() {
        try {
            // создаём сокет общения на стороне клиента в конструкторе объекта
            File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (client)\\src\\files\\SocketSettings.txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            String Host = input.readLine();
            int port = Integer.parseInt(input.readLine());
            input.close();
            socket = new Socket(Host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Клиент подключился к сокету");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Client/view/Dialogs/NoConnection.fxml") );

            try {
                loader.load();
            } catch (IOException er) {
                er.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }

    public void SendMess()
    {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Object RecvMess() throws ClassNotFoundException {
        Object in = null;
        try {
            in = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
    public void StopClient()
    {
        message = "quit";
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public Object getMessage() {
        return message;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}