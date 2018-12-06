package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Main class
 * 
 * @author Tony Tu, Eric Maccoux, Tanner Blanke, Jack Pientka
 *
 */
public class Main extends Application {
	
	public static BorderPane root;
	public static int foodInfoScene;
	public static Label foodInfo;
	public static HBox loadFoodBox;
	public static HBox optionsBox;
	public static VBox mealListAddBox;
	public static VBox createMealScreen;
	public static VBox queryBox;
	public static ScrollPane mealScrollPane;
	public static VBox mealScrollList;
	public static TextField optionsField;
	public static TextField createMealField;
	public static Label food;
	public static FoodData foodDataList;
	public static VBox foodList;
	public static VBox addFoodScreen;
	public static VBox mealInfoScreen;
	public static VBox mealList;

	/**
	 * Starts GUI
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//initializes GUI and public static fields
			GUI.initGUI();
			
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
			Button options = new Button("Options");
			Button query = new Button("Query");
			Button addMealButton = new Button("Add");
			Button addFoodButton = new Button("Add");
			Button addFoodSubmit = new Button("Submit");
			Button analyzeMeal = new Button("Analyze Meal");
			Button saveMeal = new Button("Save Meal");
			
			// Initializes all buttons
			GUI.initButton(loadFood);
			GUI.initButton(saveFood);
			GUI.initButton(createMeal);
			GUI.initButton(options);
			GUI.initButton(query);
			GUI.initButton(addMealButton);
			GUI.initButton(addFoodSubmit);
			GUI.initButton(saveMeal);
			
			// initializes center area box
			createMealScreen.setAlignment(Pos.TOP_CENTER);
			addMealButton.setPrefWidth(45);
			addMealButton.setFont(Font.font("Arial", 14));
			
			
			// initializes button box at bottom of screen
			HBox bottomBox = new HBox();
			
			// initializes color button box in the options menu
			HBox colorBox = new HBox();
			
			bottomBox.getChildren().addAll(loadFood, saveFood, createMeal, query, options);
			
			// creates all labels
			Label newFoodName = new Label("Name: ");
			Label calories = new Label("Calories: ");
			Label fat = new Label("Fat (Grams): ");
			Label carbohydrates = new Label("Carbohydrates (Grams): ");
			Label fiber = new Label("Fiber (Grams): ");
			Label protein = new Label("Protein (Grams): ");
			Label foodListTitle = new Label("Foods                             ");
			Label mealListTitle = new Label("Meals                   ");
			Label queryLabel = new Label("Filter Query:");
			Label loadFileLabel = new Label("Load File");
			Label addFoodLabel = new Label("Add New Food");
			Label optionsLabel = new Label("Options: ");
			Label createMealTitleLabel = new Label("Create New Meal");
			Label addFoodTitleLabel = new Label("Add New Food");
			Label currentMealLabel = new Label("Current Meal");
			Label sortLabel = new Label("Sort By:");
			Label mealNameLabel = new Label("Meal Name: ");
			
			// creates all check boxes
			CheckBox caloriesCheckBox = new CheckBox("Filter By Calories");
			CheckBox fatCheckBox = new CheckBox("Filter By Fat");
			CheckBox carbohydrateCheckBox = new CheckBox("Filter By Carbohydrate");
			CheckBox fiberCheckBox = new CheckBox("Filter By Fiber");
			CheckBox proteinCheckBox = new CheckBox("Filter By Protein");
			
			// creates all radio buttons
			RadioButton nameSort = new RadioButton("Sort By Name");
			RadioButton caloriesSort = new RadioButton("Sort By Calories");
			RadioButton fatSort = new RadioButton("Sort By Fat");
			RadioButton carbohydrateSort = new RadioButton("Sort By Carbohydrates");
			RadioButton fiberSort = new RadioButton("Sort By Fiber");
			RadioButton proteinSort = new RadioButton("Sort By Protein");
			RadioButton badgerRed = new RadioButton("Badger Red");
			RadioButton green = new RadioButton("Green");
			RadioButton off = new RadioButton("Off");
			RadioButton purple = new RadioButton("Purple");
			RadioButton blue = new RadioButton("Blue");
			
			// creates all toggle groups
			ToggleGroup sortGroup = new ToggleGroup();
			ToggleGroup colorGroup = new ToggleGroup();
			
			// creates all text fields
			TextField loadFoodField = new TextField();
			TextField addFoodName = new TextField();
			TextField addFoodCals = new TextField();
			TextField addFoodFats = new TextField();
			TextField addFoodCarbs = new TextField();
			TextField addFoodFibers = new TextField();
			TextField addFoodProteins = new TextField();
			
			// initializes Radio Buttons
			GUI.initRadio(nameSort);
			GUI.initRadio(caloriesSort);
			GUI.initRadio(fatSort);
			GUI.initRadio(carbohydrateSort);
			GUI.initRadio(fiberSort);
			GUI.initRadio(proteinSort);
			GUI.initRadio(badgerRed);
			GUI.initRadio(green);
			GUI.initRadio(off);
			GUI.initRadio(purple);
			GUI.initRadio(blue);
			
			// initializes check boxes
			GUI.initCheckBox(caloriesCheckBox);
			GUI.initCheckBox(fatCheckBox);
			GUI.initCheckBox(carbohydrateCheckBox);
			GUI.initCheckBox(fiberCheckBox);
			GUI.initCheckBox(proteinCheckBox);
			
			// initializes labels
			GUI.initLabel(calories, 1);
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
			GUI.initLabel(optionsLabel, 0);
			GUI.initLabel(createMealTitleLabel, 0);
			GUI.initLabel(currentMealLabel, 0);
			GUI.initLabel(sortLabel, 0);
			GUI.initLabel(newFoodName, 1);
			GUI.initLabel(addFoodTitleLabel, 1);
			GUI.initLabel(mealNameLabel, 0);
			
			// sets up food info screen
			food.setFont(Font.font("Arial", 24));
			foodInfo.setPrefWidth(500);
			foodInfo.setAlignment(Pos.TOP_CENTER);
			foodInfo.setStyle("-fx-font: 40 arial;");
		
			saveMeal.setPrefWidth(850);
			createMealTitleLabel.setFont(Font.font("arial", FontWeight.BOLD, 36));
			createMealTitleLabel.setPrefWidth(850);
			optionsField.setPrefWidth(400);
			createMealField.setPrefWidth(400);
			
			// sets spacing within button box
			HBox.setMargin(saveFood, new Insets(14));
			HBox.setMargin(loadFood, new Insets(14));
			HBox.setMargin(createMeal, new Insets(14));
			HBox.setMargin(options, new Insets(14));
			HBox.setMargin(query, new Insets(14));
			HBox createMealFieldBox = new HBox();
			
			// creates a scrolling pane that contains the list of food
			ScrollPane foodPane = new ScrollPane();
			foodPane.setContent(foodList);
			foodPane.setPrefHeight(700);
			
			// creates a scrolling pane that contains the list of meals
			ScrollPane mealPane = new ScrollPane();
			mealPane.setContent(mealList);
			mealPane.setPrefHeight(700);
			
			// initializes the scroll panes
			GUI.initScroll(mealPane);
			GUI.initScroll(foodPane);
			
			// initializes left pane (contains foodListTitle and foodList)
			VBox leftPane = new VBox();
			leftPane.setPrefWidth(215);
			leftPane.getChildren().addAll(new HBox(foodListTitle, addFoodButton), foodPane);
			
			// initializes right pane (contains mealList and mealInfo)
			VBox rightPane = new VBox();
			rightPane.setPrefWidth(215);
			rightPane.getChildren().addAll(new HBox(mealListTitle, analyzeMeal), mealPane);
			
			// Adds radiobuttons to Toggle groups
			nameSort.setToggleGroup(sortGroup);
			caloriesSort.setToggleGroup(sortGroup);
			fatSort.setToggleGroup(sortGroup);
			carbohydrateSort.setToggleGroup(sortGroup);
			fiberSort.setToggleGroup(sortGroup);
			proteinSort.setToggleGroup(sortGroup);
			badgerRed.setToggleGroup(colorGroup);
			green.setToggleGroup(colorGroup);
			off.setToggleGroup(colorGroup);
			purple.setToggleGroup(colorGroup);
			blue.setToggleGroup(colorGroup);
			
			// putting all four of our panes into root 
			root.setLeft(leftPane);
			root.setCenter(foodInfo);
			root.setRight(rightPane);
			root.setBottom(bottomBox);
			
			// adds label/field to LoadFoodBox
			loadFoodBox.getChildren().addAll(loadFileLabel, loadFoodField);
			
			// adds label/field to AnalyzeMealBox
			optionsBox.getChildren().addAll(optionsLabel, optionsField, colorBox);
			
			// adds label/field/button to CreateMealFieldBox
			createMealFieldBox.getChildren().addAll(addFoodLabel, createMealField, addMealButton);
			
			// adds label/field/box/label to MealListAdd (create meal screen)
			mealListAddBox.getChildren().addAll(createMealTitleLabel, new HBox(mealNameLabel, optionsField), createMealFieldBox, currentMealLabel, mealScrollPane, saveMeal);
			
			// adds necessary checkboxes to queryBox
			queryBox.getChildren().addAll(queryLabel, caloriesCheckBox, fatCheckBox, carbohydrateCheckBox,
					fiberCheckBox, proteinCheckBox, sortLabel, nameSort, caloriesSort, fatSort, carbohydrateSort,
					fiberSort, proteinSort);
			
			// adds necessary labels/text fields to addFoodScreen
			addFoodScreen.getChildren().addAll(addFoodTitleLabel, new HBox(newFoodName, addFoodName), 
					new HBox(calories, addFoodCals), new HBox(fat, addFoodFats), new HBox(carbohydrates, addFoodCarbs), 
					new HBox(fiber, addFoodFibers), new HBox(protein, addFoodProteins), addFoodSubmit);
			
			// initializes list of items in meal, adds it to MealListAdd
			mealScrollPane.setPrefHeight(400);
			mealScrollPane.setContent(mealScrollList);
			
			// Adds all radio buttons to color list
			colorBox.getChildren().addAll(badgerRed, blue, purple, green, off);
						
			// sets actions for each buttons
			loadFood.setOnAction(MealEventHandler.foodInfoHandler);
			options.setOnAction(MealEventHandler.optionsHandler);
			createMeal.setOnAction(MealEventHandler.createMealHandler);
			addMealButton.setOnAction(MealEventHandler.addMealHandler);
			query.setOnAction(MealEventHandler.queryHandler);
			badgerRed.setOnAction(MealEventHandler.colorHandler);
			green.setOnAction(MealEventHandler.colorHandler);
			off.setOnAction(MealEventHandler.colorHandler);
			purple.setOnAction(MealEventHandler.colorHandler);
			blue.setOnAction(MealEventHandler.colorHandler);
			addFoodButton.setOnAction(MealEventHandler.addFoodScreenHandler);
			saveMeal.setOnAction(MealEventHandler.saveMealHandler);
			
			// displays the stage
			primaryStage.show();
			
		} catch(Exception e) {
			Alert dialog = new Alert(Alert.AlertType.ERROR);
			dialog.setHeaderText("An internal error occured, please re-attempt or re-load program.\n"
					+ "If problem persists, please contact system architecture by email: jpientka@wisc.edu");
			dialog.showAndWait();
			primaryStage.hide();
			e.printStackTrace();
		}
	}
	
	/**
	 * Main method that launches everything.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
