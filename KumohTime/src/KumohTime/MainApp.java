package KumohTime;

import javax.swing.text.PlainDocument;

import KumohTime.Model.AppData;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.SaveData.SaveData;
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

		LoadingDialog dialog = new LoadingDialog(primaryStage);

		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				updateMessage("DB 불러오는 중");
				
				new DataBase().writeLog();
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
							v.getYear().get(),
							v.getQuarter().get(),
							v.getName().get(),
							v.getCode().get(),
							v.getColor().getRed(),
							v.getColor().getGreen(),
							v.getColor().getBlue()
							));
		}
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
