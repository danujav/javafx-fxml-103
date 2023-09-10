package lk.ijse.javafxfxml103.model;

/*
    @author DanujaV
    @created 9/10/23 - 4:30 PM   
*/

import lk.ijse.javafxfxml103.db.DbConnection;
import lk.ijse.javafxfxml103.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemModel {
    public boolean saveItem(ItemDto dto) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO item VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, dto.getCode());
        pstm.setString(2, dto.getDescription());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setInt(4, dto.getQtyOnHand());

        return pstm.executeUpdate() > 0;
    }

    public ItemDto searchItem(String code) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();

        ItemDto itemDto = null;
        if(resultSet.next()) {
            String itemCode = resultSet.getString(1);
            String itemDescription = resultSet.getString(2);
            double itemUnitPrice = resultSet.getDouble(3);
            int itemQtyOnHand = resultSet.getInt(4);

            //since JDK11
            itemDto = new ItemDto(itemCode, itemDescription, itemUnitPrice, itemQtyOnHand);
        }

        return itemDto;
    }
}
