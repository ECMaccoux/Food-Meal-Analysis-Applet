package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class MealEventHandler {
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
}
