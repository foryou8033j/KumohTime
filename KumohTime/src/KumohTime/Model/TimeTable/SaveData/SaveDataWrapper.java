package KumohTime.Model.TimeTable.SaveData;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LectureList")
public class SaveDataWrapper {
	
	private List<SaveData> lectures;

	@XmlElement(name = "lecture")
	public List<SaveData> getDatas() {
		return lectures;
	}

	public void setDatas(List<SaveData> lectures) {
		this.lectures = lectures;
	}
}
