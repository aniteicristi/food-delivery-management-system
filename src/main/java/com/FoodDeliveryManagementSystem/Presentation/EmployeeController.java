package com.FoodDeliveryManagementSystem.Presentation;


import com.FoodDeliveryManagementSystem.BusinessLogic.DeliveryService;
import com.FoodDeliveryManagementSystem.BusinessLogic.MenuItem;
import com.FoodDeliveryManagementSystem.BusinessLogic.Order;
import com.FoodDeliveryManagementSystem.Presentation.Components.OrderController;
import com.FoodDeliveryManagementSystem.Util.Tuple;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * the employee controller controlls the employee window. it is also an observer of the delivery service and when a new
 * order is added, it will generate an ui element for that order from the prescribed component: order.
 */
public class EmployeeController extends BaseController implements Observer {
    /**
     * the list of UI elements of the orders
     */
    @FXML private VBox orders;

    /**
     * the initialize method will subscribe the controller to the delivery service
     */
    public void initialize(){
        DeliveryService.getService().Subscribe(this);
    }

    /**
     * will remove the specified comopnent form the orders list
     * @param root the component to be removed.
     */
    public void remove(Pane root) {
        orders.getChildren().remove(root);
    }

    /**
     * the function will generate a new UI element when it recieves an update from the delivery service.
     * @param o the delivery service
     * @param arg the new order/array<menuitem> tuple.
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("recieved");
        try{
            Tuple<Order, ArrayList<MenuItem>> tuple = (Tuple<Order, ArrayList<MenuItem>>) arg;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Components/order.fxml"));
            orders.getChildren().add(loader.load());
            OrderController controller = loader.getController();
            controller.init(tuple.getFirst(), tuple.getSecond(), this);
        }
        catch (Exception e){
            System.out.println(e);
        }


    }
}
