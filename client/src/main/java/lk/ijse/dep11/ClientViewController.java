package lk.ijse.dep11;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.tm.Customer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
    public ComboBox <String> cmbFoodItem;
    public Spinner <Integer> spnValue;
    public Socket socket ;


    public void initialize(){

        txtId.setDisable(true);
        txtContact.setDisable(true);
        txtName.setDisable(true);

        spnValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0,1));

        String [] foods = {"Fried Rice", "Kottu", "Hot Dog", "Bread"};
        cmbFoodItem.getItems().addAll(foods);


        ObservableList<Customer> customers = FXCollections.observableList(Data.arr);
        tblClient.setItems(customers);

        tblClient.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClient.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClient.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));
        txtId.setText(setupId());

        new Thread(()->{
            try {
                // Establish a socket connection to the server
                socket = new Socket("192.168.90.65", 5055); // Replace with the server's IP address

                InputStream is = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ObjectInputStream inputStream = new ObjectInputStream(bis);

                Customer updateCustomer = (Customer )inputStream.readObject();
                System.out.println(updateCustomer);
                updateCustomer(updateCustomer);


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();



    }

    public void btnSubmitOnAction(ActionEvent actionEvent) {
        if(!validate()) return;

        if(cmbFoodItem.getSelectionModel().getSelectedItem()==null){
            cmbFoodItem.requestFocus();
            return;
        } else if (spnValue.getValue()==0) {
            spnValue.requestFocus();
            return;
        }

        String id = txtId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String food = cmbFoodItem.getSelectionModel().getSelectedItem();
        int qty = spnValue.getValue();
        String status = "Update";
        Customer C1 = new Customer(id, name, contact,food, status,qty);
        getCustomer().add(C1);
        btnNew.fire();

        try {
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ObjectOutputStream outputStream = new ObjectOutputStream(bos);

            outputStream.writeObject(C1);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomer(Customer updateCustomer) {
        List<Customer> customerlist = getCustomer();
        for(Customer customer: customerlist){
            if(customer.getId().equals(updateCustomer.getId())){
                customer.setStatus(updateCustomer.getStatus());
            }
        }
        tblClient.refresh();
    }

    public boolean validate(){
        if(!txtName.getText().strip().matches("[A-Za-z ]+")){
            txtName.selectAll();
            txtName.requestFocus();
            return false;
        }  else if (!txtContact.getText().matches("0\\d{2}-\\d{7}")) {
            txtContact.selectAll();
            txtContact.requestFocus();
            return false;
        }
        return true;
    }
    public List<Customer> getCustomer(){
        return tblClient.getItems();
    }
    public String setupId(){
        if(getCustomer().isEmpty()) return "E-001";
        String lastId = getCustomer().get(getCustomer().size()-1).getId();
        int id = Integer.parseInt(lastId.substring(2))+1;
        return String.format("E-%03d",id);
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtContact.setDisable(false);
        txtName.setDisable(false);
        txtId.setDisable(false);

        txtId.setText(setupId());
        txtName.setText("");
        txtContact.setText("");
        txtName.requestFocus();
        cmbFoodItem.getSelectionModel().clearSelection();
        spnValue.getValueFactory().setValue(0);
    }
}
