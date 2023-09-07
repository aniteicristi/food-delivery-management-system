package com.FoodDeliveryManagementSystem.Presentation;

import com.FoodDeliveryManagementSystem.BusinessLogic.BaseProduct;
import com.FoodDeliveryManagementSystem.BusinessLogic.CompositeProduct;
import com.FoodDeliveryManagementSystem.BusinessLogic.DeliveryService;
import com.FoodDeliveryManagementSystem.BusinessLogic.MenuItem;
import com.FoodDeliveryManagementSystem.Presentation.Components.BaseProductController;
import com.FoodDeliveryManagementSystem.Presentation.Components.CompositeProductController;
import com.FoodDeliveryManagementSystem.Presentation.Dialogs.CheckoutDialogController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * the controller which controlls the ui of the client.
 */
public class ClientController extends BaseController{
    /**
     * the root of the UI
     */
    public StackPane root;
    /**
     * the items the user has selected to order.
     */
    private ArrayList<MenuItem> cartItems;
    /**
     * the container of the products.
     */
    public VBox productContainer;
    /**
     * the scrollpane which scrolls the product container.
     */
    @FXML private ScrollPane scrollPane;
    /**
     * the textfield of the name
     */
    public JFXTextField nameFilter;
    /**
     * the textfield of the price
     */
    public JFXTextField priceFilter;
    /**
     * the textfield of the rating
     */
    public JFXTextField ratingFilter;
    /**
     * the textfield of the fat
     */
    public JFXTextField fatFilter;
    /**
     * the textfield of the proteins
     */
    public JFXTextField proteinFilter;
    /**
     * the textfield of the sodium
     */
    public JFXTextField sodiumFilter;
    /**
     * the textfield of the calroeies
     */
    public JFXTextField caloriesFilter;
    /**
     * a refference to the checkout dialog
     */
    @FXML private JFXDialog checkoutDialog;
    /**
     * a refference to its controller.
     */
    @FXML private CheckoutDialogController checkoutDialogController;

    /**
     * the initialize method will take care to select only a part of the total products and to render them as custom
     * components. When teh user will scroll to the bottom of the list it will load the next batch, thus implementing
     * pagination.
     */
    @FXML private void initialize(){
        checkoutDialogController.setParent(root);
        cartItems = new ArrayList<>();
        AddProductViews(filteredList().subList(0, Math.min(19, filteredList().size())));
        scrollPane.vvalueProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() == scrollPane.getVmax()){
                int elems = productContainer.getChildren().size();
                if(elems < DeliveryService.getService().getProducts().size()){
                    AddProductViews(filteredList().subList(elems, Math.min(elems + 20, filteredList().size())));
                }
            }
        }));
    }

    /**
     * creates a new product view of the list of menu items, depending on their type: composite or base
     * @param menuItems the lsit of menu items to generate views for
     */
    private void AddProductViews(List<MenuItem> menuItems) {
        menuItems.forEach(item -> {
            try {
                if(item instanceof BaseProduct){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Components/baseProduct.fxml"));
                    productContainer.getChildren().add(loader.load());
                    BaseProductController controller = loader.getController();
                    controller.init((BaseProduct) item, this);
                }
                else if(item instanceof CompositeProduct){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Components/compositeProduct.fxml"));
                    productContainer.getChildren().add(loader.load());
                    CompositeProductController controller = loader.getController();
                    controller.init((CompositeProduct) item, this);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        });
    }

    /**
     * a method which returns the original menu item list, but filtered based on the criterion specified from the textfields.
     * if the textfield is empty, the items will not get filtered based on that criterion.
     * @return the list of items after the original products list has been filtered.
     */
    public List<MenuItem> filteredList(){
        return DeliveryService.getService().getProducts().stream()
                .filter(item -> nameFilter.getText().equals("") || item.getTitle().toLowerCase(Locale.ROOT).contains(nameFilter.getText().toLowerCase(Locale.ROOT)))
                .filter(item -> ratingFilter.getText().equals("") || item.getRating() > Float.parseFloat(ratingFilter.getText()))
                .filter(item -> priceFilter.getText().equals("") || item.getPrice() == Integer.parseInt(priceFilter.getText()))
                .filter(item -> fatFilter.getText().equals("") || item.getFat() == Integer.parseInt(fatFilter.getText()))
                .filter(item -> caloriesFilter.getText().equals("") || item.getCalories() == Integer.parseInt(caloriesFilter.getText()))
                .filter(item -> proteinFilter.getText().equals("") || item.getProtein() == Integer.parseInt(proteinFilter.getText()))
                .filter(item -> sodiumFilter.getText().equals("") || item.getSodium() == Integer.parseInt(sodiumFilter.getText()))
                .collect(Collectors.toList());
    }

    /**
     * getter for the cart items.
     * @return the cart items
     */
    public ArrayList<MenuItem> getCartItems() {
        return cartItems;
    }

    /**
     * function which updates the views of the newly filtered products.
     * @param actionEvent
     */
    public void performFiltering(ActionEvent actionEvent) {
        productContainer.getChildren().clear();
        AddProductViews(filteredList().subList(0, Math.min(19, filteredList().size())));
    }

    /**
     * function which opens the checkout dialog.
     * @param actionEvent
     */
    public void openCheckoutDialog(ActionEvent actionEvent) {
        checkoutDialogController.setUser(this.loggedInUser);
        this.checkoutDialogController.OpenDialog(cartItems);
        cartItems.clear();
    }
}
