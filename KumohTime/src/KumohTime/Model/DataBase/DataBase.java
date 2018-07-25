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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataBase extends DBHeader implements DataBaseAdapter {

	private Connection conn;

	private void initConnection() {
		conn = super.getConnection();
	}
	
	public void writeLog() {
		initConnection();

		Statement stmt = null;

		try {
			
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			
			DateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			
			String sql = "INSERT INTO Log(time, ip) VALUES('" + f.format(Calendar.getInstance().getTime()) + "', '" + ip.getHostAddress() + "')";
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
	
	public void bugReport(String message, String version) {
		initConnection();

		Statement stmt = null;

		try {
			
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			
			DateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			
			String sql = "INSERT INTO Bug(message, ip, time, version) VALUES('" + message + "', '" + ip.getHostAddress() + "', ' " + f.format(Calendar.getInstance().getTime()) + "','"+version+"')";
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

	public AppData loadAppData() {

		
		List<String> notifications = new LinkedList<String>();
		initConnection();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM "+ getDBName() +".Application";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			float version = 0;
			String path = null;

			while (rs.next()) {
				version = Float.valueOf(rs.getString("version"));
				path = rs.getString("path");
				String notificaition = rs.getString("notification");
				notifications.add(notificaition);
			}

			return new AppData(version, path, notifications);

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
	
	public ObservableList<Lecture> loadLectureList(){
		
		ObservableList<Lecture> lectureList = FXCollections.observableArrayList();
		initConnection();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM "+ getDBName() +".TimeTable";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				lectureList.add(new Lecture(
						rs.getInt("TimeTable_id"),
						rs.getInt("년도"),
						rs.getString("학기"),
						rs.getString("교과목_종류"),
						rs.getString("교육과정명"),
						rs.getInt("이수_대상_학년"),
						rs.getString("이수_구분"),
						rs.getString("교과목명"),
						rs.getInt("학점"),
						rs.getString("개설교과목코드"),
						rs.getString("담당교수"),
						rs.getString("수강학과"),
						rs.getString("강의시간강의실"),
						rs.getInt("제한_인원"),
						rs.getString("수강_꾸러미")
						));
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
	
	/*
	@Override
	public boolean Register(String name, String id, String password) {

		initConnection();

		Statement stmt = null;

		try {
			String encryptedPassword = new SHA256().encrypt(password);
			String sql = "INSERT INTO User(user_name, user_id, user_password) VALUES('" + name + "', '" + id + "', '"
					+ encryptedPassword + "')";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			return false;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	@Override
	public User Login(String id, String password) {

		initConnection();

		User loginUser = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM ci_cooperation.`User` WHERE user_id='" + id + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			rs.next();

			int user_key = Integer.valueOf(rs.getString("user_key"));
			String user_name = rs.getString("user_name");
			String user_id = rs.getString("user_id");
			String user_password = rs.getString("user_password");

			String decryptedPassword = new SHA256().decrypt(user_password);

			if (password.equals(decryptedPassword)) {
				loginUser = new User(user_key, user_name, user_id, user_password);
			} else {
				loginUser = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return loginUser;
	}

	public User changePassword(User user, String password) {
		initConnection();

		User loginUser = null;
		Statement stmt = null;

		try {

			String encrypedPassword = new SHA256().encrypt(password);

			String sql = "UPDATE ci_cooperation.`User` SET user_password='" + encrypedPassword + "' WHERE user_key='"
					+ user.getKey() + "';";

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

			loginUser = new User(user.getKey(), user.getName(), user.getStudentID(), encrypedPassword);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return loginUser;
	}

	@Override
	public int startWork(int user_key, Work work) {
		initConnection();

		int work_key;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "INSERT INTO ci_cooperation.WorkHistory(user_key, start_time, goal_time) VALUES(" + user_key
					+ ", '" + work.getTimeToString(work.getStartTime()) + "', '"
					+ work.getTimeToString(work.getGoalTime()) + "')";

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();

			String sql1 = "SELECT * FROM ci_cooperation.WorkHistory WHERE start_time='"
					+ work.getTimeToString(work.getStartTime()) + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1);
			rs.next();

			work_key = Integer.valueOf(rs.getString("work_key"));

		} catch (Exception e) {
			return -1;
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return work_key;
	}

	@Override
	public void saveWork(Work work) {
		initConnection();

		Statement stmt = null;

		try {

			int workKey = work.getKey();
			String todayWork = work.getTodayWork();
			String todoWork = work.getTodoWork();

			String sql = "UPDATE ci_cooperation.WorkHistory SET today_work='" + todayWork + "', todo_work='" + todoWork
					+ "', isComplete=0 WHERE work_key=" + workKey + ";";

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

	public void modifyWork(Work work) {
		initConnection();

		Statement stmt = null;

		try {

			int workKey = work.getKey();
			String startTime = work.getTimeToString(work.getStartTime());
			String endTime = "";
			if(work.getEndTime() != null)
				endTime = work.getTimeToString(work.getEndTime());
			String todayWork = work.getTodayWork();
			String todoWork = work.getTodoWork();

			String sql = "UPDATE ci_cooperation.WorkHistory SET start_time='"+ startTime +"', end_time='" + endTime + "' WHERE work_key=" + workKey + ";";

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

	@Override
	public void endWork(Work work) {

		initConnection();

		Statement stmt = null;

		try {

			int workKey = work.getKey();
			String endTime = work.getTimeToString(work.getEndTime());
			String todayWork = work.getTodayWork();
			String todoWork = work.getTodoWork();

			String sql = "UPDATE ci_cooperation.WorkHistory SET end_time='" + endTime + "', today_work='" + todayWork
					+ "', todo_work='" + todoWork + "', isComplete=1 WHERE work_key=" + workKey + ";";

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

	public Work loadWork(Work work) {

		initConnection();

		Work tmpWork = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM ci_cooperation.WorkHistory WHERE work_key=" + work.getKey() + "";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int work_key = Integer.valueOf(rs.getString("work_key"));
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");
				String goalTime = rs.getString("goal_time");
				String todayWork = rs.getString("today_work");
				String todoWork = rs.getString("todo_work");
				int isComplete = Integer.valueOf(rs.getString("isComplete"));

				tmpWork = new Work(work_key, startTime, endTime, goalTime, todayWork, todoWork, isComplete);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return tmpWork;
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tmpWork;
	}

	@Override
	public ObservableList<Work> getWorkList(int userKey) {

		ObservableList<Work> workList = FXCollections.observableArrayList();

		initConnection();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM ci_cooperation.WorkHistory WHERE user_key=" + userKey + "";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int work_key = Integer.valueOf(rs.getString("work_key"));
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");
				int isComplete = Integer.valueOf(rs.getString("isComplete"));

				workList.add(new Work(work_key, startTime, endTime, isComplete));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return workList;
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return workList;

	}

	public AppData loadAppStatus() {

		AppData tmpAppData = new AppData();
		List<String> notifications = new LinkedList<String>();
		initConnection();

		Work tmpWork = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM ci_cooperation.Application";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			float version = 0;
			String path = null;

			while (rs.next()) {
				version = Float.valueOf(rs.getString("version"));
				path = rs.getString("path");
				String notificaition = rs.getString("notification");
				notifications.add(notificaition);
			}

			tmpAppData.setDefaultData(version, path, notifications);

		} catch (Exception e) {
			e.printStackTrace();
			return tmpAppData;
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tmpAppData;
	}

	public ObservableList<User> loadUserList() {
		ObservableList<User> userList = FXCollections.observableArrayList();

		initConnection();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM ci_cooperation.User";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int user_key = Integer.valueOf(rs.getString("user_key"));
				String user_name = rs.getString("user_name");
				String user_id = rs.getString("user_id");
				String user_password = rs.getString("user_password");

				User user = new User(user_key, user_name, user_id, user_password);
				user.loadWorkList();

				userList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return userList;
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userList;
	}

	public void deleteUser(User user) {
		initConnection();

		Statement stmt = null;

		try {

			int userKey = user.getKey();

			String sql = "DELETE FROM ci_cooperation.User WHERE user_key='" + userKey + "';";

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				deleteWork(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteWork(User user) {
		initConnection();

		Statement stmt = null;

		try {

			int userKey = user.getKey();

			String sql = "DELETE FROM ci_cooperation.WorkHistory WHERE user_key='" + userKey + "';";

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

	public void deleteWork(Work work) {
		initConnection();

		Statement stmt = null;

		try {

			int work_key = work.getKey();

			String sql = "DELETE FROM ci_cooperation.WorkHistory WHERE work_key='" + work_key + "';";

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
*/
}
