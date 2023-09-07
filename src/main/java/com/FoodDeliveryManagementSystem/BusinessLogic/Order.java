package com.FoodDeliveryManagementSystem.BusinessLogic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The order describes an order that a user may create.
 */
public class Order implements Serializable {
    /**
     * The id of the order will be an UUID
     */
    private String orderID;
    /**
     * The email of the client who created the order
     */
    private String clientEmail;
    /**
     * The date adn time the order was created
     */
    private LocalDateTime orderDate;
    /**
     * The total price of the items ordered/
     */
    private int total;
    /**
     * a boolean indicating that the order was completed
     */
    private boolean completed;

    /**
     * Constructs an order give the properties
     * @param orderID the id
     * @param clientEmail the email
     * @param orderDate the date
     * @param total the total price
     */
    public Order(String orderID, String clientEmail, LocalDateTime orderDate, int total) {
        this.orderID = orderID;
        this.clientEmail = clientEmail;
        this.orderDate = orderDate;
        this.total = total;
        completed = false;
    }

    /**
     * compares the orders based on all of their characteristics
     * @param o the other order to compare it to
     * @return true if the objects are effectively equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderID == order.orderID && clientEmail == order.clientEmail && orderDate.equals(order.orderDate);
    }

    /**
     * Generates a hashcode from the properties of the order.
     * @return an integer hashcode of the order.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientEmail, orderDate);
    }

    /**
     * getter for the id
     * @return the id
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * getter for the email
     * @return the email
     */
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     * getter for the order date
     * @return the date
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * getter for the total price
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * A method which will mark the order as completed.
     */
    public void MarkAsComplete(){
        completed = true;
    }

}
