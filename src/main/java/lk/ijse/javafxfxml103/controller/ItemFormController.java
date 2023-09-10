package lk.ijse.javafxfxml103.controller;

/*
    @author DanujaV
    @created 9/3/23 - 3:38 PM   
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.javafxfxml103.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemFormController {
    public AnchorPane rootNode;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO item VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);
            pstm.setString(2, description);
            pstm.setDouble(3, unitPrice);
            pstm.setInt(4, qtyOnHand);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "item added!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtCodeOnAction(ActionEvent event) {
        String code = txtCode.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM item WHERE code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
                String itemCode = resultSet.getString(1);
                String itemDescription = resultSet.getString(2);
                double itemUnitPrice = resultSet.getDouble(3);
                int itemQtyOnHand = resultSet.getInt(4);

                setField(itemCode, itemDescription, itemUnitPrice, itemQtyOnHand);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setField(String itemCode, String itemDescription, double itemUnitPrice, int itemQtyOnHand) {
        txtCode.setText(itemCode);
        txtDescription.setText(itemDescription);
        txtUnitPrice.setText(String.valueOf(itemUnitPrice));
        txtQtyOnHand.setText(String.valueOf(itemQtyOnHand));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setTitle("Dashboard");

        primaryStage.setScene(scene);
    }
}
