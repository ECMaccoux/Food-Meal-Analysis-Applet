package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Main class
 * @author Tony Tu, Eric Maccoux, Tanner Blanke, Jack Pientka
 *
 */
public class Main extends Application {
	
	public static BorderPane root;
	public static int foodInfoScene;
	public static Label foodInfo;
	public static HBox loadFoodBox;
	public static HBox optionsBox;
	//public static VBox mealListAddBox;
	//public static VBox createMealScreen;
	public static VBox queryBox;
	public static ScrollPane mealScrollPane;
	public static VBox mealScrollList;
	public static TextField optionsField;
	public static Label createMealField;
	public static ScrollPane ruleScrollPane;
	public static Label food;
	public static FoodData foodDataList;
	public static VBox foodList;
	public static VBox addFoodScreen;
	//public static VBox mealInfoScreen;
	//public static VBox mealList;
	public static ScrollPane mealPane;
	public static FoodItem currFood;
	// creates all text fields
	public static TextField loadFoodField;
	public static TextField addFoodName;
	public static TextField addFoodCals;
	public static TextField addFoodID;
	public static TextField addFoodFats;
	public static TextField addFoodCarbs;
	public static TextField addFoodFibers;
	public static TextField addFoodProteins;
	// STUFF THAT ERIC ADDED
	
	public static ToggleGroup ruleTypeGroup = new ToggleGroup();
	public static ToggleGroup ruleRuleGroup = new ToggleGroup();
	public static TextField queryNumberField = new TextField();
	public static Button queryAddRuleButton = new Button("Add Rule");
	public static VBox ruleList = new VBox();
	public static TextField nameQueryField = new TextField();
	public static ScrollPane foodPane = new ScrollPane();
	public static VBox queryFoodList = new VBox();
	public static FoodData queryFoodDataList = new FoodData();
	public static VBox foodInfoScreen = new VBox();
	public static Button addToMealButton = new Button("Add to Meal");

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
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// creates the stage
			primaryStage.setResizable(false);
			primaryStage.setMaxWidth(1280);
			primaryStage.setMaxHeight(640);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Food Query and Meal Analysis");
			
			// creates all buttons
			Button loadFood = new Button("Load Foods");
			Button saveFood = new Button("Save Foods");
			//Button createMeal = new Button("Create Meal");
			Button options = new Button("Options");
			Button query = new Button("Query");
			Button removeMealButton = new Button("Remove from Meal");
			Button addFoodButton = new Button("Add");
			Button addFoodSubmit = new Button("Submit");
			Button analyzeMeal = new Button("Analyze Meal");
			Button saveMeal = new Button("Save Meal");
			Button applyQuery = new Button("Apply Query");
			Button clearMeal = new Button("Clear");
			
			// Initializes all buttons
			GUI.initButton(loadFood);
			GUI.initButton(saveFood);
			//GUI.initButton(createMeal);
			GUI.initButton(options);
			GUI.initButton(query);
			GUI.initButton(addToMealButton);
			GUI.initButton(removeMealButton);
			GUI.initButton(addFoodSubmit);
			GUI.initButton(saveMeal);
			GUI.initButton(applyQuery);
			
			// initializes center area box
			//addToMealButton.setPrefWidth(45);
			//addToMealButton.setFont(Font.font("Arial", 14));
			
			// initializes button box at bottom of screen
			HBox bottomBox = new HBox();
			
			HBox nameQueryBox = new HBox();
			
			// initializes color button box in the options menu
			HBox colorBox = new HBox();
			HBox ruleBox = new HBox();
			VBox nameRuleBox = new VBox();
			VBox ruleRuleBox = new VBox();
			
			bottomBox.getChildren().addAll(loadFood, saveFood/*, createMeal*/, query, options);
			
			// creates all labels
			Label newFoodName = new Label("Name: ");
			Label nameQueryLabel = new Label("Food Name: ");
			Label calories = new Label("Calories: ");
			Label fat = new Label("Fat (Grams): ");
			Label carbohydrates = new Label("Carbohydrates (Grams): ");
			Label foodID = new Label("ID: ");
			Label fiber = new Label("Fiber (Grams): ");
			Label protein = new Label("Protein (Grams): ");
			Label foodListTitle = new Label("Foods                             ");
			Label mealListTitle = new Label("Meal           ");
			Label queryLabel = new Label("Filter Query:");
			Label loadFileLabel = new Label("Load File");
			Label addFoodLabel = new Label("Add New Food");
			Label optionsLabel = new Label("Options: ");
			//Label createMealTitleLabel = new Label("Create New Meal");
			Label addFoodTitleLabel = new Label("Add New Food");
			//Label currentMealLabel = new Label("Current Meal");
			Label sortLabel = new Label("Sort By:");
			//Label mealNameLabel = new Label("Meal Name: ");
						
			// creates all radio buttons
			RadioButton badgerRed = new RadioButton("Badger Red");
			RadioButton green = new RadioButton("Green");
			RadioButton off = new RadioButton("Off");
			RadioButton purple = new RadioButton("Purple");
			RadioButton blue = new RadioButton("Blue");
			RadioButton caloriesRadioButton = new RadioButton("Calories");
			RadioButton fatRadioButton = new RadioButton("Fats");
			RadioButton carbohydrateRadioButton = new RadioButton("Carbohydrates");
			RadioButton fiberRadioButton = new RadioButton("Fibers");
			RadioButton proteinRadioButton = new RadioButton("Proteins");
			RadioButton greaterEqualThan = new RadioButton("Greater Than or Equal To");
			RadioButton lessEqualThan = new RadioButton("Less Than or Equal To");
			RadioButton equalTo = new RadioButton("Equal To");
			
			// creates all toggle groups
			ToggleGroup colorGroup = new ToggleGroup();
			
			// initializes Radio Buttons
			GUI.initRadio(badgerRed);
			GUI.initRadio(green);
			GUI.initRadio(off);
			GUI.initRadio(purple);
			GUI.initRadio(blue);
			GUI.initRadio(proteinRadioButton);
			GUI.initRadio(fiberRadioButton);
			GUI.initRadio(carbohydrateRadioButton);
			GUI.initRadio(fatRadioButton);
			GUI.initRadio(caloriesRadioButton);
			GUI.initRadio(greaterEqualThan);
			GUI.initRadio(lessEqualThan);
			GUI.initRadio(equalTo);
			
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
			//GUI.initLabel(createMealTitleLabel, 0);
			//GUI.initLabel(currentMealLabel, 0);
			GUI.initLabel(sortLabel, 0);
			GUI.initLabel(newFoodName, 1);
			GUI.initLabel(addFoodTitleLabel, 1);
			//GUI.initLabel(mealNameLabel, 0);
			GUI.initLabel(nameQueryLabel, 0);
			GUI.initLabel(foodID, 0);
			
			// sets up food info screen
			food.setFont(Font.font("Arial", 24));
			foodInfo.setPrefWidth(500);
			foodInfo.setAlignment(Pos.TOP_CENTER);
			foodInfo.setStyle("-fx-font: 40 arial;");
		
			nameQueryField.setPromptText("Name");
			nameQueryBox.getChildren().addAll(nameQueryLabel, nameQueryField);
			saveMeal.setPrefWidth(850);
			applyQuery.setPrefWidth(850);
			//createMealTitleLabel.setFont(Font.font("arial", FontWeight.BOLD, 36));
			//createMealTitleLabel.setPrefWidth(850);
			queryLabel.setFont(Font.font("arial", FontWeight.BOLD, 36));
			queryLabel.setPrefWidth(850);
			
			optionsField.setPrefWidth(400);
			createMealField.setPrefWidth(400);
			createMealField.setPrefHeight(30);
			createMealField.setBorder(new Border(new BorderStroke(Color.BLACK,
					BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			ruleScrollPane.setPrefWidth(400);
			
			// sets spacing within button box
			HBox.setMargin(saveFood, new Insets(14));
			HBox.setMargin(loadFood, new Insets(14));
			//HBox.setMargin(createMeal, new Insets(14));
			HBox.setMargin(options, new Insets(14));
			HBox.setMargin(query, new Insets(14));
			HBox createMealFieldBox = new HBox();
			
			// creates a scrolling pane that contains the list of food
			foodPane.setContent(foodList);
			foodPane.setPrefHeight(700);
			
			mealPane.setContent(new VBox());
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
			rightPane.getChildren().addAll(new HBox(mealListTitle, analyzeMeal, clearMeal), mealPane);
			
			// Adds radiobuttons to Toggle groups
			badgerRed.setToggleGroup(colorGroup);
			green.setToggleGroup(colorGroup);
			off.setToggleGroup(colorGroup);
			purple.setToggleGroup(colorGroup);
			blue.setToggleGroup(colorGroup);
			caloriesRadioButton.setToggleGroup(ruleTypeGroup);
			fatRadioButton.setToggleGroup(ruleTypeGroup);
			carbohydrateRadioButton.setToggleGroup(ruleTypeGroup);
			fiberRadioButton.setToggleGroup(ruleTypeGroup);
			proteinRadioButton.setToggleGroup(ruleTypeGroup);
			greaterEqualThan.setToggleGroup(ruleRuleGroup);
			equalTo.setToggleGroup(ruleRuleGroup);
			lessEqualThan.setToggleGroup(ruleRuleGroup);
			
			// putting all four of our panes into root 
			root.setLeft(leftPane);
			root.setCenter(foodInfo);
			root.setRight(rightPane);
			root.setBottom(bottomBox);
			
			// add rules to the ruleBox
			nameRuleBox.getChildren().addAll(caloriesRadioButton, fatRadioButton, carbohydrateRadioButton,
					fiberRadioButton, proteinRadioButton);
			
			ruleRuleBox.getChildren().addAll(greaterEqualThan, equalTo, lessEqualThan);
			
			ruleBox.getChildren().addAll(nameRuleBox, ruleRuleBox);
			
			// adds label/field to LoadFoodBox
			loadFoodBox.getChildren().addAll(loadFileLabel, loadFoodField);
			
			// adds label/field to AnalyzeMealBox
			optionsBox.getChildren().addAll(optionsLabel, optionsField, colorBox);
			
			// adds label/field/button to CreateMealFieldBox
			createMealFieldBox.getChildren().addAll(addFoodLabel, createMealField, addToMealButton);
			
			// adds label/field/box/label to MealListAdd (create meal screen)
			//mealListAddBox.getChildren().addAll(createMealTitleLabel, new HBox(mealNameLabel, optionsField), createMealFieldBox, currentMealLabel, mealScrollPane, saveMeal);
			
			// adds necessary checkboxes to queryBox
			queryBox.getChildren().addAll(queryLabel, nameQueryBox, ruleBox, ruleScrollPane, applyQuery);
			
			// adds necessary labels/text fields to addFoodScreen
			addFoodScreen.getChildren().addAll(addFoodTitleLabel, new HBox(newFoodName, addFoodName), new HBox(foodID, addFoodID),
					new HBox(calories, addFoodCals), new HBox(fat, addFoodFats), new HBox(carbohydrates, addFoodCarbs), 
					new HBox(fiber, addFoodFibers), new HBox(protein, addFoodProteins), addFoodSubmit);
			
			// initializes list of items in meal, adds it to MealListAdd
			mealScrollPane.setPrefHeight(400);
			mealScrollPane.setContent(mealScrollList);
			ruleScrollPane.setPrefHeight(400);
			
			// Adds all radio buttons to color list
			colorBox.getChildren().addAll(badgerRed, blue, purple, green, off);
						
			// sets actions for each button
			loadFood.setOnAction(MealEventHandler.loadFoodHandler);
			saveFood.setOnAction(MealEventHandler.saveFoodHandler);
			options.setOnAction(MealEventHandler.optionsHandler);
			//createMeal.setOnAction(MealEventHandler.createMealHandler);
			addToMealButton.setOnAction(MealEventHandler.addToMealHandler);
			query.setOnAction(MealEventHandler.queryHandler);
			badgerRed.setOnAction(MealEventHandler.colorHandler);
			green.setOnAction(MealEventHandler.colorHandler);
			off.setOnAction(MealEventHandler.colorHandler);
			purple.setOnAction(MealEventHandler.colorHandler);
			blue.setOnAction(MealEventHandler.colorHandler);
			addFoodButton.setOnAction(MealEventHandler.addFoodScreenHandler);
			saveMeal.setOnAction(MealEventHandler.saveMealHandler);
			applyQuery.setOnAction(MealEventHandler.applyQueryHandler);
			addFoodSubmit.setOnAction(MealEventHandler.submitNewFoodHandler);
			
			// THIS IS THE STUFF THAT ERIC HAS ADDED
			
			GUI.initButton(queryAddRuleButton);
			queryAddRuleButton.setPrefWidth(100);
			queryAddRuleButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
			queryNumberField.setPromptText("Number");
			ruleBox.getChildren().addAll(queryNumberField, queryAddRuleButton);
			HBox.setMargin(nameRuleBox, new Insets(10));
			HBox.setMargin(ruleRuleBox, new Insets(10));
			HBox.setMargin(queryNumberField, new Insets(10));
			HBox.setMargin(queryAddRuleButton, new Insets(10));
			queryAddRuleButton.setOnAction(MealEventHandler.addRuleHandler);
			ruleScrollPane.setContent(ruleList);
			
			foodInfoScreen.setAlignment(Pos.TOP_CENTER);
			addToMealButton.setOnAction(MealEventHandler.addToMealHandler);
			
			// END STUFF THAT ERIC HAS ADDED
			
			// displays the stage
			primaryStage.show();
			
		} catch(Exception e) {
			// Alert dialog that will display if an error is thrown in the main body
			Alert dialog = new Alert(Alert.AlertType.ERROR);
			dialog.setHeaderText("An internal error occured, please re-attempt or re-load program.\n"
					+ "If problem persists, please contact system administrator by email: jpientka@wisc.edu");
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
