package connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class TCPConnector extends Thread {

	public static final int PORT_CLIENT = 55055;
	public static final int PORT_ROBOT = 44044;
	
		
	private int port;
	
	private ServerSocket serverSocket;
	
	private ArrayList<TCPConnectionHandler> connectionQueue = new ArrayList<TCPConnectionHandler>();
		
	
	public TCPConnector(int port) {
		this.port = port;		
		new TCPConnectionService(port).start();
				
		start();
	}
	
	public void connectHandler(TCPConnectionHandler handler) {
		connectionQueue.add(handler);
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
				 
				 if (connectionQueue.size() != 0) {
					 System.out.println(port + ": Waiting for Device...");
					 Socket client = serverSocket.accept();
					 connectionQueue.get(0).setSocket(client);
					 System.out.println(port + ": Connected with Device!");
					 connectionQueue.remove(0);
				 }
			 }
			 			 			 
		 } 
		 catch (Exception e) {

			 System.out.println("Socket was closed!");
		 } 
		
		
	}
			
}

