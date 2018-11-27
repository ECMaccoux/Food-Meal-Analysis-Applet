package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
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
import javafx.scene.text.FontWeight;


public class Main extends Application {
	public static BorderPane root = new BorderPane();
	public static int foodInfoScene = 0;
	public static Label foodInfo = new Label("Food Info \n more food info \n\n\n\n\n\n FOOD INFOOOOO");
	public static HBox loadFoodBox = new HBox();
	public static HBox analyzeMealBox = new HBox();
	public static VBox mealListAdd = new VBox();
	public static ScrollPane mealScrollPane = new ScrollPane();
	public static VBox mealScrollList = new VBox();
	public static TextField analyzeFoodField = new TextField();
	public static TextField createMealField = new TextField();
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
			addMealButton.setPrefWidth(45);
			addMealButton.setFont(Font.font("arial", 14));
			HBox bottomBox = new HBox();
			bottomBox.setSpacing((java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-900)/6);
			double oof = primaryStage.getWidth();
			System.out.println(oof);
			//GridPane bottomGrid = new GridPane();
			bottomBox.getChildren().addAll(loadFood, saveFood, createMeal, analyzeMeal, query);
			//bottomGrid.getChildren().addAll(loadFood, saveFood, createMeal, analyzeMeal, query);
			root.setBottom(bottomBox);
			HBox.setMargin(saveFood, new Insets(14));
			HBox.setMargin(loadFood, new Insets(14));
			HBox.setMargin(createMeal, new Insets(14));
			HBox.setMargin(analyzeMeal, new Insets(14));
			HBox.setMargin(query, new Insets(14));
			VBox foodPane = new VBox();
			VBox rightPane = new VBox();
			ScrollPane foodList = new ScrollPane();
			for(int i=0; i<200; i++) {
				foodPane.getChildren().add(new Label("Test Food #" + i + " Test Food lmao"));
			}
			foodList.setPrefWidth(250);
			VBox mealPane = new VBox();
			ScrollPane mealList = new ScrollPane();
			for(int i=0; i<200; i++) {
				mealPane.getChildren().add(new Label("Test Meal #" + i + " Test Meal lmao"));
			}
			mealList.setContent(mealPane);
			mealList.setPrefWidth(250);
			mealList.setPrefHeight(400);
			
			Label mealInfo = new Label("Meal Info \n more MEAL info \nn\n\n MEAL INFOOOO");
			mealInfo.setStyle("-fx-font: 40 arial;");
			foodInfo.setStyle("-fx-font: 40 arial;");
			rightPane.getChildren().add(mealList);
			rightPane.getChildren().add(mealInfo);
			root.setRight(rightPane);
			root.setCenter(foodInfo);
			foodList.setContent(foodPane);
			root.setLeft(foodList);
			TextField loadFoodField = new TextField();
			loadFoodBox.getChildren().add(new Label("Load File:"));
			loadFoodBox.getChildren().add(loadFoodField);
			HBox createMealFieldBox = new HBox();
			analyzeMealBox.getChildren().add(new Label("Analyze Meal:"));
			analyzeMealBox.getChildren().add(analyzeFoodField);
			createMealFieldBox.getChildren().add(new Label("Add Food:"));
			createMealFieldBox.getChildren().add(createMealField);
			createMealFieldBox.getChildren().add(addMealButton);
			mealListAdd.getChildren().add(new Label("Create Meal:"));
			mealListAdd.getChildren().add(analyzeFoodField);
			mealListAdd.getChildren().add(createMealFieldBox);
			mealListAdd.getChildren().add(new Label("Current Meal"));
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
	public static void main(String[] args) {
		launch(args);
	}
}
