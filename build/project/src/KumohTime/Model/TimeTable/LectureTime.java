package KumohTime.Model.TimeTable;

import java.util.LinkedList;
import java.util.List;

public class LectureTime {

	private int dayOfWeek = 0;
	
	private boolean time[] ={false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	
	private final String room;
	
	public LectureTime(String time, String room) {
		
		setTime(time);
		this.room = room;
	}
	
	public void setTime(String time) {
		
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
	
	public int dayOfWeek() {
		return dayOfWeek;
	}
	
	public List<Number> getTime() {
		
		List<Number> hasTime = new LinkedList<Number>();
		
		for(int i=0; i<time.length; i++) {
			if(time[i])
				hasTime.add(i);
		}
		
		return hasTime;
	}
	
	public String getRoom() {
		return room;
	}
	
	public boolean isConflict(Lecture lecture) {
		
		for(LectureTime v:lecture.getLectureTime()) {
			
			if(dayOfWeek == v.dayOfWeek()) {
				
				for(Number a:getTime()) {
					for(Number b:v.getTime()) {
						if(a.intValue() == b.intValue())
							return true;
					}
				}
				
			}
			
		}
		
		return false;
	}
	
}
