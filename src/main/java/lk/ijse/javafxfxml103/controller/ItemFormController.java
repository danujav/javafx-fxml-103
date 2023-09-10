package lk.ijse.javafxfxml103.controller;

/*
    @author DanujaV
    @created 9/3/23 - 3:38 PM   
*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.javafxfxml103.db.DbConnection;
import lk.ijse.javafxfxml103.dto.ItemDto;
import lk.ijse.javafxfxml103.dto.tm.ItemTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private TableView<ItemTm> tblItem;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    public void initialize() {
        List<ItemDto> itemDtoList = getAllItems();
        setItems(itemDtoList);
    }

    private void setItems(List<ItemDto> itemDtoList) {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        for(ItemDto tm : itemDtoList) {
            var itemTm = new ItemTm(
                    tm.getCode(),
                    tm.getDescription(),
                    tm.getUnitPrice(),
                    tm.getQtyOnHand()
            );
            obList.add(itemTm);
        }

        tblItem.setItems(obList);
    }

    private List<ItemDto> getAllItems() {
        List<ItemDto> itemDtoList = new ArrayList<>();

        try {
            Connection con = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM item";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();



            while(resultSet.next()) {
                String itemCode = resultSet.getString(1);
                String itemDescription = resultSet.getString(2);
                double itemUnitPrice = resultSet.getDouble(3);
                int itemQtyOnHand = resultSet.getInt(4);

                var itemDto = new ItemDto(itemCode, itemDescription, itemUnitPrice, itemQtyOnHand);
                itemDtoList.add(itemDto);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        return itemDtoList;
    }

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

                //since JDK11
                var itemDto = new ItemDto(itemCode, itemDescription, itemUnitPrice, itemQtyOnHand);

                setField(itemDto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setField(ItemDto itemDto) {
        txtCode.setText(itemDto.getCode());
        txtDescription.setText(itemDto.getDescription());
        txtUnitPrice.setText(String.valueOf(itemDto.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(itemDto.getQtyOnHand()));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setTitle("Dashboard");

        primaryStage.setScene(scene);
    }
}
