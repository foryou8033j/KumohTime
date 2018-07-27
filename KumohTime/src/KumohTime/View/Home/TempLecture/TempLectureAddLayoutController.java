package KumohTime.View.Home.TempLecture;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import KumohTime.Model.TimeTable.Lecture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class TempLectureAddLayoutController implements Initializable{

    @FXML
    private GridPane pane;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField professor;
    
    @FXML
    private JFXTextField room;

    @FXML
    private JFXComboBox<String> dayOfweek;

    private ObservableList<String> time = FXCollections.observableArrayList("1교시","2교시","3교시","4교시","5교시","6교시","7교시","8교시","9교시","A교시","B교시","C교시","D교시","E교시","F교시");
    final CheckComboBox<String> checkComboBox = new CheckComboBox<String>(time);
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<String> week = FXCollections.observableArrayList();
		week.addAll("월", "화", "수", "목", "금");
		dayOfweek.setItems(week);
		
		pane.add(checkComboBox, 3, 0);
	}
	
	public Lecture getLectureData() {
		
		String time = dayOfweek.getSelectionModel().getSelectedItem();
		for(String v:checkComboBox.getCheckModel().getCheckedItems()) {
			time = time.concat(v.replaceAll("교시", ""));
		}
		
		return new Lecture(name.getText(), "강의-임시", professor.getText(), time.concat("/"+room.getText()), Color.CORAL);
		
	}
	
	public boolean isGetAble() {
		
		if(name.getText().length() < 1)
			return false;
		
		if(dayOfweek.getSelectionModel().getSelectedItem() == null)
			return false;
		
		if(checkComboBox.getCheckModel().getCheckedItems().size() == 0)
			return false;
		
		return true;
	}
    

}
