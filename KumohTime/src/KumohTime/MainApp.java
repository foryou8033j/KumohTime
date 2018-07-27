package KumohTime;

import javax.swing.text.PlainDocument;

import KumohTime.Model.AppData;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.SaveData.SaveData;
import KumohTime.Util.Dialog.AlertDialog;
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

				LoadingDialog dialog = new LoadingDialog(primaryStage);

				Task<Void> task = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						saveSelectedLecture();
						return null;
					}

				};

				task.setOnSucceeded(t -> {
					mainApp.getAppData().getSaveDataController().saveData();
					dialog.close();
					System.exit(0);
				});

				Thread thread = new Thread(task);
				thread.setDaemon(true);
				thread.start();

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
	}
	
	public void showUpdateLog() {
		new AlertDialog(mainApp, mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.07.28) 2/2", ""
				+ "클라이언트 사용 수는 600회가 넘는데 kit-share 에 댓글은 4개 달렸더라구요\n"
				+ "너무 신나서 공중제비 돌 뻔 했어요\n"
				+ "다음 학기, 내년, 후년에도 운영이 될 수 있도록 도와주세요!", "확인");
		
		new AlertDialog(mainApp, mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.07.28) 1/2", ""
				+ "1. 아무 메세지 없이 종료되는 분은 kit-share에서 새로운 버전을 내려 받아 주세요.\n"
				+ "   * 최초 1회는 네트워크 연결을 통해 클라이언트를 내려 받아야 합니다.\n\n"
				+ "2. 선택 한 시간표 자동 저장 기능과 내보내기, 불러오기 기능을 추가하였습니다!\n"
				+ "   선택 한 시간표는 자동으로 저장됩니다.\n\n"
				+ "3. 시간표를 이미지로 내보낼 수 있습니다!\n\n"
				+ "4. 과목코드 복사와 전체 복사 기능을 추가했습니다 :D\n"
				+ "   * 선택 한 과목 목록에서 코드 나 전체 복사를 눌러보세요ㅎ\n\n"
				+ "5. 상단 메뉴바를 통해 기존 강의 목록에 없는 시간을 추가 할 수 있습니다.\n"
				+ "   * 근로나 다른 일정을 추가해서 활용 해 보세요\n\n"
				+ "5. 일부 자잘한 버그를 수정했습니다!\n"
				+ "   * 선택한 시간표를 제거할 때 선택될 수 없는 시간표가 선택 가능하게 되는 문제를 해결했습니다.\n"
				+ "   * 시간표의 글씨 크기를 조금 줄였어요.. 글자가 크니까 자꾸 벗어나더라구요..\n"
				+ "   * 아무 메세지 없이 종료되는 문제를 수정하였습니다.\n"
				+ "   * 시간표 파일을 제대로 불러 오지 못하는 현상을 수정하였습니다.\n"
				+ "   * 토요일을 삭제 했습니다, 쓸모가 없더라구요.\n\n"
				+ "TIP : 과목을 더블 클릭해도 추가/삭제가 가능합니다", "확인");
		
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
