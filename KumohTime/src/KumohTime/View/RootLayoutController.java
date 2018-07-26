package KumohTime.View;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.SaveData.SaveData;
import KumohTime.Util.Dialog.AlertDialog;
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
			
			if(DataBase.isOfflineMode) {
				new AlertDialog(mainApp, "서버에 연결 할 수 없습니다.", "오프라인 모드로 사용됩니다.\n오프라인모드에서는 일부 기능이 제한 될 수 있습니다.", "확인");
			}
			
			if(!mainApp.getAppData().getAppPropertise().isNotify()) {
				new AlertDialog(mainApp, mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 기록 (2018.08.01)", ""
						+ "1. 네트워크 연결이 되어 있지 않은 상황에서도 사용이 가능하도록 수정하였습니다.\n"
						+ "   (최초 1회는 연결을 해서 클라이언트를 다운받아야 합니다.)\n\n"
						+ "2. 2018년 8월 이전에 내려 받으신 분은 kit-share에서 새로운 버전을 내려 받아 주세요.\n"
						+ "   (오프라인 모드가 정상 작동 하지 않을 수 있습니다.)\n\n"
						+ "3. 일부 버그를 수정하였습니다.\n"
						+ "", "확인");
				mainApp.getAppData().getAppPropertise().setNotify(true);
				mainApp.getAppData().getAppPropertise().savePropertise();
			}
			
			
			//저장 된 데이터를 불러 온다.
			for(SaveData data:mainApp.getAppData().getSaveDataController().getSaveDatas()) {
				System.out.println(data.getName());
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
