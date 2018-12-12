package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class extends javafx.application.Application and acts as the main "driver"
 * for the JavaFX program.
 * 
 * @author Tanner Blanke, Eric Maccoux, Jack Pientka, Tony Tu
 *
 */
public class Main extends Application {
	
	public static FoodData foodData;			// FoodData object that represents all food loaded into memory (left sidebar)
	public static FoodData mealFoodData;		// FoodData object that represents all food added to meal (right sidebar)
	public static FoodData queryFoodData;		// FoodData object that represents all food returned by current query (left sidebar)
	public static GUI gui;						// creates/controls GUI for program
	
	/**
	 * Starts the GUI.
	 * 
	 * @param primaryStage Stage to be created/modified/shown
	 */
	@Override
	public void start(Stage primaryStage) {
		
		foodData = new FoodData();
		mealFoodData = new FoodData();
		GUI gui = new GUI(foodData, primaryStage);
		
		primaryStage.show();
	}
	
	/**
	 * Launches the JavaFX program.
	 * 
	 * @param args arguments passed to launch() method
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
