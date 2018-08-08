package KumohTime.Util;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.text.PlainDocument;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @deprecated
 * @author Jeongsam Seo
 */
public class InfoManager {
	

	public static Popup createPopup(final String message) {
	    final Popup popup = new Popup();
	    popup.setAutoFix(true);
	    popup.setAutoHide(true);
	    popup.setHideOnEscape(true);
	    Label label = new Label(message);
	    label.setOnMouseReleased(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent e) {
	            popup.hide();
	        }
	    });
	    label.setStyle(""
	    		+ ".popup {"
	    		+ "    -fx-background-color: yellow;"
	    		+ "    -fx-padding: 10;"
	    		+ "    -fx-border-color: black; "
	    		+ "    -fx-border-width: 5;"
	    		+ "    -fx-font-size: 16;"
	    		+ "}"
	    		);
	    label.getStyleClass().add("popup");
	    popup.getContent().add(label);
	    return popup;
	}

	public static void showPopupMessage(final String message, final Stage stage) {
	    final Popup popup = createPopup(message);
	    popup.setOnShown(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent e) {
	            popup.setX(stage.getX() + stage.getWidth()/2 - popup.getWidth()/2);
	            popup.setY(stage.getY() + stage.getHeight()/2 - popup.getHeight()/2);
	        }
	    });        
	    
	}
	
	
}
