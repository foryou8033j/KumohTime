package KumohTime;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import KumohTime.Model.AppData;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.SaveData.SaveData;
import KumohTime.Util.OSCheck;
import KumohTime.Util.Dialog.AlertDialog;
import KumohTime.Util.Dialog.RecommandDialog;
import KumohTime.Util.Dialog.Loading.LoadingDialog;
import KumohTime.View.RootLayoutController;
import KumohTime.View.Update.UpdateStage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * KumohTime Main Application
 * 
 * @version 0.92
 * @author Jeongsam Seo
 * @since 2018-07-28
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private AppData appData;

	private RootLayoutController rootLayoutController;

	private MainApp mainApp = this;

	private RecommandDialog dialog = null;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		handleUpdateCheck();

	}

	/**
	 * Stage Layout Initialize
	 */
	public void initStage() {

		// PrimaryStage 가 먼저 보여줘야 LoadingDialog 가 보여질 수 있습니다.
		primaryStage.getIcons().add(new Image("icon.jpg"));
		primaryStage.show();

		LoadingDialog loading = new LoadingDialog(primaryStage);

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateMessage("로컬 DB 불러오는 중...");
				mainApp.setAppData(new AppData(new DataBase().loadAppData()));
				return null;
			}
		};

		task.setOnSucceeded(event -> {

			primaryStage.setTitle(appData.getAppPropertise().getTitle() + " 2019 " + appData.getAppPropertise().getVersion());

			// RootLayout Initialize
			initRootLayoutLayout();

			// Stage 종료 요청 시 동작
			primaryStage.setOnCloseRequest(e -> {
				handleCloseRequest(e);
			});

			loading.close();
			primaryStage.setWidth(primaryStage.getWidth() + 0.1);

		});

		loading.getText().textProperty().bind(task.messageProperty());

		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();

	}

	/**
	 * Root Layout Initialize
	 */
	private void initRootLayoutLayout() {

		try {

			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("View/RootLayout.fxml"));
			StackPane rootLayout = loader.load();

			rootLayoutController = loader.getController();
			rootLayoutController.setDefault(this);

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			primaryStage.show();

			// 최소크기를 지정하여 보여지기 적절한 크기 이하로 줄여지지 않도록 제한
			primaryStage.setMinWidth(primaryStage.getWidth());
			primaryStage.setMinHeight(primaryStage.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 업데이트 여부를 확인한다.
	 */
	private void handleUpdateCheck() {
		
		// Installer 버전에는 업데이트를 위한 스크립트가 포함되어 있다.
		if (new File("resources/updateClient.bat").exists() || new File("resources/updateClient.sh").exists()) {
			
			new UpdateStage(mainApp);
			
		} else {
			// Installer 버전이 아닌경우 사용제한 (0.92 버전 이후)
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("Installer 버전을 설치 해 주세요.");
			alert.setContentText("Portable 버전에 대한 지원이 중지 되었습니다.\nkit-share 에서 Installer 버전을 내려받아 설치 해 주세요.");
			alert.showAndWait();
			try {
				Desktop.getDesktop().browse(new URI("https://kit-share.com/"));
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stage 종료 요청시 동작 메소드
	 * 
	 * @param e WindowEvent
	 */
	private void handleCloseRequest(WindowEvent e) {
		saveSelectedLecture();

		if (!DataBase.isOfflineMode) {
			dialog = new RecommandDialog(mainApp);
			dialog.getDialog().show();
		} else {
			System.exit(0);
		}

		e.consume();
	}

	/**
	 * 선택 되어진 강의 목록을 Local 에 저장
	 */
	public void saveSelectedLecture() {

		mainApp.getAppData().getSaveDataController().getSaveDatas().clear();

		for (Lecture v : mainApp.getAppData().getTimeTableData().getSelectedLecture())
			mainApp.getAppData().getSaveDataController().getSaveDatas().add(new SaveData(v));

		mainApp.getAppData().getSaveDataController().saveData();
	}

	/**
	 * Update 로그를 보여준다.
	 */
	public void showUpdateLog() {

		/*
		 * new AlertDialog(mainApp,
		 * mainApp.getAppData().getAppPropertise().getVersionString() +
		 * " 관리자 메세지 답변 (2018.07.28) 2/3", "" +
		 * "Q. 불러오기에 오류가 있습니다. 내보낸 데이터를 불러오면 다른 데이터가 불러와집니다. 로그인 한 상태인데도 이런 오류가 있네요. 해결할 방법이 있을까요?\n"
		 * + "A. 8.3버전에서 수정하였습니다 ㅠ슈ㅠ\n\n" + "Q. 저장한 데이터 불러오기가 안되요 ㅠㅠ 버전은 0.82에요\n" +
		 * "A. 고쳣슴미다.. 또 그러면 메세지 보내주세요\n\n" + "Q. 강의 계획서를 볼 수 있게 해주세요!\n" +
		 * "A. 학교 강의 정보를 가져오는 것 부터가 난관이었슴니다, 노력해볼게요...\n", "확인");
		 */

		new AlertDialog(mainApp,
				mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.12.28)",
				"" 
				+ "1. kit-share 커뮤니티 이름 변경에 따라 업데이트 서버를 변경하였습니다.\n"
				+ "2. 2019학년도 시간표로 변경되었습니다.\n"
				+ "3. 배포 조건이 변경되었습니다. 자세한 내용은 정보->About 을 확인 해 주세요.\n"
				+ "4. 버그 리포팅시 자체 기능을 통한 버그 신고에서 금오사이에서 신고되도록 변경되었습니다.\n"
				,
				"확인");
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public AppData getAppData() {
		return appData;
	}

	public void setAppData(AppData appData) {
		this.appData = appData;
	}

	public RootLayoutController getRootLayoutController() {
		return rootLayoutController;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
