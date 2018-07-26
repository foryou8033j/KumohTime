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

public class TimeTableData {

	private ObservableList<Lecture> listLecture = FXCollections.observableArrayList();
	private ObservableList<Lecture> filteredLecture = FXCollections.observableArrayList();
	private ObservableList<Lecture> selectedLecture = FXCollections.observableArrayList();

	private ObservableList<String> filterQuater = FXCollections.observableArrayList();
	private ObservableList<String> filterMajor = FXCollections.observableArrayList();
	private ObservableList<String> filterType = FXCollections.observableArrayList();
	private ObservableList<String> filterEssential = FXCollections.observableArrayList();
	private ObservableList<String> filterGrade = FXCollections.observableArrayList();

	public TimeTableData() {

		listLecture = new SQLite().loadDataFromFile();
		filteredLecture.setAll(listLecture);

		filterQuater.add("전체");

		filterType.add("전체");

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

		filterGrade.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (Integer.valueOf(o1) > Integer.valueOf(o2))
					return 1;
				else
					return 0;
			}
		});
		filterGrade.add(0, "전체");

		filterMajor.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return OrderingByKoreanEnglishNumbuerSpecial.compare(o1, o2);
			}
		});

		filterMajor.add(0, "전체");

	}

	public void resetFilter() {
		filteredLecture.clear();
		filteredLecture.setAll(listLecture);
	}

	public void doFilter(String quater, String grade, String essential, String type, String major, String code,
			String name) {

		filteredLecture.clear();

		// 널 값 제거
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

		for (Lecture v : listLecture) {

			if (v.isFilter(quater, grade, essential, type, major, code, name)) {
				v.toString();
				filteredLecture.add(v);
			}

		}
	}

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
