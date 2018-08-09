package KumohTime.Util.Dialog;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Util.Dialog.Loading.LoadingDialog;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 개발자에게 메세지를 전송하는 Dialog
 * 
 * @author Jeongsam Seo
 * @since 2018-07-05
 */
public class BugReportDialog {

		public BugReportDialog(MainApp mainApp) {

		JFXDialogLayout content = new JFXDialogLayout();
		Text text = new Text("개발자에게 메세지 전송");
		text.setFont(Font.font("malgun gothic", FontWeight.BOLD, 18));
		content.setHeading(text);

		JFXTextArea area = new JFXTextArea();
		VBox vb = new VBox(20);
		Text body = new Text("메세지는 개발자에게 즉시 알려지게 됩니다.\n하고싶은 말이나 아이디어, 오류 등등을 보내주세요\n해결 가능 한 문제점 이라면 다음 업데이트에 반영됩니다.");
		body.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 14));
		vb.getChildren().addAll(body, area);

		content.setBody(vb);
		JFXDialog dialog = new JFXDialog(mainApp.getRootLayoutController().getRootLayout(), content,
				JFXDialog.DialogTransition.CENTER);
		JFXButton button = new JFXButton("메세지 전송");
		button.setPrefWidth(100);
		button.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 12));
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				LoadingDialog loading = new LoadingDialog(mainApp.getPrimaryStage());
				
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						dialog.close();
						new DataBase().bugReport(area.getText(), mainApp.getAppData().getAppPropertise().getVersionString());
						return null;
					}
				};
				
				task.setOnSucceeded(e -> {
					loading.close();
					new AlertDialog(mainApp, "신고 완료", "정상적으로 전송 되었습니다.\n도움 주셔서 감사합니다!", "확인");
				});
				
				Thread thread = new Thread(task);
				thread.setDaemon(true);
				thread.start();
				loading.show();
			}
		});
		
		content.setActions(button);
		dialog.show();

	}

}
