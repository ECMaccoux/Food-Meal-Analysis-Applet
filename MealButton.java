package application;

import javafx.scene.control.Button;
import java.lang.Comparable;

/**
 * MealButton, which extends javaFx.scene.control.Button, allows for the storing of a FoodItem object.
 * 
 * @author Tanner Blanke, Eric Maccoux, Jack Pientka, Tony Tu
 *
 */
public class MealButton extends Button implements Comparable<MealButton>{
	
	private FoodItem foodItem;	// FoodItem to be connected with MealButton
	
	/**
	 * Public constructor
	 * 
	 * @param name name of MealButton
	 * @param item FoodItem to be connected with MealButton
	 */
	public MealButton(String name, FoodItem item) {
		super(name);
		this.foodItem = item;
	}
	
	/**
	 * Returns FoodItem connected to MealButton
	 * 
	 * @return foodItem
	 */
	public FoodItem getFoodItem() {
		return foodItem;
	}
	
	/**
	 * Allows for two MealButtons to be compared.
	 * 
	 * @return results of compareTo between the texts of two MealButtons
	 */
	@Override
	public int compareTo(MealButton button) {
		return this.getText().toLowerCase().compareTo(button.getText().toLowerCase());
	}
}
