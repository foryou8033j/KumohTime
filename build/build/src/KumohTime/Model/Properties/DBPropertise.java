package KumohTime.Model.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;

import KumohTime.Model.AppData;
import KumohTime.Util.Dialog.ExceptionDialog;
import KumohTime.Util.Security.SHA256;
import javafx.scene.control.Alert.AlertType;

public class DBPropertise {

	final private String resourcePath = AppData.propertisePath + "db.propertise";

	private Properties properties;

	public static String host = "kit-share.com";
	private String id = "kumohtime";
	private String password = "1k5rmf\\ht1a";
	private String db = "KumohTime";

	public String getHost() {
		return host;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getDB() {
		return db;
	}

	public DBPropertise() {
		properties = new Properties();

		File file = new File(resourcePath);
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
			savePropertise();
		} else {
			loadPropertise();
			host = "kit-share.com";
			savePropertise();
		}

	}

	public void loadPropertise() {

		try {

			String propFile = resourcePath;

			Properties props = new Properties();

			FileInputStream fis = new FileInputStream(propFile);

			props.load(new java.io.BufferedInputStream(fis));

			host = props.getProperty("HOST");
			id = props.getProperty("ID");
			password = new SHA256().decrypt(props.getProperty("PASSWORD"));
			db = props.getProperty("DB");

		} catch (IOException | GeneralSecurityException e) {
			new ExceptionDialog(AlertType.ERROR,
					"오류",
					"오류",
					"어플리케이션 기본 속성 파일을 읽는 도중 오류가 발생하였습니다.",
					e).showAndWait();
			System.exit(1);
		}

	}

	public void savePropertise() {

		try {

			Properties prop = new Properties();
			prop.setProperty("HOST", host);
			prop.setProperty("ID", id);
			prop.setProperty("PASSWORD", new SHA256().encrypt(password));
			prop.setProperty("DB", db);

			OutputStream stream = new FileOutputStream(resourcePath);
			prop.store(stream, "Application Propertise");
			stream.close();
		} catch (IOException | GeneralSecurityException e) {
			new ExceptionDialog(AlertType.ERROR,
					"오류",
					"오류",
					"어플리케이션 기본 속성 파일을 읽는 도중 오류가 발생하였습니다.",
					e).showAndWait();
			System.exit(1);
		}
	}

}
