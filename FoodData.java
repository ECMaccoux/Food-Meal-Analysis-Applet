package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javafx.scene.control.Alert;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu), Tony Tu, Eric Maccoux, Tanner Blanke, Jack Pientka
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
        foodItemList = new ArrayList<FoodItem>();
        
        // creates HashMap and populates it with BPTree objects corresponding to each nutrient type
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        indexes.put("calories", new BPTree<Double, FoodItem>(3));
        indexes.put("fat", new BPTree<Double, FoodItem>(3));
        indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
        indexes.put("fiber", new BPTree<Double, FoodItem>(3));
        indexes.put("protein", new BPTree<Double, FoodItem>(3));
    }
    
    
    /**
     * Loads the data in the .csv file
     * 
     * file format:
     * <id1>,<name>,<nutrient1>,<value1>,<nutrient2>,<value2>,...
     * <id2>,<name>,<nutrient1>,<value1>,<nutrient2>,<value2>,...
     * 
     * Example:
     * 556540ff5d613c9d5f5935a9,Stewarts_PremiumDarkChocolatewithMintCookieCrunch,calories,280,fat,18,carbohydrate,34,fiber,3,protein,3
     * 
     * Note:
     *  1. All the rows are in valid format.
     *  2. All IDs are unique.
     *  3. Names can be duplicate.
     *  4. All columns are strictly alphanumeric (a-zA-Z0-9_).
     *  5. All food items will strictly contain 5 nutrients in the given order:    
     *     calories,fat,carbohydrate,fiber,protein
     *  6. Nutrients are CASE-INSENSITIVE. 
     * 
     * @param filePath path of the food item data file 
     *        (e.g. folder1/subfolder1/.../foodItems.csv) 
     */
    @Override
    public void loadFoodItems(String filePath) {
    	
		try {
			// attempts to open file at filepath
			File file = new File(filePath);
			Scanner reader = new Scanner(file);
			
			// "resets" foodItemList and indexes
			foodItemList.clear();
	    	indexes.clear();
	    	indexes.put("calories", new BPTree<Double, FoodItem>(3));
	        indexes.put("fat", new BPTree<Double, FoodItem>(3));
	        indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
	        indexes.put("fiber", new BPTree<Double, FoodItem>(3));
	        indexes.put("protein", new BPTree<Double, FoodItem>(3));
			
	        // loop goes through each line and parses them, using this information to create FoodItem objects
	        // and add them to foodItemList
	        
			boolean finishedReading = false;
			
			while(reader.hasNextLine() && !finishedReading) {
				String[] tokens = reader.nextLine().split(",");
				
				if(tokens == null || tokens.length == 0 || tokens[0] == null || tokens[0].equals("")) {
					finishedReading = true;
				}
				else {
					
					FoodItem item = new FoodItem(tokens[0], tokens[1]);
					
					for(int i = 2; i < tokens.length - 1; i += 2) {
						item.addNutrient(tokens[i].toLowerCase(), Double.parseDouble(tokens[i + 1]));
					}
					
					HashMap<String, Double> inputtedNutrients = item.getNutrients();
					
					if(inputtedNutrients.containsKey("calories") && inputtedNutrients.containsKey("fat")
							&& inputtedNutrients.containsKey("carbohydrate") && inputtedNutrients.containsKey("fiber")
							&& inputtedNutrients.containsKey("protein")) {
						addFoodItem(item);
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Alert dialog = new Alert(Alert.AlertType.ERROR);
			dialog.setHeaderText("FILE NOT FOUND\n"
					+ "\nPlease try to re-load file\n"
					+ "If problem persists, please contact system administrator by email: emaccoux@wisc.edu");
			dialog.showAndWait();
			e.printStackTrace();
		}
    }
    
    /**
     * Save the list of food items in ascending order by name
     * 
     * @param filename name of the file where the data needs to be saved 
     */
    @Override
    public void saveFoodItems(String filename) {
    	try {
    		PrintWriter writer = new PrintWriter(filename);
    		
    		// loops through every item in foodItemList and adds its information to a String
    		// which is then printed into the output file at path filename
    		
    		for(FoodItem item : foodItemList) {
    			String outputLine = "";
    			
    			outputLine += (item.getID() + ",");
    			outputLine += (item.getName() + ",");
    			outputLine += ("calories" + "," + item.getNutrientValue("calories") + ",");
    			outputLine += ("fat" + "," + item.getNutrientValue("fat") + ",");
    			outputLine += ("carbohydrate" + "," + item.getNutrientValue("carbohydrate") + ",");
    			outputLine += ("fiber" + "," + item.getNutrientValue("fiber") + ",");
    			outputLine += ("protein" + "," + item.getNutrientValue("protein"));
    			
    			writer.println(outputLine);
    		}
    		
    		writer.close();
    	} catch (FileNotFoundException e) {
			Alert dialog = new Alert(Alert.AlertType.ERROR);
			dialog.setHeaderText("FILE NOT FOUND\n"
					+ "\nPlease try to re-load file\n"
					+ "If problem persists, please contact system administrator by email: emaccoux@wisc.edu");
			dialog.showAndWait();
			e.printStackTrace();
		} 
    }

    /**
     * Gets all the food items that have name containing the substring.
     * 
     * Example:
     *     All FoodItem
     *         51c38f5d97c3e6d3d972f08a,Similac_FormulaSoyforDiarrheaReadytoFeed,calories,100,fat,0,carbohydrate,0,fiber,0,protein,3
     *         556540ff5d613c9d5f5935a9,Stewarts_PremiumDarkChocolatewithMintCookieCrunch,calories,280,fat,18,carbohydrate,34,fiber,3,protein,3
     *     Substring: soy
     *     Filtered FoodItem
     *         51c38f5d97c3e6d3d972f08a,Similac_FormulaSoyforDiarrheaReadytoFeed,calories,100,fat,0,carbohydrate,0,fiber,0,protein,3
     * 
     * Note:
     *     1. Matching should be CASE-INSENSITIVE.
     *     2. The whole substring should be present in the name of FoodItem object.
     *     3. substring will be strictly alphanumeric (a-zA-Z0-9_)
     * 
     * @param substring substring to be searched
     * @return list of filtered food items; if no food item matched, return empty list
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        List<FoodItem> listToReturn = new ArrayList<FoodItem>();
        
        for(int i = 0; i < foodItemList.size(); i++) {
        	if(foodItemList.get(i).getName().toLowerCase().contains(substring.toLowerCase())) {
        		listToReturn.add(foodItemList.get(i));
        	}
        }
        return listToReturn;
    }
    
    /**
     * DO JAVADOC STUFF
     */
    public FoodItem filterByID(String id) {
    	for(int i = 0; i < foodItemList.size(); i++) {
    		if(foodItemList.get(i).getID().equals(id)) {
    			return foodItemList.get(i);
    		}
    	}
    	
    	return null;
    }

    /**
     * Gets all the food items that fulfill ALL the provided rules
     *
     * Format of a rule:
     *     "<nutrient> <comparator> <value>"
     * 
     * Definition of a rule:
     *     A rule is a string which has three parts separated by a space:
     *         1. <nutrient>: Name of one of the 5 nutrients [CASE-INSENSITIVE]
     *         2. <comparator>: One of the following comparison operators: <=, >=, ==
     *         3. <value>: a double value
     * 
     * Note:
     *     1. Multiple rules can contain the same nutrient.
     *         E.g. ["calories >= 50.0", "calories <= 200.0", "fiber == 2.5"]
     *     2. A FoodItemADT object MUST satisfy ALL the provided rules i
     *        to be returned in the filtered list.
     *
     * @param rules list of rules
     * @return list of filtered food items; if no food item matched, return empty list
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	List<FoodItem> listToReturn = new ArrayList<FoodItem>();
    	List<List<FoodItem>> listsOfNutrients = new ArrayList<List<FoodItem>>();
    	
    	for(String rule : rules) {
    		String[] tokens = rule.split(" ");
    		listsOfNutrients.add(indexes.get(tokens[0].toLowerCase()).rangeSearch(Double.parseDouble(tokens[2]), tokens[1]));
    	}
    	
    	if(listsOfNutrients.size() == 0) {
    		return listToReturn;
    	} else {
    		for(int i = 0; i < listsOfNutrients.get(0).size(); i++) {
    			boolean inAllLists = true;
    			
    			for(int j = 0; j < listsOfNutrients.size(); j++) {
    				if(!listsOfNutrients.get(j).contains(listsOfNutrients.get(0).get(i))) {
    					inAllLists = false;
    				}
    			}
    			
    			if(inAllLists) {
    				listToReturn.add(listsOfNutrients.get(0).get(i));
    			}
    		}
    		
    		return listToReturn;
    	}
    }

    /**
     * Adds a food item to the loaded data.
     * 
     * @param foodItem the food item instance to be added
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
        
        indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
        indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
        indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
        indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
        indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);
    }

    /**
     * Gets the list of all food items.
     * 
     * @return list of FoodItem
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }
}
