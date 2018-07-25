package KumohTime.View;

import KumohTime.MainApp;
import KumohTime.View.Home.HomeLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class RootLayoutController {

	@FXML
	private StackPane rootLayout;

	private MainApp mainApp;

	private void initHomeLayout() {
		
		try {

			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("View/Home/HomeLayout.fxml"));
			BorderPane layout = loader.load();

			HomeLayoutController controller = loader.getController();
			controller.setDefault(mainApp);

			rootLayout.getChildren().add(layout);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDefault(MainApp mainApp) {
		this.mainApp = mainApp;
		initHomeLayout();
	}

	public StackPane getRootLayout() {
		return rootLayout;
	}
}
