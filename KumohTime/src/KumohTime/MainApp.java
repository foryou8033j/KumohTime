package KumohTime;

import javax.swing.text.PlainDocument;

import KumohTime.Model.AppData;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.SaveData.SaveData;
import KumohTime.Util.Dialog.AlertDialog;
import KumohTime.Util.Dialog.RecommandDialog;
import KumohTime.Util.Dialog.Loading.LoadingDialog;
import KumohTime.View.RootLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private AppData appData;

	private RootLayoutController rootLayoutController;

	private MainApp mainApp = this;

	private RecommandDialog dialog = null;
	
	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		primaryStage.getIcons().add(new Image("icon.jpg"));
		primaryStage.show();
		
		

		new Thread(()->{
			new DataBase().writeLog();
		}).start();
		
		LoadingDialog dialog = new LoadingDialog(primaryStage);

		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				updateMessage("DB 불러오는 중");
				appData = new AppData(new DataBase().loadAppData());
				
				initStage();

				return null;
			}

		};

		task.setOnSucceeded(t -> {
			dialog.close();
		});
		
		dialog.getText().textProperty().bind(task.messageProperty());

		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();

		
		
	}

	private void initStage() {

		Platform.runLater(() -> {
			primaryStage.setTitle(appData.getAppPropertise().getTitle() + " " + appData.getAppPropertise().getVersion());
			primaryStage.setOnCloseRequest(e -> {
				
				Platform.runLater(() -> {
					saveSelectedLecture();
					dialog = new RecommandDialog(mainApp);
					dialog.getDialog().show();
					
				});
				
				e.consume();
			});
			initRootLayoutLayout();
		});
	}

	private void initRootLayoutLayout() {

		try {

			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("View/RootLayout.fxml"));
			StackPane rootLayout = loader.load();

			rootLayoutController = loader.getController();
			rootLayoutController.setDefault(this);

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			primaryStage.show();

			primaryStage.setMinWidth(primaryStage.getWidth());
			primaryStage.setMinHeight(primaryStage.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveSelectedLecture() {
		mainApp.getAppData().getSaveDataController().getSaveDatas().clear();
		for(Lecture v:mainApp.getAppData().getTimeTableData().getSelectedLecture()) {
			mainApp.getAppData().getSaveDataController().getSaveDatas().add(
					new SaveData(
							v.isTemp.get(),
							v.getYear().get(),
							v.getQuarter().get(),
							v.getName().get(),
							v.getProfessor().get(),
							v.getCode().get(),
							v.getTime().get(),
							v.getColor().getRed(),
							v.getColor().getGreen(),
							v.getColor().getBlue()
							));
		}
		mainApp.getAppData().getSaveDataController().saveData();
	}
	
	public void showUpdateLog() {
		
		/*new AlertDialog(mainApp, mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.07.31) 2/2", ""
				+ "클라이언트 사용 수는 600회가 넘는데 kit-share 에 댓글은 4개 달렸더라구요\n"
				+ "너무 신나서 공중제비 돌 뻔 했어요\n"
				+ "다음 학기, 내년, 후년에도 운영이 될 수 있도록 도와주세요!", "확인");
		*/
		
		/*
		new AlertDialog(mainApp, mainApp.getAppData().getAppPropertise().getVersionString() + " 관리자 메세지 답변 (2018.07.28) 2/3", ""
				+ "Q. 불러오기에 오류가 있습니다. 내보낸 데이터를 불러오면 다른 데이터가 불러와집니다. 로그인 한 상태인데도 이런 오류가 있네요. 해결할 방법이 있을까요?\n"
				+ "A. 8.3버전에서 수정하였습니다 ㅠ슈ㅠ\n\n"
				+ "Q. 저장한 데이터 불러오기가 안되요 ㅠㅠ 버전은 0.82에요\n"
				+ "A. 고쳣슴미다.. 또 그러면 메세지 보내주세요\n\n"
				+ "Q. 강의 계획서를 볼 수 있게 해주세요!\n"
				+ "A. 학교 강의 정보를 가져오는 것 부터가 난관이었슴니다, 노력해볼게요...\n", "확인");
		*/
		
		new AlertDialog(mainApp, mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.07.31) 1/2", ""
				+ "1. 수강꾸러미 대비 수강꾸러미 모드 추가\n"
				+ "   * 의미가 없어 보이긴하는데 편하게 써주세요..\n\n"
				+ "2. 계절학기 대응 시간표 로직 수정\n"
				+ "   * 생각보니까 계절학기는 일주일에 3일이상 수업이 들어가더라구요 ㅂㄷ\n\n"
				+ "3. 시간표 기본색상을 어두컴컴 회색에서 쁘띠빤짞 파스텔톤이 기본으로 들어가게 변경했습니다!\n\n"
				+ "4. 계절학기 수강목록을 추가했습니다\n"
				+ "   * 의미 없는거 저두 압니다\n\n"
				+ "5. 메세지 박스의 버튼 크기를 좀 큼직큼직하게 바꿧어요\n"
				+ "   * 메세지 박스 밖에 클릭해도 꺼지긴하는데 잘 모르시더라구요\n\n"
				+ "5. 일부 버그를 수정했습습니다.", "확인");
		
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public AppData getAppData() {
		return appData;
	}

	public RootLayoutController getRootLayoutController() {
		return rootLayoutController;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
