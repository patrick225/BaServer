import connection.Channel;
import connection.ConnectionManager;
import connection.OnMessageReceived;
import connection.OnPlayerReady;


public class Game {

	ConnectionManager cm;
	
	Channel client1;
	Channel robot1;
	
	Channel client2;
	Channel robot2;
	public Game() {
		
		cm = ConnectionManager.getInstance();

		cm.registerPlayerReadyListener(new OnPlayerReady() {
			
			@Override
			public void playerIsReady(int player, boolean state) {

				switch (player) {
				case 1:
					if (state) {
						client1 = cm.getChannelClient1();
						robot1 = cm.getChannelRobot1();
						client1.registerMessageListener(messageListenerClient1);
					} else {
						client1.unregisterMessageListener();
					}
					break;
				case 2:

				}
			}
		});
		

	}
	
	private void forwardPlayer1(byte[] data) {
		robot1.sendMessage(new RobotCommand(data).getBytes());
	}
	
	
	
	OnMessageReceived messageListenerClient1 = new OnMessageReceived() {
		
		@Override
		public void messageReceived(byte[] data) {
			forwardPlayer1(data);
		}
	};
	

}
