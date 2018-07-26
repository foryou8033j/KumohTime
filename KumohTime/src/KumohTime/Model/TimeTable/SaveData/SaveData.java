package KumohTime.Model.TimeTable.SaveData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.scene.paint.Color;

@XmlRootElement(name = "lecture")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaveData {

	private final int year;
	private final String quater;
	private final String name;
	private final String code;
	private double red;
	private double green;
	private double blue;

	public SaveData() {
		this(0, "", "", "", Color.LIGHTGRAY.getRed(), Color.LIGHTGRAY.getGreen(), Color.LIGHTGRAY.getBlue());
	}

	public SaveData(int year, String quater, String name, String code, double red, double green, double blue) {
		super();
		this.year = year;
		this.quater = quater;
		this.name = name;
		this.code = code;
		this.red = red;
		this.green = green;
		this.blue = blue;
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

}
