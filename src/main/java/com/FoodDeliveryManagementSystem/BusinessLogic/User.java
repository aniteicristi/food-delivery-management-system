package com.FoodDeliveryManagementSystem.BusinessLogic;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

/**
 * The user class holds the information about the users.
 * This class is serializable.
 */
public class User implements Serializable {
    /**
     * The serial version UID used to identify the serialized objects
     */
    private static final long serialVersionUID = 1L;
    /**
     * the email of the user
     */
    private String email;
    /**
     * the password of the user
     */
    private String password;
    /**
     * The role of the user.
     * It can be of three values: employee, admin and client.
     */
    private String role;

    /**
     *
     * @param email the email of the user
     * @param password the password of the user
     * @param role the role of the user
     */
    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Concatenates the email and role of the user and returns it
     * @return the string form representation of the user
     */
    @Override
    public String toString() {
        return email + " | " + role;
    }

    /**
     * getter for the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * getter for the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * getter for the role
     * @return the role.
     */
    public String getRole() {
        return role;
    }



}
