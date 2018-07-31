package KumohTime.Model.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import KumohTime.Model.AppData;
import KumohTime.Model.TimeTable.Lecture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQLite {

	public ObservableList<Lecture> loadDataFromFile() {
		
		ObservableList<Lecture> lectureList = FXCollections.observableArrayList();
		
		Connection connection = null;
		Statement statement = null;

		try {
			/* SQLite JDBC 클래스가 있는지 검사하는 부분입니다. */
			Class.forName("org.sqlite.JDBC");

			/* Program.class와 같은 디렉터리에 있는 test.db를 엽니다. */
			connection = DriverManager.getConnection("jdbc:sqlite:" + AppData.databasePath);
			/* 연결 성공했을 때, connection으로부터 statement 인스턴스를 얻습니다. 여기서 SQL 구문을 수행합니다. */
			statement = connection.createStatement();

			/* 아래는 SQL 예시입니다. */
			/* Table1이라는 테이블 안에 field1(text형), field2(integer형)라는 이름의 필드가 있다고 가정합니다. */
			ResultSet rs = statement.executeQuery("SELECT * FROM TimeTable");

			/* 결과를 첫 행부터 끝 행까지 반복하며 출력합니다. */
			while (rs.next()) {
				lectureList.add(new Lecture(rs.getInt("TimeTable_id"), rs.getInt("년도"), rs.getString("학기"),
						rs.getString("교과목_종류"), rs.getString("교육과정명"), rs.getInt("이수_대상_학년"), rs.getString("이수_구분"),
						rs.getString("교과목명"), rs.getInt("학점"), rs.getString("개설교과목코드"), rs.getString("담당교수"),
						rs.getString("수강학과"), rs.getString("강의시간강의실"), rs.getInt("제한_인원"), rs.getString("수강_꾸러미")));
			}

			/* resultSet 닫기 */
			rs.close();
			/* DB와의 연결 닫기 */
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lectureList;
		
	}

}
