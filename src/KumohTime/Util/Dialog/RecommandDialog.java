package KumohTime.Util.Dialog;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.text.PlainDocument;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.sun.javafx.PlatformUtil;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import KumohTime.MainApp;
import KumohTime.Util.Browser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * 종료 전 kit-share 접속을 권장하는 Dialog
 * @author Jeongsam Seo
 * @since 2018-07-05
 */
public class RecommandDialog{

	private JFXButton button;
	private JFXButton button1;
	private JFXDialog dialog;
	
	public RecommandDialog(MainApp mainApp) {
		
		JFXDialogLayout content = new JFXDialogLayout();
		
		Text title = new Text("정말 종료하시겠습니까?");
		title.setFont(Font.font("malgun gothic", FontWeight.BOLD, 18));
	    content.setHeading(title);
	    
	    
	    Text textMessage = new Text("종료 하시기 전에 kit-share 한번 방문 해 보시는거 어떠세요?");
	    textMessage.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 14));
	    content.setBody(textMessage);
	    
	    
	    /*
	    /*** 0.93 이벤트 응모 이벤트 패치 반영  ********
	    ImageView imageView = new ImageView(new Image("https://kit-share.com/files/attach/images/467/817/013/f1eed9968927ab413fa285da0d1d7e57.jpg"));
	    Text context = new Text("종료 하시기 전에 kit-share에서 이벤트 참여하고 상품받아가세요!");
	    context.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 14));
	    content.setBody(context);
	    
	    VBox vb = new VBox(20);
	    vb.getChildren().addAll(context, imageView);
	    vb.setAlignment(Pos.CENTER);
	    content.setBody(vb);
	    ******************************/
	    
	    dialog = new JFXDialog(mainApp.getRootLayoutController().getRootLayout(), content, JFXDialog.DialogTransition.CENTER);
	    button = new JFXButton("방문 하기");
	    button.setRipplerFill(Color.BLUE);
	    button.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 12));
	    button.setPrefWidth(200);
	    button.setPrefHeight(100);
	    button.setButtonType(ButtonType.RAISED);
	    button.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	
	        	Platform.runLater(() -> {
	        		Browser.open("https://kit-share.com/");
		        	
		        	dialog.close();
		            System.exit(0);	
	        	});
	        }
	    });
	    
	    button1 = new JFXButton("그냥 종료");
	    button1.setFont(Font.font("malgun gothic", FontWeight.NORMAL, 12));
	    button1.setPrefWidth(200);
	    button1.setPrefHeight(100);
	    button1.setRipplerFill(Color.RED);
	    button1.setButtonType(ButtonType.RAISED);
	    button1.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	Platform.runLater(() -> {
	        		dialog.close();
		            System.exit(0);
	        	});
	            
	        }
	    });
	    
	    content.setActions(button, button1);
	    
	}
	
	public JFXButton getButton() {
		return button;
	}
	
	public JFXDialog getDialog() {
		return dialog;
	}

}
