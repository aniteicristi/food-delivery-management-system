package com.FoodDeliveryManagementSystem.BusinessLogic;

import java.util.ArrayList;

/**
 * the composite product is a MenuItem which instead of possesing the properties of a base product, it posseses a list
 * of other menu items, implementing the composite design pattern.
 */
public class CompositeProduct extends MenuItem{

    /**
     * the title of the composite product
     */
    private String title;
    /**
     * the rating of the composite product
     */
    private float rating;
    /**
     * the price of the composite product
     */
    private int price;
    /**
     * an array list of the products which make up the composite product.
     */
    ArrayList<MenuItem> products;

    /**
     * constructor for the composite product
     * @param title the title
     * @param rating the rating
     * @param price the price
     * @param products the composing products
     */
    public CompositeProduct(String title, float rating, int price, ArrayList<MenuItem> products) {
        this.title = title;
        this.rating = rating;
        this.price = price;
        this.products = products;
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
    /**
     * getter for the composing products
     * @return the products
     */
    public ArrayList<MenuItem> getProducts() {
        return products;
    }

}
