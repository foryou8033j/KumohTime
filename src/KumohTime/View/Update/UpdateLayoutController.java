package KumohTime.View.Update;

import java.awt.Robot;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;

import KumohTime.MainApp;
import KumohTime.Model.AppData;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.Properties.AppPropertise;
import KumohTime.Model.Properties.ResourcePropertise;
import KumohTime.Util.OSCheck;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class UpdateLayoutController {

	@FXML
	private JFXProgressBar progress;

	@FXML
	private Text percent;

	@FXML
	private JFXTextArea log;

	private MainApp mainApp;
	private AppData appData;
	private UpdateStage updateStage;

	private float serverVersion = 0.0f;
	private boolean isUpdated = false;
	

	/**
	 * 클라이언트 업데이트 버전 확인
	 * 0.92 버전 이후 업데이트 버전 확인만 수행합니다.
	 */
	private void initClientUpdate() {

		float clientVersion = new AppPropertise().getVersion();
		float serverVersion = appData.getServerVersion();

		log.appendText("업데이트 정보를 확인 중 입니다.\r\n");
		log.appendText("클라이언트 업데이트 정보를 확인 중 입니다.\r\n");

		if (serverVersion > clientVersion) {

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					try {
						log.appendText("\r\n최신 업데이트가 존재합니다.\r\n");
						log.appendText("업데이트가 예약되었습니다....\r\n");
					} catch (Exception e) {
						e.printStackTrace();
						log.appendText("서버의 파일이 손상 되었습니다.\r\n");
						log.appendText("이 문제는 관리자에게 자동으로 신고됩니다.\r\n");
						new DataBase().bugReport("ERROR: 서버의 파일을 찾을 수 없음", String.valueOf(serverVersion));
					}
					return null;
				}
			};

			task.setOnSucceeded(e -> {
				isUpdated = true;
				initDataBaseUpdate();
			});

			progress.progressProperty().bind(task.progressProperty());

			Thread thread = new Thread(task);
			thread.setDaemon(true);
			thread.start();

		} else {
			log.appendText("최신 버전 확인 완료\r\n");
			initDataBaseUpdate();
		}

	}

	/**
	 * Database 업데이트 수행, Server로부터 *.db 파일을 내려 받는다
	 */
	private void initDataBaseUpdate() {

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				log.appendText("\r\nDB 업데이트 확인 중...\r\n");

				updateProgress(-1, 100);
				float clientVersion = new ResourcePropertise().getVersion();
				serverVersion = new DataBase().loadDataBaseVersion();

				if (serverVersion > clientVersion) {

					log.appendText("\r\n새로운 DB가 존재합니다.\r\n");
					log.appendText("새로운 데이터 내려 받는 중...\r\n");

					URL url = new URL(appData.getServerPath() + "database" + "/kumohtime.db");

					URLConnection conn = url.openConnection();
					int size = conn.getContentLength();

					BufferedInputStream bis = new BufferedInputStream(url.openStream());
					FileOutputStream fis = new FileOutputStream(AppData.databasePath);
					byte[] buffer = new byte[1024];
					int count = 0;

					DoubleProperty sumCount = new SimpleDoubleProperty(0.0);

					while ((count = bis.read(buffer, 0, 1024)) != -1) {
						fis.write(buffer, 0, count);

						sumCount.set(sumCount.get() + count);
						if (size > 0) {
							updateProgress((sumCount.get() / size * 100.0), 100.0);
						}

						new Robot().delay(5);

					}
					fis.close();
					bis.close();

					new ResourcePropertise().savePropertise(serverVersion);

				}
				return null;
			}

		};

		task.setOnSucceeded(e -> {

			//업데이트 할 필요가 없을 경우
			if (!isUpdated) {
				//홈 레이아웃을 초기화한다.
				Platform.runLater(() ->{
					mainApp.initStage();
					updateStage.close();
				});
				
			} else {
				doClientUpdate();
			}

		});

		progress.progressProperty().bind(task.progressProperty());

		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	
	/**
	 * Application 업데이트를 수행한다.
	 * Windows는 batch 파일 실행을 통해, Mac은 Shell Script 실행을 통해 업데이트를 수행합니다.
	 * Mac 스크립트의 경우 윈도우에서 수정 할 경우 개행문자 오류가 발생 할 수 있으니 주의.
	 */
	private void doClientUpdate() {
		try {

			// 윈도우의 경우 updateClinet.bat 를 실행하고 Application 종료
			if (OSCheck.isWindows()) {
				Process proc = null;
				String[] cmd = { "cmd", "/c", "start", ".\\resources\\updateClient.bat", appData.getServerPath(),
						String.valueOf(appData.getServerVersion()) };
				proc = Runtime.getRuntime().exec(cmd);
				System.exit(0);

			} else if (OSCheck.isMac()) { // Mac의 경우 updateClient.sh 를 실행하고 Application 종료

				String[] changePermission = { "chmod", "+x", "./resources/updateClient.sh" };
				Runtime.getRuntime().exec(changePermission);

				// Mac의 경우 프로세스 호출 방식이 다름, 주의
				String[] cmd = { "./resources/updateClient.sh", appData.getServerPath(),
						String.valueOf(appData.getServerVersion()) };
				ProcessBuilder builder = new ProcessBuilder(cmd);
				Process process = builder.start();
				System.exit(0);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 업데이트 순서를 정의 1. Applicaiton 업데이트 확인 2. DataBase 업데이트
	 * 
	 * @param mainApp
	 * @param updateStage
	 */
	public void setDefault(MainApp mainApp, UpdateStage updateStage) {
		this.mainApp = mainApp;
		this.updateStage = updateStage;

		progress.progressProperty().addListener((observable, oldValue, newValue) -> {
			percent.setText(String.format("%.1f %%", progress.progressProperty().getValue().doubleValue() * 100));
		});

		log.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 500)
				log.clear();
		});

		appData = new DataBase().loadApplicationVersion();

		initClientUpdate();

	}

	/**
	 * 
	 * 원격 서버로부터 파일을 내려받는다.
	 * 
	 * @deprecated Shell 방식을 통합 업데이트로 변경되어 사용하지 않음.
	 * @param urlStr 다운로드 대상 URI
	 * @param file   다운로드 파일명
	 * @throws IOException
	 */
	private static void downloadUsingStream(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fis = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1) {
			fis.write(buffer, 0, count);
		}
		fis.close();
		bis.close();
	}

	/**
	 * 
	 * 원격 서버로부터 파일을 내려받는다.
	 * 
	 * @deprecated Shell 방식을 통합 업데이트로 변경되어 사용하지 않음.
	 * @param urlStr 다운로드 대상 URI
	 * @param file   다운로드 파일명
	 * @throws IOException
	 */
	private static void downloadUsingNIO(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

}
