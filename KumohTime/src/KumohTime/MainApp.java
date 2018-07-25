package KumohTime;

import KumohTime.Model.AppData;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Util.Dialog.Loading.LoadingDialog;
import KumohTime.View.RootLayoutController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private AppData appData;
	
	private RootLayoutController rootLayoutController;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		
		appData = new AppData(new DataBase().loadAppData());
		
		initStage();
		
	}
	
	private void initStage() {
	
		try {
			
			primaryStage.setTitle(appData.getAppPropertise().getTitle() + " " + appData.getAppPropertise().getVersion());
			primaryStage.setMinWidth(primaryStage.getWidth());
			primaryStage.setMinHeight(primaryStage.getHeight());
			
			primaryStage.setOnCloseRequest(e -> {
				
				LoadingDialog dialog = new LoadingDialog(primaryStage);
				
				Task<Void> task = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						
						return null;
					}
					
				};
				
				task.setOnSucceeded(t -> {
					dialog.close();
					System.exit(0);
				});
				
				Thread thread = new Thread(task);
				thread.setDaemon(true);
				thread.start();
				
			});
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		initRootLayoutLayout();
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
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
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
