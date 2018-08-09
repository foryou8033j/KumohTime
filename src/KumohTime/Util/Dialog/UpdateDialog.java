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
import KumohTime.Util.Browser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 업데이트가 존재 할 경우 보여지는 Dialog이다
 * 사용자 브라우저를 보여주고 신규버전을 내려받도록 유도한다.
 * 
 * @author Jeongsam Seo
 * @since 2018-07-05
 * @deprecated 0.2 버전 이후 자동업데이트 기능을 추가하여 사용하지 않음
 */
@Deprecated
public class UpdateDialog {

	/**
	 * 0.2 버전 이후 자동업데이트 기능을 추가하여 사용하지 않음
	 * @param mainApp	MainApp
	 * @param title		Dialog Title
	 * @param message	Dialog Message
	 * @param okMessage	Dialog Action Button
	 */
	@Deprecated
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
			Browser.open(mainApp.getAppData().getServerPath() + mainApp.getAppData().getServerVersion() + "/KumohTime.exe");
		});
		
		content.setActions(button);
		dialog.show();
	}

}
