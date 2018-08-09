package KumohTime.Model;

import java.util.Comparator;

import KumohTime.MainApp;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.DataBase.SQLite;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.LectureTime;
import KumohTime.Util.OrderingByKoreanEnglishNumbuerSpecial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 강의 정보를 전체적으로 관리한다.
 * @author Jeongsam Seo
 *
 */
public class TimeTableData {

	private ObservableList<Lecture> listLecture = FXCollections.observableArrayList();		//전체 리스트
	private ObservableList<Lecture> filteredLecture = FXCollections.observableArrayList();	//필터링된 리스트(실제 시각화되어지는 대상 리스트)
	private ObservableList<Lecture> selectedLecture = FXCollections.observableArrayList();	//선택된 리스트

	private ObservableList<String> filterQuater = FXCollections.observableArrayList();		//필터링 옵션 (학기)
	private ObservableList<String> filterMajor = FXCollections.observableArrayList();		//필터링 옵션 (전공)
	private ObservableList<String> filterType = FXCollections.observableArrayList();		//필터링 옵션 (구분)
	private ObservableList<String> filterEssential = FXCollections.observableArrayList();	//필터링 옵션 (필수여부)
	private ObservableList<String> filterGrade = FXCollections.observableArrayList();		//필터링 옵션 (학년)

	public TimeTableData() {

		//listLecture = new DataBase().loadLectureList();	// Load DataBase From Server
		listLecture = new SQLite().loadDataFromFile();		// Load DataBase From Local DB (SQLite)
		
		filteredLecture.setAll(listLecture);	//최초 필터링 리스트는 전체 강의목록을 보여준다.

		filterQuater.add("전체");		//학기 구분을 위한 전체 필터링 옵션 추가

		filterType.add("전체");	// 구분 항목을 위한 전체 필터링 옵션 추가

		//필터리스트에 필터링 목록을 추가
		for (Lecture v : listLecture) {
			if (!filterQuater.contains(v.getQuarter().get()))
				filterQuater.add(v.getQuarter().get());
			if (!filterMajor.contains(v.getTrace().get()))
				filterMajor.add(v.getTrace().get());
			if (!filterType.contains(v.getType().get()))
				filterType.add(v.getType().get());
			if (!filterEssential.contains(v.getEssential().get()))
				filterEssential.add(v.getEssential().get());
			if (!filterGrade.contains(String.valueOf(v.getGrade().get())))
				filterGrade.add(String.valueOf(v.getGrade().get()));
		}

		//학년을 오름차순으로 정렬
		filterGrade.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (Integer.valueOf(o1) > Integer.valueOf(o2))
					return 1;
				else
					return 0;
			}
		});
		filterGrade.add(0, "전체"); //학년 구분을 위한 전체 필터링 옵션 추가

		//전공을 오름차순으로 정렬
		filterMajor.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return OrderingByKoreanEnglishNumbuerSpecial.compare(o1, o2); //한글 오름차순 정렬
			}
		});

		filterMajor.add(0, "전체");	//전공 구분을 위한 전체 항목 필터링 옵션 추가

	}

	/**
	 * 필터를 리셋한다.
	 */
	public void resetFilter() {
		filteredLecture.clear();
		filteredLecture.setAll(listLecture);
	}

	/**
	 * 필터링 옵션을 검사하고 필터 리스트에 반영한다.
	 * @param quater	학기
	 * @param grade		학년
	 * @param essential	필수여부
	 * @param type		구분
	 * @param major		전공
	 * @param code		교과목코드
	 * @param name		전공명
	 */
	public void doFilter(String quater, String grade, String essential, String type, String major, String code, String name) {

		//필터리스트 제거
		filteredLecture.clear();

		// NULL 값 제거
		if (quater == null)
			quater = "";
		if (grade == null)
			grade = "";
		if (essential == null)
			essential = "";
		if (type == null)
			type = "";
		if (major == null)
			major = "";
		if (code == null)
			code = "";
		if (name == null)
			name = "";

		//전체 강의리스트에서 필터링 옵션에 맞은 강의리스트를 필터리스트레 추가
		for (Lecture v : listLecture) {

			if (v.isFilter(quater, grade, essential, type, major, code, name)) 
				filteredLecture.add(v);
		}
	}

	/**
	 * 강의시간 또는 교과목코드가 겹치는 강의를 선택할 수 없도록 비활성화
	 * @param lecture
	 */
	public void disableSimilarLecture(Lecture lecture) {

		for (Lecture v : listLecture) {

			String currentLectureCode = lecture.getCode().get().substring(0, lecture.getCode().get().indexOf("-"));
			String targetLectureCode = v.getCode().get().substring(0, v.getCode().get().indexOf("-"));

			if (currentLectureCode.equals(targetLectureCode))
				v.isSelectAble.set(false);

			for (LectureTime lecTime : lecture.getLectureTime()) {
				if (lecTime.isConflict(v))
					v.isSelectAble.set(false);
			}
		}

	}

	/**
	 * 대상 과목을 선택가능하게하면서 선택 가능 조건을 다시 설정
	 * @param lecture
	 */
	public void enableSimilarLecture(Lecture lecture) {

		lecture.isSelectAble.set(true);

		for (Lecture v : listLecture) {
			v.isSelectAble.set(true);
		}

		for (Lecture sv : selectedLecture) {
			disableSimilarLecture(sv);
		}

	}

	public ObservableList<Lecture> getListLecture() {
		return listLecture;
	}

	public void setListLecture(ObservableList<Lecture> listLecture) {
		this.listLecture = listLecture;
	}

	public ObservableList<Lecture> getFilteredLecture() {
		return filteredLecture;
	}

	public void setFilteredLecture(ObservableList<Lecture> filteredLecture) {
		this.filteredLecture = filteredLecture;
	}

	public ObservableList<Lecture> getSelectedLecture() {
		return selectedLecture;
	}

	public void setSelectedLecture(ObservableList<Lecture> selectedLecture) {
		this.selectedLecture = selectedLecture;
	}

	public ObservableList<String> getFilterQuater() {
		return filterQuater;
	}

	public ObservableList<String> getFilterMajor() {
		return filterMajor;
	}

	public ObservableList<String> getFilterType() {
		return filterType;
	}

	public ObservableList<String> getFilterEssential() {
		return filterEssential;
	}

	public ObservableList<String> getFilterGrade() {
		return filterGrade;
	}

}
