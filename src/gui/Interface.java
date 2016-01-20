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
	Pane root;
	TabPane tabPane, tabPane2;
	Tab tab1, tab2;
	Tab tab3, tab4, tab5;
	Button start, stop;
	TextArea ta;
	
	
	public void start(Stage stage) throws Exception {
		  
		stage.setTitle("Sensor Reading App");
		final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());
		root = new Pane();
		tabPane = new TabPane();
	    root.getChildren().add(menuBar);
	    root.getChildren().add(tabPane);
	    scene = new Scene(root, 1250, 950, Color.WHEAT);
	    
	    
	    
	  	stage.setScene(scene);

	  	tab1 = new Tab();
	  	tab1.setText("Session");
	  	tab1.setClosable(false);
	  	tabPane.getTabs().add(tab1);
	  	tabPane.setLayoutX(0);
	  	tabPane.setLayoutY(25);
	  	
	  	Pane tabPane1 = new Pane();
	  	tab1.setContent(tabPane1);
	  	
	  	start = new Button();
	  	start.setText("Start");
	  	start.setLayoutX(20);
	  	start.setLayoutY(50);
	  	//start.setPrefWidth(100);
	  	//start.setPrefHeight(150);
	  	tabPane1.getChildren().add(start);
	  	
	  	stop = new Button();
	  	stop.setText("Stop");
	  	stop.setLayoutX(90);
	  	stop.setLayoutY(50);
	  	//stop.setPrefWidth(30);
	  	//stop.setPrefHeight(30);
	  	tabPane1.getChildren().add(stop);
	  	
	  	
	            /*new Person("Jacob", "Smith", "jacob.smith@example.com"),
	            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
	            new Person("Ethan", "Williams", "ethan.williams@example.com"),
	            new Person("Emma", "Jones", "emma.jones@example.com"),
	            new Person("Michael", "Brown", "michael.brown@example.com")*/
	  	
	  	
	  	
	  	tabPane2 = new TabPane();
	   tabPane1.getChildren().add(tabPane2);
	    
	   
	   
	    tab3 = new Tab();
	  	tab3.setText("Heat Flux Sensor");
	  	tab3.setClosable(false);
	  	tabPane2.getTabs().add(tab3);
	  	tabPane2.setLayoutX(20);
	  	tabPane2.setLayoutY(100);
	  	
	  	Pane tabPane3 = new Pane();
	  	tab3.setContent(tabPane3);
	  	
	  	TableView table = new TableView();
	  	
	  	final Label label = new Label("                        Heat Flux Sensor");
        label.setFont(new Font("Arial", 20));
	  	
	  	table.setEditable(false);
	  	 
        TableColumn sensorId = new TableColumn("Sensor ID");
        TableColumn sensorName = new TableColumn("Sensor Name");
        TableColumn timeStamp = new TableColumn("Time Stamp");
        TableColumn heatData = new TableColumn("Heat Flux Data");
        TableColumn surfaceData = new TableColumn("Surface Temp Data");
        TableColumn airData = new TableColumn("Air Temp Data");
        
        
       // table.add("Jacob", "Smith", "jacob.smith@example.com");
        
        table.getColumns().addAll(sensorId, sensorName, timeStamp, heatData, surfaceData, airData);
        //tabPane.getTabs().add(tab3);
        
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        vbox.setLayoutX(50);
	  	vbox.setLayoutY(100);
	  
        //tabPane1.getChildren().add(vbox);
	  	
        tabPane3.getChildren().add(vbox);
        
        
        
        
 
        
	  	tab4 = new Tab();
	  	tab4.setText("External Temp Sensor");
	  	tab4.setClosable(false);
	  	tabPane2.getTabs().add(tab4);
	  	
	  	Pane tabPane4 = new Pane();
	  	tab4.setContent(tabPane4);
	  	
	  	TableView table2 = new TableView();
        final Label label2 = new Label("       External Temperature Sensor");
        label2.setFont(new Font("Arial", 20));
	  	
	  	table.setEditable(true);
	  	 
        TableColumn sensorId2 = new TableColumn("Sensor ID");
        TableColumn sensorName2 = new TableColumn("Sensor Name");
        TableColumn timeStamp2 = new TableColumn("Time Stamp");
        TableColumn surfaceData2 = new TableColumn("Surface Temp Data");
        TableColumn airData2 = new TableColumn("Air Temp Data");
        
        table2.getColumns().addAll(sensorId2, sensorName2, timeStamp2, surfaceData2, airData2);
 
        final VBox vbox2 = new VBox();
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 0, 0, 10));
        vbox2.getChildren().addAll(label2, table2);
        vbox2.setLayoutX(50);
	  	vbox2.setLayoutY(100);
        tabPane4.getChildren().add(vbox2);
	  	
		
        
        
        
        
        tab5 = new Tab();
	  	tab5.setText("Air Temp Data");
	  	tab5.setClosable(false);
	  	tabPane2.getTabs().add(tab5);
        
	  	Pane tabPane5 = new Pane();
	  	tab5.setContent(tabPane5);
	  	
        TableView table3 = new TableView();
        final Label label3 = new Label("           Air Temperature Data");
        label3.setFont(new Font("Arial", 20));
	  	
	  	table3.setEditable(true);
	  	 
        TableColumn sensorId3 = new TableColumn("Sensor ID");
        TableColumn sensorName3 = new TableColumn("Sensor Name");
        TableColumn timeStamp3 = new TableColumn("Time Stamp");
        TableColumn surfaceData3= new TableColumn("Surface Temp Data");
        TableColumn airData3 = new TableColumn("Air Temp Data");
        
        table3.getColumns().addAll(sensorId3, sensorName3, timeStamp3, surfaceData3, airData3);
 
        final VBox vbox3 = new VBox();
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 0, 0, 10));
        vbox3.getChildren().addAll(label3, table3);
        vbox3.setLayoutX(50);
	  	vbox3.setLayoutY(100);
        tabPane5.getChildren().add(vbox3);
        
	  	
	  	
		tabPane1.getChildren().add(chart);
	  	xAxis.setLabel("Time");
	  	yAxis.setLabel("Temperature");
	  	chart.setTitle("Sensor's Graph");
	  	chart.setLayoutX(650);
	  	chart.setLayoutY(250);
	  	//chart.setPrefWidth(30);
	  	//chart.setPrefHeight(30);

	  	tab2 = new Tab();
	  	tab2.setText("Historical Data");
	  	tab2.setClosable(false);
	  	tabPane.getTabs().add(tab2);
	  	
	  	
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


