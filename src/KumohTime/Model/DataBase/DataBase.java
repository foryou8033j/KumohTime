package KumohTime.Model.DataBase;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import KumohTime.Model.AppData;
import KumohTime.Model.TimeTable.Lecture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DataBase 통신 클래스
 * @author Jeongsam Seo
 * @since 2018-07-28
 *
 */
public class DataBase extends DBHeader implements DataBaseAdapter {

	private Connection conn;

	static public boolean isOfflineMode = false;
	static private String dbVersion = "version";

	/**
	 * 커넥션 리셋
	 * @return
	 */
	private boolean initConnection() {
		conn = super.getConnection();
		if (conn == null) {
			if (!isOfflineMode)
				isOfflineMode = true;
			return false;
		}
		else
			return true;
	}

	public void writeLog(String version) {
		if (!initConnection())
			return;

		Statement stmt = null;

		try {

			InetAddress ip;
			ip = InetAddress.getLocalHost();

			DateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

			String sql = "INSERT INTO Log(time, ip, version) VALUES('" + f.format(Calendar.getInstance().getTime()) + "', '"
					+ ip.getHostAddress() + "', '"+ version +"')";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean bugReport(String message, String version) {
		if (!initConnection())
			return false;

		Statement stmt = null;

		try {

			InetAddress ip;
			ip = InetAddress.getLocalHost();

			DateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

			String sql = "INSERT INTO Bug(message, ip, time, version) VALUES('" + message + "', '" + ip.getHostAddress()
					+ "', ' " + f.format(Calendar.getInstance().getTime()) + "','" + version + "')";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;

	}

	public AppData loadAppData() {

		if (!initConnection())
			return new AppData(0.0f, "");

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM " + getDBName() + ".Application";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			float version = 0;
			String path = null;
			
			while (rs.next()) {
				version = Float.valueOf(rs.getString(dbVersion));
				path = rs.getString("path");
			}

			return new AppData(version, path);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String[] getSugangTime() {

		String[] str = {"현재시간", ""};
		
		if (!initConnection())
			return str;
		
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM " + getDBName() + ".Application";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			String time = null;
			String message = null;
			
			while (rs.next()) {
				message = rs.getString("sugangmessage");
				time = rs.getString("sugangtime");
			}
			
			str[0] = message;
			str[1] = time;

			return str;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public float loadDataBaseVersion() {

		initConnection();

		float version = 0.0f;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM "+ getDBName() +".Application";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				version = Float.valueOf(rs.getString("db"));
			}
		} catch (Exception e) {
			System.out.println("DB 서버에 연결 할 수 없습니다.");
		} finally {
			try {
				rs.close();
				stmt.close();
				
			} catch (SQLException e) {
				System.out.println("DB 서버에 연결 할 수 없습니다.");
			}
		}
		
		return version;
	}
	
	public AppData loadApplicationVersion() {

		if(!initConnection())
			return null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM "+ getDBName() +".Application";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			float version = 0;
			String path = null;

			while (rs.next()) {
				version = Float.valueOf(rs.getString(dbVersion));
				path = rs.getString("path");
			}

			return new AppData(version, path);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB 서버에 연결 할 수 없습니다.");
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("DB 서버에 연결 할 수 없습니다.");
			}
		}
		return null;
	}

	public ObservableList<Lecture> loadLectureList() {

		ObservableList<Lecture> lectureList = FXCollections.observableArrayList();
		initConnection();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM " + getDBName() + ".TimeTable";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				lectureList.add(new Lecture(rs.getInt("TimeTable_id"), rs.getInt("년도"), rs.getString("학기"),
						rs.getString("교과목_종류"), rs.getString("교육과정명"), rs.getInt("이수_대상_학년"), rs.getString("이수_구분"),
						rs.getString("교과목명"), rs.getInt("학점"), rs.getString("개설교과목코드"), rs.getString("담당교수"),
						rs.getString("수강학과"), rs.getString("강의시간강의실"), rs.getInt("제한_인원"), rs.getString("수강_꾸러미")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lectureList;

	}
	
}
