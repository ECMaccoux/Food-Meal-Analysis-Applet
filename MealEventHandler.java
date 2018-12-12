package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * MealEventHandler class
 * 
 * @author Tony Tu, Eric Maccoux, Tanner Blanke, Jack Pientka
 *
 */
public class MealEventHandler {
	
	// int foodInfoScene: 	0 = default
	//						2 = options
	//						3 = create meal (unneeded)
	//						4 = food info
	// 						5 = query screen
	//						6 = add FoodItem
	
	static VBox alphabetize(VBox boxToAlphabetize) {
		Object[] array = boxToAlphabetize.getChildren().toArray();
    	Arrays.sort(array);
    	
    	VBox newFoodList = new VBox();
    	
    	for(int i = 0; i < array.length; i++) {
    		newFoodList.getChildren().add((Node) array[i]);
    	}
    	
    	return newFoodList;
	}
	
	/**
	 * Handler that will be used by the load button.
	 * 
	 * Pops up a browser that will search through the hard drive.
	 */
	static EventHandler<ActionEvent> loadFoodHandler = new EventHandler<ActionEvent>() {
	    @SuppressWarnings("unchecked")
		@Override
	    public void handle(ActionEvent event) {
	    	FileChooser chooser = new FileChooser();
	    	chooser.setTitle("Open Food List File");
	    	
	    	Stage chooserStage = new Stage();
	    	File file = chooser.showOpenDialog(chooserStage);
	    	int errors = 0;
	    	boolean error = false;
	    	try {
	    		Main.foodDataList.loadFoodItems(file.getPath());
		    	
		    	List<FoodItem> list = Main.foodDataList.getAllFoodItems();
		    	
		    	for(int i = 0; i < list.size(); i++) {
					for(Node button : Main.foodList.getChildren()) {
						if(((MealButton) button).getFoodItem().getID().equals(list.get(i).getID())) {
							errors += 1;
							error = true;
						}else {
							error = false;
						}
					}
					if(!error) {
			    		MealButton newItem = new MealButton(list.get(i).getName(), list.get(i));
			    		GUI.initFoodItemButton(newItem, list.get(i));
			    		Main.foodList.getChildren().add(newItem);
					}
		    	}
				if(errors > 0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Duplicate Meal ID's detected. There was " + errors + " duplicates detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
				}
	    	} catch (NullPointerException e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Internal Error, please try again\n\n"
						+ "If problem persists, please contact system architecture by email: emaccoux@wisc.edu");
				dialog.showAndWait();
				e.printStackTrace();
	    	}
	    	
	    	Main.foodList = MealEventHandler.alphabetize(Main.foodList);
	    	Main.foodPane.setContent(Main.foodList);
	    	
	        event.consume();
	    }
	};
	
	/**
	 * Handler that will be used by the save button.
	 * 
	 * Saves all the in the current list in a foodItems.csv file in the any folder.
	 */
	static EventHandler<ActionEvent> saveFoodHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Save Food List File");
			
			Stage chooserStage = new Stage();
			File file = chooser.showSaveDialog(chooserStage);
			
			try {
				Main.foodDataList.saveFoodItems(file.getPath());
			} catch (NullPointerException e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Internal Error, please try again\n\n"
						+ "If problem persists, please contact system architecture by email: emaccoux@wisc.edu");
				dialog.showAndWait();
				e.printStackTrace();
	    	}
			
	        event.consume();
		}
	};
	
	/**
	 * Handler for the options button.
	 * 
	 * Opens up the options menu
	 */
	static EventHandler<ActionEvent> optionsHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 2) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;

			}else {
				Main.root.setCenter(Main.optionsBox);
				Main.foodInfoScene = 2;
			}
			event.consume();
		}
	};
	
	/**
	 * Handler for the create meal button.
	 * 
	 * Opens up the create meal menu
	 */
	static EventHandler<ActionEvent> createMealHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 3) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
			}else {
				//Main.root.setCenter(Main.mealListAddBox);
				Main.foodInfoScene = 3;
			}
			event.consume();
		}
	};
	
	/**
	 * Handler for the add button in the create meal button.
	 * 
	 * Opens up the add button in the create meal menu
	 */
	static EventHandler<ActionEvent> addToMealHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			
			//FoodItem itemToAdd = Main.foodDataList.filterByID(Main.currID);
			FoodItem itemToAdd = Main.currFood;
			Main.mealFoodDataList.addFoodItem(itemToAdd);
			
			MealButton newFoodButton = new MealButton(itemToAdd.getName(), itemToAdd);
			GUI.initFoodItemButton(newFoodButton, itemToAdd);

			VBox newVBox = (VBox) Main.mealPane.getContent();
			newVBox.getChildren().add(newFoodButton);
			
			Main.mealPane.setContent(MealEventHandler.alphabetize(newVBox));
			
			event.consume();
		}
	};
	
	/**
	 * Handler for the scroll Meal button.
	 * 
	 * Opens up the scroll Meal menu.
	 */
	static EventHandler<ActionEvent> scrollFoodHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			if (Main.foodInfoScene == 4 && ("Food: " + ((Button) event.getSource()).getText()).equalsIgnoreCase(Main.food.getText())) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
			} else if(Main.foodInfoScene == 3){
				Main.createMealField.setText(((Button) event.getSource()).getText());
			} else {
				Main.currFood = ((MealButton) event.getSource()).getFoodItem();
				FoodItem itemToFind = Main.currFood;
				
				Main.food.setText("Food: " + itemToFind.getName());
				Label calories = new Label("Calories: " + itemToFind.getNutrientValue("calories"));
				Label fat = new Label("Fat (Grams): " + itemToFind.getNutrientValue("fat"));
				Label carbohydrates = new Label("Carbohydrates (Grams): " + itemToFind.getNutrientValue("carbohydrate"));
				Label fiber = new Label("Fiber (Grams): " + itemToFind.getNutrientValue("fiber"));
				Label protein = new Label("Protein (Grams): " + itemToFind.getNutrientValue("protein"));
				
				GUI.initLabel(calories, 1);
				GUI.initLabel(Main.food, 1);
				GUI.initLabel(fat, 1);
				GUI.initLabel(carbohydrates, 1);
				GUI.initLabel(fiber, 1);
				GUI.initLabel(protein, 1);
				
				Main.food.setFont(Font.font("Arial", 24));
				
				Main.foodInfoScreen.getChildren().clear();
				Main.foodInfoScreen.getChildren().addAll(Main.food, calories, fat, carbohydrates, fiber, protein, Main.addToMealButton);
				
				Main.root.setCenter(Main.foodInfoScreen);
				Main.foodInfoScene = 4;
			}
			event.consume();
		}
	};
	
	/**
	 * Handler for the query button.
	 * 
	 * Opens up the query menu.
	 */
	static EventHandler<ActionEvent> queryHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 5) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
			}else {
				Main.root.setCenter(Main.queryBox);
				Main.foodInfoScene = 5;
			}
			event.consume();
		}
	};
	
	/**
	 * Handler for the add Food Screen button.
	 * 
	 * Opens up the add Food Screen menu.
	 */
	static EventHandler<ActionEvent> addFoodScreenHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 6) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
			} else {
				Main.root.setCenter(Main.addFoodScreen);
				Main.foodInfoScene = 6;
			}
			event.consume();
		}
	};
	
	/**
	 * Handler for the color radio buttons.
	 * 
	 * Opens up the color radio buttons.
	 */
	static EventHandler<ActionEvent> colorHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			String color = ((RadioButton)event.getSource()).getText();
			switch(color) {
			case "Badger Red" : GUI.color = Color.rgb(197, 5, 12); 
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadowBool=true;
			break;
			case "Off" : GUI.dropShadowBool=false;
			break;
			case "Blue" : GUI.color = Color.BLUE;
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadowBool=true;
			break;
			case "Purple" : GUI.color = Color.PURPLE;
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadowBool=true;
			break;
			case "Green" : GUI.color = Color.GREEN;
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadowBool=true;
			break;
			}
			event.consume();
		}
	};
	
	/**
	 * Handler for apply Query button.
	 * 
	 * applies the Query.
	 */
	static EventHandler<ActionEvent> applyQueryHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			Main.queryFoodDataList = new FoodData();
			Main.queryFoodList.getChildren().clear();
			
			if(Main.ruleList.getChildren().size() == 0) {
				event.consume();
				return;
			}
			
			List<String> listOfRules = new ArrayList<String>();
			List<String> listOfNames = new ArrayList<String>();
			
			for(int i = 0; i < Main.ruleList.getChildren().size(); i++) {
				Label currentRule = (Label) ((HBox) Main.ruleList.getChildren().get(i)).getChildren().get(0);
				String[] tokens = currentRule.getText().split(" ");
				
				if(tokens[0].equals("calories") || tokens[0].equals("fat") || tokens[0].equals("carbohydrate")
						|| tokens[0].equals("fiber") || tokens[0].equals("protein")) {
					listOfRules.add(currentRule.getText());
				} else {
					listOfNames.add(currentRule.getText());
				}
			}
			
			List<FoodItem> foodRuleResults = Main.foodDataList.filterByNutrients(listOfRules);
			List<ArrayList<FoodItem>> foodNameResults = new ArrayList<ArrayList<FoodItem>>();
			
			for(int i = 0; i < listOfNames.size(); i++) {
				foodNameResults.add((ArrayList<FoodItem>) Main.foodDataList.filterByName(listOfNames.get(i)));
			}
			
			if (foodRuleResults.size() > 0) {
				for(int i = 0; i < foodRuleResults.size(); i++) {
					boolean inAllLists = true;
					
					for(int j = 0; j < foodNameResults.size(); j++) {
						if(!foodNameResults.get(j).contains(foodRuleResults.get(i))) {
							inAllLists = false;
						}
					}
					
					if(inAllLists) {
						Main.queryFoodDataList.addFoodItem(foodRuleResults.get(i));
						MealButton newItem = new MealButton(foodRuleResults.get(i).getName(), foodRuleResults.get(i));
			    		GUI.initFoodItemButton(newItem, foodRuleResults.get(i));
			    		Main.queryFoodList.getChildren().add(newItem);
					}
				}
			} else if (foodNameResults.size() > 0) {
				for(int i = 0; i < foodNameResults.get(0).size(); i++) {
					boolean inAllLists = true;
					
					for(int j = 0; j < foodNameResults.size(); j++) {
						if(!foodNameResults.get(j).contains(foodNameResults.get(0).get(i))) {
	    					inAllLists = false;
	    				}
					}
					
					if(inAllLists) {
						Main.queryFoodDataList.addFoodItem(foodNameResults.get(0).get(i));
						Button newItem = new MealButton(foodNameResults.get(0).get(i).getName(), foodNameResults.get(0).get(i));
			    		GUI.initFoodItemButton(newItem, foodNameResults.get(0).get(i));
			    		Main.queryFoodList.getChildren().add(newItem);
					}
				}
			}
			
			Main.foodPane.setContent(MealEventHandler.alphabetize(Main.queryFoodList));
			
			if(Main.leftPane.getChildren().size() == 2) {
				Button resetButton = new Button("Reset");
				resetButton.setOnAction(resetQueryHandler);
				Main.leftPane.getChildren().add(resetButton);
			}
			
			event.consume();
		}
	};
	
	static EventHandler<ActionEvent> resetQueryHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			Main.leftPane.getChildren().remove(2);
			
			Main.foodPane.setContent(Main.foodList);
		}
	};
	
	/**
	 * 
	 */
	static EventHandler<ActionEvent> addRuleHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			String nameToAdd = "";
			
			if(!Main.nameQueryField.getText().equals("")) {
				nameToAdd = Main.nameQueryField.getText();
			}
			
			if(!nameToAdd.equals("")) {
				Button removeButton = new Button("Remove");
				removeButton.setOnAction(MealEventHandler.removeRuleHandler);
				Main.ruleList.getChildren().add(new HBox(new Label(nameToAdd), removeButton));
			}
			
			String ruleToAdd = "";
			
			if(Main.ruleTypeGroup.getToggles().get(0).isSelected()) {
				ruleToAdd += "calories ";
			} else if(Main.ruleTypeGroup.getToggles().get(1).isSelected()) {
				ruleToAdd += "fat ";
			} else if(Main.ruleTypeGroup.getToggles().get(2).isSelected()) {
				ruleToAdd += "carbohydrate ";
			} else if(Main.ruleTypeGroup.getToggles().get(3).isSelected()) {
				ruleToAdd += "fiber ";
			} else if(Main.ruleTypeGroup.getToggles().get(4).isSelected()) {
				ruleToAdd += "protein ";
			} else {
				event.consume();
				return;
			}
			
			if(Main.ruleRuleGroup.getToggles().get(0).isSelected()) {
				ruleToAdd += ">= ";
			} else if(Main.ruleRuleGroup.getToggles().get(1).isSelected()) {
				ruleToAdd += "== ";
			} else if(Main.ruleRuleGroup.getToggles().get(2).isSelected()) {
				ruleToAdd += "<= ";
			} else {
				event.consume();
				return;
			}
			
			if(!Main.queryNumberField.getText().equals("")) { 
				double numberToAdd = 0.0;
				
				try {
					numberToAdd = Double.parseDouble(Main.queryNumberField.getText());
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
			
			Button removeButton = new Button("Remove");
			removeButton.setOnAction(MealEventHandler.removeRuleHandler);
			Main.ruleList.getChildren().add(new HBox(new Label(ruleToAdd), removeButton));
			
			event.consume();
		}
	};
	
	static EventHandler<ActionEvent> removeRuleHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			Main.ruleList.getChildren().remove(((Button) event.getSource()).getParent());
			
			event.consume();
		}
	};
	
	static EventHandler<ActionEvent> helpButtonHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
			
			dialog.setTitle("Food Query and Meal Analysis User Information");
			
			// int foodInfoScene: 	0 = default
			//						2 = options
			//						3 = create meal (unneeded)
			//						4 = food info
			// 						5 = query screen
			//						6 = add FoodItem
			
			dialog.setHeaderText("Use this program to organize your food ingrediants and meals.\n\n"
					+ "	-On the left is a list of your available foods. Load foods from file with \"Load Foods\" button\n"
					+ "		at the bottom or create a new food with the \"Add\" button at top of food list. You can \n"
					+ "		save your current list of foods to a file with the \"Save Foods\" button at the bottom.\n\n"
					+ "	-On the right is your current meal with the foods you have choosen. The \"Analyze Meal\" button\n"
					+ "		shows the nutritional values of the meal. The \"Clear All\" button clears the entire meal.\n"
					+ " ");
			if(Main.foodInfoScene == 2) {
				dialog.setContentText("Here on the \"Options\" page, you can choose a color for the dropshadow used\n"
						+ "in the program or turn it off.");
			}
			if(Main.foodInfoScene == 5) {
				dialog.setContentText("Here on the \"Query\" page, you can filter your list of foods by your specified rules.\n"
					+ "	-To add a rule pick one nutrient, one comparator value, and type in the number you wish to seach by in the box, then click\n"
					+ "		\"Add Rule\". Add as many rules as you like and hit the \"Apply Querey\" button to apply the rules.\n"
					+ "	-To revert the food list back to the original list, hit the \"Reset\" button.");
			}
			if(Main.foodInfoScene == 6) {
				dialog.setContentText("Here on the \"Add\" button you must fill in all text fields the appropriate values.\n"
						+ "	-Name can be anything.\n"
						+ "	-Nutrients must contain positive numbers.");
			}

			dialog.showAndWait();
		}
	};

	static EventHandler<ActionEvent> clearMealHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			Main.mealPane.setContent(new VBox());
			Main.mealFoodDataList = new FoodData();
			
			event.consume();
		}
	};
	
	static EventHandler<ActionEvent> submitNewFoodHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			int temp = 0;
			String uniqueID = UUID.randomUUID().toString();
			
			if(Main.addFoodName.getText().equals("")) {
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Empty food name detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			
			FoodItem newFoodItem = new FoodItem(uniqueID, Main.addFoodName.getText());
			
			try {
				temp = Integer.parseInt(Main.addFoodCals.getText());
				if(temp<0) {
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
			newFoodItem.addNutrient("calories", temp);
			
			try {
				temp = Integer.parseInt(Main.addFoodFats.getText());
				if(temp<0) {
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
			newFoodItem.addNutrient("fat", temp);
			
			try {
				temp = Integer.parseInt(Main.addFoodCarbs.getText());
				if(temp<0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Carbohydrates detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			}catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Carbohydrates or Empty Carbohydrates detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("carbohydrate", temp);
			
			try {
				temp = Integer.parseInt(Main.addFoodFibers.getText());
				if(temp<0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Fibers detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			}catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Fibers or Empty Fibers detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("fiber", temp);
			
			try {
				temp = Integer.parseInt(Main.addFoodProteins.getText());
				if(temp<0) {
		    		Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("Negative Protiens detected. \n"
							+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
					dialog.showAndWait();
					return;
				}
			}catch(Exception e) {
	    		Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Alphanumerics in Protiens or Empty Protiens detected. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			newFoodItem.addNutrient("protein", temp);
			
			Main.foodDataList.addFoodItem(newFoodItem);
    		MealButton newItem = new MealButton(Main.addFoodName.getText(), newFoodItem);
    		GUI.initFoodItemButton(newItem, newFoodItem);
    		((VBox) Main.foodPane.getContent()).getChildren().add(newItem);
    		Main.foodPane.setContent(MealEventHandler.alphabetize((VBox) Main.foodPane.getContent()));
			
			event.consume();
		}
	};
	
	static EventHandler<ActionEvent> analyzeMealHandler = new EventHandler<ActionEvent> () {
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 1) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
				return;
			} else {
				Main.foodInfoScene = 1;
			}
			
			if(Main.mealFoodDataList.getAllFoodItems().isEmpty()) {
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setHeaderText("Meal is empty.  Analysis cannot be completed. \n"
						+ "If you belive this is an error, please contact the programmer by email: ttu4@wisc.edu");
				dialog.showAndWait();
				return;
			}
			
			double totalCals = 0.0;
			double totalFats = 0.0;
			double totalCarbs = 0.0;
			double totalFiber = 0.0;
			double totalProtein = 0.0;
			List<FoodItem> mealFoodDataList = Main.mealFoodDataList.getAllFoodItems();
			
			for(FoodItem item : mealFoodDataList) {
				totalCals += item.getNutrientValue("calories");
				totalFats += item.getNutrientValue("fat");
				totalCarbs += item.getNutrientValue("carbohydrate");
				totalFiber += item.getNutrientValue("fiber");
				totalProtein += item.getNutrientValue("protein");
			}
			
			Label titleLabel = new Label("Meal Analysis");
			Label caloriesLabel = new Label("Calories: " + totalCals);
			Label fatLabel = new Label("Fat (Grams): " + totalFats);
			Label carbohydratesLabel = new Label("Carbohydrates (Grams): " + totalCarbs);
			Label fiberLabel = new Label("Fiber (Grams): " + totalFiber);
			Label proteinLabel = new Label("Protein (Grams): " + totalProtein);
			
			GUI.initLabel(titleLabel, 1);
			GUI.initLabel(caloriesLabel, 1);
			GUI.initLabel(fatLabel, 1);
			GUI.initLabel(carbohydratesLabel, 1);
			GUI.initLabel(fiberLabel, 1);
			GUI.initLabel(proteinLabel, 1);
			
			titleLabel.setFont(Font.font("Arial", 24));
			
			Main.analyzeMealScreen.getChildren().clear();
			Main.analyzeMealScreen.getChildren().addAll(titleLabel, caloriesLabel, fatLabel, carbohydratesLabel,
					fiberLabel, proteinLabel);
			
			Main.root.setCenter(Main.analyzeMealScreen);
		}
	};
}