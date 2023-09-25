package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.tm.Customer;

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

    public void btnSubmitOnAction(ActionEvent actionEvent) {
    }
}
