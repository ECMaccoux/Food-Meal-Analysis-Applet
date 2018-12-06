package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        indexes.put("calories", new BPTree<Double, FoodItem>(3));
        indexes.put("fat", new BPTree<Double, FoodItem>(3));
        indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
        indexes.put("fiber", new BPTree<Double, FoodItem>(3));
        indexes.put("protein", new BPTree<Double, FoodItem>(3));
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
    	
		try {
			File file = new File(filePath);
			Scanner reader = new Scanner(file);
			
			foodItemList.clear();
	    	indexes.clear();
	    	indexes.put("calories", new BPTree<Double, FoodItem>(3));
	        indexes.put("fat", new BPTree<Double, FoodItem>(3));
	        indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
	        indexes.put("fiber", new BPTree<Double, FoodItem>(3));
	        indexes.put("protein", new BPTree<Double, FoodItem>(3));
			
			boolean finishedReading = false;
			
			while(reader.hasNextLine() && !finishedReading) {
				String[] tokens = reader.nextLine().split(",");
				
				if(tokens == null || tokens.length == 0 || tokens[0] == null || tokens[0] == "") {
					finishedReading = true;
				}
				else {
					
					FoodItem item = new FoodItem(tokens[0], tokens[1]);
					
					for(int i = 2; i < tokens.length - 1; i += 2) {
						item.addNutrient(tokens[i], Double.parseDouble(tokens[i + 1]));
					}
					
					addFoodItem(item);
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
     * 
     */
    @Override
    public void saveFoodItems(String filename) {
    	try {
    		PrintWriter writer = new PrintWriter(filename);
    		
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

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        List<FoodItem> listToReturn = new ArrayList<FoodItem>();
        
        for(int i = 0; i < foodItemList.size(); i++) {
        	if(foodItemList.get(i).getName().equals(substring)) {
        		listToReturn.add(foodItemList.get(i));
        	}
        }
        return listToReturn;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	List<FoodItem> listToReturn = new ArrayList<FoodItem>();
    	List<List<FoodItem>> listsOfNutrients = new ArrayList<List<FoodItem>>();
    	
    	for(String rule : rules) {
    		String[] tokens = rule.split(" ");
    		listsOfNutrients.add(indexes.get(tokens[0].toLowerCase()).rangeSearch(Double.parseDouble(tokens[2]), tokens[1]));
    	}
    	
    	if(listToReturn.size() == 0) {
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

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
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

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }
}
