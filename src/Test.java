
public class Test {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		System.out.println("start");

		TCPServer serverToClient = null;
		TCPServer serverToRobot = null;



		OnMessageReceived listenerRobot = new OnMessageReceived(serverToRobot) {
			
			@Override
			public void messageReceived(byte[] data) {

				//@TODO incoming messages from robot
			}
		};
		
		serverToRobot = new TCPServer(listenerRobot, 44044);
		serverToRobot.start();
		
		
		
		OnMessageReceived listenerClient = new OnMessageReceived(serverToRobot) {

			@Override
			public void messageReceived(byte[] command) {
				System.out.println("C: " + command[0] + " " + command[1] + " "
						+ command[2]);
				
				if (toRobot != null) {
					toRobot.sendMessage(getCommandToRobot(command));
				} else {
					System.out.println("toRobot null!");
					
				}
						
			}
		};
		
		serverToClient = new TCPServer(listenerClient, 55055);
		serverToClient.start();
		
		
		
		


	}

}
