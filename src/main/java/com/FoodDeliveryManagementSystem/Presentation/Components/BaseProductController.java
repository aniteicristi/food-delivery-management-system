package com.FoodDeliveryManagementSystem.Presentation.Components;

import com.FoodDeliveryManagementSystem.BusinessLogic.BaseProduct;
import com.FoodDeliveryManagementSystem.Presentation.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * the component for the base product.
 */
public class BaseProductController {
    /**
     * the item to be presented
     */
    @FXML private BaseProduct item;
    /**
     * the controller creating htis component.
     */
    private ClientController parent;
    /**
     * title label
     */
    @FXML private Label title;
    /**
     * rating label
     */
    @FXML private Label rating;
    /**
     * price label.
     */
    @FXML private Label price;
    /**
     * details label.
     */
    @FXML private Label details;

    /**
     * function which initializes the component
     * @param item the item to present
     * @param parent the controller creating this component
     */
    public void init(BaseProduct item, ClientController parent){
        this.item = item;
        this.parent = parent;
        title.setText(item.getTitle());
        rating.setText(item.getRating() + " Stars");
        price.setText(item.getPrice() + "$");
        details.setText("Calories: " + item.getCalories() +
                        " Fats: " + item.getFat() +
                        " Protein: " + item.getProtein() +
                        " Sodium: " + item.getProtein());
    }
    /**
     * method which adds this presented product to the shopping cart.
     * @param actionEvent
     */
    public void AddToCart(ActionEvent actionEvent) {
        parent.getCartItems().add(item);
    }
}
