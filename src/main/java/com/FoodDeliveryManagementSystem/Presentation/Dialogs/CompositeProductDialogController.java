package com.FoodDeliveryManagementSystem.Presentation.Dialogs;

import com.FoodDeliveryManagementSystem.BusinessLogic.CompositeProduct;
import com.FoodDeliveryManagementSystem.BusinessLogic.DeliveryService;
import com.FoodDeliveryManagementSystem.BusinessLogic.MenuItem;
import com.FoodDeliveryManagementSystem.Presentation.AdminController;
import com.FoodDeliveryManagementSystem.Presentation.BaseController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * the dialog for inserting/updating the composite product.
 */
public class CompositeProductDialogController extends BaseController {
    /**
     * the title textfield
     */
    public JFXTextField title;
    /**
     * the rating textfield
     */
    public JFXTextField rating;
    /**
     * the rpice textfield
     */
    public JFXTextField price;
    /**
     * the list of total products
     */
    public ListView<MenuItem> products;
    /**
     * the lsit of the products inside the composite product.
     */
    public ListView<MenuItem> added;
    /**
     * A reference to the save button of the dialog.
     */
    @FXML private JFXButton setupSave;
    /**
     * A reference to the root element of the dialog.
     */
    @FXML private JFXDialog root;
    /**
     * A field designating the current operation.
     * It will either be insert or update.
     */
    private String operation;
    /**
     * A reference to the main controller of the application.
     */
    private AdminController mainController;
    /**
     * A reference to the root of the application.
     */
    private Parent parent;

    /**
     * method that initializes the lists.
     */
    public void initialize(){
        products.setCellFactory(param -> new ListCell<MenuItem>(){
            @Override
            protected void updateItem(MenuItem item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null)
                    setText(null);
                else
                    setText(item.getTitle());
            }
        });
        added.setCellFactory(param -> new ListCell<MenuItem>(){
            @Override
            protected void updateItem(MenuItem item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null)
                    setText(null);
                else
                    setText(item.getTitle());
            }
        });
        products.setItems(FXCollections.observableArrayList(DeliveryService.getService().getProducts()));
    }

    /**
     * the product to be edited.
     */
    private CompositeProduct product;

    /**
     * Setter for the parent
     * @param parent the parent to be set.
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }
    /**
     * Setter for the main controller
     * @param mainController the controller to be set.
     */
    public void setMainController(AdminController mainController) {
        this.mainController = mainController;
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
    public void OpenInsertDialog(){
        ResetFields();
        product = null;
        root.show((StackPane) parent);
        setupSave.setText("Insert");
        operation = "insert";
    }
    /**
     * A method for opening the dialog for update operation.
     * @param p the product which we need to update.
     */
    public void OpenEditDialog(CompositeProduct p){
        ResetFields();
        product = p;
        root.show((StackPane) parent);
        setupSave.setText("Update");
        SetFields(p);
        operation = "update";
    }
    /**
     * method which will save the new / edited product to the delivery service.
     * @param actionEvent
     */
    public void SaveDialog(ActionEvent actionEvent) {
        if(operation.equals("update")){
            DeliveryService.getService().RemoveProduct(product);
        }
        CompositeProduct p = new CompositeProduct(
                title.getText(),
                Float.parseFloat(rating.getText()),
                Integer.parseInt(price.getText()),
                new ArrayList<>(added.getItems())
        );
        DeliveryService.getService().AddProduct(p);

        root.close();
    }
    /**
     * This method will initialize the fields of the form with the data of the client in case of update.
     * @param p the product to update.
     */
    private void SetFields(CompositeProduct p){
        title.setText(p.getTitle());
        rating.setText(Float.toString(p.getRating()));
        price.setText(Integer.toString(p.getPrice()));
        added.setItems(FXCollections.observableArrayList(p.getProducts()));
    }
    /**
     * A method for resetting the input fields and their validation.
     */
    private void ResetFields(){
        title.setText("");
        title.resetValidation();
        rating.setText("");
        rating.resetValidation();
        price.setText("");
        price.resetValidation();
    }

    /**
     * method which will take the selected product from the list and add it to the composite product list
     * @param actionEvent
     */
    public void addProduct(ActionEvent actionEvent) {
        MenuItem item = products.getSelectionModel().getSelectedItem();
        if(item != null)
            added.getItems().add(item);
    }

    /**
     * method which will remove the selected product from the composite product list
     */
    public void removeProduct() {
        MenuItem item = added.getSelectionModel().getSelectedItem();
        if(item != null){
            added.getItems().remove(item);
        }
    }
}
