package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;
import sensors.Sensor;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

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
	Tab heatTab, externalTab, airTab; //tab  to the tabpane withi the sessionTab 
	Button start, stop, search;
	TextField startTextField, endTextField;
	Timestamp timestamp;
	TableView heatTable, externalTable, airTable;
	public HeatFluxSensor heatFluxSensor;
	public AirTempSensor airTempSensor;
	public ExternalTempSensor externalTempSensor;
	private Dimension frame;
	
	public void start(Stage stage) throws Exception {
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
	  	
	  	
	  	sessionPane = new Pane(); //within the sessionTab
	  	sessionTab.setContent(sessionPane);
	  	
	  	// Start Button
	  	start = new Button();
	  	start.setText("Start");
	  	start.setLayoutX(20);
	  	start.setLayoutY(50);
	  	start.setPrefWidth(100);
	  	sessionPane.getChildren().add(start);
	    start.setOnAction(new EventHandler<ActionEvent>() {
	    	  public void handle(ActionEvent e) 
	    	  {
	    		  System.out.println("Start button pressed");
	    	  }	  
	      });
	  	
	  	// Stop Button
	  	stop = new Button();
	  	stop.setText("Stop");
	  	stop.setLayoutX(150);
		stop.setLayoutY(50);
	  	stop.setPrefWidth(100);
	  	sessionPane.getChildren().add(stop);
	  	stop.setOnAction(new EventHandler<ActionEvent>() {
	    	  public void handle(ActionEvent e) 
	    	  {
	    		  System.out.println("Stop button pressed");
	    	  }	  
	      });
	  	
	  	//sessionTab's tabpane and corresponding tabs
	    sessionTabTabpane = new TabPane();
	    sessionPane.getChildren().add(sessionTabTabpane);
	    sessionTabTabpane.setLayoutX(20);
	  	sessionTabTabpane.setLayoutY(100);
	   
	    heatTab = new Tab(); //heatFlux tab
	  	heatTab.setText("Heat Flux Sensor");
	  	heatTab.setClosable(false);
	  	sessionTabTabpane.getTabs().add(heatTab);
	  	
	  	
	  	externalTab = new Tab(); //externalTemp tab
	  	externalTab.setText("External Temp Sensor");
	  	externalTab.setClosable(false);
	  	sessionTabTabpane.getTabs().add(externalTab);
	  	
	  	
        airTab = new Tab(); //airTemp tab
	  	airTab.setText("Air Temp Data");
	  	airTab.setClosable(false);
	  	sessionTabTabpane.getTabs().add(airTab);
	  	
	  	//temp test arrayList
	  	ArrayList<Sensor> test = new ArrayList<Sensor>();
	  	
	  	heatFluxPane = new Pane();
	  	heatTab.setContent(heatFluxPane);
	  	heatFluxPane.getChildren().add(createTable("HeatFluxSensor"));
	  	    
	  	externalTempPane = new Pane();
	  	externalTab.setContent(externalTempPane);
	  	externalTempPane.getChildren().add(createTable("ExternalTempSensor"));
        
	  	airTempPane = new Pane();
	  	airTab.setContent(airTempPane);
	  	airTempPane.getChildren().add(createTable("AirTempSensor"));
       
	  	
	  	// Graph
        //View view = new View();
		sessionPane.getChildren().add(chart);
	  	xAxis.setLabel("Time");
	  	yAxis.setLabel("Temperature");
	  	chart.setTitle("Sensor's Graph");
	  	chart.setLayoutX(700);
	  	chart.setLayoutY(250);
	  	//chart.setPrefWidth(30);
	  	//chart.setPrefHeight(30);
	  	
	  	// Historical Data tab
	  	historicalTab = new Tab();
	  	historicalTab.setText("Historical Data");
	  	historicalTab.setClosable(false);
	  	mainTabPane.getTabs().add(historicalTab);
	  	
	  	historicalPane = new Pane();
	  	historicalTab.setContent(historicalPane);
	  	
	  	final Label label4 = new Label("  Search Between Dates");
        label4.setFont(new Font("Arial", 20));
        label4.setLayoutX(20);
	  	label4.setLayoutY(50);
        
	  	// Start Date Text Field
        startTextField = new TextField();
        startTextField.setPrefWidth(120);
	 	//start = new Button();
        startTextField.setText("Start Date");
        startTextField.setLayoutX(60);
        startTextField.setLayoutY(120);
	  	//startTextField.setPrefWidth(100);
	  	//startTextField.setPrefHeight(150);
	  	
        //End Date Text Field
        endTextField = new TextField();
        endTextField.setPrefWidth(120);
	 	//start = new Button();
        endTextField.setText("End Date");
        endTextField.setLayoutX(250);
        endTextField.setLayoutY(120);
	  	//endTextField.setPrefWidth(100);
	  	//endTextField.setPrefHeight(150);
        
        // Search Button in Historical tab to search between dates
        search = new Button();
        search.setText("Search");
        search.setLayoutX(165);
        search.setLayoutY(180);
	  	search.setPrefWidth(100);
	  	//search.setPrefHeight(150);
        
        historicalPane.getChildren().addAll(label4, startTextField, endTextField, search);
	  	
	  	stage.show();
	}

	
	

	NumberAxis xAxis = new NumberAxis();
	NumberAxis yAxis = new NumberAxis();
	ScatterChart<Number,Number> chart = new ScatterChart<Number,Number>(xAxis,yAxis);
	//Interface interface = new Interface();

	public void drawChart(ArrayList<Customer> customers)
	{
		chart.getData().clear();
		XYChart.Series series = new XYChart.Series();
		series.setName("Model Data");
		for(Customer c : customers)
		series.getData().add(new XYChart.Data(c.age, c.balance));
		chart.getData().add(series);
	
	}
	
	public VBox createTable(String sensorType)
	{		
	  	final Label lblTableName = new Label("Heat Flux Sensor");
        lblTableName.setFont(new Font("Arial", 20));
	  	heatTable.setEditable(true);

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
            heatTable.getColumns().addAll(sensorID, sensorName, timestamp, airData);
        } else if (sensorType.equals("ExternalTempSensor"))
        {
        	heatTable.getColumns().addAll(sensorID, sensorName, timestamp, surfaceTemp, airData);
        } else if (sensorType.equals("HeatFluxSensor"))
        {
        	heatTable.getColumns().addAll(sensorID, sensorName, timestamp, heatData, surfaceTemp, airData);
        }
        
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(lblTableName, heatTable);
        vbox.setLayoutX(0);
	  	vbox.setLayoutY(100);
	  	vbox.setPrefWidth(680);
        
        return vbox;
	}
	
	public void populateTable(Sensor sensor)
	{
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		Timestamp ts =  new Timestamp(now.getTime());
		
		
		//Heat Flux data table
	  	TableView<Sensor> heatTable = new TableView<Sensor>();
	  	ObservableList<Sensor> heatSensorData = FXCollections.observableArrayList(sensor);
	}


class Customer {
		String name;
		int age;
		double balance;
	
		public Customer(String name, int age, double balance) {
		super();
		this.name = name;
		this.age = age;
		this.balance = balance;
	}
}

}



