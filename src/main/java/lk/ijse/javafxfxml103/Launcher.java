package lk.ijse.javafxfxml103;

/*
    @author DanujaV
    @created 9/3/23 - 11:07 AM   
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //load scene graph to this method
        AnchorPane rootPane = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));

        //set scene graph to the Scene
        Scene scene = new Scene(rootPane);

        //set Scene to the Primary Stage
        stage.setScene(scene);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();

        //show stage
        stage.show();

    }
}
