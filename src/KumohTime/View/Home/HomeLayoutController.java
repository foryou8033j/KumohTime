package KumohTime.View.Home;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import KumohTime.MainApp;
import KumohTime.Model.AppData;
import KumohTime.Model.DataBase.DataBase;
import KumohTime.Model.TimeTable.Lecture;
import KumohTime.Model.TimeTable.LectureTime;
import KumohTime.Model.TimeTable.SaveData.SaveData;
import KumohTime.Util.Browser;
import KumohTime.Util.Dialog.AlertDialog;
import KumohTime.Util.Dialog.BugReportDialog;
import KumohTime.Util.Dialog.TempLectureAddDialog;
import KumohTime.View.Home.SelectedLecture.SelectedLectureLayoutController;
import KumohTime.View.SugangMode.SugangModeDialog;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * Home Layout Controller
 * @author Jeongsam Seo
 * @since 2018-07-28
 */
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
	
    @FXML
    private JFXToggleButton sugangpackmode;
	
    @FXML
    private BorderPane capturePane;

    // 선택된 강의목록을 보여주기위한 GridPane ObservableList
	private ObservableList<GridPane> selectedLayoutList = FXCollections.observableArrayList();
	private ObservableList<SelectedLectureLayoutController> selectedLayoutControllerList = FXCollections.observableArrayList(); //선택 된 강의 목록 Controller

	private ObservableList<VBox> showedNode = FXCollections.observableArrayList();
	
	private SugangModeDialog sugangModeDialog = null;	// 수강신청 모드 Dailog
	
    @FXML
    void handleMakeLecture(ActionEvent event) {
    	new TempLectureAddDialog(mainApp);
    }

    @FXML
    void hadleMenubarDataSave(ActionEvent event) {
    	handleSaveData(null);
    }
    
    @FXML
    void handleMenubarDataLoad(ActionEvent event) {
    	handleLoadData(null);
    }

    @FXML
    void handleMenubarSave(ActionEvent event) {
    	
    	mainApp.getAppData().getSaveDataController().saveData();
    	new AlertDialog(mainApp, "성공!", "시간표 목록 저장 완료\n사실, 프로그램이 정상적으로 종료되면 자동으로 저장됩니다 ㅎㅎ", "확인");
    }

    @FXML
    void handleUpdateLog(ActionEvent event) {
    	mainApp.showUpdateLog();
    }

    @FXML
    void handleAbout(ActionEvent event) {
    	new AlertDialog(mainApp, "금오공대 시간표 생성기 KumohTime " + mainApp.getAppData().getAppPropertise().getVersion() + "v", ""
    			+ "이 프로그램은 금오공과대학교 커뮤니티 우리사이, 금오사이에서만 배포가 가능합니다.\n"
    			+ "허가받지 않은 2차 배포는 불이익이 발생 할 수 있습니다.\n\n"
    			+ "Contact : foryou8033j@gmail.com\n"
    			+ "Copyright(c)2018 서정삼 All rights reserved Exclude course information\n"
    			+ "금오공과대학교 컴퓨터공학과", "확인");
    }
	
	@FXML
	void handleAdd(ActionEvent event) {
		try {
			Lecture v = table.getSelectionModel().getSelectedItem().getValue();
			if (v.isSelectAble.get()) {

				v.isSelected.set(true);
				v.isSelectAble.set(false);
				mainApp.getAppData().getTimeTableData().getSelectedLecture().add(v);
				mainApp.getAppData().getTimeTableData().disableSimilarLecture(v);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@FXML
	void handleAutomaticCreateTimeTable(ActionEvent event) {
		
	}

	@FXML
	void handleLoadData(ActionEvent event) {
		
		mainApp.saveSelectedLecture();

		FileChooser fileChooser = new FileChooser();

		// 확장자 필터를 설정한다.
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Data files (*.dat)", "*.dat");
		fileChooser.getExtensionFilters().add(extFilter);

		// Save File Dialog를 보여준다.
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		if (file != null) {
			try {
				
				File saveFilePath = new File(AppData.saveFilePath);
				saveFilePath.delete();
				
				Files.copy(new File(file.getPath()).toPath(), saveFilePath.toPath());

				mainApp.getAppData().getTimeTableData().getSelectedLecture().clear();
				loadFromSaveFile();
				
				new AlertDialog(mainApp, "성공!", "파일을 성공적으로 불러왔습니다.", "확인");
				mainApp.saveSelectedLecture();
			} catch (IOException e) {
				new AlertDialog(mainApp, "오류", "파일을 불러오는 도중에 오류가 발생하였습니다.", "확인");
				e.printStackTrace();
			}
		}

	}

	@FXML
	void handleRemove(ActionEvent event) {

		try {
			GridPane v = addedList.selectionModelProperty().get().getSelectedItem();
			Lecture lecture = mainApp.getAppData().getTimeTableData().getSelectedLecture()
					.get(selectedLayoutList.indexOf(v));

			lecture.isSelected.set(false);
			mainApp.getAppData().getTimeTableData().getSelectedLecture().remove(lecture);
			mainApp.getAppData().getTimeTableData().enableSimilarLecture(lecture);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	

    @FXML
    void handleOpenGithub(ActionEvent event) {
    	Browser.open("https://github.com/foryou8033j/KumohTime");
    }

	@FXML
	void handleBugReport(ActionEvent event) {
		
		Browser.open("https://kumoh42.com/help");
		
		/*
		if (!DataBase.isOfflineMode)
			new BugReportDialog(mainApp);
		else
			new AlertDialog(mainApp, "알림", "오프라인모드에서는 사용 할 수 없습니다.", "확인");
		*/
	}

	@FXML
	void handleSaveData(ActionEvent event) {
		
		mainApp.saveSelectedLecture();

		FileChooser fileChooser = new FileChooser();

		// 확장자 필터를 설정한다.
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Data files (*.dat)", "*.dat");
		fileChooser.getExtensionFilters().add(extFilter);

		// Save File Dialog를 보여준다.
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {

			// 정확한 확장자를 가져야 한다.
			if (file.getPath().endsWith(".dat")) {
				try {
					File targetFile = new File(file.getPath());
					targetFile.delete();
					Files.copy(new File(AppData.saveFilePath).toPath(), targetFile.toPath());
					new AlertDialog(mainApp, "성공!", "파일을 성공적으로 저장하였습니다.", "확인");

				} catch (IOException e) {
					new AlertDialog(mainApp, "오류", "파일을 내보내는 도중에 오류가 발생하였습니다.", "확인");
					e.printStackTrace();
				}
			}

		}

	}

	@FXML
	void handleSaveTimeTable(ActionEvent event) {

		

		try {
			
			WritableImage writableImage = capturePane.snapshot(new SnapshotParameters(), null);
			
			FileChooser fileChooser = new FileChooser();

			// 확장자 필터를 설정한다.
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png", "*.PNG");
			fileChooser.getExtensionFilters().add(extFilter);

			// Save File Dialog를 보여준다.
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

			if (file != null) {

				// 정확한 확장자를 가져야 한다.
				if (file.getPath().endsWith(".png") || file.getPath().endsWith(".PNG")) {
					try {
						
						if(file.exists())
							file.delete();
						
						ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
						
						new AlertDialog(mainApp, "성공!", "이미지를 성공적으로 저장하였습니다.", "확인");

					} catch (Exception e) {
						new AlertDialog(mainApp, "오류", "파일을 저장하는 도중에 오류가 발생하였습니다.", "확인");
						e.printStackTrace();
					}
				}

			}
			
			System.out.println("Captured: " + file.getAbsolutePath());
		} catch (Exception ex) {
			//Logger.getLogger(JavaFXCaptureScreen.class.getName()).log(Level.SEVERE, null, ex);
		}
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
										item.isSelected.set(false);
										mainApp.getAppData().getTimeTableData().getSelectedLecture().remove(item);
										mainApp.getAppData().getTimeTableData().enableSimilarLecture(item);
									} else if (item.isSelectAble.get())
										handleAdd(null);
								}
							});

							if (!item.isSelectAble.get()) {
								setStyle("-fx-background-color:lightgrey");
								if (item.isSelected.get())
									setStyle("-fx-background-color:lightcoral");
							} else {
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

					if (mainApp.getAppData().getTimeTableData().getSelectedLecture().size() == 0) {
						remove.setDisable(true);
					} else {
						remove.setDisable(false);
					}

					while (c.next()) {

						for (Lecture v : c.getAddedSubList()) {
							FXMLLoader loader = new FXMLLoader(
									MainApp.class.getResource("View/Home/SelectedLecture/SelectedLectureLayout.fxml"));
							GridPane pane = loader.load();
							pane.setPrefWidth(addedList.getWidth()-40);
							SelectedLectureLayoutController controller = loader.getController();
							controller.setDefault(mainApp, v, pane, true);

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

					for (Lecture v : mainApp.getAppData().getTimeTableData().getSelectedLecture()) {

						if(v.isTemp.get()) continue;
						
						String type = v.getType().get();
						String essential = v.getEssential().get();
						int point = v.getPoint().get();

						if (type.equals("전공")) {
							if (essential.equals("필수"))
								mE += point;
							else
								mS += point;
						} else if (type.equals("전문교양")) {
							if (essential.equals("필수"))
								rE += point;
							else
								rS += point;
						} else if (type.equals("MSC")) {
							bE += point;
						} else {
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
					sumOfLecture.setText(String.valueOf(mE + mS + rE + rS + bE + eP));

					table.getSelectionModel().clearSelection();
					table.refresh();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		
		addedList.widthProperty().addListener((observable, oldValue, newValue) -> {
			for (GridPane v:selectedLayoutList) {
				v.setPrefWidth(addedList.getWidth()-40);
			}
		});

		loadFromSaveFile();
		
		sugangModeDialog = new SugangModeDialog(mainApp, sugangpackmode);
	}

	private void loadFromSaveFile() {
		
		if (mainApp.getAppData().getSaveDataController().isExists()) {
			
			mainApp.getAppData().getSaveDataController().loadData();
			
			// 저장 된 데이터를 불러 온다.
			for (SaveData data : mainApp.getAppData().getSaveDataController().getSaveDatas()) {
				for (Lecture v : mainApp.getAppData().getTimeTableData().getListLecture()) {
					if (data.getName().equals(v.getName().get()) && data.getCode().equals(v.getCode().get())
							&& data.getYear() == v.getYear().get() && data.getQuater().equals(v.getQuarter().get())) {
						v.setColor(Color.color(data.getRed(), data.getGreen(), data.getBlue()));
						v.isSelected.set(true);
						mainApp.getAppData().getTimeTableData().getSelectedLecture().add(v);
						mainApp.getAppData().getTimeTableData().disableSimilarLecture(v);
						
					}
				}
				
				if(data.isTemp()) {
					Lecture lecture = new Lecture(data.getName(), data.getCode(), data.getProfessor(), data.getTime(), Color.color(data.getRed(), data.getGreen(), data.getBlue()));
					mainApp.getAppData().getTimeTableData().getSelectedLecture().add(lecture);
					mainApp.getAppData().getTimeTableData().disableSimilarLecture(lecture);
				}
			}
			table.refresh();
			table.getSelectionModel().clearSelection();
			
			System.out.print("Save File Loaded");
		}else {
			System.out.print("Save File doesn't exists");
		}
	}

	private void doFilter() {
		mainApp.getAppData().getTimeTableData().doFilter(filterQuarter.getValue(), filterGrade.getValue(),
				filterEssential.getValue(), filterType.getValue(), filterMajor.getValue(), filterCode.getText(),
				filterName.getText());
	}

	protected void redrawTimeTable() {

		timeTable.getChildren().removeAll(showedNode);
		showedNode.clear();

		for (Lecture v : mainApp.getAppData().getTimeTableData().getSelectedLecture()) {

			double r = v.getColor().getRed() * 255;
			double g = v.getColor().getGreen() * 255;
			double b = v.getColor().getBlue() * 255;

			for (LectureTime time : v.getLectureTime()) {
				boolean isDrawed = false;
				for (Number timeOfDay : time.getTime()) {
					VBox vb = new VBox();
					vb.setAlignment(Pos.CENTER);
					vb.setStyle("-fx-background-color:rgb(" + r + "," + g + ", " + b + ")");
					Text name = new Text(v.getName().get());
					name.setFont(Font.font("malgun gothic", 12));
					Text professor = new Text(v.getProfessor().get());
					professor.setFont(Font.font("malgun gothic", 12));
					Text room = new Text(time.getRoom());
					room.setFont(Font.font("malgun gothic", 12));

					if (v.getName().get().length() > 8) {
						int parseIndex = name.getText().length() / 2;
						if (name.getText().contains(" ") && name.getText().indexOf(" ") > parseIndex / 2)
							parseIndex = name.getText().indexOf(' ');
						else if (name.getText().contains("과") && name.getText().indexOf("과") > parseIndex / 2)
							parseIndex = name.getText().indexOf('과');
						else if (name.getText().contains("와") && name.getText().indexOf("와") > parseIndex / 2)
							parseIndex = name.getText().indexOf('와');
						else if (name.getText().contains("의") && name.getText().indexOf("의") > parseIndex / 2)
							parseIndex = name.getText().indexOf('의');
						else if (name.getText().contains("및") && name.getText().indexOf("및") > parseIndex / 2)
							parseIndex = name.getText().indexOf('및');

						String first = name.getText().substring(0, parseIndex + 1);
						String last = name.getText().substring(parseIndex + 1, name.getText().length());
						String txt = first + "\r\n" + last;

						name.setTextAlignment(TextAlignment.CENTER);
						name.setText(txt);

						if (first.length() > 6 || last.length() > 8) {
							name.setFont(Font.font("malgun gothic", 10));
							professor.setFont(Font.font("malgun gothic", 10));
							room.setFont(Font.font("malgun gothic", 10));
						}
							
					}

					
					
					if (!isDrawed)
						vb.getChildren().addAll(name, professor, room);
					
					timeTable.add(vb, time.dayOfWeek() + 1, timeOfDay.intValue());
					showedNode.add(vb);
					isDrawed = true;
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
			table.refresh();
			table.getSelectionModel().clearSelection();
		});

		filterQuarter.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
			table.refresh();
			table.getSelectionModel().clearSelection();
		});

		filterGrade.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
			table.refresh();
			table.getSelectionModel().clearSelection();
		});

		filterEssential.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
			table.refresh();
			table.getSelectionModel().clearSelection();
		});

		filterType.valueProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
			table.refresh();
			table.getSelectionModel().clearSelection();
		});

		filterCode.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
			table.refresh();
			table.getSelectionModel().clearSelection();
		});

		filterName.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setSelected(true);
			doFilter();
			table.refresh();
			table.getSelectionModel().clearSelection();
		});

		filter.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (filter.isSelected()) {
				doFilter();
				table.refresh();
				table.getSelectionModel().clearSelection();
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
		
		sugangpackmode.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.booleanValue()) {
				mainApp.getPrimaryStage().close();
				sugangModeDialog.show();
			}else {
				 mainApp.getPrimaryStage().show();
			}
		});

	}

}
