package KumohTime.View.SugangMode;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import KumohTime.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class SugangModeLayoutController implements Initializable {

    @FXML
    private JFXListView<?> list;

    @FXML
    private Text time;

    @FXML
    void handleOpenKitShare(ActionEvent event) {

    }

    @FXML
    void handleOpenSugangPage(ActionEvent event) {

    }

    @FXML
    void handleOpenUnivPage(ActionEvent event) {

    }

	private MainApp mainApp;

	public void setDefault(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		

	}

}
