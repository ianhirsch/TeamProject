package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.io.*;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
    	this.foodItemList = new ArrayList<FoodItem>();
    	this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(file);
            String next = reader.readLine();
            while(next != null && !next.equals("") && !next.equals(",,,,,,,,,,,")) {
                String [] data = next.split(",");
            
                FoodItem foodItem = new FoodItem(data[0] , data[1]);
            
                for (int i = 2; i < data.length; i = i + 2){
                    foodItem.addNutrient(data[i], Double.parseDouble(data[i + 1]));
                }
                addFoodItem(foodItem);
                next = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.println("exception was thrown.");
        }
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        List<FoodItem> nameFilter = new ArrayList<FoodItem>();
        for (FoodItem temp : foodItemList) {
            if (temp.getName().toLowerCase().contains(substring.toLowerCase())) {
                nameFilter.add(temp);
            }
        }
        return nameFilter;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        // TODO : Complete
        foodItemList.add(foodItem);
    }
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }
    
    public void saveFoodItems(String filename) {
    	try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (FoodItem temp : foodItemList) {
            writer.write(temp.getID() + ",");
            writer.write(temp.getName() + ",");
            HashMap<String,Double> nutrients = temp.getNutrients();
            for(String key : nutrients.keySet()) {
                writer.write(key + ",");
                writer.write(temp.getNutrientValue(key) + ",");
            }
            writer.newLine();
        }
        writer.close();
    	} catch(IOException e) {
    		//THROW ERROR MESSAGE
    	}
    }   

}
