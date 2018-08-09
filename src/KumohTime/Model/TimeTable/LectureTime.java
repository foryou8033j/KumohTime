package KumohTime.Model.TimeTable;

import java.util.LinkedList;
import java.util.List;

/**
 * 강의 시간정보를 저장하는 클래스
 * 1개 객체에 하나의 요일정보만을 저장한다.
 * @author Jeongsam Seo
 * @since 2018-07-28
 *
 */
public class LectureTime {

	//요일정보
	private int dayOfWeek = 0;
	
	//시간정보 (교시)
	private boolean time[] ={false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	
	//강의실 정보
	private final String room;
	
	public LectureTime(String time, String room) {
		
		setTime(time);
		this.room = room;
	}
	
	/**
	 * 문자열로부터 강의시간을 설정한다.
	 * @param time
	 */
	public void setTime(String time) {
		
		//가장 첫번째 문자열은 요일정보이어야한다.
		//요일 정보 설정
		char ch = time.charAt(0);
		
		if(ch == '월')
			dayOfWeek = 0;
		if(ch == '화')
			dayOfWeek = 1;
		if(ch == '수')
			dayOfWeek = 2;
		if(ch == '목')
			dayOfWeek = 3;
		if(ch == '금')
			dayOfWeek = 4;
		if(ch == '토')
			dayOfWeek = 5;
		if(ch == '일')
			dayOfWeek = 6;
		
		
		//시간 정보 설정
		for(int i=1; i<time.length(); i++) {
			
			if(time.charAt(i) == 'A') {
				this.time[10] = true;
				continue;
			}
			if(time.charAt(i) == 'B') {
				this.time[11] = true;
				continue;
			}
			if(time.charAt(i) == 'C') {
				this.time[12] = true;
				continue;
			}
			if(time.charAt(i) == 'D') {
				this.time[13] = true;
				continue;
			}
			if(time.charAt(i) == 'E') {
				this.time[14] = true;
				continue;
			}
			if(time.charAt(i) == 'F') {
				this.time[15] = true;
				continue;
			}
			try {
				this.time[time.charAt(i) - '0'] = true;
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	/**
	 * 요일 정보를 반환한다.
	 * @return DayOfWeek
	 */
	public int dayOfWeek() {
		return dayOfWeek;
	}
	
	/**
	 * 시간 정보를 반환한다.
	 * @return boolean Array 형태를 List에 담아 반환
	 */
	public List<Number> getTime() {
		
		List<Number> hasTime = new LinkedList<Number>();
		
		for(int i=0; i<time.length; i++) {
			if(time[i])
				hasTime.add(i);
		}
		
		return hasTime;
	}
	
	/**
	 * 강의실 정보를 반환한다.
	 * @return
	 */
	public String getRoom() {
		return room;
	}
	
	/**
	 * 비교대상 강의와 시간이 충돌하는지 검사한다.
	 * @param lecture 비교대상 강의
	 * @return 충돌여부
	 */
	public boolean isConflict(Lecture lecture) {
		
		for(LectureTime v:lecture.getLectureTime()) {
			
			if(dayOfWeek == v.dayOfWeek()) { //같은 요일에서
				
				//비교대상과 원본이 충돌하는지 검사한다.
				for(Number a:getTime()) {
					for(Number b:v.getTime()) {
						if(a.intValue() == b.intValue()) //같은 시간이 존재한다면 
							return true;	//충돌 상태 반환
					}
				}
				
			}
			
		}
		
		return false;
	}
	
}
