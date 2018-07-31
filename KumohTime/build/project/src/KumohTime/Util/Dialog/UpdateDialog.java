package KumohTime.Util.Dialog;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 간단한 오류 메세지를 보여주는 Dialog이다.
 * 
 * @author Jeongsam
 * @since 2018-07-05
 */
public class UpdateDialog {

	public UpdateDialog(MainApp mainApp, String title, String message, String okMessage) {


		JFXDialogLayout content = new JFXDialogLayout();
		Text text = new Text(title);
		text.setFont(Font.font("malgun gothic", FontWeight.BOLD, 18));
		content.setHeading(text);

		Text textMessage = new Text(message);
		textMessage.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 14));
		content.setHeading(text);

		content.setBody(textMessage);
		JFXDialog dialog = new JFXDialog(mainApp.getRootLayoutController().getRootLayout(), content,
				JFXDialog.DialogTransition.CENTER);
		JFXButton button = new JFXButton(okMessage);
		button.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 12));
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		dialog.setOnDialogClosed(event -> {
			try {
				Desktop.getDesktop().browse(new URI(mainApp.getAppData().getServerPath() + mainApp.getAppData().getServerVersion() + "/KumohTime.exe"));
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		});
		
		content.setActions(button);
		dialog.show();
	}

}
