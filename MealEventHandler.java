package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
	//						1 = load food
	//						2 = analyze meal
	//						3 = create meal
	//						4 = food info
	// 						5 = query screen
	//						6 = add item
	
	/**
	 * Handler that will be used by the load button.
	 * 
	 * Pops up a browser that will search through the hard drive.
	 */
	static EventHandler<ActionEvent> loadFoodHandler = new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
	    	FileChooser chooser = new FileChooser();
	    	chooser.setTitle("Open Food List File");
	    	
	    	Stage chooserStage = new Stage();
	    	File file = chooser.showOpenDialog(chooserStage);
	    	
	    	try {
	    		Main.foodDataList.loadFoodItems(file.getPath());
		    	
		    	List<FoodItem> list = Main.foodDataList.getAllFoodItems();
		    	
		    	for(int i = 0; i < list.size(); i++) {
		    		Button newItem = new Button(list.get(i).getName());
		    		GUI.initFoodItemButton(newItem, list.get(i));
		    		Main.foodList.getChildren().add(newItem);
		    	}
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
			Button newFoodButton = new Button(Main.currFood);
			
			List<FoodItem> itemList = Main.foodDataList.filterByName(Main.currFood);
			
			FoodItem itemToFind = itemList.get(0);
			GUI.initFoodItemButton(newFoodButton, itemToFind);
			for(Node button : ((VBox) Main.mealPane.getContent()).getChildren()) {
				if(((Button) button).getText() == Main.currFood) {
					// Alert dialog that will display if an error is thrown in the main body
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setHeaderText("An internal error occured, please re-attempt or re-load program.\n"
							+ "If problem persists, please contact system administrator by email: jpientka@wisc.edu");
					dialog.showAndWait();
					//Main.primaryStage.hide();
					return;
				}
			}
			GUI.initFoodItemButton(newFoodButton, itemToFind);

			VBox newVBox = (VBox) Main.mealPane.getContent();
			newVBox.getChildren().add(newFoodButton);
			
			Main.mealPane.setContent(newVBox);
			
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
				Main.currFood = ((Button) event.getSource()).getText();
				List<FoodItem> itemList = Main.foodDataList.filterByName(((Button) event.getSource()).getText());
				FoodItem itemToFind = itemList.get(0);
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
	
	static EventHandler<ActionEvent> scrollMealHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			String[] nameTokens = Main.food.getText().split(" ");
			
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
	 * Handler for save meal buttons.
	 * 
	 * Opens up the save meal buttons.
	 */
	static EventHandler<ActionEvent> saveMealHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			Button newMealButton = new Button(Main.optionsField.getText());
			//TODO: add FoodData
			GUI.initMealItemButton(newMealButton, new FoodData());
			//Main.mealList.getChildren().add(newMealButton);
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
				Main.foodPane.setContent(Main.foodList);
				
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
						Button newItem = new Button(foodRuleResults.get(i).getName());
			    		GUI.initFoodItemButton(newItem, foodRuleResults.get(i));
			    		Main.queryFoodList.getChildren().add(newItem);
					}
				}
			} else {
				for(int i = 0; i < foodNameResults.get(0).size(); i++) {
					boolean inAllLists = true;
					
					for(int j = 0; j < foodNameResults.size(); j++) {
						if(!foodNameResults.get(j).contains(foodNameResults.get(0).get(i))) {
	    					inAllLists = false;
	    				}
					}
					
					if(inAllLists) {
						Main.queryFoodDataList.addFoodItem(foodNameResults.get(0).get(i));
						Button newItem = new Button(foodNameResults.get(0).get(i).getName());
			    		GUI.initFoodItemButton(newItem, foodNameResults.get(0).get(i));
			    		Main.queryFoodList.getChildren().add(newItem);
					}
				}
			}
			
			Main.foodPane.setContent(Main.queryFoodList);
			
			event.consume();
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
}