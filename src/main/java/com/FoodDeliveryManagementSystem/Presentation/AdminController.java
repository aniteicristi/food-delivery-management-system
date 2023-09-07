package com.FoodDeliveryManagementSystem.Presentation;


import com.FoodDeliveryManagementSystem.BusinessLogic.*;
import com.FoodDeliveryManagementSystem.DataAccess.CSVParser;
import com.FoodDeliveryManagementSystem.Presentation.Dialogs.CompositeProductDialogController;
import com.FoodDeliveryManagementSystem.Presentation.Dialogs.ProductDialogController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

public class AdminController extends BaseController{

    /**
     * the listview of the clients
     */
    public ListView<User> clients;
    /**
     * the from textfield
     */
    public JFXTextField from;
    /**
     * the to textfield
     */
    public JFXTextField to;
    /**
     * the times textfield
     */
    public JFXTextField times;
    /**
     * the client Times textifeld
     */
    public JFXTextField clientTimes;
    /**
     * the order value textfield
     */
    public JFXTextField orderValue;
    /**
     * the date ordered textfield
     */
    public JFXTextField dateOrdered;
    /**
     * the list of the orders
     */
    @FXML private ListView<Order> orders;
    /**
     * the list of the menu items. It is orderd alphabetically.
     */
    @FXML private ListView<MenuItem> menuItems;
    /**
     * refference to the UI root of the window.
     */
    @FXML private Parent root;
    /**
     * A reference to the product dialog root element. The root element is the dialog object itself.
     */
    @FXML private JFXDialog productDialog;
    /**
     * A reference to the controller of the product dialog component.
     */
    @FXML private ProductDialogController productDialogController;
    /**
     * A reference to the composite product dialog root element. The root element is the dialog object itself.
     */
    @FXML private JFXDialog compositeProductDialog;
    /**
     * A reference to the controller of the composite product dialog component.
     */
    @FXML private CompositeProductDialogController compositeProductDialogController;

    /**
     * a function which initializes the dialogs and lists.
     */
    @FXML public void initialize() {
        productDialogController.setParent(root);
        productDialogController.setMainController(this);
        compositeProductDialogController.setParent(root);
        compositeProductDialogController.setMainController(this);
        menuItems.setCellFactory(param -> new ListCell<MenuItem>(){
            @Override
            protected void updateItem(MenuItem item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null)
                    setText(null);
                else
                    setText(item.getTitle());
            }
        });
        menuItems.setItems(FXCollections.observableArrayList(
                DeliveryService.getService().getProducts().stream().sorted(Comparator.comparing(MenuItem::getTitle)).collect(Collectors.toList())
        ));
        clients.setCellFactory(param -> new ListCell<User>(){
            @Override
            protected void updateItem(User item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null)
                    setText(null);
                else
                    setText(item.toString());
            }
        });
        clients.setItems(FXCollections.observableArrayList(DeliveryService.getService().getUsers()));
        orders.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(item.getClientEmail() + " " + item.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + item.getTotal() + "$");
            }
        });
        orders.setItems(FXCollections.observableArrayList(DeliveryService.getService().getOrders().keySet()));
    }

    /**
     * function which calls the csv parser and creates the resulting objects.
     * @param actionEvent
     */
    public void Import(ActionEvent actionEvent) {
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File selectedFile = fs.showOpenDialog(stage);
        CSVParser parser = new CSVParser();
        HashSet<BaseProduct> newItems = parser.Parse(this, selectedFile);
        //add them to the main list.
        DeliveryService.getService().setProducts(newItems);
        menuItems.setItems(FXCollections.observableArrayList(DeliveryService.getService().getProducts()));
    }

    /**
     * the function that will open a create dialog
     * @param actionEvent
     */
    public void Create(ActionEvent actionEvent) {
        productDialogController.OpenInsertDialog();
    }

    /**
     * fucntion that will open a create composite dialog
     * @param actionEvent
     */
    public void CreateComposite(ActionEvent actionEvent){
        compositeProductDialogController.OpenInsertDialog();
    }

    /**
     * function that deletes a selected menu item.
     * @param actionEvent
     */
    public void Delete(ActionEvent actionEvent) {
        MenuItem item = menuItems.getSelectionModel().getSelectedItem();
        if(item != null){
            DeliveryService.getService().RemoveProduct(item);
            menuItems.getItems().remove(item);
        }
    }

    /**
     * function which opens update dialog based on the type of the object selected from amongst the menu items.
     * @param actionEvent
     */
    public void Update(ActionEvent actionEvent) {
        if(menuItems.getSelectionModel().getSelectedItem() instanceof BaseProduct)
            productDialogController.OpenEditDialog((BaseProduct) menuItems.getSelectionModel().getSelectedItem());
        else if(menuItems.getSelectionModel().getSelectedItem() instanceof CompositeProduct){
            compositeProductDialogController.OpenEditDialog((CompositeProduct) menuItems.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * function that generates the first report
     * @param actionEvent
     */
    public void report1(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File f = chooser.showDialog(stage);
        if(f == null) return;
        try (FileWriter fout = new FileWriter(f.getAbsolutePath() + "\\report.txt")) {
            DeliveryService.getService().reportOrderTimeInterval(Integer.parseInt(from.getText()), Integer.parseInt(to.getText())).forEach(ord -> {
                try {
                    fout.write(ord.getOrderID() + " | " + ord.getClientEmail() + " Total: " + ord.getTotal() + " At " + ord.getOrderDate().format(DateTimeFormatter.ISO_DATE_TIME) + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(ex.toString());
            a.show();
        }
    }
    /**
     * function that generates the second report
     * @param actionEvent
     */
    public void report2(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File f = chooser.showDialog(stage);
        if(f == null) return;
        try (FileWriter fout = new FileWriter(f.getAbsolutePath() + "\\report.txt")) {
            DeliveryService.getService().reportNumberOfTimes(Integer.parseInt(times.getText())).forEach(prod -> {
                try {
                    fout.write(prod.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(ex.toString());
            a.show();
        }
    }
    /**
     * function that generates the third report
     */
    public void report3() {
        DirectoryChooser chooser = new DirectoryChooser();
        File f = chooser.showDialog(stage);
        if(f == null) return;
        try (FileWriter fout = new FileWriter(f.getAbsolutePath() + "\\report.txt")) {
            DeliveryService.getService().reportNumberOfClients(Integer.parseInt(clientTimes.getText()), Integer.parseInt(orderValue.getText())).forEach(user -> {
                try {
                    fout.write(user.getEmail() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(ex.toString());
            a.show();
        }
    }
    /**
     * function that generates the fourth report
     * @param actionEvent
     */
    public void report4(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File f = chooser.showDialog(stage);
        if(f == null) return;
        LocalDate time;
        try{
            time = LocalDate.parse(dateOrdered.getText());
            System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_DATE));
            FileWriter fout = new FileWriter(f.getAbsolutePath() + "\\report.txt");
            DeliveryService.getService().reportProductsInADay(time).forEach(prod -> {
                try {
                    fout.write(prod.getFirst().toString() + " Times Ordered: " + prod.getSecond()  + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fout.close();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(ex.toString());
            a.show();
        }
    }
}
