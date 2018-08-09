package KumohTime.Util.Dialog;

import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import KumohTime.MainApp;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.LectureTime;
import KumohTime.View.Home.TempLecture.TempLectureAddLayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 임시 강의 정보 추가 Dialog
 * 
 * @author Jeongsam Seo
 * @since 2018-07-05
 */
public class TempLectureAddDialog {

	private JFXButton button;
	private JFXDialog dialog;

	private ObservableList<Lecture> tmpList = FXCollections.observableArrayList();
	private ObservableList<TempLectureAddLayoutController> controllerList = FXCollections.observableArrayList();

	private MainApp mainApp;

	public TempLectureAddDialog(MainApp mainApp) {

		this.mainApp = mainApp;

		try {

			JFXDialogLayout content = new JFXDialogLayout();
			Text text = new Text("임시 강의 추가");
			text.setFont(Font.font("malgun gothic", FontWeight.BOLD, 18));
			content.setHeading(text);

			VBox vb = new VBox(10);
			vb.setAlignment(Pos.CENTER);

			Text bodyText = new Text("과목명을 비워두거나 시간을 입력하지 않으면 추가되지 않습니다.");
			bodyText.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 14));
			vb.getChildren().add(bodyText);
			vb.getChildren().add(addBox());

			JFXButton addButton = new JFXButton("추가");
			addButton.setPrefWidth(100);
			addButton.setPrefHeight(30);
			addButton.setRipplerFill(Color.RED);
			addButton.setButtonType(ButtonType.RAISED);
			addButton.setOnAction(e -> {
				if(controllerList.size() > 5) {
					new AlertDialog(mainApp, "오류", "더 이상 추가 할 수 없습니다.", "확인");
					return;
				}else
					vb.getChildren().add(vb.getChildren().size() - 1, addBox());
			});
			vb.getChildren().add(addButton);

			content.setBody(vb);
			dialog = new JFXDialog(mainApp.getRootLayoutController().getRootLayout(), content,
					JFXDialog.DialogTransition.CENTER);
			button = new JFXButton("완료");
			button.setPrefWidth(100);
			button.setPrefHeight(50);
			button.setRipplerFill(Color.BLUE);
			button.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 12));
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					HashMap<Number, String> map = new HashMap<>();
					int i=1;

					for (TempLectureAddLayoutController controller : controllerList) {

						if (controller.isGetAble()) {
							Lecture data = controller.getLectureData();
							if(isConflictCheck(data)) {
								map.put(i++, "이미 중복 된 시간표가 존재 합니다. -> 등록 실패");
								continue;
							}

							mainApp.getAppData().getTimeTableData().getSelectedLecture().addAll(data);
							mainApp.getAppData().getTimeTableData().disableSimilarLecture(data);
							map.put(i++, "임시 강의 정보를 등록하였습니다 -> 등록 성공");

						} else {
							map.put(i++, "과목명 또는 시간이 입력되지 않았습니다 -> 등록실패");
						}
					}
					
					if(map.size()>0) {
						String str = "";
						
						for(Number k:map.keySet()) {
							str += k +"번째 데이터 : " + map.get(k) + "\n";
						}
						
						new AlertDialog(mainApp, "등록 결과", str, "확인");
					}

					dialog.close();
				}
			});
			
			content.setActions(button);
			dialog.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private BorderPane addBox() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("View/Home/TempLecture/TempLectureAddLayout.fxml"));
			BorderPane pane = loader.load();
			controllerList.add(loader.getController());

			return pane;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean isConflictCheck(Lecture data) {
		for (Lecture lecture : mainApp.getAppData().getTimeTableData().getSelectedLecture()) {
			for (LectureTime lecTime : lecture.getLectureTime()) {
				if (lecTime.isConflict(data)) {
					return true;
				}
			}
		}
		
		return false;
	}

	public JFXButton getButton() {
		return button;
	}

	public JFXDialog getDialog() {
		return dialog;
	}

	public ObservableList<Lecture> getLectureList() {
		return tmpList;
	}

}
