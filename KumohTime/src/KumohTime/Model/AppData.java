package KumohTime.Model;

import java.util.LinkedList;
import java.util.List;

import KumohTime.MainApp;
import KumohTime.Model.Properties.AppPropertise;

public class AppData {

	final public static String propertisePath = System.getenv("APPDATA") + "/kumohtime/config/";
	final public static String dataPath = System.getenv("APPDATA") + "/kumohtime/data/";
	final public static String databasePath= System.getenv("APPDATA") + "/kumohtime/kumohtime.db";
	
	public static int runningThread = 0;
	public static int totalThread = 0;
	
	private float appVersion;
	private float serverVersion;
	private String serverPath;
	private List<String> notification = new LinkedList<String>();
	
	private TimeTableData timeTableData;
	
	private AppPropertise appPropertise;
	
	public AppData(AppData appData) {
		appPropertise = new AppPropertise();
		this.appVersion = getAppPropertise().getVersion();
		this.serverVersion = appData.getServerVersion();
		this.notification.addAll(appData.getNotificaitionList());
		this.serverPath = appData.getServerPath();
		
		timeTableData = new TimeTableData();
	}

	public AppData(float serverVersion, String serverPath, List<String> notifications) {
		this.serverVersion = serverVersion;
		this.serverPath = serverPath;
		this.notification.addAll(notifications);
	}

	public String getServerPath() {
		return serverPath;
	}

	public float getServerVersion() {
		return serverVersion;
	}

	public List<String> getNotificaitionList() {
		return notification;
	}
	
	public AppPropertise getAppPropertise() {
		return appPropertise;
	}
	
	public TimeTableData getTimeTableData() {
		return timeTableData;
	}

}
