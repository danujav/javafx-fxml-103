package lk.ijse.javafxfxml103.dto.tm;

/*
    @author DanujaV
    @created 9/10/23 - 3:14 PM   
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTm {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
