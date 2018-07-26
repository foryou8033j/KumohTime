package KumohTime.Model.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import KumohTime.Model.AppData;
import KumohTime.Util.Dialog.ExceptionDialog;
import javafx.scene.control.Alert.AlertType;

public class AppPropertise {

	final private String resourcePath = AppData.propertisePath + "app.propertise";

	private Properties properties;

	final private float BIND_VERSION = 0.8f;

	private String title = "KumohTime";
	private float version = BIND_VERSION;
	private boolean isNotified = false;

	public String getTitle() {
		return title;
	}

	public float getVersion() {
		return version;
	}

	public String getVersionString() {
		return String.valueOf(version);
	}

	public void setNotify(boolean set) {
		isNotified = set;
	}

	public boolean isNotify() {
		return isNotified;
	}

	public AppPropertise() {
		properties = new Properties();

		File file = new File(resourcePath);
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
			savePropertise();
		}else {
			loadPropertise();
			version = BIND_VERSION;
			savePropertise();
		}
		
	}

	public void loadPropertise() {

		try {

			String propFile = resourcePath;

			Properties props = new Properties();

			FileInputStream fis = new FileInputStream(propFile);

			props.load(new java.io.BufferedInputStream(fis));

			title = props.getProperty("TITLE");
			version = Float.valueOf(props.getProperty("VERSION"));
			isNotified = Boolean.valueOf(props.getProperty("NOTIFY"));

		} catch (IOException e) {
			new ExceptionDialog(AlertType.ERROR, "오류", "오류", "어플리케이션 기본 속성 파일을 읽는 도중 오류가 발생하였습니다.", e).showAndWait();
			System.exit(1);
		}

	}

	public void savePropertise() {

		Properties prop = new Properties();
		prop.setProperty("TITLE", title);
		prop.setProperty("VERSION", getVersionString());
		prop.setProperty("NOTIFY", String.valueOf(isNotified));

		try {
			OutputStream stream = new FileOutputStream(resourcePath);
			prop.store(stream, "Application Propertise");
			stream.close();
		} catch (IOException e) {
			new ExceptionDialog(AlertType.ERROR, "오류", "오류", "어플리케이션 기본 속성 파일을 읽는 도중 오류가 발생하였습니다.", e).showAndWait();
			System.exit(1);
		}
	}

}
