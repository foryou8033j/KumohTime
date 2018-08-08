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
	
	

	public Lecture(String name, String code, String professor, String time,
			Color color) {
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
	
	private void stringToTime(String time) {
		if (time != null && time.contains(",")) {
			StringTokenizer token = new StringTokenizer(time, ",");
			
			String rt = "";
			
			while (token.hasMoreTokens()) {
				try {
					rt = "";
					rt = token.nextToken();
					String t = rt.substring(0, rt.indexOf("/"));
					String rm = rt.substring(rt.indexOf("/")+1, rt.length());
					
					convertedTime.add(new LectureTime(t, rm));
					
				}catch (Exception e) {
					System.out.println("오류 : "+ rt + "\n"+toString());
					e.printStackTrace();
				}
			}
		}else if(time != null) {
			
			String t = time.substring(0, time.indexOf("/"));
			String rm = time.substring(time.indexOf("/")+1, time.length());
			
			for(int i=0; i<t.length(); i++) {
				
				char ch = t.charAt(i);
				String tm = "";
				String tmp = t.copyValueOf(t.toCharArray());
				
				if(ch=='월' || ch=='화' || ch=='수' || ch=='목' || ch=='금' || ch=='토' || ch=='일')
					tm += ch;
				else
					continue;
				
				tmp = tmp.replaceAll("월", "");
				tmp = tmp.replaceAll("화", "");
				tmp = tmp.replaceAll("수", "");
				tmp = tmp.replaceAll("목", "");
				tmp = tmp.replaceAll("금", "");
				tmp = tmp.replaceAll("토", "");
				tmp = tmp.replaceAll("일", "");
				
				tm += tmp;
				convertedTime.add(new LectureTime(tm, rm));
			}
			
			
		}
	}



	public IntegerProperty getYear() {
		return year;
	}

	public StringProperty getQuarter() {
		return quarter;
	}

	public IntegerProperty getIndex() {
		return index;
	}

	public StringProperty getType() {
		return type;
	}

	public StringProperty getTrace() {
		return trace;
	}

	public IntegerProperty getGrade() {
		return grade;
	}

	public StringProperty getEssential() {
		return essential;
	}

	public StringProperty getName() {
		return name;
	}

	public IntegerProperty getPoint() {
		return point;
	}

	public StringProperty getCode() {
		return code;
	}

	public StringProperty getProfessor() {
		return professor;
	}

	public StringProperty getMajor() {
		return major;
	}

	public StringProperty getTime() {
		return time;
	}

	public IntegerProperty getLimitPerson() {
		return limitPerson;
	}

	public StringProperty getLecPackage() {
		return lecPackage;
	}

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
