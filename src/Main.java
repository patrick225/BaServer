import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import connection.Channel;
import connection.ConnectionManager;
import connection.OnMessageReceived;



public class Main {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		restart();
	}
	
	
	public static void restart() {
			
		try {
			new Game();
		} catch (Exception e) {
			System.out.println("Fatal Error");
			e.printStackTrace();
			System.out.println("Restarting...");
			restart();
		}
	}

}
