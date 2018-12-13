package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The MealEventHandler class handles all button presses within the GUI,
 * defining which actions should be completed upon a particular press.
 * 
 * @author Tanner Blanke, Eric Maccoux, Jack Pientka, Tony Tu
 *
 */
public class MealEventHandler {
	
	/**
	 * Defines actions for when the "Load Food" button is pressed on the main screen.
	 * 
	 * Loads in a .csv file and displays it.
	 */
	static EventHandler<ActionEvent> loadFoodFromFileHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			// FileChooser object for choosing file
			FileChooser chooser = new FileChooser();
	    	chooser.setTitle("Open Food List File");
	    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".csv files (*.csv, extensions)", "*.csv");
	    	chooser.getExtensionFilters().add(extFilter);
	    	
	    	// creates new stage for FileChooser
	    	Stage chooserStage = new Stage();
	    	File file = chooser.showOpenDialog(chooserStage);
	    	
	    	// if no file has been chosen, return without error
	    	if (file == null) {
	    		event.consume();
	    		return; 
	    	}
	    	
	    	// clears foodItemButtonList
	    	GUI.foodItemButtonList.getChildren().clear();
	    	
	    	try {
	    		// loads items from file into foodData
	    		Main.foodData.loadFoodItems(file.getPath());
		    	
	    		// List<FoodItem> of all items in foodData
		    	List<FoodItem> foodItemList = Main.foodData.getAllFoodItems();
		    	
		    	// loops through foodItemList, creates MealButton for each FoodItem
		    	for(int i = 0; i < foodItemList.size(); i++) {
		    		MealButton newItem = new MealButton(foodItemList.get(i).getName(), foodItemList.get(i));
		    		NodeInitializer.initFoodItemButton(newItem, foodItemList.get(i));
		    		GUI.foodItemButtonList.getChildren().add(newItem);
		    	}
	    	} catch (NullPointerException e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Internal Error, please try again\n\n"
						+ "If problem persists, please contact system architecture by email: emaccoux@wisc.edu");
				dialog.showAndWait();
	    	}
	    	
	    	// alphabetizes foodItemButtonList, displays it in the right pane
	    	GUI.foodItemButtonList = alphabetize(GUI.foodItemButtonList);
	    	GUI.foodPane.setContent(GUI.foodItemButtonList);
	    	
	        event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Save Food" button is pressed on the main screen.
	 * 
	 * Saves food list to a .csv file.
	 */
	static EventHandler<ActionEvent> saveFoodToFileHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			// FileChooser for choosing where to save file
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Save Food List File");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".csv files (*.csv, extensions)", "*.csv");
	    	chooser.getExtensionFilters().add(extFilter);
			
			// new Stage for FileChooser
			Stage chooserStage = new Stage();
			File file = chooser.showSaveDialog(chooserStage);
			
			// if no file has been chosen, return without error
	    	if (file == null) {
	    		event.consume();
	    		return;
	    	}
			
			try {
				// calls foodData's saveFoodItems method to save FoodItems to file
				Main.foodData.saveFoodItems(file.getPath());
			} catch (NullPointerException e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Internal Error, please try again\n\n"
						+ "If problem persists, please contact system architecture by email: emaccoux@wisc.edu");
				dialog.showAndWait();
	    	}
			
	        event.consume();
		}
	};
	
	/**
	 * Defines actions for when a FoodItem button is pressed in either the food list (left pane)
	 * or meal list (right pane).
	 * 
	 * Displays information about selected FoodItem.
	 */
	static EventHandler<ActionEvent> foodItemButtonHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			// if food info screen for selected item is already being displayed, go back to "Select Food" screen
			if(GUI.currentScreen == 1 && GUI.currentFood != null && GUI.currentFood.getID().equals(((MealButton) event.getSource()).getFoodItem().getID())) {
				GUI.root.setCenter(GUI.selectFoodLabel);
				GUI.currentScreen = 0;
			} else {
				// gets FoodItem that corresponds to the MealButton clicked
				FoodItem itemToFind = ((MealButton) event.getSource()).getFoodItem();
				GUI.currentFood = itemToFind;
				
				// labels for all of the FoodItem's information
				Label name = new Label("Food: " + itemToFind.getName());
				Label calories = new Label("Calories: " + itemToFind.getNutrientValue("calories"));
				Label fat = new Label("Fat (Grams): " + itemToFind.getNutrientValue("fat"));
				Label carbohydrates = new Label("Carbohydrates (Grams): " + itemToFind.getNutrientValue("carbohydrate"));
				Label fiber = new Label("Fiber (Grams): " + itemToFind.getNutrientValue("fiber"));
				Label protein = new Label("Protein (Grams): " + itemToFind.getNutrientValue("protein"));
				
				// initializes info labels
				NodeInitializer.initLabel(name, 1);
				NodeInitializer.initLabel(calories, 1);
				NodeInitializer.initLabel(fat, 1);
				NodeInitializer.initLabel(carbohydrates, 1);
				NodeInitializer.initLabel(fiber, 1);
				NodeInitializer.initLabel(protein, 1);
				
				// sets font for name label
				name.setFont(Font.font("Arial", 24));
				
				// "Add to Meal" button
				Button addToMealButton = new Button("Add to Meal");
				NodeInitializer.initButton(addToMealButton);
				addToMealButton.setOnAction(MealEventHandler.addToMealHandler);
				
				// adds all items on food info screen to a VBox, aligns box to top-center of screen
				VBox foodInfoScreen = new VBox();
				foodInfoScreen.getChildren().addAll(name, calories, fat, carbohydrates, fiber, protein, addToMealButton);
				foodInfoScreen.setAlignment(Pos.TOP_CENTER);
				
				// sets margins for all items in foodInfoScreen
				VBox.setMargin(name, new Insets(14));
				VBox.setMargin(calories, new Insets(14));
				VBox.setMargin(fat, new Insets(14));
				VBox.setMargin(carbohydrates, new Insets(14));
				VBox.setMargin(fiber, new Insets(14));
				VBox.setMargin(protein, new Insets(14));
				VBox.setMargin(addToMealButton, new Insets(14));
				
				// displays foodInfoScreen
				GUI.root.setCenter(foodInfoScreen);
				GUI.currentScreen = 1;
			}
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Options" button is pressed on the main screen.
	 * 
	 * Opens up "Options" screen.
	 */
	static EventHandler<ActionEvent> optionsHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			
			// if currently on options screen when button is pressed, go back to "Select Food" screen
			// else, display options screen
			if(GUI.currentScreen == 2) {
				GUI.root.setCenter(GUI.selectFoodLabel);
				GUI.currentScreen = 0;

			} else {
				GUI.root.setCenter(GUI.optionsScreen);
				GUI.currentScreen = 2;
			}
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Query" button is pressed on the main screen.
	 * 
	 * Opens up "Query" screen.
	 */
	static EventHandler<ActionEvent> queryHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {

			// if currently on options screen when button is pressed, go back to "Select Food" screen
			// else, display query screen
			if(GUI.currentScreen == 3) {
				GUI.root.setCenter(GUI.selectFoodLabel);
				GUI.currentScreen = 0;
			} else {
				GUI.root.setCenter(GUI.queryScreen);
				GUI.currentScreen = 3;
			}
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Add Rule" button is pressed on the "Query" screen.
	 * 
	 * Adds inputted rule to rule list.
	 */
	static EventHandler<ActionEvent> addRuleHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			String nameToAdd = "";		// name rule to add to rule list
			
			// if there is text in queryNameField, set nameToAdd to that text
			if(!GUI.queryNameField.getText().equals("")) {
				nameToAdd = GUI.queryNameField.getText();
			}
			
			// if nameToAdd is not empty, create a "Remove" button,
			// place it in an HBox with nameToAdd, and add the HBox to ruleList
			if(!nameToAdd.equals("")) {
				Label ruleLabel = new Label(nameToAdd);
				ruleLabel.setFont(Font.font("Arial", 20));
				Button removeButton = new Button("Remove");
				removeButton.setOnAction(MealEventHandler.removeRuleHandler);
				HBox completeRule = new HBox(8);
				completeRule.getChildren().addAll(ruleLabel, removeButton);
				GUI.ruleList.getChildren().add(completeRule);
			}
			
			String ruleToAdd = "";		// nutrient rule to be added
			
			// determines which nutrient type is selected and adds it to ruleToAdd
			if(GUI.ruleTypeGroup.getToggles().get(0).isSelected()) {
				ruleToAdd += "calories ";
			} else if(GUI.ruleTypeGroup.getToggles().get(1).isSelected()) {
				ruleToAdd += "fat ";
			} else if(GUI.ruleTypeGroup.getToggles().get(2).isSelected()) {
				ruleToAdd += "carbohydrate ";
			} else if(GUI.ruleTypeGroup.getToggles().get(3).isSelected()) {
				ruleToAdd += "fiber ";
			} else if(GUI.ruleTypeGroup.getToggles().get(4).isSelected()) {
				ruleToAdd += "protein ";
			} else {
				event.consume();
				return;
			}
			
			// determines which comparator is selected and adds it to ruleToAdd
			if(GUI.comparatorRuleGroup.getToggles().get(0).isSelected()) {
				ruleToAdd += ">= ";
			} else if(GUI.comparatorRuleGroup.getToggles().get(1).isSelected()) {
				ruleToAdd += "== ";
			} else if(GUI.comparatorRuleGroup.getToggles().get(2).isSelected()) {
				ruleToAdd += "<= ";
			} else {
				event.consume();
				return;
			}
			
			// parses text in queryNumberField, adds it to ruleToAdd
			if(!GUI.queryNumberField.getText().equals("")) { 
				double numberToAdd = 0.0;
				
				try {
					numberToAdd = Double.parseDouble(GUI.queryNumberField.getText());
				} catch (NumberFormatException e) {
					event.consume();
					return;
				}
				
				if(numberToAdd >= 0.0) {
					ruleToAdd += numberToAdd;
				} else {
					event.consume();
					return;
				}
			} else {
				event.consume();
				return;
			}
			
			// creates a "Remove" button and rule label, places them into an HBox,
			// and adds that HBox to ruleList
			Label ruleLabel = new Label(ruleToAdd);
			ruleLabel.setFont(Font.font("Arial", 20));
			Button removeButton = new Button("Remove");
			removeButton.setOnAction(MealEventHandler.removeRuleHandler);
			HBox completeRule = new HBox(8);
			completeRule.getChildren().addAll(ruleLabel, removeButton);
			GUI.ruleList.getChildren().add(completeRule);
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Remove" button is pressed within the rule list on the "Query" screen.
	 * 
	 * Removes the rule from the rule list.
	 */
	static EventHandler<ActionEvent> removeRuleHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			// removes rule and "Remove" button from ruleList
			GUI.ruleList.getChildren().remove(((Button) event.getSource()).getParent());
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Apply Query" button is pressed on the "Query" screen.
	 * 
	 * Applies selected rules to left pane's food list.
	 */
	static EventHandler<ActionEvent> applyQueryHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			// clears queryFoodData and queryButtonList objects
			Main.queryFoodData = new FoodData();
			GUI.queryButtonList.getChildren().clear();
			
			// if there are no rules to apply, return
			if(GUI.ruleList.getChildren().size() == 0) {
				event.consume();
				return;
			}
			
			List<String> listOfRules = new ArrayList<String>();		// list of nutrient rules to be applied
			List<String> listOfNames = new ArrayList<String>();		// list of name rules to be applied
			
			// loops through list of rules to be applied, determines if rule is a name rule or nutrient rule,
			// and inserts it into the appropriate list
			for(int i = 0; i < GUI.ruleList.getChildren().size(); i++) {
				Label currentRule = (Label) ((HBox) GUI.ruleList.getChildren().get(i)).getChildren().get(0);
				String[] tokens = currentRule.getText().split(" ");
				
				if(tokens[0].equals("calories") || tokens[0].equals("fat") || tokens[0].equals("carbohydrate")
						|| tokens[0].equals("fiber") || tokens[0].equals("protein")) {
					listOfRules.add(currentRule.getText());
				} else {
					listOfNames.add(currentRule.getText());
				}
			}
			
			List<FoodItem> nutrientRuleResults = Main.foodData.filterByNutrients(listOfRules);		// list of FoodItems that result from nutrient rules
			List<ArrayList<FoodItem>> nameRuleResults = new ArrayList<ArrayList<FoodItem>>();		// list of lists of FoodItems that result from name rules
			
			// adds results from name rules to foodNameResults
			for(int i = 0; i < listOfNames.size(); i++) {
				nameRuleResults.add((ArrayList<FoodItem>) Main.foodData.filterByName(listOfNames.get(i)));
			}
			
			// if there are nutrient rules to parse through
			if (nutrientRuleResults.size() > 0) {
				
				// if a FoodItem is in all results (for both name and nutrient rules), 
				// create a MealButton for it and add that MealButton to queryButtonList
				for(int i = 0; i < nutrientRuleResults.size(); i++) {
					boolean inAllLists = true;
					
					for(int j = 0; j < nameRuleResults.size(); j++) {
						if(!nameRuleResults.get(j).contains(nutrientRuleResults.get(i))) {
							inAllLists = false;
						}
					}
					
					if(inAllLists) {
						Main.queryFoodData.addFoodItem(nutrientRuleResults.get(i));
						MealButton newItem = new MealButton(nutrientRuleResults.get(i).getName(), nutrientRuleResults.get(i));
			    		NodeInitializer.initFoodItemButton(newItem, nutrientRuleResults.get(i));
			    		GUI.queryButtonList.getChildren().add(newItem);
					}
				}
			// if there are no nutrient rules to parse through but there are name rules to parse through
			} else if (nameRuleResults.size() > 0) {
				
				// if a FoodItem is in all name rule results, create a MealButton for it
				// and at that MealButton to queryButtonList
				for(int i = 0; i < nameRuleResults.get(0).size(); i++) {
					boolean inAllLists = true;
					
					for(int j = 0; j < nameRuleResults.size(); j++) {
						if(!nameRuleResults.get(j).contains(nameRuleResults.get(0).get(i))) {
	    					inAllLists = false;
	    				}
					}
					
					if(inAllLists) {
						Main.queryFoodData.addFoodItem(nameRuleResults.get(0).get(i));
						Button newItem = new MealButton(nameRuleResults.get(0).get(i).getName(), nameRuleResults.get(0).get(i));
			    		NodeInitializer.initFoodItemButton(newItem, nameRuleResults.get(0).get(i));
			    		GUI.queryButtonList.getChildren().add(newItem);
					}
				}
			}
			
			// display an alphabetized version of queryButtonList
			GUI.foodPane.setContent(MealEventHandler.alphabetize(GUI.queryButtonList));
			
			// if a query has not already been applied, add a "Reset" button to the left pane
			if(GUI.leftPane.getChildren().size() == 2) {
				Button resetButton = new Button("Reset Query");
				resetButton.setOnAction(resetQueryHandler);
				resetButton.setMaxWidth(260);
				resetButton.setPrefWidth(260);
				GUI.leftPane.getChildren().add(resetButton);
			}
			
			event.consume();
		}
	};

	/**
	 * Defines actions for when the "Reset Query" button is pressed underneath the food list in the left pane.
	 * 
	 * Resets query and displays original food list.
	 */
	static EventHandler<ActionEvent> resetQueryHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			
			// removes reset button
			GUI.leftPane.getChildren().remove(2);
			
			// displays original MealButton list in foodPane
			GUI.foodPane.setContent(GUI.foodItemButtonList);
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Add" button is pressed above the food list in the left pane.
	 * 
	 * Displays "Add New Food" screen.
	 */
	static EventHandler<ActionEvent> addFoodScreenHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			
			// if currently on options screen when button is pressed, go back to "Select Food" screen
			// else, display "Add Food" screen
			if(GUI.currentScreen == 4) {
				GUI.root.setCenter(GUI.selectFoodLabel);
				GUI.currentScreen = 0;
			} else {
				GUI.root.setCenter(GUI.addFoodScreen);
				GUI.currentScreen = 4;
			}

			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Submit" button is pressed on the "Add New Food" screen.
	 * 
	 * Adds new FoodItem to food list (left pane).
	 */
	static EventHandler<ActionEvent> submitNewFoodHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			
			// if there isn't a name inputted, show an error and return
			if(GUI.addFoodNameField.getText().equals("")) {
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Empty food name detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			
			// generates unique ID for new FoodItem
			String uniqueID = UUID.randomUUID().toString();
			
			// new FoodItem to be added to Main.foodData
			FoodItem newFoodItem = new FoodItem(uniqueID, GUI.addFoodNameField.getText());
			
			double inputtedNumber = 0.0;	// number inputted into TextField being parsed through
			
			// Parses input in addFoodCalsField.  If correctly formatted, add this nutrient to newFoodItem.
			// If incorrectly formatted in any way, show an error and return.
			try {
				inputtedNumber = Double.parseDouble(GUI.addFoodCalsField.getText());
				
				if(inputtedNumber < 0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Calories detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			} catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Calories or Empty Calories detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("calories", inputtedNumber);
			
			// Parses input in addFoodFatsField.  If correctly formatted, add this nutrient to newFoodItem.
			// If incorrectly formatted in any way, show an error and return.
			try {
				inputtedNumber = Double.parseDouble(GUI.addFoodFatsField.getText());
				if(inputtedNumber < 0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Fats detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			} catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Fats or Empty Fats detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("fat", inputtedNumber);
			
			// Parses input in addFoodCarbsField.  If correctly formatted, add this nutrient to newFoodItem.
			// If incorrectly formatted in any way, show an error and return.
			try {
				inputtedNumber = Double.parseDouble(GUI.addFoodCarbsField.getText());
				if(inputtedNumber < 0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Carbohydrates detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			} catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Carbohydrates or Empty Carbohydrates detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("carbohydrate", inputtedNumber);
			
			// Parses input in addFoodFibersField.  If correctly formatted, add this nutrient to newFoodItem.
			// If incorrectly formatted in any way, show an error and return.
			try {
				inputtedNumber = Double.parseDouble(GUI.addFoodFibersField.getText());
				if(inputtedNumber<0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Fibers detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			} catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Fibers or Empty Fibers detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("fiber", inputtedNumber);
			
			// Parses input in addFoodProteinsField.  If correctly formatted, add this nutrient to newFoodItem.
			// If incorrectly formatted in any way, show an error and return.
			try {
				inputtedNumber = Double.parseDouble(GUI.addFoodProteinsField.getText());
				if(inputtedNumber<0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Protiens detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			} catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Protiens or Empty Proteins detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("protein", inputtedNumber);
			
			// add newFoodItem to foodData
			Main.foodData.addFoodItem(newFoodItem);
			
			// creates new MealButton for newFoodItem, add it to foodPane
    		MealButton newItem = new MealButton(GUI.addFoodNameField.getText(), newFoodItem);
    		NodeInitializer.initFoodItemButton(newItem, newFoodItem);
    		((VBox) GUI.foodPane.getContent()).getChildren().add(newItem);
    		
    		// alphabetizes foodPane's contents
    		GUI.foodPane.setContent(MealEventHandler.alphabetize((VBox) GUI.foodPane.getContent()));
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Add to Meal" button is pressed on the food info screen.
	 * 
	 * Adds selected food to meal list (right pane).
	 */
	static EventHandler<ActionEvent> addToMealHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			
			// sets itemToAdd to the current food being viewed, adds itemToADd to mealFoodData
			FoodItem itemToAdd = GUI.currentFood;
			Main.mealFoodData.addFoodItem(itemToAdd);
			
			// creates a new MealButton for itemToAdd
			MealButton newFoodButton = new MealButton(itemToAdd.getName(), itemToAdd);
			NodeInitializer.initFoodItemButton(newFoodButton, itemToAdd);

			// sets newVBox to mealPane's current contents, adds newFoodButton to it
			VBox newVBox = (VBox) GUI.mealPane.getContent();
			newVBox.getChildren().add(newFoodButton);
			
			// sets mealPane's contents to an alphabetized version of newVBox
			GUI.mealPane.setContent(MealEventHandler.alphabetize(newVBox));
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Clear" button is pressed above the meal list (right pane).
	 * 
	 * Clears meal list.
	 */
	static EventHandler<ActionEvent> clearMealHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			
			// clears mealPane and mealFoodData
			GUI.mealPane.setContent(new VBox());
			Main.mealFoodData = new FoodData();
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Analyze Meal" button is pressed above the meal list (right pane).
	 * 
	 * Displays "Meal Analysis" screen for current meal.
	 */
	static EventHandler<ActionEvent> analyzeMealHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			
			// if mealFoodData is empty (meaning that there is no food in the meal),
			// show an error and return
			if(Main.mealFoodData.getAllFoodItems().isEmpty()) {
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Meal is empty.  Analysis cannot be completed. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			
			double totalCals = 0.0;			// total calories of meal
			double totalFats = 0.0;			// total fats of meal
			double totalCarbs = 0.0;		// total carbohydrates of meal
			double totalFiber = 0.0;		// total fiber of meal
			double totalProtein = 0.0;		// total protein of meal
			
			List<FoodItem> mealFoodDataList = Main.mealFoodData.getAllFoodItems();	// list of all FoodItem objects in mealFoodData
			
			// for each item in mealFoodDataList, add its nutrient values
			// to totalCals, totalFats, totalCarbs, totalFiber, and totalProtein
			for(FoodItem item : mealFoodDataList) {
				totalCals += item.getNutrientValue("calories");
				totalFats += item.getNutrientValue("fat");
				totalCarbs += item.getNutrientValue("carbohydrate");
				totalFiber += item.getNutrientValue("fiber");
				totalProtein += item.getNutrientValue("protein");
			}
			
			// title/nutrient labels
			Label titleLabel = new Label("Meal Analysis");
			Label caloriesLabel = new Label("Total Calories: " + totalCals);
			Label fatLabel = new Label("Total Fat (Grams): " + totalFats);
			Label carbohydratesLabel = new Label("Total Carbohydrates (Grams): " + totalCarbs);
			Label fiberLabel = new Label("Total Fiber (Grams): " + totalFiber);
			Label proteinLabel = new Label("Total Protein (Grams): " + totalProtein);
			
			// initializes title/nutrient labels
			NodeInitializer.initLabel(titleLabel, 1);
			NodeInitializer.initLabel(caloriesLabel, 1);
			NodeInitializer.initLabel(fatLabel, 1);
			NodeInitializer.initLabel(carbohydratesLabel, 1);
			NodeInitializer.initLabel(fiberLabel, 1);
			NodeInitializer.initLabel(proteinLabel, 1);
			
			// sets margins for title/nutrient labels
			VBox.setMargin(titleLabel, new Insets(14));
			VBox.setMargin(caloriesLabel, new Insets(14));
			VBox.setMargin(fatLabel, new Insets(14));
			VBox.setMargin(carbohydratesLabel, new Insets(14));
			VBox.setMargin(fiberLabel, new Insets(14));
			VBox.setMargin(proteinLabel, new Insets(14));
			
			// sets font for titleLabel
			titleLabel.setFont(Font.font("Arial", 24));
			
			// adds all labels to VBox, aligns VBox to center
			VBox analyzeMealScreen = new VBox();
			analyzeMealScreen.getChildren().addAll(titleLabel, caloriesLabel, fatLabel, carbohydratesLabel,
					fiberLabel, proteinLabel);
			analyzeMealScreen.setAlignment(Pos.TOP_CENTER);
			
			// displays "Analyze Meal" screen
			GUI.root.setCenter(analyzeMealScreen);
			
			event.consume();
		}
	};
	
	/**
	 * Defines actions for when the "Help" button is pressed on the main screen.
	 * 
	 * Displays "Help" dialog box that gives information pertaining to the current screen.
	 */
	static EventHandler<ActionEvent> helpButtonHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			// creates Alert dialog box
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
			dialog.setTitle("Food Query and Meal Analysis User Information");
			
			// sets header text for dialog
			dialog.setHeaderText("Use this program to organize your food ingrediants and meals.\n\n"
					+ "	- On the left is a list of your available foods. Load foods from file with \"Load Foods\" button\n"
					+ "		at the bottom or create a new food with the \"Add\" button at top of food list. You can \n"
					+ "		save your current list of foods to a file with the \"Save Foods\" button at the bottom.\n\n"
					+ "	- On the right is your current meal with the foods you have chosen. The \"Analyze Meal\" button\n"
					+ "		shows the nutritional values of the meal. The \"Clear\" button clears the entire meal.\n\n"
					+ "	- To add food items to a meal, you must first create a food or load foods from file with either\n"
					+ "		the \"Load Foods\" button or the \"Add\" button.  Then, click on the food item listed in the\n"
					+ "		food list and click \"Add to Meal\".  From there, the food should show up in the meal list.");
			
			// sets content text relevant to screen being currently viewed
			if(GUI.currentScreen == 2) {
				dialog.setContentText("Here on the \"Options\" page, you can choose a color for the dropshadow used "
						+ "in the program or turn it off.");
			}
			if(GUI.currentScreen == 3) {
				dialog.setContentText("Here on the \"Query\" page, you can filter your list of foods by your specified rules.\n"
					+ "	- To add a rule pick one nutrient, one comparator value, and type in the number you wish to seach by in the box, then click\n"
					+ "		\"Add Rule\". Add as many rules as you like and hit the \"Apply Querey\" button to apply the rules.\n"
					+ "	- To revert the food list back to the original list, hit the \"Reset\" button.");
			}
			if(GUI.currentScreen == 4) {
				dialog.setContentText("Here on the \"Add\" button you must fill in all text fields with the appropriate values.\n"
						+ "	- Name can be anything.\n"
						+ "	- Nutrients must contain positive numbers.");
			}
			
			// displays dialog box
 			dialog.showAndWait();
		}
	};
	
	/**
	 * Defines actions for when a color option is chosen on the "Options" screen.
	 * 
	 * Switches the current color theme to the selected option.
	 */
	static EventHandler<ActionEvent> colorHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			
			String color = ((RadioButton) event.getSource()).getText();
			
			if(color.equals("Badger Red")) {
				GUI.scene.getStylesheets().clear();
				GUI.scene.getStylesheets().add(getClass().getResource("themeRed.css").toExternalForm());
			} else if(color.equals("Blue")) {
				GUI.scene.getStylesheets().clear();
				GUI.scene.getStylesheets().add(getClass().getResource("themeBlue.css").toExternalForm());
			} else if(color.equals("Purple")) {
				GUI.scene.getStylesheets().clear();
				GUI.scene.getStylesheets().add(getClass().getResource("themePurple.css").toExternalForm());
			} else if(color.equals("Green")) {
				GUI.scene.getStylesheets().clear();
				GUI.scene.getStylesheets().add(getClass().getResource("themeGreen.css").toExternalForm());
			} else if(color.equals("Off")) {
				GUI.scene.getStylesheets().clear();
				GUI.scene.getStylesheets().add(getClass().getResource("themeRegular.css").toExternalForm());
			}
			
			event.consume();
		}
	};
	
	/**
	 * Alphabetizes a VBox of MealButtons.
	 * 
	 * @param boxToAlphabetize VBox of MealButtons to be alphabetized
	 * @return alphabetized VBox of MealButtons
	 */
	private static VBox alphabetize(VBox boxToAlphabetize) {
		// converts VBox to Object[], sorts this array
		Object[] array = boxToAlphabetize.getChildren().toArray();
    	Arrays.sort(array);
    	
    	// VBox for storing newly-alphabetized objects
    	VBox newFoodList = new VBox();
    	
    	// adds all alphabetized objects to VBox
    	for(int i = 0; i < array.length; i++) {
    		newFoodList.getChildren().add((Node) array[i]);
    	}
    	
    	return newFoodList;
	}
	
}
