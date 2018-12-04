package application;

import java.io.File;
import java.io.FileNotFoundException;
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
			
			boolean finishedReading = false;
			
			while(reader.hasNextLine() && !finishedReading) {
				String[] tokens = reader.nextLine().split(",");
				
				if(tokens == null || tokens.length == 0 || tokens[0] == null || tokens[0] == "") {
					finishedReading = true;
				}
				else {
					tokens[1] = tokens[1].replaceAll("([A-Z])", " $1");

					
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
					+ "If problem persists, please contact system architecture by email: emaccoux@wisc.edu");
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
        // TODO : Complete
        return null;
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
    
    @Override
    public void saveFoodItems(String filename) {
    	
    }

}
