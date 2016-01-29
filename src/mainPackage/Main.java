package mainPackage;

import gui.Interface;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
	
	Interface gui;
	

	/**
	 * @param args
	 * @throws SQLException
	 */
	
	public Main() {
		Interface.launch(Interface.class);
	}
	
	
	
	public static void main(String[] args) {
		Main main = new Main();
	}

}
