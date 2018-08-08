package KumohTime.Model.TimeTable.SaveData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import KumohTime.Model.TimeTable.Lecture;
import javafx.scene.paint.Color;

@XmlRootElement(name = "lecture")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaveData {

	private final boolean temp;
	private final int year;
	private final String quater;
	private final String name;
	private final String professor;
	private final String code;
	private final String time;
	private double red;
	private double green;
	private double blue;

	public SaveData() {
		this(false, 0, "", "", "", "", "", Color.LIGHTGRAY.getRed(), Color.LIGHTGRAY.getGreen(), Color.LIGHTGRAY.getBlue());
	}

	public SaveData(boolean temp,int year, String quater, String name, String professor, String code, String time, double red, double green, double blue) {
		super();
		this.temp = temp;
		this.year = year;
		this.quater = quater;
		this.name = name;
		this.professor = professor;
		this.code = code;
		this.time = time;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public SaveData(Lecture lecture) {
		super();
		this.temp = lecture.isTemp.get();
		this.year = lecture.getYear().get();
		this.quater = lecture.getQuarter().get();
		this.name = lecture.getName().get();
		this.professor = lecture.getProfessor().get();
		this.code = lecture.getCode().get();
		this.time = lecture.getTime().get();
		this.red = lecture.getColor().getRed();
		this.green = lecture.getColor().getGreen();
		this.blue = lecture.getColor().getBlue();
	}

	public boolean isTemp() {
		return temp;
	}

	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}

	public int getYear() {
		return year;
	}

	public String getQuater() {
		return quater;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getProfessor() {
		return professor;
	}

	public String getTime() {
		return time;
	}
	
	
	
	

}
