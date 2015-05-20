import connection.ConnectionManager;



public class Main {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Server started...");
		
		ConnectionManager cm = new ConnectionManager();
		
		
		
		while (!cm.isPlayer1Ready()) {
		}
		Player player1 = new Player(cm.getClientChannelPlayer1(), cm.getRobotChannelPlayer1());
		
		
		
		


	}

}
