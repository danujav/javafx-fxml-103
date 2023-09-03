package lk.ijse.javafxfxml103;

/*
    @author DanujaV
    @created 9/3/23 - 11:07 AM   
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootNode = FXMLLoader.load(this.getClass()
                .getResource("/view/main_form.fxml"));

        Scene scene = new Scene(rootNode);

        stage.setScene(scene);

        stage.setTitle("Main Form");
        stage.centerOnScreen();

        stage.show();
    }
}
