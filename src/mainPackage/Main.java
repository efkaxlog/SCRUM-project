package mainPackage;

import gui.Interface;

import java.sql.SQLException;

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
