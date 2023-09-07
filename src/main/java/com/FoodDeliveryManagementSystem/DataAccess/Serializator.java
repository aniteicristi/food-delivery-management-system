package com.FoodDeliveryManagementSystem.DataAccess;

import com.FoodDeliveryManagementSystem.BusinessLogic.MenuItem;
import com.FoodDeliveryManagementSystem.BusinessLogic.Order;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * a class which contains several static and generic methods for serializing and deserailizng objects.
 */
public class Serializator {
    /**
     * loads an array of deserialized items of type T from the filename.
     * @param filename the file to laod from
     * @param tclass the class of the object deserialized
     * @param <T> the type of the object to be deserialized
     * @return an array of deserialized objects
     */
    public static <T> ArrayList<T> loadFrom(String filename, Class<T> tclass){
        ArrayList<T> list = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream(filename);
            if(fs.available() == 0){
                fs.close();
                return list;
            }
            ObjectInputStream stream = new ObjectInputStream(fs);
            while(true){
                try {
                    list.add((T) stream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
            stream.close();
            fs.close();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            ex.printStackTrace();
            a.setContentText(ex.toString());
            a.show();
        }
        return list;
    }

    /**
     * serializes an array of items of type T to the file specified
     * @param filename the file to serialize to
     * @param list the list to be serialized
     * @param <T> the type of the serialized object
     */
    public static <T> void persistTo(String filename, ArrayList<T> list){
        try {
            FileOutputStream fs = new FileOutputStream(filename);
            ObjectOutputStream stream = new ObjectOutputStream(fs);
            list.forEach((elem) -> {
                try{
                    stream.writeObject(elem);
                }
                catch (Exception ex){
                    System.out.println(ex);
                }
            });
            stream.close();
            fs.close();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            ex.printStackTrace();
            a.setContentText(ex.toString());
            a.show();
        }
    }

    /**
     * loads a map of deserialized items  from the filename.
     * @param filename the file to load from
     * @return the map
     */
    public static HashMap<Order, ArrayList<MenuItem>> loadMapFrom(String filename) {
        HashMap<Order, ArrayList<MenuItem>> map = new HashMap<>();
        try {
            FileInputStream fs = new FileInputStream(filename);
            if(fs.available() == 0){
                System.out.println("yes");
                fs.close();
                return map;
            }
            ObjectInputStream stream = new ObjectInputStream(fs);
            map = (HashMap<Order, ArrayList<MenuItem>>) stream.readObject();
            stream.close();
            fs.close();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            ex.printStackTrace();
            a.setContentText(ex.toString());
            a.show();
        }
        return map;
    }

    /**
     * serializes a map of items  to the file specified
     * @param filename the file to serialize to
     * @param map the map to serialize
     */
    public static void persistMapTo(String filename, HashMap<Order, ArrayList<MenuItem>> map){
        try {
            FileOutputStream fs = new FileOutputStream(filename);
            ObjectOutputStream stream = new ObjectOutputStream(fs);
            stream.writeObject(map);
            stream.close();
            fs.close();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR);
            ex.printStackTrace();
            a.setContentText(ex.toString());
            a.show();
        }
    }
}
