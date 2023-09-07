package com.FoodDeliveryManagementSystem.DataAccess;

import com.FoodDeliveryManagementSystem.BusinessLogic.BaseProduct;
import com.FoodDeliveryManagementSystem.Presentation.BaseController;

import java.io.*;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * a class that handles the parsing of a CSV file and reading base products from it.
 */
public class CSVParser{

    /**
     * a method which takes as an argument a file and returns a hashset of base products.
     * @param controller the controller is passed in the eventuality of any errors
     * @param file the file to be parsed
     * @return the hashset of baseproducts parsed from the file
     */
    public HashSet<BaseProduct> Parse(BaseController controller, File file){
        HashSet<BaseProduct> set = new HashSet<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            return reader.lines().skip(1)
                    .map(line -> line.split(","))
                    .map(data ->
                            new BaseProduct(
                                    data[0],
                                    Float.parseFloat(data[1]),
                                    Integer.parseInt(data[2]),
                                    Integer.parseInt(data[3]),
                                    Integer.parseInt(data[4]),
                                    Integer.parseInt(data[5]),
                                    Integer.parseInt(data[6]))
                    ).collect(Collectors.toCollection(HashSet::new));

        }
        catch (Exception e) {
            e.printStackTrace();
            controller.showError("Failure parsing the csv file. Invalid format or file.");
        }
        return set;
    }

}
