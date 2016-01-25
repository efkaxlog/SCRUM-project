package gui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;
import sensors.Sensor;


import java.sql.Timestamp;
import java.util.Calendar;

import com.apple.laf.AquaButtonBorder.Dynamic;

public class Interface extends Application {
	 public static void main(String[] args) 
	 {
		launch(args);

     }  
	
	  // Constructor
	  public Interface() 
	  {
			
	  }
	
	  private MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty)
	  {
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
	    		  //JOptionPane.showMessageDialog(this, "Inventory App Version 0.1");
	    		  Alert alert = new Alert(AlertType.INFORMATION);
	    		  alert.setTitle("Version");
	    		  alert.setHeaderText("About");
	    		  String s ="Sensor's Readings App Version 1.0";
	    		  alert.setContentText(s);
	    		  alert.show();
	    		  
	    	  }
	      });
	      
	      MenuItem MIExit = new MenuItem("Exit");
	      fileMenu.getItems().add(MIExit);
	      MIExit.setOnAction(new EventHandler<ActionEvent>() 
	      {
	    	  public void handle(ActionEvent e) 
	    	  {
	    		  System.exit(0);
	    	  }
	      });
	     
	      menuBar.getMenus().add(fileMenu);
		  
		  menuBar.prefWidthProperty().bind(menuWidthProperty);
		  return menuBar; 
	  }

	Scene scene;
	Pane root, sessionPane, historicalPane; //Pane for main tab
	Pane heatFluxPane, externalTempPane, airTempPane;  //Pane for sub tabs 
	TabPane mainTabPane, sessionTabTabpane;
	Tab sessionTab, historicalTab;  // Main tabs at the top
	Tab heatTab, externalTab, airTab; //tab  to the tabpane with the sessionTab 
	Button start, stop, search;
	TextField startTextField, endTextField;
	Timestamp timestamp;
	ArrayList<Sensor> sensors = new ArrayList<Sensor>(); //ArrayList which holds the values of all sensors in the tables
	BorderPane sessionsStartBtnsPane;
	
	TableView<Sensor> heatFluxTable = new TableView<Sensor>(), externalTempTable = new TableView<Sensor>(), airTempTable = new TableView<Sensor>();
	NumberAxis xAxis = new NumberAxis();
	NumberAxis yAxis = new NumberAxis();
	ScatterChart<Number,Number> chart = new ScatterChart<Number,Number>(xAxis,yAxis);
	
	public void start(Stage stage) throws Exception 
	{
		stage.setTitle("Sensor Reading App");
		root = new Pane();
		scene = new Scene(root, 1250, 950, Color.WHEAT);
		mainTabPane = new TabPane();
		mainTabPane.setLayoutY(29);
	  	mainTabPane.setPrefWidth(scene.getWidth());  // <---- needs to change when maximized
		final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());
	    root.getChildren().add(menuBar);
	    root.getChildren().add(mainTabPane);
	    stage.setScene(scene);

	    
	    
	    // Session tab
	  	sessionTab = new Tab();
	  	sessionTab.setText("Session");
	  	sessionTab.setClosable(false);
	  	mainTabPane.getTabs().add(sessionTab);
	  	
	  	//SessionPane held within the session tab
	  	sessionPane = new Pane(); //within the sessionTab
	  	sessionTab.setContent(sessionPane);
	  	
	    //Historical Data tab
	  	historicalTab = new Tab();
	  	historicalTab.setText("Historical Data");
	  	historicalTab.setClosable(false);
	  	mainTabPane.getTabs().add(historicalTab);
	  	
	  	historicalPane = new Pane();
	  	historicalTab.setContent(historicalPane);
	  	
	  	//<--Objects within the Session tab--
	  	
	  	// Start Button
	  	start = new Button();
	  	start.setText("Start");
	  	start.setLayoutX(20);
	  	start.setLayoutY(10);
	  	start.setPrefWidth(100);
	  	sessionPane.getChildren().add(start);
	    start.setOnAction(new EventHandler<ActionEvent>() {
	    	int x = 1; //temp counter                 <---------needs removing
	    	  public void handle(ActionEvent e) 
	    	  {
	    		  System.out.println("Start button pressed");
	    		  Calendar c = Calendar.getInstance();
	    			java.util.Date now = c.getTime();
	    			Timestamp ts =  new Timestamp(now.getTime());
	    	        populateTable(new HeatFluxSensor(x, "test", "test", ts, x, x, x));
	    	        x = x + 1;
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
	    	  public void handle(ActionEvent e) 
	    	  {
	    		  System.out.println("Stop button pressed");
	    		  drawChart(sensors);
	    	  }	  
	      });
	  	
	  	//sessionTab's tabpane and corresponding tabs
	    sessionTabTabpane = new TabPane();
	    sessionPane.getChildren().add(sessionTabTabpane);
	    sessionTabTabpane.setLayoutX(0);
	  	sessionTabTabpane.setLayoutY(50);
	   
	    heatTab = new Tab(); //heatFlux tab
	  	heatTab.setText("Heat Flux Sensor");
	  	heatTab.setClosable(false);
	  	sessionTabTabpane.getTabs().add(heatTab);
	  	
	  	heatFluxPane = new Pane(); //heatFlux pane, held within the heat flux tab
	  	heatTab.setContent(heatFluxPane);
	  	heatFluxPane.getChildren().add(createTable("HeatFluxSensor", "Heat Flux Sensors"));
	  	
	  	
	  	externalTab = new Tab(); //externalTemp tab
	  	externalTab.setText("External Temp Sensor");
	  	externalTab.setClosable(false);
	  	sessionTabTabpane.getTabs().add(externalTab);
	  	
	  	externalTempPane = new Pane(); //externalTemp pane, held within the external temp tab
	  	externalTab.setContent(externalTempPane);
	  	externalTempPane.getChildren().add(createTable("ExternalTempSensor", "External Temperature Sensors"));
	  	
	  	
        airTab = new Tab(); //airTemp tab
	  	airTab.setText("Air Temp Data");
	  	airTab.setClosable(false);
	  	sessionTabTabpane.getTabs().add(airTab);  
        
	  	airTempPane = new Pane(); //airTemp pane, held within the airTemp tab
	  	airTab.setContent(airTempPane);
	  	airTempPane.getChildren().add(createTable("AirTempSensor", "Air Temperature Sensors"));
	  	
       
	  	
	  	// Graph                   <--------- Create a new method to create a graph for each table??????
		sessionPane.getChildren().add(chart);
	  	xAxis.setLabel("Time");
	  	yAxis.setLabel("Temperature");
	  	chart.setTitle("Sensor's Graph");
	  	chart.setLayoutX(700);
	  	chart.setLayoutY(250);
	  	
	  	
	    //<--Objects within the HistoricalData tab-->
	  	
	  	// Start Date Text Field
        startTextField = new TextField();
        startTextField.setPrefWidth(120);
        startTextField.setText("Start Date");
        startTextField.setLayoutX(60);
        startTextField.setLayoutY(120);
	  	
        //End Date Text Field
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

	@SuppressWarnings("rawtypes")
	public void drawChart(ArrayList<Sensor> sensors)
	{
		chart.getData().clear();
		XYChart.Series series = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
		//series.setName("Model Data");
		for(Sensor sensor : sensors)
		{
		    //series.getData().add(new XYChart.Data(((List<MenuItem>) sensor.getTimestamp()).get(Calendar.MINUTE), sensor.getSensorID()));
		    series2.getData().add(new XYChart.Data(sensor.getTimestamp().getSeconds() + 10, sensor.getSensorID()));
		}
		chart.getData().add(series);
		chart.getData().add(series2);
	
	}
	
	@SuppressWarnings("rawtypes")
	public VBox createTable(String sensorType, String tableName)
	{	
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
        if (sensorType.equals("HeatFluxSensor")  || sensorType.equals("ExternalTempSensor"))
        {
        	if (sensorType.equals("HeatFluxSensor"))
        	{
        		heatData.setCellValueFactory(new PropertyValueFactory<Sensor, String>("heatFluxTemp"));
        	}
            surfaceTemp.setCellValueFactory(new PropertyValueFactory<Sensor, String>("internalWallSurfaceTemp"));
        }
        airData.setCellValueFactory(new PropertyValueFactory<Sensor, String>("internalAirTemp"));
        
        if (sensorType.equals("AirTempSensor"))
        {
            airTempTable.getColumns().addAll(sensorID, sensorName, timestamp, airData);
        } else if (sensorType.equals("ExternalTempSensor"))
        {
        	externalTempTable.getColumns().addAll(sensorID, sensorName, timestamp, surfaceTemp, airData);
        } else if (sensorType.equals("HeatFluxSensor"))
        {
        	heatFluxTable.getColumns().addAll(sensorID, sensorName, timestamp, heatData, surfaceTemp, airData);
        }
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
	  	vbox.setPrefWidth(680);
	  	
	  	if (sensorType.equals("AirTempSensor"))
        {
	  		vbox.getChildren().addAll(lblTableName, airTempTable);
        } else if (sensorType.equals("ExternalTempSensor"))
        {
        	vbox.getChildren().addAll(lblTableName, externalTempTable);
        } else if (sensorType.equals("HeatFluxSensor"))
        {
        	vbox.getChildren().addAll(lblTableName, heatFluxTable);
        }
        return vbox;
	}
	
	public void populateTable(Sensor sensor)
	{
		sensors.add(sensor);
		
		ArrayList<Sensor> heatFluxSensors = new ArrayList<Sensor>(), externalTempSensors = new ArrayList<Sensor>(), airTempSensors = new ArrayList<Sensor>();
		
		for (Sensor currentSensor : sensors)
		{
			if (currentSensor instanceof AirTempSensor)
			{
				airTempSensors.add(currentSensor);
			} else if (currentSensor instanceof ExternalTempSensor)
			{
				externalTempSensors.add(currentSensor);
			} else if (currentSensor instanceof HeatFluxSensor)
			{
				heatFluxSensors.add(currentSensor);
			}
		}
			
		if (sensor instanceof AirTempSensor)
		{
			ObservableList<Sensor> tableData = FXCollections.observableArrayList(airTempSensors);
			airTempTable.setItems(tableData);
		} else if (sensor instanceof ExternalTempSensor)
		{
			ObservableList<Sensor> tableData = FXCollections.observableArrayList(externalTempSensors);
			externalTempTable.setItems(tableData);
		} else if (sensor instanceof HeatFluxSensor)
		{
			ObservableList<Sensor> tableData = FXCollections.observableArrayList(heatFluxSensors);
			heatFluxTable.setItems(tableData);
		}
	}
}







