package KumohTime.Model.DataBase;

import KumohTime.Model.AppData;
import KumohTime.Model.TimeTable.Lecture;
import javafx.collections.ObservableList;

/**
 * DataBase 규격 Interface
 * @author Jeongsam Seo
 * @since 2018-07-28
 *
 */
public interface DataBaseAdapter {

	/**
	 * 실행 로그 작성 쿼리
	 * @param version 버전 정보
	 */
	public void writeLog(String version);
	
	/**
	 * 개발자에게 메세지 보내기 쿼리
	 * @param message 메세지 내용
	 * @param version 버전 정보
	 * @return 메세지 전송 여부
	 */
	public boolean bugReport(String message, String version);
	
	/**
	 * Application 의 정보를 DataBase 로부터 수신하는 쿼리
	 * @return
	 */
	public AppData loadAppData();
	
	/**
	 * 수강신청 시간 정보 수신 쿼리
	 * @return 수강신청메세지, 수강신청시간
	 */
	public String[] getSugangTime();
	
	/**
	 * 데이터베이스 버전 수신 쿼리
	 * @return
	 */
	public float loadDataBaseVersion();
	
	/**
	 * 최신 Application 버전 수신 쿼리
	 * @return
	 */
	public AppData loadApplicationVersion();
	
	/**
	 * 데이터베이스로부터 강의정보를 수신하는 쿼리
	 * @deprecated 해당 메소드 사용시 DataBase의 부담이 커져 사용하지 않음
	 * @see SQLite SQLite 대체 사용
	 * @return 강의 정보 리스트
	 */
	public ObservableList<Lecture> loadLectureList();
	
}
