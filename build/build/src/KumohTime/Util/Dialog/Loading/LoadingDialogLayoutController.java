package KumohTime.Util.Dialog.Loading;

import com.jfoenix.controls.JFXSpinner;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LoadingDialogLayoutController {

    @FXML
    private JFXSpinner spinner;

    @FXML
    private Text text;
    
    public JFXSpinner getSpinner() {
    	return spinner;
    }
    
    public Text getText() {
    	return text;
    }

}
