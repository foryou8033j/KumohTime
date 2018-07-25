package KumohTime.View.Home;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import KumohTime.MainApp;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.LectureTime;
import KumohTime.View.Home.SelectedLecture.SelectedLectureLayoutController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class HomeLayoutController implements Initializable {

	@FXML
	private JFXToggleButton filter;

	@FXML
	private JFXComboBox<String> filterQuarter;

	@FXML
	private JFXComboBox<String> filterType;

	@FXML
	private JFXComboBox<String> filterGrade;

	@FXML
	private JFXComboBox<String> filterEssential;

	@FXML
	private JFXComboBox<String> filterMajor;

	@FXML
	private JFXTextField filterCode;

	@FXML
	private JFXTextField filterName;

	@FXML
	private JFXTreeTableView<Lecture> table;

	@FXML
	private TreeTableColumn<Lecture, String> typeColumn;

	@FXML
	private TreeTableColumn<Lecture, String> majorColumn;

	@FXML
	private TreeTableColumn<Lecture, String> gradeColumn;

	@FXML
	private TreeTableColumn<Lecture, String> essentialColumn;

	@FXML
	private TreeTableColumn<Lecture, String> nameColumn;

	@FXML
	private TreeTableColumn<Lecture, String> pointColumn;

	@FXML
	private TreeTableColumn<Lecture, String> professorColumn;

	@FXML
	private TreeTableColumn<Lecture, String> codeColumn;

	@FXML
	private TreeTableColumn<Lecture, String> timeColumn;

	@FXML
	private TreeTableColumn<Lecture, String> limitPersonColumn;

	@FXML
	private TreeTableColumn<Lecture, String> packageColumn;

	@FXML
	private JFXButton add;

	@FXML
	private JFXButton remove;

	@FXML
	private JFXListView<GridPane> addedList;

	@FXML
	private GridPane timeTable;

	@FXML
	private Text majorEssential;

	@FXML
	private Text majorSelect;

	@FXML
	private Text refinementEssential;

	@FXML
	private Text refinementSelect;

	@FXML
	private Text basicEssential;
	
	@FXML
	private Text etcPoint;

	@FXML
	private Text sumOfLecture;

	private ObservableList<GridPane> selectedLayoutList = FXCollections.observableArrayList();
	private ObservableList<SelectedLectureLayoutController> selectedLayoutControllerList = FXCollections
			.observableArrayList();
	
	private ObservableList<VBox> showedNode = FXCollections.observableArrayList();

	@FXML
	void handleAdd(ActionEvent event) {
		try {
			Lecture v = table.getSelectionModel().getSelectedItem().getValue();
			if (v.isSelectAble.get()) {
				mainApp.getAppData().getTimeTableData().disableSimilarLecture(v);
				mainApp.getAppData().getTimeTableData().getSelectedLecture().add(v);
			}
		} catch (Exception e) {
			return;
		}
	}

	@FXML
	void handleAutomaticCreateTimeTable(ActionEvent event) {

	}

	@FXML
	void handleLoadData(ActionEvent event) {

	}

	@FXML
	void handleRemove(ActionEvent event) {
		
		try {
			GridPane v = addedList.selectionModelProperty().get().getSelectedItem();
			Lecture lecture = mainApp.getAppData().getTimeTableData().getSelectedLecture()
					.get(selectedLayoutList.indexOf(v));
			mainApp.getAppData().getTimeTableData().enableSimilarLecture(lecture);
			mainApp.getAppData().getTimeTableData().getSelectedLecture().remove(lecture);
			
		}catch (Exception e) {
			return;
		}
	}

	@FXML
	void handleSaveData(ActionEvent event) {

	}

	@FXML
	void handleSaveTimeTable(ActionEvent event) {

	}

	@FXML
	void handleTimeTableClicked(MouseEvent event) {

	}

	@FXML
	void handleTimeTableContextMenuRequested(ContextMenuEvent event) {

	}

	private void columnClickHandler(TreeTableColumn<Lecture, String> column) {

		column.setCellFactory(new Callback<TreeTableColumn<Lecture, String>, TreeTableCell<Lecture, String>>() {
			@Override
			public TreeTableCell<Lecture, String> call(TreeTableColumn<Lecture, String> param) {

				TreeTableCell<Lecture, String> cell = new TreeTableCell<Lecture, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						setAlignment(Pos.CENTER);
						setText(empty ? null : getString());
						setGraphic(null);
					}

					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}

				};

				cell.setStyle("-fx-alignment: CENTER;");

				return cell;
			}
		});

		table.setRowFactory(new Callback<TreeTableView<Lecture>, TreeTableRow<Lecture>>() {

			public TreeTableRow<Lecture> call(TreeTableView<Lecture> param) {
				return new TreeTableRow<Lecture>() {
					@Override
					protected void updateItem(Lecture item, boolean empty) {

						super.updateItem(item, empty);

						if (item != null) {

							setOnMouseClicked(e -> {
								if (e.getClickCount() == 2) {
									if (item.isSelected.get()) {
										Lecture lecture = table.getSelectionModel().getSelectedItem().getValue();
										mainApp.getAppData().getTimeTableData().enableSimilarLecture(lecture);
										mainApp.getAppData().getTimeTableData().getSelectedLecture().remove(lecture);
									} else if (item.isSelectAble.get())
										handleAdd(null);
								}
							});

							if (!item.isSelectAble.get()) {
								setStyle("-fx-background-color:lightgrey");
								if (item.isSelected.get())
									setStyle("-fx-background-color:lightcoral");
							}

							else {
								setDisable(false);
								setStyle(null);
							}
						}

					}

				};
			};

			{

			};
		});

	}

	private MainApp mainApp;

	public void setDefault(MainApp mainApp) {
		this.mainApp = mainApp;

		final TreeItem<Lecture> root = new RecursiveTreeItem<Lecture>(
				mainApp.getAppData().getTimeTableData().getFilteredLecture(), RecursiveTreeObject::getChildren);

		table.setRoot(root);
		table.setShowRoot(false);
		table.setEditable(true);

		columnClickHandler(typeColumn);
		columnClickHandler(majorColumn);
		columnClickHandler(gradeColumn);
		columnClickHandler(essentialColumn);
		columnClickHandler(nameColumn);
		columnClickHandler(pointColumn);
		columnClickHandler(professorColumn);
		columnClickHandler(codeColumn);
		columnClickHandler(timeColumn);
		columnClickHandler(limitPersonColumn);
		columnClickHandler(packageColumn);

		filterMajor.setItems(mainApp.getAppData().getTimeTableData().getFilterMajor());
		filterQuarter.setItems(mainApp.getAppData().getTimeTableData().getFilterQuater());
		filterType.setItems(mainApp.getAppData().getTimeTableData().getFilterType());
		filterGrade.setItems(mainApp.getAppData().getTimeTableData().getFilterGrade());
		filterEssential.setItems(mainApp.getAppData().getTimeTableData().getFilterEssential());

		filter.setSelected(false);

		addedList.setItems(selectedLayoutList);

		mainApp.getAppData().getTimeTableData().getSelectedLecture().addListener(new ListChangeListener<Lecture>() {
			@Override
			public void onChanged(Change<? extends Lecture> c) {

				try {
					
					if(mainApp.getAppData().getTimeTableData().getSelectedLecture().size()==0){
						remove.setDisable(true);
					}else {
						remove.setDisable(false);
					}

					while (c.next()) {

						for (Lecture v : c.getAddedSubList()) {
							FXMLLoader loader = new FXMLLoader(
									MainApp.class.getResource("View/Home/SelectedLecture/SelectedLectureLayout.fxml"));
							GridPane pane = loader.load();
							SelectedLectureLayoutController controller = loader.getController();
							controller.setDefault(mainApp, v, pane);

							selectedLayoutList.add(pane);
							selectedLayoutControllerList.add(controller);
						}

						for (Lecture v : c.getRemoved()) {
							for (int i = 0; i < selectedLayoutControllerList.size(); i++) {
								if (selectedLayoutControllerList.get(i).getLecture().equals(v)) {
									selectedLayoutList.remove(i);
									selectedLayoutControllerList.remove(i);
									break;
								}
							}
						}
					}
					
					int mE = 0;
					int mS = 0;
					int rE = 0;
					int rS = 0;
					int bE = 0;
					int eP = 0;
					
					for(Lecture v:mainApp.getAppData().getTimeTableData().getSelectedLecture()) {
						
						String type = v.getType().get();
						String essential = v.getEssential().get();
						int point = v.getPoint().get();
						
						if(type.equals("전공")) {
							if(essential.equals("필수"))
								mE += point;
							else
								mS += point;
						}else if(type.equals("전문교양")) {
							if(essential.equals("필수"))
								rE += point;
							else
								rS += point;
						}else if(type.equals("MSC")) {
							bE += point;
						}else {
							eP += point;
						}
						
						v.getColorProperty().addListener((observable, oldValue, newValue) -> {
							redrawTimeTable();
						});
						
					}
					
					majorEssential.setText(String.valueOf(mE));
					majorSelect.setText(String.valueOf(mS));
					refinementEssential.setText(String.valueOf(rE));
					refinementSelect.setText(String.valueOf(rS));
					basicEssential.setText(String.valueOf(bE));
					etcPoint.setText(String.valueOf(eP));
					sumOfLecture.setText(String.valueOf(mE+mS+rE+rS+bE+eP));

					table.getSelectionModel().clearSelection();
					table.refresh();
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	private void doFilter() {
		mainApp.getAppData().getTimeTableData().doFilter(filterQuarter.getValue(), filterGrade.getValue(),
				filterEssential.getValue(), filterType.getValue(), filterMajor.getValue(), filterCode.getText(),
				filterName.getText());
	}
	
	protected void redrawTimeTable() {
		
		timeTable.getChildren().removeAll(showedNode);
		showedNode.clear();
		
		for(Lecture v:mainApp.getAppData().getTimeTableData().getSelectedLecture()) {
			
			double r = v.getColor().getRed() * 255;
			double g = v.getColor().getGreen() * 255;
			double b = v.getColor().getBlue() * 255;
			
			for(LectureTime time:v.getLectureTime()) {
				
				for(Number timeOfDay:time.getTime()) {
					VBox vb = new VBox();
					vb.setAlignment(Pos.CENTER);
					vb.setStyle("-fx-background-color:rgb(" + r+ "," + g + ", " + b+ ")");
					Text name = new Text(v.getName().get());
					if(v.getName().get().length() > 6) {
						String first = name.getText().substring(0, name.getText().length()/2);
						String last = name.getText().substring(name.getText().length()/2, name.getText().length());
						String txt = first+"\r\n"+last;
						name.setText(txt);
					}
					Text professor = new Text(v.getProfessor().get());
					Text room = new Text(time.getRoom());
					vb.getChildren().addAll(name, professor, room);
					
					timeTable.add(vb, time.dayOfWeek()+1, timeOfDay.intValue());
					showedNode.add(vb);
				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		typeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getType());
		majorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getTrace());
		gradeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				String.valueOf(cellData.getValue().getValue().getGrade().intValue())));
		essentialColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getEssential());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getName());
		pointColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				String.valueOf(cellData.getValue().getValue().getPoint().intValue())));
		professorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getProfessor());
		codeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getCode());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getTime());
		limitPersonColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				String.valueOf(cellData.getValue().getValue().getLimitPerson().intValue())));
		packageColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getLecPackage());

		filterMajor.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
		});

		filterQuarter.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
		});

		filterGrade.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
		});

		filterEssential.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
		});

		filterType.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
		});

		filterCode.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
		});

		filterName.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
		});

		filter.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (filter.isSelected()) {
				doFilter();
			} else {
				mainApp.getAppData().getTimeTableData().resetFilter();
			}
		});
		
		selectedLayoutList.addListener(new ListChangeListener<GridPane>() {
			@Override
			public void onChanged(Change<? extends GridPane> c) {
				
				redrawTimeTable();
			}
		});

	}

}
