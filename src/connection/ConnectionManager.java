package connection;

import java.net.Socket;

public class ConnectionManager {
			
	private OnPlayerReady playerReadyListener;
	
	private Socket tcpClientPlayer1;
	private Socket tcpRobotPlayer1;
	
	private Socket tcpClientPlayer2;
	private Socket tcpRobotPlayer2;
	
	private int tcpClientCount = 0;
	private int tcpRobotCount = 0;
	
	private TCPConnector connectorClient;
	private TCPConnector connectorRobot;
		

	public ConnectionManager(OnPlayerReady playerReadyListener) {
		
		this.playerReadyListener = playerReadyListener;
		
		connectorClient = new TCPConnector(TCPConnector.PORT_CLIENT, 
				new ConnectionListener() {
					
					@Override
					public void newConnection(Socket socket) {
						
						if (tcpClientCount == 0) {
							tcpClientPlayer1 = socket;
							System.out.println("Client Player 1 connected!");
							playerReady();
							tcpClientCount++;
						} else {
							tcpClientPlayer2 = socket;
							System.out.println("Client Player 2 connected!");
							closeTcpConnector("client");
							playerReady();
							System.out.println("All Clients connected!");
						}
						
					}
				});
		
		connectorRobot = new TCPConnector(TCPConnector.PORT_ROBOT,
				new ConnectionListener() {
					
					@Override
					public void newConnection(Socket socket) {
						
						if (tcpRobotCount == 0) {
							tcpRobotPlayer1 = socket;
							System.out.println("Robot Player 1 connected!");
							playerReady();
							tcpRobotCount++;
						} else {
							tcpRobotPlayer2 = socket;
							System.out.println("Robot Player 2 connected!");
							closeTcpConnector("robot");
							playerReady();
							System.out.println("All Robots connected!");
						}
					}
				});

		
		
	}
	
	private void closeTcpConnector(String which) {
		if (which.equals("client"))
			connectorClient.close();
		if (which.equals("robot"))
			connectorRobot.close();
	}
	
	
	static boolean player1Triggered = false;
	static boolean player2Triggered = false;
	private void playerReady() {
		
		if (tcpClientPlayer1 != null && tcpRobotPlayer1 != null && !player1Triggered) {
			playerReadyListener.playerIsReady(getChannel("client1"), getChannel("robot1"));
			System.out.println("player 1 triggered");
			player1Triggered = true;
		}
		if (tcpClientPlayer2 != null && tcpRobotPlayer2 != null && !player2Triggered) {
			playerReadyListener.playerIsReady(getChannel("client2"), getChannel("robot2"));
			System.out.println("player 2 triggered");
			player2Triggered = true;
		}
	}
	
	public Channel getChannel(String from) {
		
		Channel channel = null;
		
		if (from.equals("client1")) {
			if (tcpClientPlayer1 != null)
				channel = new TCPConnectionHandler(tcpClientPlayer1);
			
		} else if (from.equals("client2")) {
			if (tcpClientPlayer2 != null)
				channel = new TCPConnectionHandler(tcpClientPlayer2);
			
		} else if (from.equals("robot1")) {
			if (tcpRobotPlayer1 != null)
				channel = new TCPConnectionHandler(tcpRobotPlayer1);
			
		} else if (from.equals("robot2")) {
			if (tcpRobotPlayer2 != null)
				channel = new TCPConnectionHandler(tcpRobotPlayer2);
		}
		
		return channel;
	}

}
