package com.FoodDeliveryManagementSystem.Presentation;

import com.FoodDeliveryManagementSystem.BusinessLogic.DeliveryService;
import com.FoodDeliveryManagementSystem.BusinessLogic.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Optional;

/**
 * the controller for the login window. Extends base controller
 */
public class LoginController extends BaseController {

    /**
     * the email text field
     */
    @FXML private JFXTextField email;
    /**
     * the password text field
     */
    @FXML private JFXPasswordField password;
    /**
     * the label for the error message
     */
    @FXML private Label errMessage;

    /**
     * the initialization function
     */
    @FXML public void initialize() {
        errMessage.setText("");
    }

    /**
     * the login function will check the data entered against the users saved in the database. If any matches, it will
     * open the appropriate window.
     */
    @FXML private void login(){
        errMessage.setText("");
        Optional<User> u = DeliveryService.getService().getUsers().stream()
                .filter(e -> e.getEmail().equals(email.getText()) && e.getPassword().equals(password.getText()))
                .findFirst();
        if(u.isPresent()){
            switch (u.get().getRole()){
                case "client": openWindow("client", new Stage(), u.get());
                    break;
                case "employee": openWindow("employee", new Stage(), u.get());
                    break;
                case "admin": openWindow("admin", new Stage(), u.get());
                    break;
                default: break;
            }
        }
        else{
            errMessage.setText("Wrong email or password");
        }
    }

    /**
     * the register function will add the fields as a new user of type client.
     */
    @FXML private void register(){
        errMessage.setText("");
        if(DeliveryService
                .getService()
                .getUsers()
                .stream()
                .noneMatch(e -> e.getEmail().equals(email.getText())))
        {
            User nUser = new User(email.getText(), password.getText(), "client");
            DeliveryService.getService().AddUser(nUser);
        }
        else {
            errMessage.setText("Email already exists in the database");
        }
    }

    /**
     * Will open a new window depending on the name specified
     * @param name the name of the window. it can be: employee, admin or client
     * @param s the stage to open
     * @param u the user who opened the window.
     */
    public void openWindow(String name, Stage s, User u){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(name + ".fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("../Styles/main.css").toString());
            ((BaseController) loader.getController()).titleBarController.setStage(s);
            ((BaseController) loader.getController()).setUser(u);
            ((BaseController) loader.getController()).setStage(s);
            s.initStyle(StageStyle.UNDECORATED);
            s.setScene(new Scene(root, 1200, 800));
            s.show();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            ex.printStackTrace();
            a.setContentText(ex.toString());
            a.show();
        }
    }

}
