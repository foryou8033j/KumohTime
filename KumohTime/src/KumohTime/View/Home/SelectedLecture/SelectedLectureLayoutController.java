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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SelectedLectureLayoutController implements Initializable{

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
    
    Color[] pastell = {
    		Color.rgb(237, 223, 185),
    		Color.rgb(237, 199, 121),
    		Color.rgb(215, 127, 110),
    		Color.rgb(115, 165, 152),
    		Color.rgb(169, 188, 139),
    		Color.rgb(255, 222, 255),
    		Color.rgb(222, 222, 255),
    		Color.rgb(255, 255, 222),
    		
    		Color.rgb(222, 222, 239),
    		Color.rgb(222, 239, 255),
    		Color.rgb(222, 255, 255),
    		
    		Color.rgb(239, 222, 239),
    		Color.rgb(255, 222, 222),
    		Color.rgb(239, 222, 222),
    		Color.rgb(255, 239, 222),
    		Color.rgb(255, 222, 239),

    		Color.rgb(239, 222, 255),
    		Color.rgb(222, 255, 239),
    		Color.rgb(239, 255, 222),
    		Color.rgb(222, 255, 222),
    		Color.rgb(222, 239, 222),
    		Color.rgb(222, 239, 239),
    		Color.rgb(239, 239, 222)
    		};

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
    private Boolean isUseAble;
    
    public void setDefault(MainApp mainApp, Lecture lecture, GridPane layout, boolean isUseAble) {
    	
    	this.isUseAble = isUseAble;
    	this.mainApp = mainApp;
    	this.lecture = lecture;
    	this.layout = layout;
    	
    	if(!isUseAble) {
    		delete.setVisible(false);
    		colorPicker.setDisable(true);
    	}
    	
    	name.setText(lecture.getName().get());
    	professor.setText(lecture.getProfessor().get());
    	code.setText(lecture.getCode().get());
    	time.setText(lecture.getTime().get());
    	
    	if(lecture.getColor().equals(Color.LIGHTGRAY)) {
    		lecture.setColor(pastell[new Random().nextInt(pastell.length)]);
    	}
    	
    	colorPicker.valueProperty().set(lecture.getColor());
    	if(isUseAble) {
			colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
				lecture.setColor(newValue);
			});
		}
    }
    
    public Lecture getLecture() {
    	return lecture;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
