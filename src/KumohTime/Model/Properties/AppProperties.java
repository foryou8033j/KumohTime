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

/**
 * Application 의 고정 정보를 관리한다.
 * @author Jeongsam Seo
 *
 */
public class AppProperties {

	final private String resourcePath = AppData.propertisePath + "app.propertise";

	private Properties properties;

	//배포 버전의 버전관리
	final public float BIND_VERSION = 0.931f;

	//Application Title
	private String title = "KumohTime";
	
	//아래 두개 변수는 개발도중 업데이트 버전 관리 착오로 생겨났습니다, 두개 변수를 수정 할 경우 0.92 버전 이전 사용자가 업데이트를 수행 할 수 없습니다.
	private float version = BIND_VERSION;	//0.92 이전의 버전 컨트롤 변수 (Portable 전용)
	private float _version = 0;				//0.92 이후의 버전 컨트롤 변수 (Installer 전용)
	
	@Deprecated
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

	@Deprecated
	public void setNotify(boolean set) {
		isNotified = set;
	}

	@Deprecated
	public boolean isNotify() {
		return isNotified;
	}

	public AppProperties() {
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
			_version = Float.valueOf(props.getProperty("_VERSION", "0.1"));
			

			System.out.println("asd");
			
			if(_version != BIND_VERSION) {
				isNotified = false;
				_version = BIND_VERSION;
			}
			
		} catch (Exception e) {
			new ExceptionDialog(AlertType.ERROR, "오류", "오류", "어플리케이션 기본 속성 파일을 읽는 도중 오류가 발생하였습니다.", e).showAndWait();
			System.exit(1);
		}

	}

	public void savePropertise() {

		Properties prop = new Properties();
		prop.setProperty("TITLE", title);
		prop.setProperty("VERSION", getVersionString());
		prop.setProperty("_VERSION", String.valueOf(_version));
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
