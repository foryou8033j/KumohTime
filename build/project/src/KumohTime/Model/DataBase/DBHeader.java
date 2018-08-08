package KumohTime.Model.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import KumohTime.Model.Properties.DBPropertise;
import KumohTime.Util.Dialog.ExceptionDialog;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;

/**
 * 데이터베이스 접속을 위한 기본 환경 설정
 * @author Jeongsam
 * @since 2018-07-05
 *
 */
public class DBHeader {

	//DB Propertise
	private DBPropertise dbPropertise = new DBPropertise();
	
	private String url = "jdbc:mysql://"+ dbPropertise.getHost() +":3306/"+ dbPropertise.getDB() +"?serverTimezone=UTC";
	private Connection conn;
	
	private String id = dbPropertise.getId();
	private String pw = dbPropertise.getPassword();
	
	protected String getDBName() {
		return dbPropertise.getDB();
	}
	
	protected DBHeader() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.err.println("jdbc Class name Not Found");
		} catch (SQLException e) {
			System.err.println("Can not Connect to DB Server");
		}
	}
	
	protected Connection getConnection() {
		
		try {
			return DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
		}
		
		return null;
	}
	
}
