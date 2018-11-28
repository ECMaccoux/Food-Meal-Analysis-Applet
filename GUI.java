package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
}
