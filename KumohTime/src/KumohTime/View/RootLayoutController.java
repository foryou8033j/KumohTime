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
				new AlertDialog(mainApp, mainApp.getAppData().getAppPropertise().getVersionString() + " 버전 업데이트 ! (2018.07.26)", ""
						+ "1. 네트워크 연결이 되어 있지 않은 상황에서도 사용이 가능하도록 수정하였습니다.\n"
						+ "   * 최초 1회는 연결을 해서 클라이언트를 다운받아야 합니다.\n\n"
						+ "2. 아무 메세지 없이 종료되는 분은 kit-share에서 새로운 버전을 내려 받아 주세요.\n"
						+ "   * 자동 업데이트로 해결되기 어려워보입니다 ㅠ 새로 받아 주셔야해요\n\n"
						+ "3. 선택 한 시간표 자동 저장 기능과 내보내기, 불러오기 기능을 구현하였습니다!\n"
						+ "   내 친구에게 시간표 파일을 전송 해 보세욥!\n\n"
						+ "4. 시간표를 이미지로 내보낼 수 있습니다!\n"
						+ "   해상도가 좀 구리긴한데 볼만은 할거에요...\n\n"
						+ "5. 과목코드 복사와 전체 복사 기능을 추가했습니다 :D\n"
						+ "   * 선택 한 과목 목록에서 코드 나 전체 복사를 눌러보세요ㅎ\n\n"
						+ "5. 일부 자잘한 버그를 수정했습니다!\n"
						+ "   * 선택한 시간표를 제거할 때 선택될 수 없는 시간표가 선택 가능하게 되는 문제를 해결했습니다.\n"
						+ "   * 시간표의 글씨 크기를 조금 줄였어요.. 글자가 크니까 자꾸 벗어나더라구요..\n"
						+ "   * 아무 메세지 없이 종료되는 문제를 수정하였습니다.\n\n"
						+ "TIP : 과목을 더블 클릭하면 추가/수정이 편리해요~", "확인");
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
