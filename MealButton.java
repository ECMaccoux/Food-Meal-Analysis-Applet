package application;

import javafx.scene.control.Button;
import java.lang.Comparable;

/**
 * Connect a Button in Javafx to a FoodItem
 * 
 * @author Tony Tu
 *
 */
public class MealButton extends Button implements Comparable<MealButton>{
	
	private FoodItem foodItem;
	
	/**
	 * 
	 * 
	 * @param name
	 * @param item
	 */
	public MealButton(String name, FoodItem item) {
		super(name);
		this.foodItem = item;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public FoodItem getFoodItem() {
		return foodItem;
	}
	
	/**
	 * 
	 */
	public int compareTo(MealButton button) {
		return this.getText().toLowerCase().compareTo(button.getText().toLowerCase());
	}
}
