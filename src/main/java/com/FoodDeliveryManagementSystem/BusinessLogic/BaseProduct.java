package com.FoodDeliveryManagementSystem.BusinessLogic;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class BaseProduct extends MenuItem implements Serializable {
    /**
     * The serial version unique id.
     */
    @Serial
    private static final long serialVersionUID = 12L;
    /**
     * The title of the base product
     */
    private String title;
    /**
     * The rating of the base product
     */
    private float rating;
    /**
     * The calories of the base product
     */
    private int calories;
    /**
     * the proteins of the base product
     */
    private int protein;
    /**
     * the fat of the base product
     */
    private int fat;
    /**
     * the sodium of the base product
     */
    private int sodium;
    /**
     * the price of the base product
     */
    private int price;

    /**
     * constructor for the base product
     * @param title the title
     * @param rating the rating
     * @param calories the calories
     * @param protein the protein
     * @param fat the fat
     * @param sodium the sodium
     * @param price the price
     */
    public BaseProduct(String title, float rating, int calories, int protein, int fat, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    /**
     * Equals method returns true if the this and the o base product have the same title.
     * @param o the other object
     * @return true if the objects have the same name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseProduct)) return false;
        BaseProduct that = (BaseProduct) o;
        return Objects.equals(title, that.title);
    }

    /**
     * generates an integer hash of the title of the product
     * @return the integer hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    /**
     * @return the string representation of the object
     */
    @Override
    public String toString(){
        return getTitle();
    }

    /**
     * getter for the title
     * @return the title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * setter for the title
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * getter for the rating
     * @return the rating
     */
    @Override
    public float getRating() {
        return rating;
    }

    /**
     * setter for the rating
     * @param rating the rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }
    /**
     * getter for the calories
     * @return the calories
     */
    @Override
    public int getCalories() {
        return calories;
    }

    /**
     * setter for the calories
     * @param calories the calories
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }
    /**
     * getter for the protein
     * @return the protein
     */
    @Override
    public int getProtein() {
        return protein;
    }

    /**
     * setter for the protein
     * @param protein the proteins
     */
    public void setProtein(int protein) {
        this.protein = protein;
    }
    /**
     * getter for the fat
     * @return the fat
     */
    @Override
    public int getFat() {
        return fat;
    }

    /**
     * setter for the fat
     * @param fat the fat
     */
    public void setFat(int fat) {
        this.fat = fat;
    }
    /**
     * getter for the sodium
     * @return the sodium
     */
    @Override
    public int getSodium() {
        return sodium;
    }

    /**
     * setter for the sodium
     * @param sodium the sodium
     */
    public void setSodium(int sodium) {
        this.sodium = sodium;
    }
    /**
     * getter for the price
     * @return the price
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * setter for the price
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
    }



}
