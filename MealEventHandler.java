package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Tony Tu
 *
 */
public class MealEventHandler {
	
	// intfoodInfoScene: 	0 = default
	//						1 = load food
	//						2 = analyze meal
	//						3 = create meal
	//						4 = food info
	// 						5 = query screen
	
	/**
	 * 
	 */
	static EventHandler<ActionEvent> foodInfoHandler = new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
	    	FileChooser chooser = new FileChooser();
	    	chooser.setTitle("Open Food List File");
	    	
	    	Stage chooserStage = new Stage();
	    	File file = chooser.showOpenDialog(chooserStage);
	    	
	    	Main.foodDataList.loadFoodItems(file.getPath());
	    	
	    	List<FoodItem> list = Main.foodDataList.getAllFoodItems();
	    	
	    	for(int i = 0; i < list.size(); i++) {
	    		Button newItem = new Button(list.get(i).getName());
	    		GUI.initFoodItemButton(newItem, list.get(i));
	    		Main.foodList.getChildren().add(newItem);
	    	}
	    	
	        event.consume();
	    }
	};
	
	/**
	 * 
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
	 * 
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
	 * 
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
	 * 
	 */
	static EventHandler<ActionEvent> scrollMealHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 4 && ("Food: " + ((Button) event.getSource()).getText()).equalsIgnoreCase(Main.food.getText())) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
			}else {
				
				
				Main.food.setText("Food: ");
				
				Label calories = new Label("Calories: ");
				Label fat = new Label("Fat (Grams): ");
				Label carbohydrates = new Label("Carbohydrates (Grams): ");
				Label fiber = new Label("Fiber (Grams): ");
				Label protein = new Label("Protein (Grams): ");
				
				GUI.initLabel(calories, 1);
				GUI.initLabel(Main.food, 1);
				GUI.initLabel(fat, 1);
				GUI.initLabel(carbohydrates, 1);
				GUI.initLabel(fiber, 1);
				GUI.initLabel(protein, 1);
				
				Main.food.setFont(Font.font("Arial", 24));
				
				Main.mealInfoBox.getChildren().addAll(Main.food, calories, fat, carbohydrates, fiber, protein);
				
				Main.root.setCenter(Main.mealInfoBox);
				Main.foodInfoScene = 4;
			}
			event.consume();
		}
	};
	
	/**
	 * 
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
	 * 
	 */
	static EventHandler<ActionEvent> colorHandler = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			String color = ((RadioButton)event.getSource()).getText();
			switch(color) {
			case "Badger Red" : GUI.color = Color.rgb(197, 5, 12); 
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadow.setRadius(4.5);
			break;
			case "Off" : GUI.dropShadow.setRadius(0);
			break;
			case "Blue" : GUI.color = Color.BLUE;
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadow.setRadius(4.5);
			break;
			case "Purple" : GUI.color = Color.PURPLE;
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadow.setRadius(4.5);
			break;
			case "Green" : GUI.color = Color.GREEN;
			GUI.dropShadow.setColor(GUI.color);
			GUI.dropShadow.setRadius(4.5);
			break;
			}
			event.consume();
		}
	};
}