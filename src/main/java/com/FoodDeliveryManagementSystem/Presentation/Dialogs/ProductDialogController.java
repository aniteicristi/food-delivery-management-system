package com.FoodDeliveryManagementSystem.Presentation.Dialogs;

import com.FoodDeliveryManagementSystem.BusinessLogic.BaseProduct;
import com.FoodDeliveryManagementSystem.BusinessLogic.DeliveryService;
import com.FoodDeliveryManagementSystem.Presentation.AdminController;
import com.FoodDeliveryManagementSystem.Presentation.BaseController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * the dialog for inserting/updating the base product.
 */
public class ProductDialogController extends BaseController {
    /**
     * the ttile textfield
     */
    public JFXTextField title;
    /**
     * the rating textfield
     */
    public JFXTextField rating;
    /**
     * the calories textfield
     */
    public JFXTextField calories;
    /**
     * the proteins textfield
     */
    public JFXTextField proteins;
    /**
     * the fats textfield
     */
    public JFXTextField fats;
    /**
     * the sodium textfield
     */
    public JFXTextField sodium;
    /**
     * the price textfield
     */
    public JFXTextField price;
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
     * the product to be edited.
     */
    private BaseProduct product;

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
    public void OpenEditDialog(BaseProduct p){
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
        BaseProduct p = new BaseProduct(
                title.getText(),
                Float.parseFloat(rating.getText()),
                Integer.parseInt(calories.getText()),
                Integer.parseInt(proteins.getText()),
                Integer.parseInt(fats.getText()),
                Integer.parseInt(sodium.getText()),
                Integer.parseInt(price.getText())
        );
        DeliveryService.getService().AddProduct(p);

        root.close();
    }
    /**
     * This method will initialize the fields of the form with the data of the client in case of update.
     * @param p the product to update.
     */
    private void SetFields(BaseProduct p){
        title.setText(p.getTitle());
        rating.setText(Float.toString(p.getRating()));
        calories.setText(Integer.toString(p.getCalories()));
        proteins.setText(Integer.toString(p.getProtein()));
        fats.setText(Integer.toString(p.getFat()));
        sodium.setText(Integer.toString(p.getSodium()));
        price.setText(Integer.toString(p.getPrice()));
    }
    /**
     * A method for resetting the input fields and their validation.
     */
    private void ResetFields(){
        title.setText("");
        title.resetValidation();
        rating.setText("");
        rating.resetValidation();
        calories.setText("");
        calories.resetValidation();
        proteins.setText("");
        proteins.resetValidation();
        fats.setText("");
        fats.resetValidation();
        sodium.setText("");
        sodium.resetValidation();
        price.setText("");
        price.resetValidation();
    }
}
