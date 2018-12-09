package application;

import javafx.scene.control.Button;

/**
 * Connect a Button in Javafx to a FoodItem
 * 
 * @author Tony Tu
 *
 */
public class MealButton extends Button{
	private FoodItem foodItem;
	public MealButton(String name, FoodItem item) {
		super(name);
		this.foodItem = item;
	}
	public FoodItem getFoodItem() {
		return foodItem;
	}
}
