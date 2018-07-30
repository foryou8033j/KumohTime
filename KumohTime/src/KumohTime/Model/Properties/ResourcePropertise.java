package KumohTime.Model.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import KumohTime.Model.AppData;

public class ResourcePropertise {

	final private String resourcePath = AppData.dataPath + "resources.propertise";

	private Properties properties;

	private float version;

	public float getVersion() {
		return version;
	}

	public String getVersionString() {
		return String.valueOf(version);
	}

	public ResourcePropertise() {
		properties = new Properties();

		File file = new File(resourcePath);
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
			savePropertise(0.0f);
		} else {
			loadPropertise();
		}
	}

	public void loadPropertise() {

		try {

			String propFile = resourcePath;

			Properties props = new Properties();

			FileInputStream fis = new FileInputStream(propFile);

			props.load(new java.io.BufferedInputStream(fis));

			version = Float.valueOf(props.getProperty("VERSION"));

		} catch (IOException e) {
			
			System.exit(1);
		}

	}

	public void savePropertise(float version) {
		
		this.version = version;

		savePropertise();
	}
	
	public void savePropertise() {

		Properties prop = new Properties();
		prop.setProperty("VERSION", getVersionString());

		try {
			OutputStream stream = new FileOutputStream(resourcePath);
			prop.store(stream, "Application Propertise");
			stream.close();
		} catch (IOException e) {
			
			System.exit(1);
		}
	}

}
