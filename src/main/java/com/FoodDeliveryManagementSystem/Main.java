package com.FoodDeliveryManagementSystem;

import com.FoodDeliveryManagementSystem.Presentation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * the entry class of the application.
 */
public class Main extends Application {

    /**
     * the entry point of the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * the start method that will open the login window.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Presentation/login.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("Styles/main.css").toString());
            ((BaseController) loader.getController()).titleBarController.setStage(primaryStage);
            ((BaseController) loader.getController()).setStage(primaryStage);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 400, 350));
            primaryStage.show();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            ex.printStackTrace();
            a.setContentText(ex.toString());
            a.show();
        }
    }

}
