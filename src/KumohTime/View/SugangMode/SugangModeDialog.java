package KumohTime.View.SugangMode;

import com.jfoenix.controls.JFXToggleButton;

import KumohTime.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 수강신청 모드 Stage
 * @author Jeongsam Seo
 *
 */
public class SugangModeDialog extends Stage {
	
	public SugangModeDialog(MainApp mainApp, JFXToggleButton toggleButton) {
		
		super();
		
		getIcons().add(new Image("icon.jpg"));
		setTitle(mainApp.getAppData().getAppPropertise().getTitle() + " " + mainApp.getAppData().getAppPropertise().getVersionString() + "v 수강꾸러미모드");
		
		resizableProperty().setValue(Boolean.FALSE);
		setAlwaysOnTop(true);
		
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("View/SugangMode/SugangModeLayout.fxml"));
			BorderPane pane = loader.load();
			
			SugangModeLayoutController controller = loader.getController();
			controller.setDefault(mainApp, toggleButton, this);
			
			Scene scene = new Scene(pane);
			setScene(scene);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//Application 의 좌측 하단에 보여지게 한다.
		setOnShown( e-> {
			double x = mainApp.getPrimaryStage().getX();
			double y = mainApp.getPrimaryStage().getY() + mainApp.getPrimaryStage().getHeight() - getHeight();
			
			setX(x);
			setY(y);
		});

		setOnCloseRequest(e -> {
			
			toggleButton.selectedProperty().set(false);
			
		});
		
		
		
	}

}
