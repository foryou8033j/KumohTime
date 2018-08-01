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
		
		//For Installer Version (Windows, OSX)
		if(new File("resources/updateClient.bat").exists() || new File("resources/updateClient.sh").exists()) {
			
			UpdateStage updateStage = new UpdateStage(mainApp);
			
		}else{
			// Cannot use Portable Version.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("Installer 버전을 설치 해 주세요.");
			alert.setContentText("Portable 버전에 대한 지원이 중지 되었습니다.\nkit-share 에서 Installer 버전을 내려받아 설치 해 주세요.");
			alert.showAndWait();
			try {
				Desktop.getDesktop().browse(new URI("https://kit-share.com/"));
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void initStage() {

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
		
		task.setOnSucceeded(event->{
			
			primaryStage.setTitle(appData.getAppPropertise().getTitle() + " " + appData.getAppPropertise().getVersion());
			initRootLayoutLayout();

			primaryStage.setOnCloseRequest(e -> {

				saveSelectedLecture();

				if (!DataBase.isOfflineMode) {
					dialog = new RecommandDialog(mainApp);
					dialog.getDialog().show();
				} else {
					System.exit(0);
				}

				e.consume();
			});
			
			loading.close();
			
		});
		
		loading.getText().textProperty().bind(task.messageProperty());
		
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
		
		
		

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
		for (Lecture v : mainApp.getAppData().getTimeTableData().getSelectedLecture()) {
			mainApp.getAppData().getSaveDataController().getSaveDatas()
					.add(new SaveData(v.isTemp.get(), v.getYear().get(), v.getQuarter().get(), v.getName().get(),
							v.getProfessor().get(), v.getCode().get(), v.getTime().get(), v.getColor().getRed(),
							v.getColor().getGreen(), v.getColor().getBlue()));
		}
		mainApp.getAppData().getSaveDataController().saveData();
	}

	public void showUpdateLog() {

		new AlertDialog(mainApp,
				mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.07.30) 2/2",
				"" + "KumohTime 써 주셔서 너무 너무 감사한데 댓글도 안달아주시고\n" + "kit-share 놀러도 안오시고" + "너무 씐나서 공중제비 겁나게 돌고 있어요\n"
						+ "다음 학기, 내년, 후년에도 운영이 될 수 있도록 도와주세요!",
				"확인");

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
				mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.07.30) 1/2","" 
						+ "1. 수강꾸러미 대비 수강꾸러미 모드 추가\n" 
						+ "   * 의미가 없어 보이긴하는데 편하게 써주세요..\n\n" 
						+ "2. 계절학기 대응 시간표 로직 수정\n"
						+ "   * 겨울계절을 기약하며.. ㅂㄷ\n\n" 
						+ "3. 상단 메뉴바를 통해 기존 강의 목록에 없는 시간을 추가 할 수 있습니다\n"
						+ "   * 근로나 밥타임같은 시간을 추가해서 활용 해 보세요!\n\n"
						+ "4. 시간표 기본색상을 어두컴컴 회색에서 쁘띠빤짞 파스텔톤이 기본으로 들어가게 변경했습니다!\n\n" 
						+ "5. 여름계절학기 수강목록을 추가했습니다\n"
						+ "   * 의미없는거압니드아\n\n" 
						+ "5. 메세지 박스의 버튼 크기를 좀 큼직큼직하게 바꿧어요\n\n" 
						+ "5. 일부 버그를 수정했습니다.\n"
						+ "   * 파일을 제대로 불러오지 못하는 오류를 수정 했습니다 x3\n" 
						+ "   * 일부 시간이 겹쳐지는 문제를 수정했습니다.\n"
						+ "   * 강의시간이 없는 과목은 색상지정이 불가능하도록 변경했습니다\n"
						+ "   * 수강코드 복사 시 '-' 가 복사되지 않도록 수정하였습니다.\n\n" 
						+ "6. 기존에 배포되던 Protable 버전에 대한 지원이 중지되었습니다...",
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
