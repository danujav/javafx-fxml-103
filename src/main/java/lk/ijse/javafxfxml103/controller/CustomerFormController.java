package lk.ijse.javafxfxml103.controller;

/*
    @author DanujaV
    @created 9/3/23 - 3:13 PM   
*/

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.javafxfxml103.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerFormController {
    public TextField txtSalary;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtId;

    public void initialize() {
        System.out.println("Customer form just loaded!");
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO customer VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setDouble(4, salary);

            boolean isSaved = pstm.executeUpdate() > 0;
            if(isSaved) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
                String cusId = resultSet.getString(1);
                String cusName = resultSet.getString(2);
                String cusAddress = resultSet.getString(3);
                double cusSalary = resultSet.getDouble(4);

                fillFields(cusId, cusName, cusAddress, cusSalary);
            } else {
                new Alert(Alert.AlertType.WARNING, "oops! customer not found!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(String cusId, String cusName, String cusAddress, double cusSalary) {
        txtId.setText(cusId);
        txtName.setText(cusName);
        txtAddress.setText(cusAddress);
        txtSalary.setText(String.valueOf(cusSalary));
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtSalary.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "UPDATE customer set name = ?, address = ?, salary = ? WHERE id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setDouble(3, salary);
            pstm.setString(4, id);

            boolean isUpdated = pstm.executeUpdate() > 0;

            if(isUpdated) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();
            String sql = "DELETE FROM customer WHERE id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            boolean isDeleted = pstm.executeUpdate() > 0;

            if(isDeleted) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
