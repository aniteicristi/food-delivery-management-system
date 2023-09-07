package com.FoodDeliveryManagementSystem.Presentation.Components;

import com.FoodDeliveryManagementSystem.BusinessLogic.CompositeProduct;
import com.FoodDeliveryManagementSystem.Presentation.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * a component whcih displays a composite product in the UI
 */
public class CompositeProductController {

    /**
     * the composite product to be displayed.
     */
    private CompositeProduct item;
    /**
     * the controller of this component
     */
    private ClientController clientController;
    /**
     * title label
     */
    @FXML private Label title;
    /**
     * rating label.
     */
    @FXML private Label rating;
    /**
     * container of the items inside the composition
     */
    @FXML private VBox details;
    /**
     * price label.
     */
    @FXML private Label price;

    /**
     * method which initializes the component
     * @param item the item it presents
     * @param clientController the controller.
     */
    public void init(CompositeProduct item, ClientController clientController) {
        this.clientController = clientController;
        this.item = item;
        title.setText(item.getTitle());
        rating.setText(Float.toString(item.getRating()));
        price.setText(item.getPrice() + "$");
        item.getProducts().forEach( i -> {
            Label l = new Label();
            l.setStyle("-fx-font-size: 13; -fx-text-fill: #a4a4a4");
            l.setText(i.getTitle());
            details.getChildren().add(l);
        });

    }

    /**
     * method which adds this presented product to the shopping cart.
     * @param actionEvent
     */
    public void AddToCart(ActionEvent actionEvent) {
        clientController.getCartItems().add(item);
    }

}
