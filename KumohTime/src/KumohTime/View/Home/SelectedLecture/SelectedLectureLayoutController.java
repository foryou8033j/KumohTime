package KumohTime.View.Home.SelectedLecture;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;

import KumohTime.MainApp;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Util.InfoManager;
import javafx.application.Platform;
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
    private Text time;
    
    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    private JFXButton code;
    
    @FXML
    private JFXButton copyAll;

    @FXML
    void handleCodeCopy(ActionEvent event) {
    	StringSelection stringSelection = new StringSelection(code.getText());
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(stringSelection, null);
    	
    	new Thread(()->{
    		Platform.runLater(()->{
    			code.setText("복사됨");
    		});
    		
        	try {
    			new Robot().delay(1000);
    		} catch (AWTException e) {
    			e.printStackTrace();
    		}
        	
        	Platform.runLater(()->{
        		code.setText(lecture.getCode().get());
    		});
        	
    	}).start();
    	
    	
    	
    }
    
    
    @FXML
    public void handleCopy(ActionEvent event) {
    	StringSelection stringSelection = new StringSelection(code.getText() + "\t" + professor.getText() + "\t\t"+ name.getText() + "\t\t" + time.getText());
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(stringSelection, null);
    	
    	new Thread(()->{
    		Platform.runLater(()->{
    			copyAll.setText("복사됨");
    		});
    		
        	try {
    			new Robot().delay(1000);
    		} catch (AWTException e) {
    			e.printStackTrace();
    		}
        	
        	Platform.runLater(()->{
        		copyAll.setText("전체 복사");
    		});
        	
    	}).start();
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
