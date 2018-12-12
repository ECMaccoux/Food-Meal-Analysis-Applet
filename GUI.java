package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;

/**
 * The GUI class creates all parts of the graphical user interface, modifies them to display
 * correctly, and displays them within the stage.
 * 
 * @author Tanner Blanke, Eric Maccoux, Jack Pientka, Tony Tu
 *
 */
public class GUI {
	
	// Static variables of GUI class
	
	public static Scene scene;													// Scene being created/modified
	
	public static int currentScreen = 0;										// current screen being viewed
		// currentScreen: 				0 = "Select Food" screen
				//						1 = food info screen
				//						2 = options screen
				// 						3 = query screen
				//						4 = add FoodItem screen
	
	
	public static BorderPane root = new BorderPane();							// BorderPane that contains all GUI objects
	
	private final int MAX_WIDTH = 1280;											// max width of program window
	private final int MAX_HEIGHT = 640;											// max height of program window
	
	// static items for left pane of program
		public static VBox leftPane = new VBox();								// entire left pane
		public static VBox foodItemButtonList = new VBox();						// list of MealButtons for left pane
		public static VBox queryButtonList = new VBox();						// list of MealButtons for left pane when query has been applied
		public static ScrollPane foodPane = new ScrollPane();					// ScrollPane that contains/displays foodItemButtonList
	
	// static items for center pane of program
		// "Select Food" screen
			public static Label selectFoodLabel = new Label("Select Food");		// Label that acts as main "Select Food" screen
		// "Food Info" screen
			public static FoodItem currentFood = null;							// FoodItem that is currently being viewed
		// "Options" screen
			public static HBox optionsScreen = new HBox();						// HBox that contains all items for options screen
		// "Query" screen
			public static VBox queryScreen = new VBox();						// VBox that contains all items for query screen
			public static ScrollPane ruleScrollPane = new ScrollPane();			// ScrollPane that contains/displays list of added rules
			public static VBox ruleList = new VBox();							// VBox that contains all added rules
			public static TextField queryNameField = new TextField();			// TextField, where name to be filtered for is inputted
			public static ToggleGroup ruleTypeGroup = new ToggleGroup();		// group of RadioButtons for which nutrient is to be filtered for
			public static ToggleGroup comparatorRuleGroup = new ToggleGroup();	// group of RadioButtons for which comparator is to be filtered with
			public static TextField queryNumberField = new TextField();			// TextField, where number to be filtered for is inputted
		// "Add New Food" screen
			public static VBox addFoodScreen = new VBox();						// VBox that contains all items for "Add New Food" screen
			public static TextField addFoodNameField = new TextField();			// TextField, where name of food to be added is inputted
			public static TextField addFoodCalsField = new TextField();			// TextField, where calories of food to be added is inputted
			public static TextField addFoodFatsField = new TextField();			// TextField, where fats of food to be added is inputted
			public static TextField addFoodCarbsField = new TextField();		// TextField, where carbohydrates of food to be added is inputted
			public static TextField addFoodFibersField = new TextField();		// TextField, where fibers of food to be added is inputted
			public static TextField addFoodProteinsField = new TextField();		// TextField, where proteins of food to be added is inputted
	
	// static items for right pane of program
		public static ScrollPane mealPane = new ScrollPane();					// ScrollPane that contains/displays mealItemButtonList
		public static VBox mealItemButtonList = new VBox();						// list of MealButtons for right pane

	/**
	 * Two-argument constructor, initializes the GUI
	 * 
	 * @param foodData FoodData object to be used in program
	 * @param primaryStage Stage to be modified/shown
	 */
	public GUI(FoodData foodData, Stage primaryStage) {
		
		scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
		
		scene.getStylesheets().add(getClass().getResource("themeRegular.css").toExternalForm());
		
		primaryStage.setResizable(true);
		primaryStage.setMaxWidth(MAX_WIDTH);
		primaryStage.setMaxHeight(MAX_HEIGHT);
		primaryStage.setTitle("Food Query and Meal Analysis");
		primaryStage.setScene(scene);
		
		build(root);
	}
	
	/**
	 * Builds the GUI, utilizing the other build methods to do so
	 * 
	 * @param root BorderPane that GUI objects will be displayed within
	 */
	private void build(BorderPane root) {
		buildLeft(root);
		buildCenter(root);
		buildRight(root);
		buildBottom(root);
	}
	
	/**
	 * Builds the left pane of the GUI, which consists of the main food list.
	 * 
	 * @param root BorderPane that GUI objects will be displayed within
	 */
	private void buildLeft(BorderPane root) {
		
		// title label
		Label foodListTitle = new Label("Foods                             ");
		NodeInitializer.initLabel(foodListTitle, 1);
		
		// add button
		Button addFoodScreenButton = new Button("Add");
		addFoodScreenButton.setOnAction(MealEventHandler.addFoodScreenHandler);
		
		// scroll pane
		foodPane.setPrefHeight(700);
		NodeInitializer.initScrollPane(foodPane);
		
		// VBox inside scroll pane
		foodPane.setContent(foodItemButtonList);
		
		// full left pane
		leftPane.getChildren().addAll(new HBox(foodListTitle, addFoodScreenButton), foodPane);
		
		// places leftPane in scene
		root.setLeft(leftPane);
	}
	
	/**
	 * Builds the center pane of the GUI, which consists of either the "Query" screen,
	 * "Add New Food" screen, "Options" screen, "Analyze Meal" screen, or "Food Info" screen.
	 * 
	 * @param root BorderPane that GUI objects will be displayed within
	 */
	private void buildCenter(BorderPane root) {

	// "Select Food" Screen
		
		NodeInitializer.initLabel(selectFoodLabel, 0);
		selectFoodLabel.setPrefWidth(500);
		selectFoodLabel.setAlignment(Pos.TOP_CENTER);
		selectFoodLabel.setStyle("-fx-font: 40 arial;");
		
	// "Query" Screen
		
		// query screen title
		Label queryScreenTitle = new Label("Filter Query");
		NodeInitializer.initLabel(queryScreenTitle, 0);
		queryScreenTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));
		queryScreenTitle.setPrefWidth(850);
		
		// input name query rule
		Label queryNameLabel = new Label("Food Name: ");
		NodeInitializer.initLabel(queryNameLabel, 0);
		queryNameLabel.setFont(Font.font("Arial", 16));
		queryNameField.setPromptText("Name");
		
		// input nutrient query rule (with comparators)
		VBox nutrientRuleBox = new VBox();
		RadioButton caloriesRadioButton = new RadioButton("Calories");
		RadioButton fatRadioButton = new RadioButton("Fats");
		RadioButton carbohydrateRadioButton = new RadioButton("Carbohydrates");
		RadioButton fiberRadioButton = new RadioButton("Fibers");
		RadioButton proteinRadioButton = new RadioButton("Proteins");
		nutrientRuleBox.getChildren().addAll(caloriesRadioButton, fatRadioButton, carbohydrateRadioButton,
				fiberRadioButton, proteinRadioButton);
		
		// initializes all nutrient query radio buttons
		NodeInitializer.initRadioButton(caloriesRadioButton);
		NodeInitializer.initRadioButton(fatRadioButton);
		NodeInitializer.initRadioButton(carbohydrateRadioButton);
		NodeInitializer.initRadioButton(fiberRadioButton);
		NodeInitializer.initRadioButton(proteinRadioButton);
		
		// assigns all nutrient query radio buttons to same ToggleGroup
		caloriesRadioButton.setToggleGroup(ruleTypeGroup);
		fatRadioButton.setToggleGroup(ruleTypeGroup);
		carbohydrateRadioButton.setToggleGroup(ruleTypeGroup);
		fiberRadioButton.setToggleGroup(ruleTypeGroup);
		proteinRadioButton.setToggleGroup(ruleTypeGroup);
		
		// comparators for nutrient query rule
		VBox comparatorRuleBox = new VBox();
		RadioButton greaterEqualThan = new RadioButton("Greater Than or Equal To");
		RadioButton lessEqualThan = new RadioButton("Less Than or Equal To");
		RadioButton equalTo = new RadioButton("Equal To");
		comparatorRuleBox.getChildren().addAll(greaterEqualThan, equalTo, lessEqualThan);
		
		// initializes comparator radio buttons
		NodeInitializer.initRadioButton(greaterEqualThan);
		NodeInitializer.initRadioButton(lessEqualThan);
		NodeInitializer.initRadioButton(equalTo);
		
		// assigns all comparator radio buttons to same ToggleGroup
		greaterEqualThan.setToggleGroup(comparatorRuleGroup);
		equalTo.setToggleGroup(comparatorRuleGroup);
		lessEqualThan.setToggleGroup(comparatorRuleGroup);
		
		// text field for inputting a number for a rule
		queryNumberField.setPromptText("Number");
		
		// button to add rule to rule list
		Button queryAddRuleButton = new Button("Add Rule");
		NodeInitializer.initButton(queryAddRuleButton);
		queryAddRuleButton.setPrefWidth(200);
		queryAddRuleButton.setFont(Font.font("Arial", 14));
		queryAddRuleButton.setOnAction(MealEventHandler.addRuleHandler);
		
		// rule scroll pane that shows list of rules
		ruleScrollPane.setPrefWidth(400);
		ruleScrollPane.setPrefHeight(400);
		
		// VBox inside scroll pane
		ruleScrollPane.setContent(ruleList);
		
		// button to apply all query rules
		Button applyQueryButton = new Button("Apply Query");
		NodeInitializer.initButton(applyQueryButton);
		applyQueryButton.setPrefWidth(850);
		applyQueryButton.setOnAction(MealEventHandler.applyQueryHandler);
		
		// add all items for query screen into queryScreen
		queryScreen.getChildren().addAll(queryScreenTitle, new HBox(queryNameLabel, queryNameField),
				new HBox(nutrientRuleBox, comparatorRuleBox, new VBox(queryNumberField, queryAddRuleButton)), ruleScrollPane, 
				applyQueryButton);
		
		// sets margins/spacing for query screen
		HBox.setMargin(nutrientRuleBox, new Insets(10));
		HBox.setMargin(comparatorRuleBox, new Insets(10));
		VBox.setMargin(queryNumberField, new Insets(15));
		VBox.setMargin(queryAddRuleButton, new Insets(15));
		
	// "Options" Screen
		
		// title label for options screen
		Label optionsLabel = new Label("Themes: ");
		NodeInitializer.initLabel(optionsLabel, 0);
		
		// HBox for all color options
		HBox colorBox = new HBox();
		
		// radio buttons for color options
		RadioButton badgerRed = new RadioButton("Badger Red");
		RadioButton green = new RadioButton("Green");
		RadioButton purple = new RadioButton("Purple");
		RadioButton blue = new RadioButton("Blue");
		RadioButton off = new RadioButton("Off");
		colorBox.getChildren().addAll(badgerRed, green, purple, blue, off);
		
		// initializes color radio buttons
		NodeInitializer.initRadioButton(badgerRed);
		NodeInitializer.initRadioButton(green);
		NodeInitializer.initRadioButton(purple);
		NodeInitializer.initRadioButton(blue);
		NodeInitializer.initRadioButton(off);
		off.setSelected(true);
		
		// assigns all color radio buttons into new ToggleGroup
		ToggleGroup colorGroup = new ToggleGroup();
		badgerRed.setToggleGroup(colorGroup);
		green.setToggleGroup(colorGroup);
		off.setToggleGroup(colorGroup);
		purple.setToggleGroup(colorGroup);
		blue.setToggleGroup(colorGroup);
		
		// sets actions for all radio buttons
		badgerRed.setOnAction(MealEventHandler.colorHandler);
		green.setOnAction(MealEventHandler.colorHandler);
		purple.setOnAction(MealEventHandler.colorHandler);
		blue.setOnAction(MealEventHandler.colorHandler);
		off.setOnAction(MealEventHandler.colorHandler);
		
		// add all items on options screen to optionsScreen
		optionsScreen.getChildren().addAll(optionsLabel, colorBox);
		
	// "Add New Food" screen
		
		// title for "Add New Food" screen
		Label addFoodTitleLabel = new Label("Add New Food");
		NodeInitializer.initLabel(addFoodTitleLabel, 0);
		addFoodTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
		addFoodTitleLabel.setPrefWidth(850);
		
		// label/text box for new name
		Label addFoodNameLabel = new Label("Name: ");
		NodeInitializer.initLabel(addFoodNameLabel, 1);
		
		// labels for all new nutrient amounts
		Label addCaloriesLabel = new Label("Calories: ");
		Label addFatLabel = new Label("Fat (Grams): ");
		Label addCarbsLabel = new Label("Carbohydrates (Grams): ");
		Label addFiberLabel = new Label("Fiber (Grams): ");
		Label addProteinLabel = new Label("Protein (Grams): ");
		NodeInitializer.initLabel(addCaloriesLabel, 1);
		NodeInitializer.initLabel(addFatLabel, 1);
		NodeInitializer.initLabel(addCarbsLabel, 1);
		NodeInitializer.initLabel(addFiberLabel, 1);
		NodeInitializer.initLabel(addProteinLabel, 1);
		
		// button for submitting new food item
		Button addFoodSubmit = new Button("Submit");
		NodeInitializer.initButton(addFoodSubmit);
		addFoodSubmit.setOnAction(MealEventHandler.submitNewFoodHandler);
		
		// adds all objects on "Add New Food" screen to addFoodScreen
		addFoodScreen.getChildren().addAll(addFoodTitleLabel, new HBox(addFoodNameLabel, addFoodNameField),
				new HBox(addCaloriesLabel, addFoodCalsField), new HBox(addFatLabel, addFoodFatsField), new HBox(addCarbsLabel, addFoodCarbsField), 
				new HBox(addFiberLabel, addFoodFibersField), new HBox(addProteinLabel, addFoodProteinsField), addFoodSubmit);
		
		// places center pane in scene
		root.setCenter(selectFoodLabel);
	}
	
	/**
	 * Builds the right pane of the GUI, which consists of the meal list.
	 * 
	 * @param root BorderPane that GUI objects will be displayed within
	 */
	private void buildRight(BorderPane root) {
		// title label
		Label mealListTitle = new Label("Meal           ");
		NodeInitializer.initLabel(mealListTitle, 1);
		
		// analyze meal button
		Button analyzeMealButton = new Button("Analyze Meal");
		analyzeMealButton.setOnAction(MealEventHandler.analyzeMealHandler);
		
		// clear meal button
		Button clearMealButton = new Button("Clear");
		clearMealButton.setOnAction(MealEventHandler.clearMealHandler);
		
		// scroll pane
		mealPane.setPrefHeight(700);
		NodeInitializer.initScrollPane(mealPane);
		
		// VBox inside scroll pane
		mealPane.setContent(mealItemButtonList);
		
		// full pane
		VBox rightPane = new VBox();
		rightPane.getChildren().addAll(new HBox(mealListTitle, analyzeMealButton, clearMealButton), mealPane);
		
		// places rightPane in scene
		root.setRight(rightPane);
	}
	
	/**
	 * Builds the bottom pane of the GUI, which consists of the program's main navigational buttons.
	 * 
	 * @param root BorderPane that GUI objects will be displayed within
	 */
	private void buildBottom(BorderPane root) {
		
		// Load Food button
		Button loadFoodButton = new Button("Load Foods");
		NodeInitializer.initButton(loadFoodButton);
		loadFoodButton.setOnAction(MealEventHandler.loadFoodFromFileHandler);
		
		// Save Food button
		Button saveFoodButton = new Button("Save Foods");
		NodeInitializer.initButton(saveFoodButton);
		saveFoodButton.setOnAction(MealEventHandler.saveFoodToFileHandler);
		
		// Query button
		Button queryButton = new Button("Query");
		NodeInitializer.initButton(queryButton);
		queryButton.setOnAction(MealEventHandler.queryHandler);
		
		// Options button
		Button optionsButton = new Button("Options");
		NodeInitializer.initButton(optionsButton);
		optionsButton.setOnAction(MealEventHandler.optionsHandler);
		
		// Help button
		Button helpButton = new Button("Help");
		NodeInitializer.initButton(helpButton);
		helpButton.setOnAction(MealEventHandler.helpButtonHandler);
		
		// Bottom box that contains buttons
		HBox bottomBox = new HBox();
		bottomBox.getChildren().addAll(loadFoodButton, saveFoodButton, queryButton, optionsButton, helpButton);
		
		// sets spacing within bottom box
		HBox.setMargin(saveFoodButton, new Insets(14));
		HBox.setMargin(loadFoodButton, new Insets(14));
		HBox.setMargin(optionsButton, new Insets(14));
		HBox.setMargin(queryButton, new Insets(14));
		HBox.setMargin(helpButton, new Insets(14));
		
		// places bottomBox in scene
		root.setBottom(bottomBox);
	}
	
}
