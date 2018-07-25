package KumohTime.Util.Dialog;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import KumohTime.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 간단한 오류 메세지를 보여주는 Dialog이다.
 * @author Jeongsam
 * @since 2018-07-05
 */
public class AlertDialog{

	private JFXButton button;
	
	public AlertDialog(MainApp mainApp,String title, String message, String okMessage) {
		
		JFXDialogLayout content = new JFXDialogLayout();
		Text text = new Text(title);
		text.setFont(Font.font("malgun gothic", FontWeight.BOLD, 18));
	    content.setHeading(text);
	    
	    Text textMessage = new Text(message);
	    textMessage.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 14));
	    content.setHeading(text);
	    
	    content.setBody(textMessage);
	    JFXDialog dialog = new JFXDialog(mainApp.getRootLayoutController().getRootLayout(), content, JFXDialog.DialogTransition.CENTER);
	    button = new JFXButton(okMessage);
	    button.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 12));
	    button.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            dialog.close();
	        }
	    });
	    content.setActions(button);
	    dialog.show();
	    
	}
	
	public JFXButton getButton() {
		return button;
	}

}
