package application;

// imports
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


/**
 * GUI class
 * @author Tony Tu, Eric Maccoux, Tanner Blanke, Jack Pientka
 */
public class GUI {
	public static DropShadow dropShadow = new DropShadow();
	public static Color color = Color.rgb(197, 5, 12);
	public static boolean dropShadowBool;
	public static FadeTransition fadeAnimation;
	
	/**
	 *  instantiate the public static objects in the Main class
	 *  sets drop shadow of button
	 */
	public static void initGUI() {
		Main.root = new BorderPane();
		Main.mealList = new VBox();
		Main.foodInfoScene = 0;
		Main.foodInfo = new Label("Select Food");
		Main.loadFoodBox = new HBox();
		Main.optionsBox = new HBox();
		Main.mealListAddBox = new VBox();
		Main.createMealScreen = new VBox();
		Main.queryBox = new VBox();
		Main.mealScrollPane = new ScrollPane();
		Main.mealScrollList = new VBox();
		Main.optionsField = new TextField();
		Main.createMealField = new TextField();
		Main.food = new Label();
		Main.foodDataList = new FoodData();
		Main.foodList = new VBox();
		Main.addFoodScreen = new VBox();
		Main.mealInfoScreen = new VBox();
		Main.ruleList = new ScrollPane();
		
		dropShadow.setRadius(4.5);
		dropShadow.setOffsetX(2);
		dropShadow.setOffsetY(2);
		dropShadow.setColor(color);
		dropShadowBool=true;
		fadeAnimation = new FadeTransition(Duration.millis(300));
	}
	
	/**
	 * Initializes button
	 * 
	 * @param button the button that will be initiated
	 */
	public static void initButton(Button button) {
		
		// sets font, width, and text color of button
		button.setFont(Font.font("arial", FontWeight.BOLD, 24));
		button.setPrefWidth(500);
		button.setTextFill(Color.BLACK);
				
		// when mouse enters button area, changes color of text and displays drop shadow
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	        	if(dropShadowBool) {
		        	button.setTextFill(color);
		            button.setEffect(dropShadow);
	        	}
	        }
		});
		
		// when mouse exits button area, resets color of text and hides drop shadow
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent e) {
	            button.setEffect(null);
	        	button.setTextFill(Color.BLACK);
	        }
		});
		
	}
	
	/**
	 * Initializes label
	 * 
	 * @param label
	 * @param type
	 */
	public static void initLabel(Label label, int type) {
		// setting font, width, and text color of label
		label.setFont(Font.font("arial", FontWeight.BOLD, 16));
		label.setTextFill(Color.BLACK);
		if(type==0) {
			label.setPrefWidth(150);
		}
		
		// when mouse enters label area, change the color and show the dropshadow
		label.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
	        	if(dropShadowBool) {
					label.setTextFill(color);
	        	}
			}
		});
		
		// when mouse exits label area, change color back and hide the dropshadow
		label.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				label.setTextFill(Color.BLACK);
			}
		});
	}
	
	/**
	 * Initializes scroll buttons
	 * 
	 * @param button
	 */
	public static void initFoodItemButton(Button button, FoodItem item) {
		
		// sets text color, action, and border for button
		button.setTextFill(Color.BLACK);
		button.setOnAction(MealEventHandler.scrollMealHandler);
		button.setBorder(null);
		button.setPrefWidth(200);
		button.setMaxWidth(200);
        button.setOnMouseEntered(e -> fadeAnimation.playFromStart());
        button.setOnMouseExited(e -> fadeAnimation.playFromStart());

        button.setOpacity(0.8);
		// when mouse enters the button area, change color and show dropshadow
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
	        	if(dropShadowBool) {
					button.setTextFill(color);
					button.setEffect(dropShadow);
	        	}
			}
		});
		
		// when mouse exits the button area, change color back and hide dropshadow
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				button.setEffect(null);
				button.setTextFill(Color.BLACK);
			}
		});
	}
	
	/**
	 * Initializes scroll pane
	 * 
	 * @param scrollPane
	 */
	public static void initScroll(ScrollPane scrollPane) {
		
		// sets width, policies, and style of scroll pane
		scrollPane.setPrefWidth(215);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setStyle("-fx-border-color:crimson; -fx-border-width:0;");
	}
	
	/**
	 * Initializes check box
	 * 
	 * @param checkBox
	 */
	public static void initCheckBox(CheckBox checkBox) {
		
		// sets font, width, and style of check box
		checkBox.setFont(Font.font("arial", FontWeight.BOLD, 16));
		checkBox.setPrefWidth(400);
		checkBox.setStyle("-fx-border-color:crimson; -fx-border-width:0;");
		
		// when mouse enters area of check box, show drop shadow
		checkBox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
	        	if(dropShadowBool) {
					checkBox.setTextFill(color);
					checkBox.setEffect(dropShadow);
	        	}
			}
		});
		
		// when mouse exits area of check box, hide drop shadow
		checkBox.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				checkBox.setEffect(null);
				checkBox.setTextFill(Color.BLACK);
			}
		});
	}
	
	/**
	 * 
	 * @param radioButton
	 */
	public static void initRadio(RadioButton radioButton) {
		
		// sets font, width, and style of check box
		radioButton.setFont(Font.font("arial", FontWeight.BOLD, 16));
		radioButton.setStyle("-fx-border-color:crimson; -fx-border-width:0;");
		
		// when mouse enters area of check box, show drop shadow
		radioButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
	        	if(dropShadowBool) {
					radioButton.setTextFill(color);
					radioButton.setEffect(dropShadow);
	        	}
			}
		});
		
		// when mouse exits area of check box, hide drop shadow
		radioButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				radioButton.setEffect(null);
				radioButton.setTextFill(Color.BLACK);
			}
		});
	}
	
	/**
	 * Initializes scroll buttons
	 * TODO: finish this method
	 * @param button
	 */
	public static void initMealItemButton(Button button, FoodData data) {
		
		// sets text color, action, and border for button
		button.setTextFill(Color.BLACK);
		button.setOnAction(MealEventHandler.scrollMealHandler);
		button.setBorder(null);
		button.setPrefWidth(200);
		button.setMaxWidth(200);
		
		// when mouse enters the button area, change color and show dropshadow
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
	        	if(dropShadowBool) {
					button.setTextFill(color);
					button.setEffect(dropShadow);
	        	}
			}
		});
		
		// when mouse exits the button area, change color back and hide dropshadow
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				button.setEffect(null);
				button.setTextFill(Color.BLACK);
			}
		});
	}
}
