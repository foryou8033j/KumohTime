package KumohTime.Model;

import KumohTime.Model.Properties.AppProperties;
import KumohTime.Model.Properties.ResourceProperties;
import KumohTime.Model.TimeTable.SaveData.SaveDataController;

/**
 * Application 의 동적 데이터를 관리한다.
 * @author Jeongsam Seo
 * @since 2018.07-28
 *
 */
public class AppData {

	//Properties 등 Data Path
	final public static String propertisePath = "config/";
	final public static String dataPath = "data/";
	final public static String databasePath= "kumohtime.db";
	final public static String saveFilePath= dataPath + "savefile.dat";
	final public static String clientPath = "KumohTime.jar";
	
	private float appVersion;		//Application Version
	private float serverVersion;	//Server Application Version (Recently)
	private String serverPath;		//Server Data Path
	
	private TimeTableData timeTableData;	//TimeTable Data Collection
	
	private AppProperties appProperties;	//Application Properties
	private ResourceProperties resourcesProperties;	//Local Database Properties
	
	private SaveDataController saveDataController;	//Save Data File Controller
	
	public AppData(AppData appData) {
		
		appProperties = new AppProperties();
		this.appVersion = getAppPropertise().getVersion();
		this.serverVersion = appData.getServerVersion();
		this.serverPath = appData.getServerPath();
		
		timeTableData = new TimeTableData();
		saveDataController = new SaveDataController();
		resourcesProperties = new ResourceProperties();
	}

	public AppData(float serverVersion, String serverPath) {
		this.serverVersion = serverVersion;
		this.serverPath = serverPath;
	}

	public String getServerPath() {
		return serverPath;
	}

	public float getServerVersion() {
		return serverVersion;
	}
	
	public AppProperties getAppPropertise() {
		return appProperties;
	}
	
	public TimeTableData getTimeTableData() {
		return timeTableData;
	}

	public SaveDataController getSaveDataController() {
		return saveDataController;
	}
	
	public ResourceProperties getResourcePropertise() {
		return resourcesProperties;
	}
	
	

}
