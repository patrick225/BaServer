package connection;

import java.net.Socket;

public class ConnectionManager {
			
	public static final int NUMBER_OF_CONNECTIONS = 2;
	
	Socket[] tcpClientPlayers = new Socket[NUMBER_OF_CONNECTIONS];
	Socket[] tcpRobotPlayers = new Socket[NUMBER_OF_CONNECTIONS];
		

	public ConnectionManager() {
		
		new TCPConnector(TCPConnector.PORT_CLIENT, 
				new ConnectionListener() {
					
					@Override
					public void newConnection(Socket socket) {

						if (tcpClientPlayers[0] != null) {
							tcpClientPlayers[0] = socket;
						} else {
							tcpClientPlayers[1] = socket;
						}
						
					}
				});
		
		new TCPConnector(TCPConnector.PORT_ROBOT,
				new ConnectionListener() {
					
					@Override
					public void newConnection(Socket socket) {
						
						if (tcpRobotPlayers[0] != null) {
							tcpRobotPlayers[0] = socket;
						} else {
							tcpRobotPlayers[1] = socket;
						}						
					}
				});
		
		
	}
	
	public boolean isPlayer1Ready() {
		
		// TODO spaeter erst ready wenn auch robot verbunden ist
		if (tcpClientPlayers[0] != null) {
			return true;
		}
		return false;
	}
	
	public Channel getClientChannelPlayer1() {	
		
		if (tcpClientPlayers[0] != null) {
			return new TCPConnectionHandler(tcpClientPlayers[0]);
		}
		
		return null;
	}
	
	public Channel getRobotChannelPlayer1() {
		
		if (tcpRobotPlayers[0] != null) {
			return new TCPConnectionHandler(tcpRobotPlayers[0]);
		}
		
		return null;
	}

}
