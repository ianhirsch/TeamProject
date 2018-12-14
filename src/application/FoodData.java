package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    //private BPTree protein = new BPTree(BranchingFactor);
    
    /**
     * Public constructor
     */
    public FoodData() {
    	this.foodItemList = new ArrayList<FoodItem>();
    	this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
    	BPTree holderTree = new BPTree(3);
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
                //hash the nutrient type, divide it by the number of nutrients. then add it to the indexes bp tree
                
                
                    Iterator it = foodItem.getNutrients().entrySet().iterator();
                    
                    while(it.hasNext()) {
                       Map.Entry pair = (Entry) it.next();
                       if(pair.getKey() == "fiber") {
                          double holder = (double) pair.getValue();
                           indexes.put(pair.getKey().toString(), holderTree<holder, foodItem.getName()>);
                       }
                       
                       }
                       
                       
                       
                        
                        
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
        List<FoodItem> filteredListofFoods = new ArrayList<FoodItem>();
        
        
       String toBeSplit = rules.get(0);
       
       String[] splited = toBeSplit.split(" ");
       
       String nutrient = splited[0].toString();
       String comparator = splited[1].toString();
       Double value = Double.parseDouble(splited[2]);
        
       int hashmapLocation = nutrient.hashCode() % 5;
       
       filteredListofFoods = indexes.get(hashmapLocation).rangeSearch(value, comparator); //performs a rangeSearch on the hashMap of given nutrient
       int i =1;
       while(!(rules.get(i).isEmpty())){
           
           List<FoodItem> newRuleList = new ArrayList<FoodItem>();
           
           String toBeSplitIteration = rules.get(i); //splits next rule
           
           String[] splitedIteration = toBeSplitIteration.split("\\s+");
           
           String nutrientIteration = splitedIteration[0].toString();
           String comparatorIteration = splited[1].toString();
           Double valueIteration = Double.parseDouble(splited[2]);
           
           int hashmapLocationIteration = nutrientIteration.hashCode();
            newRuleList = indexes.get(hashmapLocationIteration).rangeSearch(valueIteration, comparatorIteration);
            
            List<FoodItem> temporaryHolder = new ArrayList<FoodItem>();
            
            for (FoodItem temp : filteredListofFoods) {
                if (newRuleList.contains(temp)) {
                    temporaryHolder.add(temp);
                    }
            }
            
            filteredListofFoods = temporaryHolder;
            
            i = i+1;
          
       }
       
       
       return filteredListofFoods;
        
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
       
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
