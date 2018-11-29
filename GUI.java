package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI {
	public static void initButton(Button button) {
		
		button.setFont(Font.font("arial", FontWeight.BOLD, 24));
		button.setPrefWidth(185);
		button.setTextFill(Color.BLACK);
		
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(150, 15, 15));
		
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	        	button.setTextFill(Color.rgb(150, 15, 15));
	            button.setEffect(dropShadow);
	        }
		});
		
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	            button.setEffect(null);
	        	button.setTextFill(Color.BLACK);
	        }
		});
		
	}
	
	public static void initLabel(Label label, int type) {
		label.setFont(Font.font("arial", FontWeight.BOLD, 16));
		label.setTextFill(Color.BLACK);
		if(type==0) {
			label.setPrefWidth(150);
		}
			
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(150, 15, 15)); 
		
		label.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				label.setTextFill(Color.rgb(150, 15, 15));
				label.setEffect(dropShadow);
			}
		});
		
		label.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				label.setEffect(null);
				label.setTextFill(Color.BLACK);
			}
		});
	}
	
	public static void initLabel(Label label) {
		label.setFont(Font.font("arial", FontWeight.BOLD, 16));
		label.setPrefWidth(150);
		label.setTextFill(Color.BLACK);
		
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(150, 15, 15)); 
		
		label.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				label.setTextFill(Color.rgb(150, 15, 15));
				label.setEffect(dropShadow);
			}
		});
		
		label.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				label.setEffect(null);
				label.setTextFill(Color.BLACK);
			}
		});
	}
	
	public static void initScrollButton(Button button) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(150, 15, 15)); 
		
		button.setTextFill(Color.BLACK);
		button.setOnAction(MealEventHandler.scrollMealHandler);
		button.setBorder(null);
		
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				button.setTextFill(Color.rgb(150, 15, 15));
				button.setEffect(dropShadow);
			}
		});
		
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				button.setEffect(null);
				button.setTextFill(Color.BLACK);
			}
		});
	}
	
	public static void initScroll(ScrollPane scrollPane) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(150, 15, 15)); 
		
		scrollPane.setPrefWidth(250);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setStyle("-fx-border-color:crimson; -fx-border-width:0;");
		
		scrollPane.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				scrollPane.setEffect(dropShadow);
			}
		});
		
		scrollPane.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				scrollPane.setEffect(null);
			}
		});
	}
}
