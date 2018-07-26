package KumohTime.Model.TimeTable.SaveData;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SaveData {

	private final IntegerProperty year = new SimpleIntegerProperty(0);
	private final StringProperty quater = new SimpleStringProperty("");
	private final StringProperty name = new SimpleStringProperty("");
	private final StringProperty code = new SimpleStringProperty("");
	
	public SaveData() {
		this(0, "", "", "");
	}
	
	public SaveData(int year, String quater, String name, String code) {
		super();
		this.year.set(year);
		this.quater.set(quater);
		this.name.set(name);
		this.code.set(code);
		
		System.out.println(year + " " + quater + " " + name + " " + code);
	}

	public int getYear() {
		return year.get();
	}

	public void setYear(int year) {
		this.year.set(year);
	}

	public String getQuater() {
		return quater.get();
	}

	public void setQuater(String quater) {
		this.quater.set(quater);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getCode() {
		return code.get();
	}

	public void setCode(String code) {
		this.code.set(code);
	}
	
	
	
}
