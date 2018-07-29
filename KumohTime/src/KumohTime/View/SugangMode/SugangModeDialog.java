package KumohTime.View.SugangMode;

import KumohTime.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SugangModeDialog extends Stage {
	
	private MainApp mainApp;
	
	public SugangModeDialog(MainApp mainApp) {
		
		super();
		
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("View/SugangMode/SugangModeLayout.fxml"));
			BorderPane pane = loader.load();
			
			SugangModeLayoutController controller = loader.getController();
			controller.setDefault(mainApp);
			
			Scene scene = new Scene(pane);
			setScene(scene);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		setOnCloseRequest(e -> {
			
			mainApp.getPrimaryStage().show();
			
		});
		
	}

}
