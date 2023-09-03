package lk.ijse.javafxfxml103.controller;

/*
    @author DanujaV
    @created 9/3/23 - 11:11 AM   
*/

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class MainFormController {

    public TextField txtMsg;

    public void btnClickMeOnAction(ActionEvent actionEvent) {
        String text = txtMsg.getText();

        System.out.println(text);
    }
}
