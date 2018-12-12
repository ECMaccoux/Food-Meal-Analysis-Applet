package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The NodeInitializer class contains static methods that initialize different types
 * of GUI objects so that they display correctly within the GUI.
 * 
 * @author Tanner Blanke, Eric Maccoux, Jack Pientka, Tony Tu
 *
 */
public class NodeInitializer {
	
	/**
	 * Initializes label
	 * 
	 * @param label Label object to be initialized
	 * @param type type of Label
	 */
	public static void initLabel(Label label, int type) {
		// setting font, width, and text color of label
		label.setFont(Font.font("arial", FontWeight.BOLD, 16));
		label.setTextFill(Color.BLACK);
		
		if(type==0) {
			label.setPrefWidth(150);
		}
	}
	
	/**
	 * Initializes scroll pane
	 * 
	 * @param scrollPane ScrollPane object to be initialized
	 */
	public static void initScrollPane(ScrollPane scrollPane) {
		
		// sets width, policies, and style of scroll pane
		scrollPane.setPrefWidth(215);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}
	
	/**
	 * Initializes button
	 * 
	 * @param button Button object to be initialized
	 */
	public static void initButton(Button button) {
		
		// sets font, width, and text color of button
		button.setPrefWidth(500);
		button.getStyleClass().add("normal-button");
	}
	
	
	/**
	 * Initializes radio button
	 * 
	 * @param radioButton RadioButon object to be initialized
	 */
	public static void initRadioButton(RadioButton radioButton) {
		
		// sets font, width, and style of check box
		radioButton.getStyleClass().add("radio-button");
	}
	
	/**
	 * Initializes buttons within scroll panes
	 * 
	 * @param button Button to be initialized
	 */
	public static void initFoodItemButton(Button button, FoodItem item) {
		
		// sets text color, action, and border for button
		button.setId(item.getID());
		button.setTextFill(Color.BLACK);
		button.setOnAction(MealEventHandler.foodItemButtonHandler);
		button.setBorder(null);
		button.setPrefWidth(240);
		button.setMaxWidth(240);
		
	}
	
}
