package KumohTime.Model.TimeTable.SaveData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import KumohTime.Model.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 선택 강의 목록 저장을 위한 Controller
 * @author Jeongsam Seo
 * @since 2018-08-01
 */
public class SaveDataController {

	private File file;
	private ObservableList<SaveData> saveDatas = FXCollections.observableArrayList();

	/**
	 * 현 객체를 초기화하며 이미 존재하는 데이터가 있을경우 불러온다.
	 */
	public SaveDataController() {
		
		// 0.92 버전 이상 Installer 대응, 기존에 Portable 버전이용자의 데이터의 존재 유무를 확인한다.
		File oldFile = new File(System.getenv("APPDATA") + "/kumohtime/data/savefile.dat");
		file = new File(AppData.saveFilePath);
		
		// 이전 데이터가 존재 할 경우
		if(oldFile.exists()) {
			try {
				file.delete();
				Files.copy(oldFile.toPath(), file.toPath());	//파일을 복사 해 온다.
				oldFile.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 저장된 수강목록 불러오기
		if(!isExists()) {
			saveData();
		}
		else
			loadData();
	}

	public ObservableList<SaveData> getSaveDatas() {
		return saveDatas;
	}
	
	public boolean isExists() {
		return file.exists();
	}

	public void loadData() {
		try {
			
			JAXBContext context = JAXBContext.newInstance(SaveDataWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			SaveDataWrapper wrapper = (SaveDataWrapper) um.unmarshal(file);

			saveDatas.setAll(wrapper.getDatas());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveData() {
		try {
			JAXBContext context = JAXBContext.newInstance(SaveDataWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			SaveDataWrapper wrapper = new SaveDataWrapper();
			wrapper.setDatas(saveDatas);

			m.marshal(wrapper, file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
