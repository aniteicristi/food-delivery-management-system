package com.FoodDeliveryManagementSystem.BusinessLogic;

import java.io.Serial;
import java.io.Serializable;

/**
 * The menu item class acts as a base class for all items which are part of the menu of the delivery service
 */
public class MenuItem implements Serializable {

    /**
     * The serial version UID for serialization
     */
    @Serial
    private static final long serialVersionUID = 11L;

    /**
     * Getter for the price
     * @return the price
     */
    public int getPrice() {
        return 0;
    }
    /**
     * Getter for the sodium
     * @return the sodium
     */
    public int getSodium() {
        return 0;
    }
    /**
     * Getter for the fat
     * @return the fat
     */
    public int getFat() {
        return 0;
    }
    /**
     * Getter for the protein
     * @return the protein
     */
    public int getProtein() {
        return 0;
    }
    /**
     * Getter for the calories
     * @return the calories
     */
    public int getCalories() {
        return 0;
    }
    /**
     * Getter for the rating
     * @return the rating
     */
    public float getRating() {
        return 0.0f;
    }

    /**
     * Getter for the title
     * @return the title
     */
    public String getTitle() {
        return "";
    }
}
