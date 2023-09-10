package lk.ijse.javafxfxml103.dto;

/*
    @author DanujaV
    @created 9/10/23 - 12:33 PM   
*/

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Setter
@Getter
@ToString*/
public class ItemDto {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
