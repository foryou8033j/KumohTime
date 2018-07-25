package KumohTime.Util.Dialog;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JTextArea;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 간단한 오류 메세지를 보여주는 Dialog이다.
 * 
 * @author Jeongsam
 * @since 2018-07-05
 */
public class BugReportDialog {

		public BugReportDialog(MainApp mainApp) {

			
			
		JFXDialogLayout content = new JFXDialogLayout();
		Text text = new Text("오류 신고");
		text.setFont(Font.font("malgun gothic", FontWeight.BOLD, 18));
		content.setHeading(text);

		JFXTextArea area = new JFXTextArea();
		VBox vb = new VBox();
		vb.getChildren().add(area);

		content.setBody(vb);
		JFXDialog dialog = new JFXDialog(mainApp.getRootLayoutController().getRootLayout(), content,
				JFXDialog.DialogTransition.CENTER);
		JFXButton button = new JFXButton("신고");
		button.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 12));
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new DataBase().bugReport(area.getText(), mainApp.getAppData().getAppPropertise().getVersionString());
				dialog.close();
				new AlertDialog(mainApp, "신고 완료", "정상적으로 신고 되었습니다.\n도움 주셔서 감사합니다!", "확인");
			}
		});
		
		content.setActions(button);
		dialog.show();

	}

}
