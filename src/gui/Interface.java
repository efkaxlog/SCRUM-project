package gui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Interface extends Application {
	
	
	
	  public Interface() {
			
	  }
	
	  private MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty)
	  {
		  final MenuBar menuBar = new MenuBar();
		  
		  
		  final Menu fileMenu = new Menu("File");
		  
	      fileMenu.getItems().add(new MenuItem("New"));
	      fileMenu.getItems().add(new MenuItem("Save"));
	      fileMenu.getItems().add(new MenuItem("About"));
	      fileMenu.getItems().add(new MenuItem("Exit"));
	      menuBar.getMenus().add(fileMenu);
		  
		  
		  
		  menuBar.prefWidthProperty().bind(menuWidthProperty);
		  return menuBar; 
	  }
	
	public static void main(String[] args) {
			launch(args);
	}
	
	
	Scene scene;
	Pane root, sessionPane; //Pane for main tab
	Pane heatPane, externalPane, airPane;  //Pane for sub tabs 
	TabPane mainTabPane, subTabPane;
	Tab sessionTab, historicalTab;  // Main tabs at the top
	Tab heatTab, externalTab, airTab; //Sub tabs for displaying different tables
	Button start, stop;
	
	
	public void start(Stage stage) throws Exception {
		  
		stage.setTitle("Sensor Reading App");
		final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());
		root = new Pane();
		mainTabPane = new TabPane();
	    root.getChildren().add(menuBar);
	    root.getChildren().add(mainTabPane);
	    scene = new Scene(root, 1250, 950, Color.WHEAT);
	    
	    
	    
	  	stage.setScene(scene);

	  	sessionTab = new Tab();
	  	sessionTab.setText("Session");
	  	sessionTab.setClosable(false);
	  	mainTabPane.getTabs().add(sessionTab);
	  	mainTabPane.setLayoutX(0);
	  	mainTabPane.setLayoutY(25);
	  	
	  	sessionPane = new Pane();
	  	sessionTab.setContent(sessionPane);
	  	
	  	start = new Button();
	  	start.setText("Start");
	  	start.setLayoutX(20);
	  	start.setLayoutY(50);
	  	//start.setPrefWidth(100);
	  	//start.setPrefHeight(150);
	  	sessionPane.getChildren().add(start);
	  	
	  	stop = new Button();
	  	stop.setText("Stop");
	  	stop.setLayoutX(90);
	  	stop.setLayoutY(50);
	  	//stop.setPrefWidth(30);
	  	//stop.setPrefHeight(30);
	  	sessionPane.getChildren().add(stop);
	  	
	  	
	  	
	   subTabPane = new TabPane();
	   sessionPane.getChildren().add(subTabPane);
	    
	   
	   
	    heatTab = new Tab();
	  	heatTab.setText("Heat Flux Sensor");
	  	heatTab.setClosable(false);
	  	subTabPane.getTabs().add(heatTab);
	  	subTabPane.setLayoutX(20);
	  	subTabPane.setLayoutY(100);
	  	
	  	heatPane = new Pane();
	  	heatTab.setContent(heatPane);
	  	
	  	TableView heatTable = new TableView();
	  	
	  	final Label label = new Label("                        Heat Flux Sensor");
        label.setFont(new Font("Arial", 20));
	  	
	  	heatTable.setEditable(false);
	  	 
        TableColumn sensorId = new TableColumn("Sensor ID");
        TableColumn sensorName = new TableColumn("Sensor Name");
        TableColumn timeStamp = new TableColumn("Time Stamp");
        TableColumn heatData = new TableColumn("Heat Flux Data");
        TableColumn surfaceData = new TableColumn("Surface Temp Data");
        TableColumn airData = new TableColumn("Air Temp Data");
        
        
       // heatTable.add("Jacob", "Smith", "jacob.smith@example.com");
        
        heatTable.getColumns().addAll(sensorId, sensorName, timeStamp, heatData, surfaceData, airData);
        //mainTabPane.getTabs().add(heatTab);
        
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, heatTable);
        vbox.setLayoutX(50);
	  	vbox.setLayoutY(100);
	  
        //sessionPane.getChildren().add(vbox);
	  	
        heatPane.getChildren().add(vbox);
        
        
        
        
 
        
	  	externalTab = new Tab();
	  	externalTab.setText("External Temp Sensor");
	  	externalTab.setClosable(false);
	  	subTabPane.getTabs().add(externalTab);
	  	
	  	externalPane = new Pane();
	  	externalTab.setContent(externalPane);
	  	
	  	TableView externalTable = new TableView();
        final Label label2 = new Label("       External Temperature Sensor");
        label2.setFont(new Font("Arial", 20));
	  	
	  	externalTable.setEditable(true);
	  	 
        TableColumn sensorId2 = new TableColumn("Sensor ID");
        TableColumn sensorName2 = new TableColumn("Sensor Name");
        TableColumn timeStamp2 = new TableColumn("Time Stamp");
        TableColumn surfaceData2 = new TableColumn("Surface Temp Data");
        TableColumn airData2 = new TableColumn("Air Temp Data");
        
        externalTable.getColumns().addAll(sensorId2, sensorName2, timeStamp2, surfaceData2, airData2);
 
        final VBox vbox2 = new VBox();
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 0, 0, 10));
        vbox2.getChildren().addAll(label2, externalTable);
        vbox2.setLayoutX(50);
	  	vbox2.setLayoutY(100);
        externalPane.getChildren().add(vbox2);
	  	
		
        
        
        
        
        airTab = new Tab();
	  	airTab.setText("Air Temp Data");
	  	airTab.setClosable(false);
	  	subTabPane.getTabs().add(airTab);
        
	  	airPane = new Pane();
	  	airTab.setContent(airPane);
	  	
        TableView airTable = new TableView();
        final Label label3 = new Label("           Air Temperature Data");
        label3.setFont(new Font("Arial", 20));
	  	
	  	airTable.setEditable(true);
	  	 
        TableColumn sensorId3 = new TableColumn("Sensor ID");
        TableColumn sensorName3 = new TableColumn("Sensor Name");
        TableColumn timeStamp3 = new TableColumn("Time Stamp");
        TableColumn surfaceData3= new TableColumn("Surface Temp Data");
        TableColumn airData3 = new TableColumn("Air Temp Data");
        
        airTable.getColumns().addAll(sensorId3, sensorName3, timeStamp3, surfaceData3, airData3);
 
        final VBox vbox3 = new VBox();
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 0, 0, 10));
        vbox3.getChildren().addAll(label3, airTable);
        vbox3.setLayoutX(50);
	  	vbox3.setLayoutY(100);
        airPane.getChildren().add(vbox3);
        
	  	
	  	
		sessionPane.getChildren().add(chart);
	  	xAxis.setLabel("Time");
	  	yAxis.setLabel("Temperature");
	  	chart.setTitle("Sensor's Graph");
	  	chart.setLayoutX(650);
	  	chart.setLayoutY(250);
	  	//chart.setPrefWidth(30);
	  	//chart.setPrefHeight(30);

	  	historicalTab = new Tab();
	  	historicalTab.setText("Historical Data");
	  	historicalTab.setClosable(false);
	  	mainTabPane.getTabs().add(historicalTab);
	  	
	  	
	  	stage.show();
	}
	
	NumberAxis xAxis = new NumberAxis();
	NumberAxis yAxis = new NumberAxis();
	ScatterChart<Number,Number> chart = new ScatterChart<Number,Number>(xAxis,yAxis);

	
	void drawChart(ArrayList<Customer> customers)
	{
	chart.getData().clear();
	XYChart.Series series = new XYChart.Series();
	series.setName("Model Data");
	for(Customer c : customers)
	series.getData().add(new XYChart.Data(c.age, c.balance));
	chart.getData().add(series);
	
	}
	

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


