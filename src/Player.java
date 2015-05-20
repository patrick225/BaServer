import connection.Channel;
import connection.OnMessageReceived;


public class Player {

	private int score;
	
	private Channel toClient;
	private Channel toRobot;
	
	public Player(Channel toClient, Channel toRobot) {
		
		this.toClient = toClient;
		this.toRobot = toRobot;
				
	}
	
	
	

		
}
