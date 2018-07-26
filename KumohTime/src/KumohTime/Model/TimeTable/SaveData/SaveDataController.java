package KumohTime.Model.TimeTable.SaveData;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import KumohTime.Model.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaveDataController {

	private File file;
	private ObservableList<SaveData> saveDatas = FXCollections.observableArrayList();

	public SaveDataController() {
		file = new File(AppData.saveFilePath);
		if(!file.exists())
			saveData();
		else
			loadData();
	}

	public ObservableList<SaveData> getSaveDatas() {
		return saveDatas;
	}

	public void loadData() {
		try {
			
			JAXBContext context = JAXBContext.newInstance(SaveDataWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// 파일로부터 XML을 읽은 다음 역 마샬링한다.
			SaveDataWrapper wrapper = (SaveDataWrapper) um.unmarshal(file);

			saveDatas.setAll(wrapper.getDatas());

		} catch (Exception e) { // 예외를 잡는다
			e.printStackTrace();
		}
	}

	public void saveData() {
		try {
			JAXBContext context = JAXBContext.newInstance(SaveDataWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// 연락처 데이터를 감싼다.
			SaveDataWrapper wrapper = new SaveDataWrapper();
			wrapper.setDatas(saveDatas);

			// 마샬링 후 XML을 파일에 저장한다.
			m.marshal(wrapper, file);

		} catch (Exception e) { // 예외를 잡는다.
			e.printStackTrace();
		}
	}

}
