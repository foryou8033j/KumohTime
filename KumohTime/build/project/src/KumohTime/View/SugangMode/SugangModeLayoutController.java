package KumohTime.View.SugangMode;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.prism.paint.Color;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.View.Home.SelectedLecture.SelectedLectureLayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class SugangModeLayoutController implements Initializable {

    @FXML
    private JFXListView<GridPane> list;

    @FXML
    private Text time;
    
    @FXML
    private Text sugangmessage;

    @FXML
    void handleOpenKitShare(ActionEvent event) {
    	try {
			Desktop.getDesktop().browse(new URI("https://kit-share.com/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void handleOpenSugangPage(ActionEvent event) {
    	try {
			Desktop.getDesktop().browse(new URI("http://nkaiser.kumoh.ac.kr/kaiser/cre/w_cre_s5200_login.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void handleOpenUnivPage(ActionEvent event) {
    	try {
			Desktop.getDesktop().browse(new URI("http://kumoh.ac.kr/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private MainApp mainApp;
	
	private ObservableList<GridPane> selectedLayoutList = FXCollections.observableArrayList();
	private ObservableList<SelectedLectureLayoutController> selectedLayoutControllerList = FXCollections
			.observableArrayList();
	
	private Calendar targetTime;

	public void setDefault(MainApp mainApp, JFXToggleButton toggleButton, SugangModeDialog stage) {
		this.mainApp = mainApp;
		
		list.setItems(selectedLayoutList);
		
		stage.setOnShowing(e->{
			
			selectedLayoutControllerList.clear();
			selectedLayoutList.clear();
			
			for(Lecture v:mainApp.getAppData().getTimeTableData().getSelectedLecture()) {
				try {
					FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("View/Home/SelectedLecture/SelectedLectureLayout.fxml"));
					GridPane pane = loader.load();
					
					SelectedLectureLayoutController controller = loader.getController();
					controller.setDefault(mainApp, v, pane, false);

					selectedLayoutList.add(pane);
					selectedLayoutControllerList.add(controller);
				}catch (Exception a) {
					a.printStackTrace();
				}
			}
		});
		
		new Thread(()-> {
			
			String[] sugangData = new DataBase().getSugangTime();
			new DataBase().writeLog(mainApp.getAppData().getAppPropertise().getVersionString());
			
			// 시간값이 맞는지 검증
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			
			try {
				
				sugangmessage.setText(sugangData[0]);
				targetTime = Calendar.getInstance();
				
				if(!sugangData[1].equals(""))
					targetTime.setTime(format.parse(sugangData[1]));
				
				while(true) {
					
					Thread.sleep(10);
					
					if(sugangData[1].equals("")) {
						time.setText(format.format(Calendar.getInstance().getTime()));
						continue;
					}
					
					Calendar now = Calendar.getInstance();
					
					long diffTime = targetTime.getTimeInMillis() - now.getTimeInMillis();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(diffTime);
					
					long diffSeconds = diffTime / 1000 % 60;
					long diffMinutes = diffTime / (60 * 1000) % 60;
					long diffHours = diffTime / (60 * 60 * 1000) % 24;
					long diffDays = diffTime / (24 * 60 * 60 * 1000);
					
					if(diffDays != 0)
						time.setText("D-" + diffDays + " " + diffHours +"시간 "+diffMinutes+"분 "+diffSeconds + "초");
					else {
						time.setFill(Paint.valueOf("red"));
						if(diffHours != 0)
							time.setText(diffHours +"시간 "+diffMinutes+"분 "+diffSeconds + "초");
						else {
							if(diffMinutes != 0)
								time.setText(diffMinutes+"분 "+diffSeconds + "초");
							else {
								time.setText(diffSeconds + "초 ");	
							}
								
						}
						
						if(diffSeconds < 0) {
							time.setText("END");
							break;
						}
							
					}
					
					
						
						
					
				}

			} catch (Exception e) {
				// 시간 입력 형식이 맞지 않음
				e.printStackTrace();
			}
			
		}).start();
		
		
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		

	}

}
