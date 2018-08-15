package KumohTime.Model.TimeTable;

import java.util.Calendar;
import java.util.Random;
import java.util.StringTokenizer;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 * 강의 정보 객체
 * @author Jeongsam Seo
 * @since 2018-07-28
 */
public class Lecture extends RecursiveTreeObject<Lecture> {

	public BooleanProperty isSelectAble = new SimpleBooleanProperty(true);
	public BooleanProperty isSelected = new SimpleBooleanProperty(false);
	public BooleanProperty isTemp = new SimpleBooleanProperty(false);

	private IntegerProperty index;
	private IntegerProperty year;
	private StringProperty quarter;
	private StringProperty type;
	private StringProperty trace;
	private IntegerProperty grade;
	private StringProperty essential;
	private StringProperty name;
	private IntegerProperty point;
	private StringProperty code;
	private StringProperty professor;
	private StringProperty major;
	private StringProperty time;
	private IntegerProperty limitPerson;
	private StringProperty lecPackage;

	private ObservableList<LectureTime> convertedTime = FXCollections.observableArrayList();
	private ObjectProperty<Color> color = new SimpleObjectProperty<Color>(Color.LIGHTGRAY);
	
	//파스텔톤 색상 저장
    Color[] pastell = {
    		Color.rgb(237, 223, 185),
    		Color.rgb(237, 199, 121),
    		Color.rgb(215, 127, 110),
    		Color.rgb(115, 165, 152),
    		Color.rgb(169, 188, 139),
    		Color.rgb(255, 222, 255),
    		Color.rgb(222, 222, 255),
    		Color.rgb(255, 255, 222),
    		
    		Color.rgb(222, 222, 239),
    		Color.rgb(222, 239, 255),
    		Color.rgb(222, 255, 255),
    		
    		Color.rgb(239, 222, 239),
    		Color.rgb(255, 222, 222),
    		Color.rgb(239, 222, 222),
    		Color.rgb(255, 239, 222),
    		Color.rgb(255, 222, 239),

    		Color.rgb(239, 222, 255),
    		Color.rgb(222, 255, 239),
    		Color.rgb(239, 255, 222),
    		Color.rgb(222, 255, 222),
    		Color.rgb(222, 239, 222),
    		Color.rgb(222, 239, 239),
    		Color.rgb(239, 239, 222)
    		};

	public Lecture(int index, int year, String quarter, String type, String trace, int grade, String essential,
			String name, int point, String code, String professor, String major, String time, int limitPerson,
			String lecPackage) {
		super();
		
		setColor(pastell[new Random().nextInt(pastell.length)]);
		
		this.index = new SimpleIntegerProperty(index);
		this.year = new SimpleIntegerProperty(year);
		this.quarter = new SimpleStringProperty(quarter);
		this.type = new SimpleStringProperty(type);
		this.trace = new SimpleStringProperty(trace);
		this.grade = new SimpleIntegerProperty(grade);
		this.essential = new SimpleStringProperty(essential);
		this.name = new SimpleStringProperty(name);
		this.point = new SimpleIntegerProperty(point);
		this.code = new SimpleStringProperty(code);
		this.professor = new SimpleStringProperty(professor);
		this.major = new SimpleStringProperty(major);
		this.time = new SimpleStringProperty(time);
		this.limitPerson = new SimpleIntegerProperty(limitPerson);
		this.lecPackage = new SimpleStringProperty(lecPackage);
		isTemp.set(false);

		stringToTime(time);
	}
	
	

	public Lecture(String name, String code, String professor, String time, Color color) {
		super();
		
		setColor(pastell[new Random().nextInt(pastell.length)]);
		
		this.year = new SimpleIntegerProperty(Calendar.getInstance().get(Calendar.YEAR));
		this.quarter = new SimpleStringProperty("임시데이터");
		
		this.name = new SimpleStringProperty(name);
		this.code = new SimpleStringProperty(code);
		this.professor = new SimpleStringProperty(professor);
		this.time = new SimpleStringProperty(time);
		this.color.set(color);
		isTemp.set(true);
		System.out.println(isTemp.get());
		
		stringToTime(time);
	}
	
	/**
	 * 원본데이터에서 주어지는 시간정보를 강의시간정보로 변환한다.
	 * 목BC -> 객체화(LectureTime)
	 * 이 메소드는 수정이 필요합니다
	 * @param time
	 */
	private void stringToTime(String time) {
		
		
		//강의시간을 각각 분리한다. 화67/D321,금6/D231 -> 화67/D321 금6/D231
		if (time != null && time.contains(",")) {
			StringTokenizer token = new StringTokenizer(time, ",");
			
			String rt = "";
			
			//강의시간이 2개로만 구분되는것이 아닐수도 있다
			while (token.hasMoreTokens()) {
				try {
					rt = "";
					rt = token.nextToken();
					String t = rt.substring(0, rt.indexOf("/"));				//강의시간정보
					String rm = rt.substring(rt.indexOf("/")+1, rt.length());	//강의실정보
					
					convertedTime.add(new LectureTime(t, rm));	//시간 정보 저장
					
				}catch (Exception e) {
					System.out.println("오류 : "+ rt + "\n"+toString());
					e.printStackTrace();
				}
			}
			
		}else if(time != null) {
			
			String t = time.substring(0, time.indexOf("/"));				//강의시간정보
			String rm = time.substring(time.indexOf("/")+1, time.length());	//강의실정보
			
			for(int i=0; i<t.length(); i++) {
				
				char ch = t.charAt(i);
				String tm = "";
				String tmp = t.copyValueOf(t.toCharArray());
				
				/*
				 * 1개 요일 1개의 시간 정보를 저장한다.
				 * 예 ) 월화목금678 과 같이 저장되어있는경우 요일정보를 분리하여 그 갯수만큼 월678 화678 목678 금 678의 시간 객체를 생성한다.
				 * 요일정보를 하나 분리
				 */
				if(ch=='월' || ch=='화' || ch=='수' || ch=='목' || ch=='금' || ch=='토' || ch=='일')
					tm += ch;
				else
					continue;
				
				// 남아있는 요일정보를 제거
				tmp = tmp.replaceAll("월", "");
				tmp = tmp.replaceAll("화", "");
				tmp = tmp.replaceAll("수", "");
				tmp = tmp.replaceAll("목", "");
				tmp = tmp.replaceAll("금", "");
				tmp = tmp.replaceAll("토", "");
				tmp = tmp.replaceAll("일", "");
				
				tm += tmp;
				convertedTime.add(new LectureTime(t, rm));
			}
			
			
		}
	}



	/**
	 * 학사년도
	 * @return IntegerProperty
	 */
	public IntegerProperty getYear() {
		return year;
	}

	/**
	 * 학기
	 * @return IntegerProperty
	 */
	public StringProperty getQuarter() {
		return quarter;
	}

	/**
	 * 고유번호
	 * @return IntegerProperty
	 */
	public IntegerProperty getIndex() {
		return index;
	}

	/**
	 * 구분
	 * @return IntegerProperty
	 */
	public StringProperty getType() {
		return type;
	}

	/**
	 * 학과
	 * @return IntegerProperty
	 */
	public StringProperty getTrace() {
		return trace;
	}

	/**
	 * 학년
	 * @return IntegerProperty
	 */
	public IntegerProperty getGrade() {
		return grade;
	}

	/**
	 * 필수구분
	 * @return IntegerProperty
	 */
	public StringProperty getEssential() {
		return essential;
	}

	/**
	 * 강의명
	 * @return IntegerProperty
	 */
	public StringProperty getName() {
		return name;
	}

	/**
	 * 학점
	 * @return IntegerProperty
	 */
	public IntegerProperty getPoint() {
		return point;
	}

	/**
	 * 수강코드
	 * @return IntegerProperty
	 */
	public StringProperty getCode() {
		return code;
	}

	/**
	 * 교수명
	 * @return IntegerProperty
	 */
	public StringProperty getProfessor() {
		return professor;
	}

	/**
	 * 전공명
	 * @return IntegerProperty
	 */
	public StringProperty getMajor() {
		return major;
	}

	/**
	 * 강의시간
	 * @return IntegerProperty
	 */
	public StringProperty getTime() {
		return time;
	}

	/**
	 * 수강인원
	 * @return IntegerProperty
	 */
	public IntegerProperty getLimitPerson() {
		return limitPerson;
	}

	/**
	 * 수강꾸러미
	 * @return IntegerProperty
	 */
	public StringProperty getLecPackage() {
		return lecPackage;
	}

	/**
	 * 필터링 옵션에 부합하는지 검사
	 * @param quater	학기
	 * @param grade		학년
	 * @param essential	필수여부
	 * @param type		구분
	 * @param major		전공
	 * @param code		코드
	 * @param name		강의명
	 * @return	부함여부
	 */
	public boolean isFilter(String quater, String grade, String essential, String type, String major, String code,
			String name) {

		if (!(quater.equals(this.quarter.get()) || quater.equals("") || quater.equals("전체")))
			return false;

		if (!(grade.equals(String.valueOf(this.grade.get())) || grade.equals("") || grade.equals("전체")))
			return false;

		if (!(essential.equals(this.essential.get()) || essential.equals("") || essential.equals("해당없음")))
			return false;

		if (!(type.equals(this.type.get()) || type.equals("") || type.equals("전체")))
			return false;

		if (!(major.equals(this.trace.get()) || major.equals("") || major.equals("전체")))
			return false;

		if (!(this.code.get().contains(code) || code.equals("") || code.equals("전체")))
			return false;

		if (!(this.name.get().contains(name) || name.equals("") || name.equals("전체")))
			return false;

		return true;
	}
	
	public ObservableList<LectureTime> getLectureTime(){
		return convertedTime;
	}
	
	public ObjectProperty<Color> getColorProperty(){
		return color;
	}
	
	public Color getColor() {
		return color.get();
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

	@Override
	public String toString() {

		System.out.println(index.get() + "\t" + year.get() + "\t" + quarter.get() + "\t" + type.get() + "\t" + trace.get() + "\t" + grade.get() + "\t" + essential.get()
				+ "\t" + name.get() + "\t" + point.get() + "\t" + code.get() + "\t" + professor.get() + "\t" + major.get() + "\t" + time.get() + "\t" + limitPerson.get()
				+ "\t" + lecPackage.get());

		return super.toString();
	}

}
