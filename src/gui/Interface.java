package gui;

import java.util.ArrayList;

import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mainPackage.ArduinoConnector;
import sensors.HeatFluxSensor;
import sensors.Sensor;
import sensors.TemperatureSensor;


public class Interface extends Application {
	
	Scene scene;
	Pane root, sessionPane, historicalPane; // Pane for main tab
	Pane heatFluxPane, extTempPane, intTempPane; // Pane for sub tabs
	static TableView heatFluxTable;
	static TableView extTempTable;
	static TableView intTempTable;
	TabPane mainTabPane, sessionTabPane;
	Tab sessionTab, historicalTab; // Main tabs at the top
	Tab heatFluxTab, extTempTab, intTempTab; // tab to the tabpane with the sessionTab
	Button start, stop, search;
	TextField startTextField, endTextField;
	MenuBar menuBar;
	
	
	//have to create two axi for each chart. wtf javafx
	NumberAxis heatFluxTimeAxis, heatFluxDataAxis, intTempTimeAxis, intTempDataAxis, extTempTimeAxis, extTempDataAxis;
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
	
	CheckBox heatFluxCB, heatFluxAirTempCB, heatFluxSurfaceTempCB;
	CheckBox intAirTempCB, intSurfaceTempCB;
	CheckBox extAirTempCB, extSurfaceTempCB;
	
	FlowPane heatFluxCheckBoxesPane, intTempCheckBoxesPane, extTempCheckBoxesPane;
	
	ArduinoConnector arduino;
	
	int sceneWidth = 1250;
	int sceneHeight = 950;
	
	private MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty) {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");

		MenuItem MINew = new MenuItem("New");
		fileMenu.getItems().add(MINew);
		MINew.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("!test");
			}
		});
		return menuBar;
	}
		

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Sensor Reading App");
		root = new Pane();
		scene = new Scene(root, sceneWidth, sceneHeight, Color.WHEAT);
		
		mainTabPane = new TabPane();
		
		mainTabPane.setLayoutY(29);
		mainTabPane.setPrefWidth(scene.getWidth());
		
		menuBar = buildMenuBarWithMenus(stage.widthProperty());
		
		root.getChildren().add(menuBar);
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
				arduino.run();
				System.out.println("Arduino started");		
			}
		});

		// Stop Button
		stop = new Button();
		stop.setText("Stop");
		stop.setLayoutX(150);
		stop.setLayoutY(10);

		stop.setPrefWidth(100);
		sessionPane.getChildren().add(stop);
		stop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				arduino.close();
				System.out.println("Stop executed");
			}
		});
		
		heatFluxTable = createSensorTable("HFT");
		extTempTable = createSensorTable("Ext Temp");
		intTempTable = createSensorTable("Int Temp");
		
		heatFluxPane = new FlowPane();
		extTempPane = new FlowPane();
		intTempPane = new FlowPane();
		
		heatFluxPane.getChildren().add(heatFluxTable);
		extTempPane.getChildren().add(extTempTable);
		intTempPane.getChildren().add(intTempTable);
		
		sessionTabPane = new TabPane();
		sessionTabPane.setLayoutX(0);
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
		
		intAirTempCB = new CheckBox("Air Temperature");
		intAirTempCB.setSelected(true);
		intSurfaceTempCB = new CheckBox("Surface Temperature");
		intSurfaceTempCB.setSelected(true);
		
		extAirTempCB = new CheckBox("Air Temperature");
		extAirTempCB.setSelected(true);
		extSurfaceTempCB = new CheckBox("Surface Temperature");
		extSurfaceTempCB.setSelected(true);
		
		heatFluxCB = new CheckBox("Heat Flux Data");
		heatFluxCB.setSelected(true);
		heatFluxAirTempCB = new CheckBox("Air Temperature");
		heatFluxAirTempCB.setSelected(true);
		heatFluxSurfaceTempCB = new CheckBox("Surface Temperature");
		heatFluxSurfaceTempCB.setSelected(true);
		
		heatFluxCheckBoxesPane = new FlowPane(javafx.geometry.Orientation.VERTICAL);
		heatFluxCheckBoxesPane.setVgap(30);	
		heatFluxCheckBoxesPane.getChildren().addAll(heatFluxCB, heatFluxAirTempCB, heatFluxSurfaceTempCB);
		heatFluxPane.getChildren().add(heatFluxCheckBoxesPane);
		
		intTempCheckBoxesPane = new FlowPane(javafx.geometry.Orientation.VERTICAL);
		intTempCheckBoxesPane.setVgap(30);
		intTempCheckBoxesPane.getChildren().addAll(intAirTempCB, intSurfaceTempCB);
		intTempPane.getChildren().add(intTempCheckBoxesPane);
		
		extTempCheckBoxesPane = new FlowPane(javafx.geometry.Orientation.VERTICAL);
		extTempCheckBoxesPane.setVgap(30);
		extTempCheckBoxesPane.getChildren().addAll(extAirTempCB, extSurfaceTempCB);
		extTempPane.getChildren().add(extTempCheckBoxesPane);
		
		heatFluxTableData = FXCollections.observableArrayList();
		intTempTableData = FXCollections.observableArrayList();
		extTempTableData = FXCollections.observableArrayList();
		
		currentSessionSensors = new ArrayList<Sensor>();
/*
		HeatFluxSensor hfs = new HeatFluxSensor(66, "hfs name", "HFT", 4, 6, 1);
		HeatFluxSensor hfs2 = new HeatFluxSensor(54, "hfs name", "HFT", 7, 3, 80);
		hfs.setTimestamp(mainPackage.Utilities.getCurrentTimestamp());
		hfs2.setTimestamp(mainPackage.Utilities.getCurrentTimestamp());
		addSensor(hfs);
		addSensor(hfs2);*/
		
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	/**
	 * adds sensor to tables and to session sensors arraylist and adds data on chart
	 * @return 
	 */
	public static Runnable addSensor(Sensor sensor) {
		int second = sensor.getTimestamp().getSeconds();
		if (sensor.getSensorType().equals("HFT")) {
			//update table
			heatFluxTableData.add(sensor);
			heatFluxTable.setItems(heatFluxTableData);
			// update chart
			HeatFluxSensor s = (HeatFluxSensor) sensor;
			heatFluxChartData.getData().add(new XYChart.Data(second, s.getHeatFluxData()));
			heatFluxAirTempChartData.getData().add(new XYChart.Data(second, s.getAirTemp()));
			heatFluxSurfaceTempChartData.getData().add(new XYChart.Data(second, s.getSurfaceTemp()));
		} else if (sensor.getSensorName().equals("Int Temp")) {
			//update table
			intTempTableData.add(sensor);
			intTempTable.setItems(intTempTableData);
			//update chart
			TemperatureSensor s = (TemperatureSensor) sensor;
			intTempSensorAirTempData.getData().add(new XYChart.Data(second, s.getAirTemp()));
			intTempSensorSurfaceTempData.getData().add(new XYChart.Data(second, s.getSurfaceTemp()));
		}  else if (sensor.getSensorName().equals("Ext Temp")) {
			extTempTableData.add(sensor);
			extTempTable.setItems(extTempTableData);
			//update chart
			TemperatureSensor s = (TemperatureSensor) sensor;
			extTempSensorAirTempData.getData().add(new XYChart.Data(second, s.getAirTemp()));
			extTempSensorSurfaceTempData.getData().add(new XYChart.Data(second, s.getSurfaceTemp()));
			
		}
		currentSessionSensors.add(sensor);
		return null;
		
		
	}
	
	public void createAndPlaceCharts() {
		heatFluxTimeAxis = new NumberAxis();
		heatFluxDataAxis = new NumberAxis();
		heatFluxTimeAxis.setLabel("Time");
		heatFluxDataAxis.setLabel("Data");
		
		intTempTimeAxis = new NumberAxis();
		intTempDataAxis = new NumberAxis();
		intTempTimeAxis.setLabel("Time");
		intTempDataAxis.setLabel("Data");
				
		extTempTimeAxis = new NumberAxis();
		extTempDataAxis = new NumberAxis();
		extTempTimeAxis.setLabel("Time");
		extTempDataAxis.setLabel("Data");
		
		// HEAT FLUX
		heatFluxChart = new LineChart<Number, Number>(heatFluxTimeAxis, heatFluxDataAxis);
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
		intTempChart = new LineChart<Number, Number>(intTempTimeAxis, intTempDataAxis);
		intTempChart.setTitle("Internal Temperature Sensor Graph");
		intTempSensorAirTempData = new XYChart.Series<>();
		intTempSensorAirTempData.setName("Air Temperature");
		intTempChart.getData().add(intTempSensorAirTempData);
		intTempSensorSurfaceTempData = new XYChart.Series<>();
		intTempSensorSurfaceTempData.setName("Surface Temperature");
		intTempChart.getData().add(intTempSensorSurfaceTempData);
		
		//EXTERNAL TEMP SENSOR
		extTempChart = new LineChart<Number, Number>(extTempTimeAxis, extTempDataAxis);
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
		TableColumn sensorIDColumn = new TableColumn("Sensor ID");
		sensorIDColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("sensorID"));
		TableColumn sensorNameColumn = new TableColumn("Name");
		sensorNameColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("sensorName"));
		TableColumn sensorTimestampColumn = new TableColumn("Date/time");
		sensorTimestampColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("timestamp"));
		TableColumn sensorAirTempColumn = new TableColumn("Air temperature");
		sensorAirTempColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("airTemp"));
		TableColumn sensorSurfaceTempColumn = new TableColumn("Surface temperature");
		sensorSurfaceTempColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("surfaceTemp"));
		table.getColumns().addAll(sensorIDColumn, sensorNameColumn, sensorTimestampColumn,
				sensorAirTempColumn, sensorSurfaceTempColumn);
		if (sensorType.equals("HFT")) {
			TableColumn sensorHeatFluxDataColumn = new TableColumn("Heat Flux data");
			sensorHeatFluxDataColumn.setCellValueFactory(new PropertyValueFactory<Sensor, String>("heatFluxData"));
			table.getColumns().add(sensorHeatFluxDataColumn);
		}
		return table;
	}
	
}


/*
public class Interface extends Application {


	Scene scene;
	Pane root, sessionPane, historicalPane; // Pane for main tab
	Pane heatFluxPane, externalTempPane, airTempPane; // Pane for sub tabs
	TabPane mainTabPane, sessionTabTabpane;
	Tab sessionTab, historicalTab; // Main tabs at the top
	Tab heatTab, externalTab, airTab; // tab to the tabpane with the sessionTab
	Button start, stop, search;
	TextField startTextField, endTextField;
	Timestamp timestamp;
	// ArrayList which holds the values of all sensors in the tables
	static ObservableList<Sensor> airTempTableData = FXCollections.observableArrayList();
	static ObservableList<Sensor> externalTempTableData = FXCollections.observableArrayList();
	static ObservableList<Sensor> heatFluxTableData = FXCollections.observableArrayList();
	
	BorderPane sessionsStartBtnsPane;

	static TableView<Sensor> heatFluxTable;
	static TableView<Sensor> externalTempTable;
	static TableView<Sensor> airTempTable;
	NumberAxis heatFluxXAxis, externalTempXAxis, airTempXAxis;
	NumberAxis heatFluxYAxis, externalTempYAxis, airTempYAxis;
	ScatterChart<Number, Number> heatFluxChart;
	ScatterChart<Number, Number> externalTempChart;
	ScatterChart<Number, Number> airTempChart;

	CheckBox heatFluxCheckAirTemp, heatFluxCheckSurfaceTemp, heatFluxCheckFlux;
	CheckBox externalTempCheckAirTemp, externalTempCheckSurfaceTemp;
	CheckBox airTempCheckAirTemp, airTempCheckSurfaceTemp;
	
	ArduinoConnector arduino;

	private MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty) {
		final MenuBar menuBar = new MenuBar();
		final Menu fileMenu = new Menu("File");

		MenuItem MINew = new MenuItem("New");
		fileMenu.getItems().add(MINew);
		MINew.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("!test");
			}
		});

		MenuItem MISave = new MenuItem("Save");
		fileMenu.getItems().add(MISave);
		MISave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

			}
		});

		MenuItem MIAbout = new MenuItem("About");
		fileMenu.getItems().add(MIAbout);
		MIAbout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// JOptionPane.showMessageDialog(this, "Inventory App Version
				// 0.1");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Version");
				alert.setHeaderText("About");
				String s = "Sensor's Readings App Version 1.0";
				alert.setContentText(s);
				alert.show();

			}
		});

		MenuItem MIExit = new MenuItem("Exit");
		fileMenu.getItems().add(MIExit);
		MIExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});

		menuBar.getMenus().add(fileMenu);

		menuBar.prefWidthProperty().bind(menuWidthProperty);
		return menuBar;
	}

	public void start(Stage stage) throws Exception {
		arduino = new ArduinoConnector();
		
		heatFluxTable = new TableView<Sensor>();
		externalTempTable = new TableView<Sensor>();
		airTempTable = new TableView<Sensor>();

		heatFluxXAxis = new NumberAxis();
		externalTempXAxis = new NumberAxis();
		airTempXAxis = new NumberAxis();

		heatFluxYAxis = new NumberAxis();
		externalTempYAxis = new NumberAxis();
		airTempYAxis = new NumberAxis();

		heatFluxChart = new ScatterChart<Number, Number>(heatFluxXAxis, heatFluxYAxis);
		externalTempChart = new ScatterChart<Number, Number>(externalTempXAxis, externalTempYAxis);
		airTempChart = new ScatterChart<Number, Number>(airTempXAxis, airTempYAxis);

		stage.setTitle("Sensor Reading App");
		root = new Pane();
		scene = new Scene(root, 1250, 950, Color.WHEAT);
		mainTabPane = new TabPane();
		mainTabPane.setLayoutY(29);
		mainTabPane.setPrefWidth(scene.getWidth()); // <---- needs to change
													// when maximized
		final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());
		root.getChildren().add(menuBar);
		root.getChildren().add(mainTabPane);
		stage.setScene(scene);

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
			int x = 1; // temp counter <---------needs removing

			public void handle(ActionEvent e) {
				arduino.run();
				System.out.println("Arduino started");
				
			}
//				System.out.println("Start button pressed");
//				Calendar c = Calendar.getInstance();
//				java.util.Date now = c.getTime();
//				Timestamp ts = new Timestamp(now.getTime());
//				Sensor test = new HeatFluxSensor(x, "Ext Temp", "HFT", x, x, x);
//				test.setTimestamp(ts);
//				Sensor test2 = new TemperatureSensor(x, "Int Temp", "Ext Temp", x, x);
//				test2.setTimestamp(ts);
//				Sensor test3 = new TemperatureSensor(x, "Ext Temp", "Ext Temp", x, x);
//				test3.setTimestamp(ts);
//				populateTable(test);
//				populateTable(test2);
//				populateTable(test3);
//				x = x + 1;
//			}
		});

		// Stop Button
		stop = new Button();
		stop.setText("Stop");
		stop.setLayoutX(150);
		stop.setLayoutY(10);

		stop.setPrefWidth(100);
		sessionPane.getChildren().add(stop);
		stop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("stop pressed");
				drawChart(heatFluxTableData);
				arduino.close();
				System.out.println("Stop executed");
			}
//				System.out.println("Stop button pressed");
//				drawChart(heatFluxTableData);
//			}
		});

		// sessionTab's tabpane and corresponding tabs
		sessionTabTabpane = new TabPane();
		sessionPane.getChildren().add(sessionTabTabpane);
		sessionTabTabpane.setLayoutX(0);
		sessionTabTabpane.setLayoutY(50);
		sessionTabTabpane.setPrefWidth(scene.getWidth());

		heatTab = new Tab(); // heatFlux tab
		heatTab.setText("Heat Flux Sensor");
		heatTab.setClosable(false);
		sessionTabTabpane.getTabs().add(heatTab);

		heatFluxPane = new Pane(); // heatFlux pane, held within the heat flux
									// tab
		heatTab.setContent(heatFluxPane);
		heatFluxPane.getChildren().add(createTable("HeatFluxSensor", "Heat Flux Sensors"));

		// HeatFlux Graph <--------- Create a new method to create a graph for
		// each table??????
		heatFluxXAxis.setLabel("Time");
		heatFluxYAxis.setLabel("Temperature");
		heatFluxChart.setTitle("Heat Flux Data");
		heatFluxChart.setLayoutX(680);
		heatFluxChart.setLayoutY(10);
		heatFluxPane.getChildren().add(heatFluxChart);

		heatFluxCheckAirTemp = new CheckBox();
		heatFluxCheckAirTemp.setText("Air Temperature");
		heatFluxCheckAirTemp.setLayoutX(740);
		heatFluxCheckAirTemp.setLayoutY(420);
		heatFluxCheckSurfaceTemp = new CheckBox();
		heatFluxCheckSurfaceTemp.setText("Surface Temperature");
		heatFluxCheckSurfaceTemp.setLayoutX(880);
		heatFluxCheckSurfaceTemp.setLayoutY(420);
		heatFluxCheckFlux = new CheckBox();
		heatFluxCheckFlux.setText("Heat Flux Data");
		heatFluxCheckFlux.setLayoutX(1050);
		heatFluxCheckFlux.setLayoutY(420);
		heatFluxPane.getChildren().addAll(heatFluxCheckAirTemp, heatFluxCheckSurfaceTemp, heatFluxCheckFlux);

		externalTab = new Tab(); // externalTemp tab
		externalTab.setText("External Temp Sensor");
		externalTab.setClosable(false);
		sessionTabTabpane.getTabs().add(externalTab);

		externalTempPane = new Pane(); // externalTemp pane, held within the
										// external temp tab
		externalTab.setContent(externalTempPane);
		externalTempPane.getChildren().add(createTable("ExternalTempSensor", "External Temperature Sensors"));

		// ExternalTemp Graph <--------- Create a new method to create a graph
		// for each table??????
		externalTempXAxis.setLabel("Time");
		externalTempYAxis.setLabel("Temperature");
		externalTempChart.setTitle("External Temperature Data");
		externalTempChart.setLayoutX(680);
		externalTempChart.setLayoutY(10);
		externalTempPane.getChildren().add(externalTempChart);

		externalTempCheckAirTemp = new CheckBox();
		externalTempCheckAirTemp.setText("Air Temperature");
		externalTempCheckAirTemp.setLayoutX(740);
		externalTempCheckAirTemp.setLayoutY(420);
		externalTempCheckSurfaceTemp = new CheckBox();
		externalTempCheckSurfaceTemp.setText("Surface Temperature");
		externalTempCheckSurfaceTemp.setLayoutX(880);
		externalTempCheckSurfaceTemp.setLayoutY(420);
		externalTempPane.getChildren().addAll(externalTempCheckAirTemp, externalTempCheckSurfaceTemp);

		airTab = new Tab(); // airTemp tab
		airTab.setText("Air Temp Data");
		airTab.setClosable(false);
		sessionTabTabpane.getTabs().add(airTab);

		airTempPane = new Pane(); // airTemp pane, held within the airTemp tab
		airTab.setContent(airTempPane);
		airTempPane.getChildren().add(createTable("AirTempSensor", "Air Temperature Sensors"));

		// AirTemp Graph <--------- Create a new method to create a graph for
		// each table??????
		airTempXAxis.setLabel("Time");
		airTempYAxis.setLabel("Temperature");
		airTempChart.setTitle("Air Temperature Data");
		airTempChart.setLayoutX(680);
		airTempChart.setLayoutY(10);
		airTempPane.getChildren().add(airTempChart);

		airTempCheckAirTemp = new CheckBox();
		airTempCheckAirTemp.setText("Air Temperature");
		airTempCheckAirTemp.setLayoutX(740);
		airTempCheckAirTemp.setLayoutY(420);
		airTempCheckSurfaceTemp = new CheckBox();
		airTempCheckSurfaceTemp.setText("Surface Temperature");
		airTempCheckSurfaceTemp.setLayoutX(880);
		airTempCheckSurfaceTemp.setLayoutY(420);
		airTempPane.getChildren().addAll(airTempCheckAirTemp, airTempCheckSurfaceTemp);

		// <--Objects within the HistoricalData tab-->

		// Start Date Text Field
		startTextField = new TextField();
		startTextField.setPrefWidth(120);
		startTextField.setText("Start Date");
		startTextField.setLayoutX(60);
		startTextField.setLayoutY(120);

		// End Date Text Field
		endTextField = new TextField();
		endTextField.setPrefWidth(120);
		endTextField.setText("End Date");
		endTextField.setLayoutX(250);
		endTextField.setLayoutY(120);

		// Search Button in Historical tab to search between dates
		search = new Button();
		search.setText("Search");
		search.setLayoutX(165);
		search.setLayoutY(180);
		search.setPrefWidth(100);

		historicalPane.getChildren().addAll(startTextField, endTextField, search);

		stage.show();

	}
	
		
//		Calendar c = Calendar.getInstance();
//		java.util.Date now = c.getTime();
//		Timestamp ts = new Timestamp(now.getTime());
//		Sensor test = new HeatFluxSensor(x, "Ext Temp", "HFT", x, x, x);
//		test.setTimestamp(ts);
//		Sensor test2 = new TemperatureSensor(x, "Int Temp", "Ext Temp", x, x);
//		test2.setTimestamp(ts);
//		Sensor test3 = new TemperatureSensor(x, "Ext Temp", "Ext Temp", x, x);
//		test3.setTimestamp(ts);
//		populateTable(test);
//		populateTable(test2);
//		populateTable(test3);
//		x = x + 1;
	

	@SuppressWarnings("rawtypes")

	public void drawChart(ObservableList<Sensor> tableData) {
		heatFluxChart.getData().clear();
		XYChart.Series series = new XYChart.Series();
		// XYChart.Series series2 = new XYChart.Series();
		// series.setName("Model Data");
		for (Sensor sensor : tableData) {
			series.getData().add(new XYChart.Data(sensor.getTimestamp().getMinutes(), sensor.getSensorID())); // <------needs
																												// changing
			// series2.getData().add(new
			// XYChart.Data(sensor.getTimestamp().getSeconds() + 10,
			// sensor.getSensorID()));
		}
		heatFluxChart.getData().add(series);
		// heatFluxChart.getData().add(series2);


	}

	@SuppressWarnings("rawtypes")
	public VBox createTable(String sensorType, String tableName) {
		Label lblTableName = new Label(tableName);
		lblTableName.setFont(new Font("Arial", 20));

		TableColumn sensorID = new TableColumn("Sensor ID");
		TableColumn sensorName = new TableColumn("Sensor Name");
		TableColumn timestamp = new TableColumn("Time Stamp");
		TableColumn heatData = new TableColumn("Heat Flux Data");
		TableColumn surfaceTemp = new TableColumn("Surface Temp Data");
		TableColumn airData = new TableColumn("Air Temp Data");

		sensorID.setCellValueFactory(new PropertyValueFactory<Sensor, String>("sensorID"));
		sensorName.setCellValueFactory(new PropertyValueFactory<Sensor, String>("sensorName"));
		timestamp.setCellValueFactory(new PropertyValueFactory<Sensor, Timestamp>("timestamp"));
		if (sensorType.equals("HeatFluxSensor") || sensorType.equals("ExternalTempSensor")) {
			if (sensorType.equals("HeatFluxSensor")) {
				heatData.setCellValueFactory(new PropertyValueFactory<Sensor, String>("heatFluxData"));
			}
			surfaceTemp.setCellValueFactory(new PropertyValueFactory<Sensor, String>("surfaceTemp"));
		}
		airData.setCellValueFactory(new PropertyValueFactory<Sensor, String>("airTemp"));

		if (sensorType.equals("AirTempSensor")) {
			airTempTable.getColumns().addAll(sensorID, sensorName, timestamp, airData);
		} else if (sensorType.equals("ExternalTempSensor")) {
			externalTempTable.getColumns().addAll(sensorID, sensorName, timestamp, surfaceTemp, airData);
		} else if (sensorType.equals("HeatFluxSensor")) {
			heatFluxTable.getColumns().addAll(sensorID, sensorName, timestamp, heatData, surfaceTemp, airData);
		}

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.setPrefWidth(680);

		if (sensorType.equals("AirTempSensor")) {
			vbox.getChildren().addAll(lblTableName, airTempTable);
		} else if (sensorType.equals("ExternalTempSensor")) {
			vbox.getChildren().addAll(lblTableName, externalTempTable);
		} else if (sensorType.equals("HeatFluxSensor")) {
			vbox.getChildren().addAll(lblTableName, heatFluxTable);
		}
		return vbox;
	}

	public static void populateTable(Sensor sensor) {
		if ((sensor.getSensorType()).equals("HFT")) {
			heatFluxTableData.add(sensor);
			heatFluxTable.setItems(heatFluxTableData);
		} else {
			if ((sensor.getSensorName()).equals("Int Temp")) {
				airTempTableData.add(sensor);
				airTempTable.setItems(airTempTableData);
			} else if ((sensor.getSensorName()).equals("Ext Temp")) {
				externalTempTableData.add(sensor);
				externalTempTable.setItems(externalTempTableData);
			}
		}

	}
}
*/