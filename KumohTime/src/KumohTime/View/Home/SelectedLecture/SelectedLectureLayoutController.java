package KumohTime.View.Home.SelectedLecture;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXColorPicker;

import KumohTime.MainApp;
import KumohTime.Model.TimeTable.Lecture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SelectedLectureLayoutController implements Initializable{

    @FXML
    private Text name;

    @FXML
    private Text professor;

    @FXML
    private Text code;

    @FXML
    private Text time;
    
    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    void handleCopy(ActionEvent event) {
    	
    }

    @FXML
    void handleDelete(ActionEvent event) {
    	try {
    		
			mainApp.getAppData().getTimeTableData().getSelectedLecture().remove(lecture);
			mainApp.getAppData().getTimeTableData().enableSimilarLecture(lecture);
			mainApp.getAppData().getTimeTableData().getSelectedLecture().remove(lecture);
			
		}catch (Exception e) {
			return;
		}
    }
    
    private MainApp mainApp;
    private Lecture lecture;
    private GridPane layout;
    
    public void setDefault(MainApp mainApp, Lecture lecture, GridPane layout) {
    	
    	this.mainApp = mainApp;
    	this.lecture = lecture;
    	this.layout = layout;
    	
    	name.setText(lecture.getName().get());
    	professor.setText(lecture.getProfessor().get());
    	code.setText(lecture.getCode().get());
    	time.setText(lecture.getTime().get());
    	
    	colorPicker.valueProperty().set(lecture.getColor());
    }
    
    public Lecture getLecture() {
    	return lecture;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			lecture.setColor(newValue);
		});
	}

}
