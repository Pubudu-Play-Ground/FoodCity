package lk.ijse.dep11;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.tm.Customer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerViewController {

    public AnchorPane root;
    public TableView<Customer> tblServer;
    public Button btnProcessing;
    public Button btnCompleted;

    public void initialize() throws IOException {
        tblServer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblServer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("fooditem"));
        tblServer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));

        ServerSocket serverSocket = new ServerSocket(5055);
        System.out.println("pending for customer...");
        new Thread(()->{
            while (true){
                try {
                    Socket localSocket = serverSocket.accept();
                    System.out.println("awa...awa...");
                    while (true){
                        InputStream is = localSocket.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ObjectInputStream ois = new ObjectInputStream(bis);
                        Customer customer = (Customer) ois.readObject();
                        Data.arr.add(customer);
                        Data.localSocketList.add(localSocket);
                        Platform.runLater(()->{
                            tblServer.getItems().add(customer);
                            tblServer.refresh();
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(()->{
                        new Alert(Alert.AlertType.ERROR,"Server Failed").show();
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Platform.runLater(()->{
                        new Alert(Alert.AlertType.ERROR,"Server Failed").show();
                    });
                }

            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < Data.localSocketList.size(); i++) {
                try {
                    OutputStream os = Data.localSocketList.get(i).getOutputStream();
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(Data.arr.get(i));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();

    }

    public void btnProcessingOnAction(ActionEvent event) {
    }

    public void btnCompletedOnAction(ActionEvent event) {

        for (int i = 0; i < Data.localSocketList.size(); i++) {
            try {
                OutputStream os = Data.localSocketList.get(i).getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(Data.arr.get(i));
                System.out.println(Data.arr.get(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
