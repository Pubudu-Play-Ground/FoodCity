package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.tm.Customer;

import java.util.ArrayList;

public class ClientViewController {


    public AnchorPane root;
    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public Button btnSubmit;
    public TableView <Customer>tblClient;
    public Spinner <Integer> spn1;
    public Spinner <Integer>spn2;
    public Spinner <Integer>spn3;
    public Spinner <Integer>spn4;
    public Button btnNew;



    public void initialize(){
        tblClient.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClient.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClient.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        txtId.setText(setupId());
    }

    public void btnSubmitOnAction(ActionEvent actionEvent) {
    }
    public ArrayList<Customer> getCustomer(){
        return Data.arr;
    }
    public String setupId(){
        if(getCustomer().isEmpty()) return "E-001";
        String lastId = getCustomer().get(getCustomer().size()-1).getId();
        int id = Integer.parseInt(lastId.substring(2))+1;
        return String.format("E-%03d",id);
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtId.setText(setupId());
        txtName.setText("");
        txtContact.setText("");
        txtName.requestFocus();
    }
}
