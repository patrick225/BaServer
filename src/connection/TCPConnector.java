package connection;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPConnector extends Thread {

	public static final int PORT_CLIENT = 55055;
	public static final int PORT_ROBOT = 44044;
	
	private ConnectionListener connectionListener;
	
	private int connectedClients = 0; 
	
	private int port;
		
	
	public TCPConnector(int port, ConnectionListener conListner) {
		this.connectionListener = conListner;
		this.port = port;
		
		new TCPConnectionService(port).start();
				
		start();
	}
	
	@Override
	public void run() {
		super.run();
				
		 try {			 		
			 ServerSocket serverSocket = new ServerSocket(port);
			
			 while (connectedClients < ConnectionManager.NUMBER_OF_CONNECTIONS) {
				 System.out.println(port + ": Waiting for Device...");
				 Socket client = serverSocket.accept();
				 System.out.println(port + ": Connected with Device!");
				 connectionListener.newConnection(client);
			 }
			 
			 serverSocket.close();
			 			 
		 } catch (Exception e) {
			 System.out.println(port + ": Error");
			 e.printStackTrace();
		 } 
		
		
	}
			
}

