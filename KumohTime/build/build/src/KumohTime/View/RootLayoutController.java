package KumohTime.View;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.SaveData.SaveData;
import KumohTime.Util.Dialog.AlertDialog;
import KumohTime.View.Home.HomeLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

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
			
			if(DataBase.isOfflineMode) {
				new AlertDialog(mainApp, "서버에 연결 할 수 없습니다.", "오프라인 모드로 사용됩니다.\n오프라인모드에서는 일부 기능이 제한 될 수 있습니다.", "확인");
			}
			
			if(!mainApp.getAppData().getAppPropertise().isNotify()) {
				mainApp.showUpdateLog();
				mainApp.getAppData().getAppPropertise().setNotify(true);
				mainApp.getAppData().getAppPropertise().savePropertise();
			}	

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
