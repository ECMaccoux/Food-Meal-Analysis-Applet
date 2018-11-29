package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
	    	if(Main.foodInfoScene == 1) {
	    		Main.root.setCenter(Main.foodInfo);
	    		Main.foodInfoScene = 0;
	    	}else {
	    		Main.root.setCenter(Main.loadFoodBox);
	    		Main.foodInfoScene = 1;
	    	}
	        event.consume();
	    }
	};
	
	/**
	 * 
	 */
	static EventHandler<ActionEvent> analyzeMealHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(Main.foodInfoScene == 2) {
				Main.root.setCenter(Main.foodInfo);
				Main.foodInfoScene = 0;
			}else {
				Main.root.setCenter(Main.analyzeMealBox);
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
				Main.root.setCenter(Main.mealListAdd);
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
				Main.food.setText("Food: " + ((Button) event.getSource()).getText());
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
}