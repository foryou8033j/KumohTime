package KumohTime.View.Update;

import KumohTime.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 업데이트 상황을 보여주는 Stage
 * @author Jeongsam Seo
 * @since 2018-08-01
 *
 */
public class UpdateStage extends Stage {
	
	public UpdateStage(MainApp mainApp) {
		
		super();
		Alert alert = new Alert(AlertType.NONE, "업데이트 확인 중 입니다", ButtonType.OK);

		try {

			alert.setTitle("KumohTime");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.show();

			getIcons().add(new Image("icon.jpg"));

			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("View/Update/UpdateLayout.fxml"));
			BorderPane pane = loader.load();

			UpdateLayoutController controller = loader.getController();
			controller.setDefault(mainApp, this);

			Scene scene = new Scene(pane);
			setScene(scene);

			showAndWait();	//업데이트가 이루어지는동안 전체 동작 제어
			alert.close();
			
		} catch (Exception e) {
			alert.close();
			e.printStackTrace();
			System.out.println("DB 서버에 연결 할 수 없습니다.");
		}
		
	}

}
