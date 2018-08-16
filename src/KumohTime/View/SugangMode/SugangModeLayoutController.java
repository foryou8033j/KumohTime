package KumohTime.View.SugangMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Util.Browser;
import KumohTime.View.Home.SelectedLecture.SelectedLectureLayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * 수강신청 모드의 레이아웃을 컨트롤한다.
 * @author Jeongsam Seo
 * @since 2018-07-29
 *
 */
public class SugangModeLayoutController{

	@FXML
	private JFXListView<GridPane> list;

	@FXML
	private Text time;

	@FXML
	private Text sugangmessage;

	private MainApp mainApp;

	private ObservableList<GridPane> selectedLayoutList = FXCollections.observableArrayList();
	private ObservableList<SelectedLectureLayoutController> selectedLayoutControllerList = FXCollections.observableArrayList();

	private Calendar targetTime;

	@FXML
	void handleOpenKitShare(ActionEvent event) {
		Browser.open("https://kit-share.com/");
	}

	@FXML
	void handleOpenSugangPage(ActionEvent event) {
		Browser.open("http://sugang.kumoh.ac.kr/onestop/sugang/w_sugang_1.html");
	}

	@FXML
	void handleOpenUnivPage(ActionEvent event) {
		Browser.open("http://kumoh.ac.kr/");
	}
	
    @FXML
    void handleQuit(ActionEvent event) {
    	stage.close();
    	toggleButton.selectedProperty().set(false);
    }

	/**
	 * 하단에 흐르는 시간 쓰레드
	 */
	private void handleTimeTick() {
		new Thread(() -> {

			String[] sugangData = new DataBase().getSugangTime();
			new DataBase().writeLog(mainApp.getAppData().getAppPropertise().getVersionString());

			// 시간값이 맞는지 검증
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

			try {

				sugangmessage.setText(sugangData[0]);
				targetTime = Calendar.getInstance();

				if (!sugangData[1].equals(""))
					targetTime.setTime(format.parse(sugangData[1]));

				while (true) {

					Thread.sleep(10);

					if (sugangData[1].equals("")) {
						time.setText(format.format(Calendar.getInstance().getTime()));
						continue;
					}

					Calendar now = Calendar.getInstance();

					long diffTime = targetTime.getTimeInMillis() - now.getTimeInMillis();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(diffTime);

					long diffSeconds = diffTime / 1000 % 60;
					long diffMinutes = diffTime / (60 * 1000) % 60;
					long diffHours = diffTime / (60 * 60 * 1000) % 24;
					long diffDays = diffTime / (24 * 60 * 60 * 1000);

					if (diffDays != 0)
						time.setText("D-" + diffDays + " " + diffHours + "시간 " + diffMinutes + "분 " + diffSeconds + "초");
					else {
						time.setFill(Paint.valueOf("red"));
						if (diffHours != 0)
							time.setText(diffHours + "시간 " + diffMinutes + "분 " + diffSeconds + "초");
						else {
							if (diffMinutes != 0)
								time.setText(diffMinutes + "분 " + diffSeconds + "초");
							else {
								time.setText(diffSeconds + "초 ");
							}
						}

						if (diffSeconds < 0) {
							time.setText("END");
							break;
						}
					}
				}

			} catch (Exception e) {
				// 시간 입력 형식이 맞지 않음
				e.printStackTrace();
			}

		}).start();
	}
	
	private SugangModeDialog stage;
	private JFXToggleButton toggleButton;
	
	public void setDefault(MainApp mainApp, JFXToggleButton toggleButton, SugangModeDialog stage) {
		this.mainApp = mainApp;
		this.stage = stage;
		this.toggleButton = toggleButton;

		list.setItems(selectedLayoutList);

		stage.setOnShowing(e -> {

			selectedLayoutControllerList.clear();
			selectedLayoutList.clear();

			for (Lecture v : mainApp.getAppData().getTimeTableData().getSelectedLecture()) {
				try {
					FXMLLoader loader = new FXMLLoader(
							MainApp.class.getResource("View/Home/SelectedLecture/SelectedLectureLayout.fxml"));
					GridPane pane = loader.load();
					pane.setPrefWidth(list.getWidth()-40);

					SelectedLectureLayoutController controller = loader.getController();
					controller.setDefault(mainApp, v, pane, false);

					selectedLayoutList.add(pane);
					selectedLayoutControllerList.add(controller);
				} catch (Exception a) {
					a.printStackTrace();
				}
			}
			
			list.widthProperty().addListener((observable, oldValue, newValue) -> {
				for (GridPane v:selectedLayoutList) {
					v.setPrefWidth(list.getWidth()-40);
				}
			});
			
			
			
			stage.getScene().setOnKeyPressed(me -> {
				if(me.isControlDown()) {
					try {
						switch(me.getCode()) {
						case DIGIT1: selectedLayoutControllerList.get(0).handleCodeCopy(null); break;
						case DIGIT2: selectedLayoutControllerList.get(1).handleCodeCopy(null); break;
						case DIGIT3: selectedLayoutControllerList.get(2).handleCodeCopy(null); break;
						case DIGIT4: selectedLayoutControllerList.get(3).handleCodeCopy(null); break;
						case DIGIT5: selectedLayoutControllerList.get(4).handleCodeCopy(null); break;
						case DIGIT6: selectedLayoutControllerList.get(5).handleCodeCopy(null); break;
						case DIGIT7: selectedLayoutControllerList.get(6).handleCodeCopy(null); break;
						case DIGIT8: selectedLayoutControllerList.get(7).handleCodeCopy(null); break;
						case DIGIT9: selectedLayoutControllerList.get(8).handleCodeCopy(null); break;
					}
					}catch (Exception mee) {
						//ignore
					}
					
				}
			});
		});

		handleTimeTick();
		

	}

}
