package KumohTime.Model.TimeTable.SaveData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 저장 데이터를 감싸는 Wrapper 클래스
 * @author Jeongsam Seo
 * @since 2018-08-01
 *
 */
@XmlRootElement(name = "LectureList")
@XmlAccessorType (XmlAccessType.FIELD)
public class SaveDataWrapper {
	
	private ObservableList<SaveData> lectures = FXCollections.observableArrayList();

	@XmlElement(name = "lecture")
	public ObservableList<SaveData> getDatas() {
		return lectures;
	}

	public void setDatas(ObservableList<SaveData> lectures) {
		this.lectures.setAll(lectures);
	}
}
