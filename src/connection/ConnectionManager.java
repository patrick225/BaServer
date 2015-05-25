package connection;

import java.util.HashMap;


public class ConnectionManager {
	
	private static ConnectionManager instance = null;

	public static final int STATE_CONNECTED = 1;
	public static final int STATE_DISCONNECTED = 2;

	private TCPConnector connectClient = new TCPConnector(
			TCPConnector.PORT_CLIENT);
	private TCPConnector connectRobot = new TCPConnector(
			TCPConnector.PORT_ROBOT);

	private TCPConnectionHandler controller1Handler;
	private TCPConnectionHandler controller2Handler;
	private TCPConnectionHandler robot1Handler;
	private TCPConnectionHandler robot2Handler;

	private Channel controller1Channel;
	private Channel controller2Channel;
	private Channel robot1Channel;
	private Channel robot2Channel;
	
	private OnPlayerReady playerReadyListener;
	private boolean player1Ready = false;
	private boolean player2Ready = false;
	

	public ConnectionManager() {

		initTcp();
	}
	
	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	private void initTcp() {
		StateListener stateListener = new StateListener() {

			@Override
			public void stateChanged(Channel who, int state) {

				if (state == STATE_CONNECTED) {
					if (who == controller1Handler) {
						controller1Channel = controller1Handler;
						
					}
					else if (who == controller2Handler) {
						controller2Channel = controller2Handler;
					}
					else if (who == robot1Handler) {
						robot1Channel = robot1Handler;
					}
					else if (who == robot2Handler) {
						robot2Channel = robot2Handler;
					}
				} else {
					if (who == controller1Handler) {
						controller1Channel = null;
						controller1Handler = new TCPConnectionHandler(this);
						connectClient.connectHandler(controller1Handler);
					} else if (who == controller2Handler) {
						controller2Channel = null;
						controller2Handler = new TCPConnectionHandler(this);
						connectClient.connectHandler(controller2Handler);

					} else if (who == robot1Handler) {
						robot1Channel = null;
						robot1Handler = new TCPConnectionHandler(this);
						connectRobot.connectHandler(robot1Handler);
					} else if (who == robot2Handler) {
						robot2Channel = null;
						robot2Handler = new TCPConnectionHandler(this);
						connectRobot.connectHandler(robot2Handler);

					}
				}
				printStatus();
				checkForReadyPlayers();

			}
		};

		controller1Handler = new TCPConnectionHandler(stateListener);
		controller2Handler = new TCPConnectionHandler(stateListener);
		robot1Handler = new TCPConnectionHandler(stateListener);
		robot2Handler = new TCPConnectionHandler(stateListener);

		connectClient.connectHandler(controller1Handler);
		connectClient.connectHandler(controller2Handler);

		connectRobot.connectHandler(robot1Handler);
		connectRobot.connectHandler(robot2Handler);
	}
	
	public void registerPlayerReadyListener(OnPlayerReady playerListener) {
		playerReadyListener = playerListener;
	}
	
	private void checkForReadyPlayers() {
		
		// player1 was not ready, but is now
		if (!player1Ready && getChannelClient1() != null && getChannelRobot1() != null) {
			playerReadyListener.playerIsReady(1, true);
		}
		
		// player1 was ready, but is no more
		if (player1Ready && !(getChannelClient1() != null && getChannelRobot1() != null)) {
			playerReadyListener.playerIsReady(1, false);
		}
		
		// player2 was not ready, but is now
		if (!player2Ready && getChannelClient2() != null && getChannelRobot2() != null) {
			playerReadyListener.playerIsReady(2, true);
		}
		
		// player2 was ready, but is no more
		if (player2Ready && !(getChannelClient2() != null && getChannelRobot2() != null)) {
			playerReadyListener.playerIsReady(2, false);
		}
		
	}

	private void printStatus() {

		System.out.println("------------------");
		System.out.println("Connections:");
		System.out.println("Controller1: "
				+ String.valueOf(controller1Channel != null));
		System.out.println("Controller2: "
				+ String.valueOf(controller2Channel != null));
		System.out.println("Robot1: " + String.valueOf(robot1Channel != null));
		System.out.println("Robot2: " + String.valueOf(robot2Channel != null));
		System.out.println("------------------");

	}
	
	
	public Channel getChannelClient1() {
		return controller1Channel;
	}
	
	public Channel getChannelClient2() {
		return controller2Channel;
	}
	
	public Channel getChannelRobot1() {
		return robot1Channel;
	}
	
	public Channel getChannelRobot2() {
		return robot2Channel;
	}
}
