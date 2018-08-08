package KumohTime.Util.Dialog.Loading;

import com.jfoenix.controls.JFXSpinner;

import KumohTime.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingDialog extends Stage {

	private JFXSpinner spinner;
	private Text text;
	private LoadingDialogLayoutController controller;

	public LoadingDialog(Stage owner) {

		super(StageStyle.UNDECORATED);

		initOwner(owner);
		initModality(Modality.APPLICATION_MODAL);
		setTitle("");		

		try {

			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("Util/Dialog/Loading/LoadingDialogLayout.fxml"));
			VBox vb = loader.load();
			
			controller = loader.getController();
			spinner = controller.getSpinner();
			text = controller.getText();

			Scene scene = new Scene(vb);
			setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}

		double centerX = owner.getX() + owner.getWidth() / 2d;
		double centerY = owner.getY() + owner.getHeight() / 2d;

		setX(centerX);
		setY(centerY);

		setOnShown(ev -> {
			getScene().getRoot().setEffect(new DropShadow());
			getScene().setFill(Color.TRANSPARENT);
			setX(centerX - getWidth() / 2d);
			setY(centerY - getHeight() / 2d);
			show();
		});

		show();
	}

	public JFXSpinner getSpinner() {
		return spinner;
	}

	public Text getText() {
		return text;
	}

}
