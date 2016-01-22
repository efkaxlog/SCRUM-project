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
	      MIExit.setOnAction(new EventHandler<ActionEvent>() {
	    	  public void handle(ActionEvent e) {
	    		  System.exit(0);
	    	  }
	      });
	     
	      menuBar.getMenus().add(fileMenu);
		  
		  menuBar.prefWidthProperty().bind(menuWidthProperty);
		  return menuBar; 
	  }
	
	
		
	Scene scene;
	Pane root, sessionPane, historicalPane; //Pane for main tab
	Pane heatPane, externalPane, airPane;  //Pane for sub tabs 
	TabPane mainTabPane, subTabPane;
	Tab sessionTab, historicalTab;  // Main tabs at the top
	Tab heatTab, externalTab, airTab; //Sub tabs for displaying different tables
	Button start, stop, search;
	TextField startTextField, endTextField;
	Timestamp timestamp;
	TableView heatTable, externalTable, airTable;
	public HeatFluxSensor heatFluxSensor;
	public AirTempSensor airTempSensor;
	public ExternalTempSensor externalTempSensor;
	private Dimension frame;
	 TableColumn heatData;
	
	public void start(Stage stage) throws Exception {
		stage.setTitle("Sensor Reading App");
		final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());
		root = new Pane();
		mainTabPane = new TabPane();
	    root.getChildren().add(menuBar);
	    root.getChildren().add(mainTabPane);
	    scene = new Scene(root, 1250, 950, Color.WHEAT);
	  
	    //Toolkit t = Toolkit.getDefaultToolkit();
	    //Dimension d = t.getScreenSize();

	   // int h = d.height;
	   // int w = d.width;

	   // frame.setSize( w , h );
	    //scene = new Scene(root, 1250, 950);
	    //root.setSize(scene);
	    stage.setScene(scene);

	    // Session tab
	  	sessionTab = new Tab();
	  	sessionTab.setText("Session");
	  	sessionTab.setClosable(false);
	  	mainTabPane.getTabs().add(sessionTab);
	  	mainTabPane.setLayoutX(0);
	  	mainTabPane.setLayoutY(25);
	  	
	  	sessionPane = new Pane();
	  	sessionTab.setContent(sessionPane);
	  	
	  	// Start Button
	  	start = new Button();
	  	start.setText("Start");
	  	start.setLayoutX(20);
	  	start.setLayoutY(50);
	  	start.setPrefWidth(100);
	  	//start.setPrefHeight(150);
	  	
	  	//heatTab.getItems().add(start);
	    start.setOnAction(new EventHandler<ActionEvent>() {
	    	  public void handle(ActionEvent e) {
	    		  Calendar c = Calendar.getInstance();
	    		  java.util.Date now = c.getTime();
	    		   timestamp = new Timestamp(now.getTime());
	    			
	    		//Sensor airSensor = new AirTempSensor("007", "Some Air Temp Sensor", "Air Temperature Sensor", timestamp, 30.5f);
	    		//heatTable.append(airSensor);
	    		//heatTable.append();
	    		/*AirTempSensor airSensor = new AirTempSensor(
		        		airTempSensor.getSensorID(),
		        		airTempSensor.getSensorName(),
		        		airTempSensor.getSensorType(),
		        		airTempSensor.getTimestamp(),
		        		airTempSensor.getAirTemp());
	    		*/
	    		//airTable.add(airSensor);
	    		
	    		
	    		   //TableView
	    		//Sensor externalSensor = new ExternalTempSensor("008", "Some External Temp Sensor", "External Temp Sensor", timestamp, 27.5f, 20.1f);
	    		
	    		       /* ExternalTempSensor externalSensor = new ExternalTempSensor(
	    		        		externalTempSensor.getSensorID(),
	    		        		externalTempSensor.getSensorName(),
	    		        		externalTempSensor.getSensorType(),
	    		        		externalTempSensor.getTimestamp(),
	    		        		externalTempSensor.getExternalSurfaceTemp(),
	    		        		externalTempSensor.getExternalAirTemp());*/
	    	    		
	    	    		//externalTable.add(externalSensor);
	    		
	    		   //final ObservableList<HeatFluxSensor> data = FXCollections.observableArrayList(
	    		//new HeatFluxSensor("009", "Heat Flux Sensor", "HeatFluxSensor", timestamp, 20.5f, 17.1f, 77.8f));
	    		
	    		/*HeatFluxSensor heatSensor = new HeatFluxSensor(
	    		heatFluxSensor.getSensorID(),
	    		heatFluxSensor.getSensorName(),
	    		heatFluxSensor.getSensorType(),
	    		heatFluxSensor.getTimestamp(),
	    		heatFluxSensor.getHeatFluxTemp(),
	    		heatFluxSensor.getInternalWallSurfaceTemp(),
	    		heatFluxSensor.getInternalAirTemp());*/
	    		
	    		//heatTable.addAll(heatSensor);
	    	  }	  
	      });
	  	sessionPane.getChildren().add(start);
	  	
	  	// Stop Button
	  	stop = new Button();
	  	stop.setText("Stop");
	  	stop.setLayoutX(150);
		stop.setLayoutY(50);
	  	stop.setPrefWidth(100);
	  	//stop.setPrefHeight(30);
	  	sessionPane.getChildren().add(stop);
	  	
	    subTabPane = new TabPane();
	    sessionPane.getChildren().add(subTabPane);
	   
	    // Heat flux sensor tab
	    heatTab = new Tab();
	  	heatTab.setText("Heat Flux Sensor");
	  	heatTab.setClosable(false);
	  	subTabPane.getTabs().add(heatTab);
	  	subTabPane.setLayoutX(20);
	  	subTabPane.setLayoutY(100);
	  	
	  	heatPane = new Pane();
	  	heatTab.setContent(heatPane);
	  	
	  	//Heat Flux data table
	  	TableView<HeatFluxSensor> heatTable = new TableView<HeatFluxSensor>();
	  	final ObservableList<HeatFluxSensor> heatSensorData = FXCollections.observableArrayList(
	    		new HeatFluxSensor("009", "Heat Flux Sensor", "HeatFluxSensor", timestamp, 20.5f, 17.1f, 77.8f),
	    		new HeatFluxSensor("001", "Heat Flux Sensor", "HeatFluxSensor", timestamp, 15.5f, 47.1f, 127.8f)
	  			);
	    		
	  	final Label label = new Label("                        Heat Flux Sensor");
        label.setFont(new Font("Arial", 20));
	  	
	  	heatTable.setEditable(true);
	  	 

        TableColumn sensorID = new TableColumn("Sensor ID");
        TableColumn sensorName = new TableColumn("Sensor Name");
        TableColumn timeStamp = new TableColumn("Time Stamp");
        heatData = new TableColumn("Heat Flux Data");
        TableColumn surfaceData = new TableColumn("Surface Temp Data");
        TableColumn airData = new TableColumn("Air Temp Data");
        
        //TableColumn<HeatFluxSensor, String> sensorId = new TableColumn<HeatFluxSensor, String>("Sensor ID");
        
        sensorID.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("sensorID"));
        sensorName.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("sensorName"));
        //timestamp.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("timestamp"));
        heatData.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("heatFluxTemp"));
        surfaceData.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("internalWallSurfaceTemp"));
        airData.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("internalAirTemp"));
  
        
        
        heatTable.setItems(heatSensorData);
        //heatTable.getColumns().addAll((Collection<? extends TableColumn<HeatFluxSensor, ?>>) Arrays.asList(sensorId, sensorName, timeStamp, heatData, surfaceData, airData));
        heatTable.getColumns().addAll(sensorID, sensorName, timeStamp, heatData, surfaceData, airData);
        
        //heatTable.addAll("007", "Some Air Temp Sensor", 
				//"Air Temperature Sensor", timestamp, 30.5f);
        //heatTable.append(startdata);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, heatTable);
        vbox.setLayoutX(0);
	  	vbox.setLayoutY(100);
	  	vbox.setPrefWidth(680);
        heatPane.getChildren().add(vbox);
        //heatTable.add();
        
        // External temperature sensor tab
	  	externalTab = new Tab();
	  	externalTab.setText("External Temp Sensor");
	  	externalTab.setClosable(false);
	  	subTabPane.getTabs().add(externalTab);
	  	
	  	externalPane = new Pane();
	  	externalTab.setContent(externalPane);
	  	
	  	TableView<ExternalTempSensor> externalTable = new TableView<ExternalTempSensor>();
	  	final ObservableList<ExternalTempSensor> externalSensorData = FXCollections.observableArrayList(
	  			new ExternalTempSensor("005", "Some External Temp Sensor", "External Temp Sensor", timestamp, 44.5f, 66.7f),
	  			new ExternalTempSensor("008", "Some External Temp Sensor", "External Temp Sensor", timestamp, 27.5f, 20.1f)
	  			);
        final Label label2 = new Label("       External Temperature Sensor");
        label2.setFont(new Font("Arial", 20));
	  	
	  	externalTable.setEditable(true);
	  	 
        TableColumn sensorId2 = new TableColumn("Sensor ID");
        TableColumn sensorName2 = new TableColumn("Sensor Name");
        TableColumn timeStamp2 = new TableColumn("Time Stamp");
        TableColumn surfaceData2 = new TableColumn("Surface Temp Data");
        TableColumn airData2 = new TableColumn("Air Temp Data");
        
        sensorId2.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("sensorID"));
        sensorName2.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("sensorName"));
        //timestamp2.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("timestamp"));
        surfaceData2.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("externalSurfaceTemp"));
        airData2.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("externalAirTemp"));
        
        externalTable.setItems(externalSensorData);
        externalTable.getColumns().addAll(sensorId2, sensorName2, timeStamp2, surfaceData2, airData2);
 
        final VBox vbox2 = new VBox();
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 0, 0, 10));
        vbox2.getChildren().addAll(label2, externalTable);
        vbox2.setLayoutX(0);
	  	vbox2.setLayoutY(100);
	  	vbox2.setPrefWidth(600);
        externalPane.getChildren().add(vbox2);
        
        // Air temp data tab
        airTab = new Tab();
	  	airTab.setText("Air Temp Data");
	  	airTab.setClosable(false);
	  	subTabPane.getTabs().add(airTab);
        
	  	airPane = new Pane();
	  	airTab.setContent(airPane);
	  	
	  	TableView<AirTempSensor> airTable = new TableView<AirTempSensor>();
	  	final ObservableList<AirTempSensor> airSensorData = FXCollections.observableArrayList(
	  			new AirTempSensor("007", "Some Air Temp Sensor", "Air Temperature Sensor", timestamp, 30.5f),
	  			new AirTempSensor("088", "Some Air Temp Sensor", "Air Temperature Sensor", timestamp, 123.5f)
	  			);
        final Label label3 = new Label("           Air Temperature Data");
        label3.setFont(new Font("Arial", 20));
	  	
	  	airTable.setEditable(true);
	  	 
        TableColumn sensorId3 = new TableColumn("Sensor ID");
        TableColumn sensorName3 = new TableColumn("Sensor Name");
        TableColumn timeStamp3 = new TableColumn("Time Stamp");
        TableColumn airData3 = new TableColumn("Air Temp Data");
        
        
        sensorId3.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("sensorID"));
        sensorName3.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("sensorName"));
        //timestamp3.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("timestamp"));
        airData3.setCellValueFactory(new PropertyValueFactory<HeatFluxSensor, String>("airTemp"));
        
        airTable.setItems(airSensorData);
        airTable.getColumns().addAll(sensorId3, sensorName3, timeStamp3, airData3);
 
        final VBox vbox3 = new VBox();
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 0, 0, 10));
        vbox3.getChildren().addAll(label3, airTable);
        vbox3.setLayoutX(0);
	  	vbox3.setLayoutY(100);
	  	vbox3.setPrefWidth(450);
        airPane.getChildren().add(vbox3);
	  	
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



