package application;

import java.io.File;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
				Main.root.setCenter(Main.mealListAddBox);
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
	static EventHandler<ActionEvent> addMealHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 3) {
				Main.mealScrollList.getChildren().add(new Label(Main.createMealField.getText()));
				Main.createMealField.setText("");
				Main.mealScrollPane.setContent(Main.mealScrollList);
			}
			event.consume();
		}
	};
	
	/**
	 * Handler for the scroll Meal button.
	 * 
	 * Opens up the scroll Meal menu.
	 */
	static EventHandler<ActionEvent> scrollMealHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 4 && ("Food: " + ((Button) event.getSource()).getText()).equalsIgnoreCase(Main.food.getText())) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
			}else if(Main.foodInfoScene == 3){
				Main.createMealField.setText(((Button) event.getSource()).getText());
			}else {
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
				
				Main.createMealScreen.getChildren().clear();
				Main.createMealScreen.getChildren().addAll(Main.food, calories, fat, carbohydrates, fiber, protein);
				
				Main.root.setCenter(Main.createMealScreen);
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
			Main.mealList.getChildren().add(newMealButton);
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
			
			event.consume();
		}
	};
}