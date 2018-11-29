package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI {
	
	/**
	 * Initializes button
	 * 
	 * @param button
	 */
	public static void initButton(Button button) {
		
		// sets font, width, and text color of button
		button.setFont(Font.font("arial", FontWeight.BOLD, 24));
		button.setPrefWidth(185);
		button.setTextFill(Color.BLACK);
		
		// sets drop shadow of button
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(180, 5, 5));
		
		// when mouse enters button area, changes color of text and displays drop shadow
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	        	button.setTextFill(Color.rgb(180, 5, 5));
	            button.setEffect(dropShadow);
	        }
		});
		
		// when mouse exits button area, resets color of text and hides drop shadow
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
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
			
		// initialzing drop shadow
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(180, 5, 5)); 
		
		// when mouse enters label area, change the color and show the dropshadow
		label.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				label.setTextFill(Color.rgb(180, 5, 5));
				label.setEffect(dropShadow);
			}
		});
		
		// when mouse exits label area, change color back and hide the dropshadow
		label.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				label.setEffect(null);
				label.setTextFill(Color.BLACK);
			}
		});
	}
	
	/**
	 * Initialzes scroll buttons
	 * 
	 * @param button
	 */
	public static void initScrollButton(Button button) {
		// initializes drop shadow
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(180, 5, 5)); 
		
		// sets text color, action, and border for button
		button.setTextFill(Color.BLACK);
		button.setOnAction(MealEventHandler.scrollMealHandler);
		button.setBorder(null);
		button.setPrefWidth(200);
		
		// when mouse enters the button area, change color and show dropshadow
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				button.setTextFill(Color.rgb(180, 5, 5));
				button.setEffect(dropShadow);
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
		// initializes dropshadow
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(180, 5, 5)); 
		
		// sets width, policies, and style of scroll pane
		scrollPane.setPrefWidth(250);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setStyle("-fx-border-color:crimson; -fx-border-width:0;");
		
		// when mouse enters area of scroll pane, show drop shadow
		scrollPane.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				scrollPane.setEffect(dropShadow);
			}
		});
		
		// when mouse exits area of scroll pane, hide drop shadow
		scrollPane.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				scrollPane.setEffect(null);
			}
		});
	}
	
	/**
	 * Initializes check box
	 * 
	 * @param checkBox
	 */
	public static void initCheckBox(CheckBox checkBox) {
		// initializes drop shadow
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(180, 5, 5)); 
		
		// sets font, width, and style of check box
		checkBox.setFont(Font.font("arial", FontWeight.BOLD, 16));
		checkBox.setPrefWidth(400);
		checkBox.setStyle("-fx-border-color:crimson; -fx-border-width:0;");
		
		// when mouse enters area of check box, show drop shadow
		checkBox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				checkBox.setEffect(dropShadow);
			}
		});
		
		// when mouse exits area of check box, hide drop shadow
		checkBox.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				checkBox.setEffect(null);
			}
		});
	}
}
