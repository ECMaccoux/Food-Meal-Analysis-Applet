package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * 
 * @author Tony Tu, Eric Maccoux, Tanner Blanke, Jack Pientka
 *
 */
public class Main extends Application {
	
	public static BorderPane root;
	public static int foodInfoScene;
	public static Label foodInfo;
	public static HBox loadFoodBox;
	public static HBox analyzeMealBox;
	public static VBox mealListAddBox;
	public static VBox mealInfoBox;
	public static VBox queryBox;
	public static ScrollPane mealScrollPane;
	public static VBox mealScrollList;
	public static TextField analyzeFoodField;
	public static TextField createMealField;
	public static Label food;

	/**
	 * Starts GUI
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// initializes public static fields
			initialize();
			
			// creates the scene
			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

			// creates the stage
			primaryStage.setResizable(false);
			primaryStage.setMaxWidth(1280);
			primaryStage.setMaxHeight(640);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Food Query and Meal Analysis");
			
			// creates all buttons
			Button loadFood = new Button("Load Foods");
			Button saveFood = new Button("Save Foods");
			Button createMeal = new Button("Create Meal");
			Button analyzeMeal = new Button("Analyze Meal");
			Button query = new Button("Query");
			Button addMealButton = new Button("Add");
			
			// Initializes all buttons
			GUI.initButton(loadFood);
			GUI.initButton(saveFood);
			GUI.initButton(createMeal);
			GUI.initButton(analyzeMeal);
			GUI.initButton(query);
			GUI.initButton(addMealButton);
			
			// initializes center area box
			mealInfoBox.setAlignment(Pos.TOP_CENTER);
			addMealButton.setPrefWidth(45);
			addMealButton.setFont(Font.font("Arial", 14));
			
			// initializes button box at bottom of screen
			HBox bottomBox = new HBox();
			//bottomBox.setSpacing((java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 900)/6);
			bottomBox.getChildren().addAll(loadFood, saveFood, createMeal, analyzeMeal, query);
			
			// creates all labels
			Label calories = new Label("Calories: ");
			Label fat = new Label("Fat (Grams): ");
			Label carbohydrates = new Label("Carbohydrates (Grams): ");
			Label fiber = new Label("Fiber (Grams): ");
			Label protein = new Label("Protein (Grams): ");
			Label foodListTitle = new Label("Foods: ");
			Label mealListTitle = new Label("Meals: ");
			Label queryLabel = new Label("Filter Query:");
			Label loadFileLabel = new Label("Load File");
			Label addFoodLabel = new Label("Add New Food");
			Label anaylzeMealLabel = new Label("Meal Analysis");
			Label createMealLabel = new Label("Create New Meal");
			Label currentMealLabel = new Label("Current Meal");
			
			// creates all check boxes
			CheckBox caloriesCheckBox = new CheckBox("Filter By Calories");
			CheckBox fatCheckBox = new CheckBox("Filter By Fat");
			CheckBox carbohydrateCheckBox = new CheckBox("Filter By Carbohydrate");
			CheckBox fiberCheckBox = new CheckBox("Filter By Fiber");
			CheckBox proteinCheckBox = new CheckBox("Filter By Protein");
			
			// creates all radio buttons
			RadioButton nameSort = new RadioButton("Sort By Name");
			RadioButton caloriesSort = new RadioButton("Sort By Name");
			RadioButton fatSort = new RadioButton("Sort By Name");
			RadioButton carbohydrateSort = new RadioButton("Sort By Name");
			RadioButton fiberSort = new RadioButton("Sort By Name");
			RadioButton proteinSort = new RadioButton("Sort By Name");
			
			// creates all text fields
			TextField loadFoodField = new TextField();
			
			// initializes check boxes
			GUI.initCheckBox(caloriesCheckBox);
			GUI.initCheckBox(fatCheckBox);
			GUI.initCheckBox(carbohydrateCheckBox);
			GUI.initCheckBox(fiberCheckBox);
			GUI.initCheckBox(proteinCheckBox);
			
			// initializes labels
			GUI.initLabel(calories, 1);
			GUI.initLabel(food, 1);
			GUI.initLabel(fat, 1);
			GUI.initLabel(carbohydrates, 1);
			GUI.initLabel(fiber, 1);
			GUI.initLabel(protein, 1);
			GUI.initLabel(foodListTitle, 1);
			GUI.initLabel(mealListTitle, 1);
			GUI.initLabel(queryLabel, 0);
			GUI.initLabel(foodInfo, 0);
			GUI.initLabel(loadFileLabel, 0);
			GUI.initLabel(addFoodLabel, 0);
			GUI.initLabel(anaylzeMealLabel, 0);
			GUI.initLabel(createMealLabel, 0);
			GUI.initLabel(currentMealLabel, 0);
			
			// sets up food info screen
			food.setFont(Font.font("Arial", 24));
			mealInfoBox.getChildren().addAll(food, calories, fat, carbohydrates, fiber, protein);
			foodInfo.setPrefWidth(500);
			foodInfo.setAlignment(Pos.TOP_CENTER);
			foodInfo.setStyle("-fx-font: 40 arial;");
		
			// sets spacing within button box
			HBox.setMargin(saveFood, new Insets(14));
			HBox.setMargin(loadFood, new Insets(14));
			HBox.setMargin(createMeal, new Insets(14));
			HBox.setMargin(analyzeMeal, new Insets(14));
			HBox.setMargin(query, new Insets(14));
			HBox createMealFieldBox = new HBox();
			
			// TODO: figure out file parsing
			// initializes the food list
			VBox foodList = new VBox();
			Button temp = new Button();
			//foodPane.getChildren().add(foodListTitle);
			for(int i=0; i<200; i++) {
				// TODO: This is going to be changed
				temp = new Button("Spagett with #" + i + " Spicness");
				GUI.initScrollButton(temp);
				foodList.getChildren().add(temp);
			}
			
			// creates a scrolling pane that contains the list of food
			ScrollPane foodPane = new ScrollPane();
			foodPane.setContent(foodList);
			
			// TODO: figure out file parsing
			// initializes the meal list
			VBox mealList = new VBox();
			for(int i=0; i<200; i++) {
				// TODO: This is going to be changed
				temp = new Button("Test Meal #" + i + " Test Meal");
				GUI.initScrollButton(temp);
				mealList.getChildren().add(temp);
			}
			
			// creates a scrolling pane that contains the list of meals
			ScrollPane mealPane = new ScrollPane();
			mealPane.setContent(mealList);
			mealPane.setPrefHeight(250);
			
			// initializes the scroll panes
			GUI.initScroll(mealPane);
			GUI.initScroll(foodPane);
			
			// TODO: put relevant information in the meal info pane
			// initializes meal info pane
			Label mealInfo = new Label("Meal Info \n more MEAL info \nn\n\n MEAL INFOOOO");
			mealInfo.setStyle("-fx-font: 40 arial;");
			
			// initializes left pane (contains foodListTitle and foodList)
			VBox leftPane = new VBox();
			leftPane.setPrefWidth(215);
			leftPane.getChildren().addAll(foodListTitle, foodPane);
			
			// initializes right pane (contains mealList and mealInfo)
			VBox rightPane = new VBox();
			rightPane.setPrefWidth(215);
			rightPane.getChildren().addAll(mealListTitle, mealPane, mealInfo);
			
			// putting all four of our panes into root 
			root.setLeft(leftPane);
			root.setCenter(foodInfo);
			root.setRight(rightPane);
			root.setBottom(bottomBox);
			
			// adds label/field to LoadFoodBox
			loadFoodBox.getChildren().addAll(loadFileLabel, loadFoodField);
			
			// adds label/field to AnalyzeMealBox
			analyzeMealBox.getChildren().addAll(anaylzeMealLabel, analyzeFoodField);
			
			// adds label/field/button to CreateMealFieldBox
			createMealFieldBox.getChildren().addAll(addFoodLabel, createMealField, addMealButton);
			
			// adds label/field/box/label to MealListAdd (create meal screen)
			mealListAddBox.getChildren().addAll(createMealLabel, analyzeFoodField, createMealFieldBox, currentMealLabel);
			
			// adds necessary checkboxes to queryBox
			queryBox.getChildren().addAll(queryLabel, caloriesCheckBox, fatCheckBox, carbohydrateCheckBox,
					fiberCheckBox, proteinCheckBox);
			
			// initializes list of items in meal, adds it to MealListAdd
			mealScrollPane.setPrefHeight(450);
			mealScrollPane.setContent(mealScrollList);
			mealListAddBox.getChildren().add(mealScrollPane);
			
			// sets actions for each buttons
			loadFood.setOnAction(MealEventHandler.foodInfoHandler);
			analyzeMeal.setOnAction(MealEventHandler.analyzeMealHandler);
			createMeal.setOnAction(MealEventHandler.createMealHandler);
			addMealButton.setOnAction(MealEventHandler.addMealHandler);
			query.setOnAction(MealEventHandler.queryHandler);
			
			// displays the stage
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private void initialize() {
		root = new BorderPane();
		foodInfoScene = 0;
		foodInfo = new Label("Select Food");
		loadFoodBox = new HBox();
		analyzeMealBox = new HBox();
		mealListAddBox = new VBox();
		mealInfoBox = new VBox();
		queryBox = new VBox();
		mealScrollPane = new ScrollPane();
		mealScrollList = new VBox();
		analyzeFoodField = new TextField();
		createMealField = new TextField();
		food = new Label();
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
