import connection.Channel;
import connection.OnMessageReceived;

public class Player {

	private int score;

	private Channel toClient;
	private Channel toRobot;

	public Player(Channel toClient, Channel toRobot) {

		this.toClient = toClient;
		this.toRobot = toRobot;

		System.out.println("registerListener...");
		toClient.registerMessageListener(new OnMessageReceived() {

			@Override
			public void messageReceived(byte[] data) {
				 handleMessage(data);
			}
		});

		new Thread(toClient).start();
		new Thread(toRobot).start();

		System.out.println("messagelistener started");
	}

	private void handleMessage(byte[] data) {

		RobotCommand cmd = new RobotCommand(data);
		toRobot.sendMessage(cmd.getBytes());
	}

}
