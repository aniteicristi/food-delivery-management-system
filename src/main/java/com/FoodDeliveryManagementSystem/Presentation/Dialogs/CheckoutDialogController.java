package com.FoodDeliveryManagementSystem.Presentation.Dialogs;

import com.FoodDeliveryManagementSystem.BusinessLogic.*;
import com.FoodDeliveryManagementSystem.BusinessLogic.MenuItem;
import com.FoodDeliveryManagementSystem.BusinessLogic.Order;
import com.FoodDeliveryManagementSystem.BusinessLogic.User;
import com.jfoenix.controls.JFXDialog;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * the dialog for confirming the placing of a new order.
 */
public class CheckoutDialogController {
    /**
     * a list of the items to be ordered
     */
    public ListView<MenuItem> cartItems;
    /**
     * the label displaying the total price.
     */
    public Label total;
    /**
     * A reference to the root element of the dialog.
     */
    @FXML private JFXDialog root;

    /**
     * A reference to the root of the application.
     */
    private Parent parent;
    /**
     * the client which is about to make the order.
     */
    private User user;

    /**
     * This method will handle the initialization of the content.
     */
    public void initialize(){
        cartItems.setCellFactory(param -> new ListCell<MenuItem>(){
            @Override
            protected void updateItem(MenuItem item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null)
                    setText(null);
                else
                    setText(item.toString());
            }
        });
    }
    /**
     * Setter for the parent
     * @param parent the parent to be set.
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    /**
     * setter for the user.
     * @param user the user to be set
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * A method which will close the dialog window.
     */
    @FXML private void CloseDialog() {
        root.close();
    }

    /**
     * A method which will open the dialog for insertion.
     */
    public void OpenDialog(ArrayList<MenuItem> cart){
        cartItems.setItems(FXCollections.observableArrayList(cart));
        root.show((StackPane) parent);
        total.setText( "Total price: " + cartItems.getItems().stream().mapToInt(MenuItem::getPrice).sum() + "$");
    }

    /**
     * the method which will create a new order with the items specified an will attepmt to insert them into the
     * delivery service order map.
     * @param actionEvent
     */
    public void SaveDialog(ActionEvent actionEvent) {
        Order order = new Order(UUID.randomUUID().toString(),
                        user.getEmail(),
                        LocalDateTime.now(),
                        cartItems.getItems().stream().mapToInt(MenuItem::getPrice).sum());
        DeliveryService.getService().AddOrder(order, new ArrayList<>(cartItems.getItems()));

        createBill(order);
        cartItems.getItems().clear();
        CloseDialog();
    }

    /**
     * method which will generate the bill of the order.
     * @param order
     */
    private void createBill(Order order) {
        DirectoryChooser chooser = new DirectoryChooser();
        File f  = chooser.showDialog(this.parent.getScene().getWindow());
        if(f == null) return;
        try (FileWriter fout = new FileWriter(f.getAbsolutePath() + "\\bill.txt")){
            fout.write("Bill for order " + order.getOrderID() + " was purchased on " + order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            fout.write((" by client " + order.getClientEmail() + " for a total of " + order.getTotal() + "$\n" ));
            for(MenuItem item:cartItems.getItems()){
                fout.write(item.getTitle() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
