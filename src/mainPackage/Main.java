package mainPackage;

import gui.Interface;
import javafx.application.Application;

public class Main {
	
	static Interface gui;
	

	/**
	 * @param args
	 * @throws SQLException
	 */
	
	public Main() {
		
	}
	
	
	
	public static void main(String[] args) {
		
		gui = new Interface();
		gui.runGUI(args);
	}

}
