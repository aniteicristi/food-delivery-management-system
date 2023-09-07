package com.FoodDeliveryManagementSystem.Presentation.Components;

import com.FoodDeliveryManagementSystem.BusinessLogic.MenuItem;
import com.FoodDeliveryManagementSystem.BusinessLogic.Order;
import com.FoodDeliveryManagementSystem.Presentation.EmployeeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * a UI component displaying the order
 */
public class OrderController {
    /**
     * the label displaying the id
     */
    public Label orderId;
    /**
     * the box holding the order items
     */
    public VBox orderItems;
    /**
     * the controller of this controller
     */
    private EmployeeController controller;
    /**
     * the refference to the root.
     */
    @FXML private Pane root;
    /**
     * the order it displays.
     */
    private Order order;

    /**
     * function for intialization of the order.
     * @param o the order
     * @param a the list of items
     * @param controller the controller
     */
    public void init(Order o, ArrayList<MenuItem> a, EmployeeController controller){
        this.controller = controller;
        orderId.setText(o.getOrderID() + "  |  " + o.getClientEmail());
        a.forEach(item -> {
            Label l = new Label(item.getTitle());
            l.setStyle("-fx-fill: gray; -fx-font-size: 13");
            orderItems.getChildren().add(l);
        });
        this.order = o;
    }

    /**
     * method which marks an order as complete.
     * @param actionEvent
     */
    public void MarkAsComplete(ActionEvent actionEvent) {
        order.MarkAsComplete();
        controller.remove(root);
    }
}
