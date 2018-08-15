package KumohTime.View.Home.SelectedLecture;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.Random;
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
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * 선택 된 강의리스트 View Controller
 * 
 * @author Jeongsam Seo
 * @since 2018-08-01
 *
 */
public class SelectedLectureLayoutController implements Initializable{

    @FXML
    private GridPane pane;
	
    @FXML
    private Text name;

    @FXML
    private Text professor;

    @FXML
    private Text time;
    
    @FXML
    private JFXButton delete;
    
    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    private JFXButton code;
    
    @FXML
    private JFXButton copyAll;
    

    @FXML
    public void handleCodeCopy(ActionEvent event) {
    	StringSelection stringSelection = new StringSelection(code.getText().replaceAll("-", ""));
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
    		if(!isUseAble)
    			return;
    		
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
    private Boolean isUseAble;
    
    public void setDefault(MainApp mainApp, Lecture lecture, GridPane layout, boolean isUseAble) {
    	
    	this.isUseAble = isUseAble;
    	this.mainApp = mainApp;
    	this.lecture = lecture;
    	this.layout = layout;
    	
    	if(!isUseAble) {
    		colorPicker.setDisable(true);
    		delete.setText("Ctrl+" + Integer.toString(mainApp.getAppData().getTimeTableData().getSelectedLecture().indexOf(lecture)));
    		layout.getColumnConstraints().get(0).setPercentWidth(10);
    	}
    	
    	if(lecture.getLectureTime().size()==0)
    		colorPicker.setVisible(false);
    	
    	name.setText(lecture.getName().get());
    	professor.setText(lecture.getProfessor().get());
    	code.setText(lecture.getCode().get());
    	time.setText(lecture.getTime().get());
    	
    	colorPicker.valueProperty().set(lecture.getColor());
    	if(isUseAble) {
			colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
				lecture.setColor(newValue);
			});
		}
    	
    	//수강꾸러미일경우 배경 색상을 변경한다.
    	if(lecture.getLecPackage().get().equals("N"))
    		name.setFill(Color.rgb(241, 149, 104));
    	
    }
    
    public Lecture getLecture() {
    	return lecture;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
