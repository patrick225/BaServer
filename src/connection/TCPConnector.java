package connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPConnector extends Thread {

	public static final int PORT_CLIENT = 55055;
	public static final int PORT_ROBOT = 44044;
	
	private ConnectionListener connectionListener;
		
	private int port;
	
	private ServerSocket serverSocket;
		
	
	public TCPConnector(int port, ConnectionListener conListner) {
		this.connectionListener = conListner;
		this.port = port;
		
		new TCPConnectionService(port).start();
				
		start();
	}
	
	public void close () {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		super.run();
				
		 try {			 		
			 serverSocket = new ServerSocket(port);
			
			 while (true) {
				 System.out.println(port + ": Waiting for Device...");
				 Socket client = serverSocket.accept();
				 System.out.println(port + ": Connected with Device!");
				 connectionListener.newConnection(client);
			 }
			 			 			 
		 } 
		 catch (Exception e) {

			 System.out.println("Socket was closed!");
		 } 
		
		
	}
			
}

