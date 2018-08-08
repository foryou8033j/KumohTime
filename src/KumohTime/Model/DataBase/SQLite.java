package KumohTime.Model.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import KumohTime.Model.AppData;
import KumohTime.Model.TimeTable.Lecture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Local SQLite 사용을 위한 클래스
 * @author Jeongsam Seo
 * @since 2018-08-01
 */
public class SQLite {

	public ObservableList<Lecture> loadDataFromFile() {
		
		ObservableList<Lecture> lectureList = FXCollections.observableArrayList();
		
		Connection connection = null;
		Statement statement = null;

		try {
			Class.forName("org.sqlite.JDBC");

			connection = DriverManager.getConnection("jdbc:sqlite:" + AppData.databasePath);
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM TimeTable");

			while (rs.next()) {
				lectureList.add(new Lecture(rs.getInt("TimeTable_id"), rs.getInt("년도"), rs.getString("학기"),
						rs.getString("교과목_종류"), rs.getString("교육과정명"), rs.getInt("이수_대상_학년"), rs.getString("이수_구분"),
						rs.getString("교과목명"), rs.getInt("학점"), rs.getString("개설교과목코드"), rs.getString("담당교수"),
						rs.getString("수강학과"), rs.getString("강의시간강의실"), rs.getInt("제한_인원"), rs.getString("수강_꾸러미")));
			}

			rs.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lectureList;
		
	}

}
