package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * 
 * @author Tony Tu
 *
 */
public class Main extends Application {
	public static BorderPane root = new BorderPane();
	public static int foodInfoScene = 0;
	public static Label foodInfo = new Label("Select Food");
	public static HBox loadFoodBox = new HBox();
	public static HBox analyzeMealBox = new HBox();
	public static VBox mealListAdd = new VBox();
	public static VBox mealInfoBox = new VBox();
	public static ScrollPane mealScrollPane = new ScrollPane();
	public static VBox mealScrollList = new VBox();
	public static TextField analyzeFoodField = new TextField();
	public static TextField createMealField = new TextField();
	public static Label food = new Label("Food: ");

	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setMaximized(true);
			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Food Query and Meal Analysis");
			
			Button loadFood = new Button("Load Foods");
			Button saveFood = new Button("Save Foods");
			Button createMeal = new Button("Create Meal");
			Button analyzeMeal = new Button("Analyze Meal");
			Button query = new Button("Query");
			Button addMealButton = new Button("Add");
			
			GUI.initButton(loadFood);
			GUI.initButton(saveFood);
			GUI.initButton(createMeal);
			GUI.initButton(analyzeMeal);
			GUI.initButton(query);
			GUI.initButton(addMealButton);
			
			mealInfoBox.setAlignment(Pos.TOP_CENTER);
			addMealButton.setPrefWidth(45);
			addMealButton.setFont(Font.font("Arial", 14));
			
			HBox bottomBox = new HBox();
			bottomBox.setSpacing((java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-900)/6);
			bottomBox.getChildren().addAll(loadFood, saveFood, createMeal, analyzeMeal, query);
			root.setBottom(bottomBox);
			
			Label calories = new Label("Calories: ");
			Label fat = new Label("Fat (Grams): ");
			Label carbohydrates = new Label("Carbohydrates (Grams): ");
			Label fiber = new Label("Fiber (Grams): ");
			Label protein = new Label("Protein (Grams): ");
			
			GUI.initLabel(calories, 1);
			GUI.initLabel(food, 1);
			GUI.initLabel(fat, 1);
			GUI.initLabel(carbohydrates, 1);
			GUI.initLabel(fiber, 1);
			GUI.initLabel(protein, 1);
			
			food.setFont(Font.font("Arial", 24));
			mealInfoBox.getChildren().addAll(food, calories, fat, carbohydrates, fiber, protein);
			
			HBox.setMargin(saveFood, new Insets(14));
			HBox.setMargin(loadFood, new Insets(14));
			HBox.setMargin(createMeal, new Insets(14));
			HBox.setMargin(analyzeMeal, new Insets(14));
			HBox.setMargin(query, new Insets(14));
			
			VBox foodPane = new VBox();
			Button temp = new Button();
			for(int i=0; i<200; i++) {
				temp = new Button("Test Food #" + i + " Test Food");
				GUI.initScrollButton(temp);
				foodPane.getChildren().add(temp);
			}
			
			ScrollPane foodList = new ScrollPane();
			foodList.setContent(foodPane);
			
			VBox mealPane = new VBox();
			for(int i=0; i<200; i++) {
				temp = new Button("Test Meal #" + i + " Test Meal");
				GUI.initScrollButton(temp);
				mealPane.getChildren().add(temp);
			}
			
			ScrollPane mealList = new ScrollPane();
			mealList.setContent(mealPane);
			mealList.setPrefHeight(400);
			
			GUI.initScroll(mealList);
			GUI.initScroll(foodList);
			GUI.initLabel(foodInfo);
			
			foodInfo.setPrefWidth(500);
			foodInfo.setAlignment(Pos.TOP_CENTER);
			foodInfo.setStyle("-fx-font: 40 arial;");
			
			Label mealInfo = new Label("Meal Info \n more MEAL info \nn\n\n MEAL INFOOOO");
			mealInfo.setStyle("-fx-font: 40 arial;");
			
			VBox rightPane = new VBox();
			rightPane.getChildren().add(mealList);
			rightPane.getChildren().add(mealInfo);
			
			root.setRight(rightPane);
			root.setCenter(foodInfo);
			root.setLeft(foodList);
			
			TextField loadFoodField = new TextField();
			Label loadFileLabel = new Label("Load File");
			Label addFoodLabel = new Label("Add New Food");
			Label anaylzeMealLabel = new Label("Meal Analysis");
			Label createMealLabel = new Label("Create New Meal");
			Label currentMealLabel = new Label("Current Meal");
			
			GUI.initLabel(loadFileLabel);
			GUI.initLabel(addFoodLabel);
			GUI.initLabel(anaylzeMealLabel);
			GUI.initLabel(createMealLabel);
			GUI.initLabel(currentMealLabel);
			
			loadFoodBox.getChildren().add(loadFileLabel);
			loadFoodBox.getChildren().add(loadFoodField);
			
			analyzeMealBox.getChildren().add(anaylzeMealLabel);
			analyzeMealBox.getChildren().add(analyzeFoodField);
			
			HBox createMealFieldBox = new HBox();
			createMealFieldBox.getChildren().add(addFoodLabel);
			createMealFieldBox.getChildren().add(createMealField);
			createMealFieldBox.getChildren().add(addMealButton);
			
			mealListAdd.getChildren().add(createMealLabel);
			mealListAdd.getChildren().add(analyzeFoodField);
			mealListAdd.getChildren().add(createMealFieldBox);
			mealListAdd.getChildren().add(currentMealLabel);
			
			mealScrollPane.setPrefHeight(450);
			mealScrollPane.setContent(mealScrollList);
			mealListAdd.getChildren().add(mealScrollPane);
			
			loadFood.setOnAction(MealEventHandler.foodInfoHandler);
			analyzeMeal.setOnAction(MealEventHandler.analyzeMealHandler);
			createMeal.setOnAction(MealEventHandler.createMealHandler);
			addMealButton.setOnAction(MealEventHandler.addMealHandler);
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
