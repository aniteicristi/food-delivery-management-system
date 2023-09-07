package com.FoodDeliveryManagementSystem.Presentation;

import TitleBar.Controller;
import com.FoodDeliveryManagementSystem.BusinessLogic.User;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * the base controller acts as a basic controller from which all controllers can inherit the main attributes.
 */
public class BaseController {
    /**
     * the title bar refference
     */
    public Parent titleBar;
    /**
     * the controller of the titlebar
     */
    public Controller titleBarController;
    /**
     * the logged in user of the window
     */
    protected User loggedInUser;
    /**
     * the stage the controller controlls.
     */
    protected Stage stage;

    /**
     * setter for the stage
     * @param primaryStage the stage to be set
     */
    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * setter for the user
     * @param u the user to be set
     */
    public void setUser(User u) {
        loggedInUser = u;
    }

    /**
     * function to display errors in the application
     * @param message the message to be displayed.
     */
    public void showError(String message) { }
}
