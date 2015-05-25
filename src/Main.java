import connection.Channel;
import connection.ConnectionManager;
import connection.OnPlayerReady;



public class Main {
	

	private static Player player1;
	private static Player player2;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Server started...");
		
		ConnectionManager cm = new ConnectionManager(new OnPlayerReady() {
			
			@Override
			public void playerIsReady(Channel client, Channel robot) {
				if (player1 != null)
					player1 = new Player(client, robot);
				else
					player2 = new Player(client, robot);


			}
		});
		
		
		
		
		
		


	}

}
