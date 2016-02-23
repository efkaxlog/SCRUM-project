package gui;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainPackage.ArduinoConnector;
import sensors.HeatFluxSensor;
import sensors.Sensor;
import sensors.TemperatureSensor;

public class Interface extends Application {

	Scene scene;
	Pane root, sessionPane, historicalPane; // Pane for main tab
	Pane heatFluxPane, extTempPane, intTempPane; // Panes for sub tabs
	static TableView heatFluxTable;
	static TableView extTempTable;
	static TableView intTempTable;
	static TableView historicalTable;
	TabPane mainTabPane, sessionTabPane;
	Tab sessionTab, historicalTab; // Main tabs at the top
	Tab heatFluxTab, extTempTab, intTempTab; // tab to the tabpane with the
												// sessionTab
	Button start, stop, exportBtn;
	static Button clearBtn;
	MenuBar menuBar;

	// have to create two axi for each chart. wtf javafx
	NumberAxis heatFluxTimeAxis, heatFluxDataAxis, intTempTimeAxis,
			intTempDataAxis, extTempTimeAxis, extTempDataAxis;
	LineChart heatFluxChart, intTempChart, extTempChart;

	// Charts data series
	// Heat Flux
	static XYChart.Series heatFluxChartData;
	static XYChart.Series heatFluxSurfaceTempChartData;
	static XYChart.Series heatFluxAirTempChartData;
	// Int temp
	static XYChart.Series intTempSensorAirTempData;
	static XYChart.Series intTempSensorSurfaceTempData;
	// Ext temp
	static XYChart.Series extTempSensorAirTempData;
	static XYChart.Series extTempSensorSurfaceTempData;

	static ArrayList<Sensor> currentSessionSensors;
	static ObservableList<Sensor> heatFluxTableData;
	static ObservableList<Sensor> intTempTableData;
	static ObservableList<Sensor> extTempTableData;
	static ObservableList<Sensor> historicalTableData;

	ArduinoConnector arduino;

	Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

	double sceneWidth = screenSize.getWidth();
	double sceneHeight = screenSize.getHeight();
	double minSceneWidth = 1280;
	double minSceneHeight = 600;

	boolean isSessionRunning;
	static double lastTimeHftAdded = System.currentTimeMillis();

	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("Sensor Reading App");
		stage.setFullScreen(true);
		stage.setMinWidth(minSceneWidth);
		stage.setMinHeight(minSceneHeight);

		root = new Pane();
		scene = new Scene(root, sceneWidth, sceneHeight, Color.WHEAT);

		mainTabPane = new TabPane();

		mainTabPane.setPrefWidth(scene.getWidth());

		root.getChildren().add(mainTabPane);

		// Session tab
		sessionTab = new Tab();
		sessionTab.setText("Session");
		sessionTab.setClosable(false);
		mainTabPane.getTabs().add(sessionTab);

		// SessionPane held within the session tab
		sessionPane = new Pane(); // within the sessionTab
		sessionTab.setContent(sessionPane);

		// Historical Data tab
		historicalTab = new Tab();
		historicalTab.setText("Historical Data");
		historicalTab.setClosable(false);
		mainTabPane.getTabs().add(historicalTab);

		historicalPane = new Pane();
		historicalTab.setContent(historicalPane);

		// <--Objects within the Session tab--

		// Start Button
		start = new Button();
		start.setText("Start");
		start.setLayoutX(20);
		start.setLayoutY(10);
		start.setPrefWidth(100);
		sessionPane.getChildren().add(start);
		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				 if (!isSessionRunning) {
				 arduino.run();
				 start.setDisable(true);
				 stop.setDisable(false);
				 isSessionRunning = true;
				 System.out.println("Arduino started");
				 }
//				HeatFluxSensor hfs = new HeatFluxSensor(001, "heatflux", "HFT",
//						20, 30, 40);
//				TemperatureSensor intTempS = new TemperatureSensor(002,
//						"Int Temp", "temp", 50, 60);
//				TemperatureSensor extTempS = new TemperatureSensor(003,
//						"Ext Temp", "temp", 80, 90);
//				hfs.setTimestamp(mainPackage.Utilities.getCurrentTimestamp());
//				intTempS.setTimestamp(mainPackage.Utilities
//						.getCurrentTimestamp());
//				extTempS.setTimestamp(mainPackage.Utilities
//						.getCurrentTimestamp());
//				addSensor(hfs);
//				addSensor(extTempS);
//				addSensor(intTempS);
//
//			}
			}
		});

		// Stop Button
		stop = new Button();
		stop.setText("Stop");
		stop.setLayoutX(150);
		stop.setLayoutY(10);
		stop.setDisable(true);

		stop.setPrefWidth(100);
		sessionPane.getChildren().add(stop);
		stop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (isSessionRunning) {
					arduino.close();
					stop.setDisable(true);
					start.setDisable(false);
					isSessionRunning = false;
					System.out.println("Stop executed");
				}
			}
		});

		heatFluxTable = createSensorTable("HFT");
		extTempTable = createSensorTable("Ext Temp");
		intTempTable = createSensorTable("Int Temp");

		heatFluxPane = new FlowPane();
		heatFluxPane.setPadding(new Insets(10, 10, 10, 10));
		extTempPane = new FlowPane();
		extTempPane.setPadding(new Insets(10, 10, 10, 10));
		intTempPane = new FlowPane();
		intTempPane.setPadding(new Insets(10, 10, 10, 10));

		heatFluxPane.getChildren().add(heatFluxTable);
		extTempPane.getChildren().add(extTempTable);
		intTempPane.getChildren().add(intTempTable);

		sessionTabPane = new TabPane();
		sessionTabPane.setLayoutY(50);
		sessionTabPane.setPrefWidth(scene.getWidth());

		sessionPane.getChildren().add(sessionTabPane);

		// Sensor tabs
		heatFluxTab = new Tab();
		heatFluxTab.setText("Heat Flux Sensors");
		heatFluxTab.setClosable(false);
		heatFluxTab.setContent(heatFluxPane);

		extTempTab = new Tab();
		extTempTab.setText("External Temperature Sensors");
		extTempTab.setClosable(false);
		extTempTab.setContent(extTempPane);

		intTempTab = new Tab();
		intTempTab.setText("Internal Temperature Sensors");
		intTempTab.setClosable(false);
		intTempTab.setContent(intTempPane);

		sessionTabPane.getTabs().addAll(heatFluxTab, extTempTab, intTempTab);

		createAndPlaceCharts();

		heatFluxTableData = FXCollections.observableArrayList();
		intTempTableData = FXCollections.observableArrayList();
		extTempTableData = FXCollections.observableArrayList();

		currentSessionSensors = new ArrayList<Sensor>();

		// <---Code within the historical data tab--->
		historicalTable = createSensorTable("HFT");
		historicalTable.setLayoutX(20);
		historicalTable.setLayoutY(20);

		historicalTable = createSensorTable("HFT");
		historicalTableData = FXCollections.observableArrayList();
		historicalTable.setItems(historicalTableData);
		historicalTable.setLayoutX(20);
		historicalTable.setLayoutY(20);

		historicalPane.setPadding(new Insets(10, 10, 10, 10));
		exportBtn = new Button("Export to CSV");
		exportBtn.setLayoutX(720);
		exportBtn.setLayoutY(20);
		exportBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				fc.setTitle("Export to CSV");
				File file = fc.showSaveDialog(stage);
				if (file != null) {
					try {
						mainPackage.Utilities.exportToCsv(
								currentSessionSensors, file);
					} catch (Exception e) {
						System.out.println("Failed to export to CSV");
						e.printStackTrace();
					}
				}
			}
		});
		clearBtn = new Button("Clear Data");
		clearBtn.setLayoutX(720);
		clearBtn.setLayoutY(55);
		clearBtn.setPrefWidth(104);
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				heatFluxTableData.clear();
				extTempTableData.clear();
				intTempTableData.clear();
				historicalTableData.clear();

				// autosize() because clear() doesn't refresh the chart. Bug?
				heatFluxChart.getData().clear();
				heatFluxChart.autosize();
				extTempChart.getData().clear();
				extTempChart.autosize();
				intTempChart.getData().clear();
				intTempChart.autosize();

				exportBtn.setDisable(true);
				clearBtn.setDisable(true);

			}
		});

		historicalPane.getChildren().addAll(historicalTable, exportBtn,
				clearBtn);

		// Table scaling
		heatFluxTable.setMaxHeight(sceneHeight - sessionTabPane.getLayoutY()
				- sessionTabPane.getPrefHeight() - 20);
		intTempTable.setMaxHeight(sceneHeight - sessionTabPane.getLayoutY()
				- sessionTabPane.getPrefHeight() - 20);
		extTempTable.setMaxHeight(sceneHeight - sessionTabPane.getLayoutY()
				- sessionTabPane.getPrefHeight() - 20);
		historicalTable.setMaxHeight(sceneHeight - mainTabPane.getHeight());

		mainTabPane.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> ov,
							Number oldValue, Number newValue) {
						if (currentSessionSensors.isEmpty() == false) {
							exportBtn.setDisable(false);
							clearBtn.setDisable(false);
						} else {
							exportBtn.setDisable(true);
							clearBtn.setDisable(true);
						}
					}
				});

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * adds sensor to tables and to session sensors arraylist and adds data on
	 * chart
	 * 
	 * @return
	 */
	public static Runnable addSensor(Sensor sensor) {
		double graphTime = (System.currentTimeMillis() - lastTimeHftAdded) / 1000 / 3600;
		if (sensor.getSensorType().equals("HFT")) {
			// update table
			heatFluxTableData.add(sensor);
			heatFluxTable.setItems(heatFluxTableData);

			HeatFluxSensor s = (HeatFluxSensor) sensor;
			heatFluxChartData.getData().add(
					new XYChart.Data(graphTime, s.getHeatFluxData()));
			heatFluxAirTempChartData.getData().add(
					new XYChart.Data(graphTime, s.getAirTemp()));
			heatFluxSurfaceTempChartData.getData().add(
					new XYChart.Data(graphTime, s.getSurfaceTemp()));
		} else if (sensor.getSensorName().equals("Int Temp")) {
			// update table
			intTempTableData.add(sensor);
			intTempTable.setItems(intTempTableData);
			// update chart
			TemperatureSensor s = (TemperatureSensor) sensor;
			intTempSensorAirTempData.getData().add(
					new XYChart.Data(graphTime, s.getAirTemp()));
			intTempSensorSurfaceTempData.getData().add(
					new XYChart.Data(graphTime, s.getSurfaceTemp()));
		} else if (sensor.getSensorName().equals("Ext Temp")) {
			extTempTableData.add(sensor);
			extTempTable.setItems(extTempTableData);
			// update chart
			TemperatureSensor s = (TemperatureSensor) sensor;
			extTempSensorAirTempData.getData().add(
					new XYChart.Data(graphTime, s.getAirTemp()));
			extTempSensorSurfaceTempData.getData().add(
					new XYChart.Data(graphTime, s.getSurfaceTemp()));

		}
		historicalTableData.add(sensor);
		currentSessionSensors.add(sensor);
		clearBtn.setDisable(true);
		return null;
	}

	public void createAndPlaceCharts() {
		heatFluxTimeAxis = new NumberAxis();
		heatFluxDataAxis = new NumberAxis();
		heatFluxTimeAxis.setLabel("Time (Minutes and Seconds)");
		heatFluxDataAxis.setLabel("Temperature (ºC)");

		intTempTimeAxis = new NumberAxis();
		intTempDataAxis = new NumberAxis();
		intTempTimeAxis.setLabel("Time (Minutes and Seconds)");
		intTempDataAxis.setLabel("Temperature (ºC)");

		extTempTimeAxis = new NumberAxis();
		extTempDataAxis = new NumberAxis();
		extTempTimeAxis.setLabel("Time (Minutes and Seconds)");
		extTempDataAxis.setLabel("Temperature (ºC)");

		// HEAT FLUX
		heatFluxChart = new LineChart<Number, Number>(heatFluxTimeAxis,
				heatFluxDataAxis);
		heatFluxChart.setTitle("Heat Flux Data Sensor Graph");
		heatFluxChartData = new XYChart.Series<>();
		heatFluxChartData.setName("Heat Flux Data");
		heatFluxChart.getData().add(heatFluxChartData);
		heatFluxAirTempChartData = new XYChart.Series<>();
		heatFluxAirTempChartData.setName("Air Temperature");
		heatFluxChart.getData().add(heatFluxAirTempChartData);
		heatFluxSurfaceTempChartData = new XYChart.Series<>();
		heatFluxSurfaceTempChartData.setName("Surface Temperature");
		heatFluxChart.getData().add(heatFluxSurfaceTempChartData);

		// INTERNAL TEMP SENSOR
		intTempChart = new LineChart<Number, Number>(intTempTimeAxis,
				intTempDataAxis);
		intTempChart.setTitle("Internal Temperature Sensor Graph");
		intTempSensorAirTempData = new XYChart.Series<>();
		intTempSensorAirTempData.setName("Air Temperature");
		intTempChart.getData().add(intTempSensorAirTempData);
		intTempSensorSurfaceTempData = new XYChart.Series<>();
		intTempSensorSurfaceTempData.setName("Surface Temperature");
		intTempChart.getData().add(intTempSensorSurfaceTempData);

		// EXTERNAL TEMP SENSOR
		extTempChart = new LineChart<Number, Number>(extTempTimeAxis,
				extTempDataAxis);
		extTempChart.setTitle("External Temperature Sensor Graph");
		extTempSensorAirTempData = new XYChart.Series<>();
		extTempSensorAirTempData.setName("Air Temperature");
		extTempChart.getData().add(extTempSensorAirTempData);
		extTempSensorSurfaceTempData = new XYChart.Series<>();
		extTempSensorSurfaceTempData.setName("Surface Temperature");
		extTempChart.getData().add(extTempSensorSurfaceTempData);

		heatFluxPane.getChildren().add(heatFluxChart);
		intTempPane.getChildren().add(intTempChart);
		extTempPane.getChildren().add(extTempChart);
	}

	public TableView createSensorTable(String sensorType) {
		TableView<Sensor> table = new TableView<Sensor>();
		TableColumn sensorIDColumn = new TableColumn("ID");
		sensorIDColumn.setPrefWidth(50);
		sensorIDColumn
				.setCellValueFactory(new PropertyValueFactory<Sensor, String>(
						"sensorID"));
		TableColumn sensorNameColumn = new TableColumn("Name");
		sensorNameColumn
				.setCellValueFactory(new PropertyValueFactory<Sensor, String>(
						"sensorName"));
		TableColumn sensorTimestampColumn = new TableColumn("Date/Time");
		sensorTimestampColumn.setPrefWidth(100);
		sensorTimestampColumn
				.setCellValueFactory(new PropertyValueFactory<Sensor, String>(
						"timestamp"));
		TableColumn sensorAirTempColumn = new TableColumn("Air temperature");
		sensorAirTempColumn.setPrefWidth(140);
		sensorAirTempColumn
				.setCellValueFactory(new PropertyValueFactory<Sensor, String>(
						"airTemp"));
		TableColumn sensorSurfaceTempColumn = new TableColumn(
				"Surface temperature");
		sensorSurfaceTempColumn.setPrefWidth(170);
		sensorSurfaceTempColumn
				.setCellValueFactory(new PropertyValueFactory<Sensor, String>(
						"surfaceTemp"));
		table.getColumns().addAll(sensorIDColumn, sensorNameColumn,
				sensorTimestampColumn, sensorAirTempColumn,
				sensorSurfaceTempColumn);
		if (sensorType.equals("HFT")) {
			TableColumn sensorHeatFluxDataColumn = new TableColumn(
					"Heat Flux data");
			sensorHeatFluxDataColumn.setPrefWidth(130);
			sensorHeatFluxDataColumn
					.setCellValueFactory(new PropertyValueFactory<Sensor, String>(
							"heatFluxData"));
			table.getColumns().add(sensorHeatFluxDataColumn);
		}

		return table;
	}

}