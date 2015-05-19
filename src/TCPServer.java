import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer extends Thread {

	private boolean running = false;
	private OutputStream out;
	private OnMessageReceived messageListener;
	
	private int port;
		
	
	public TCPServer(OnMessageReceived messageListener, int port) {
		this.messageListener = messageListener;
		this.port = port;
	}
	
	


	public void sendMessage(byte[] data) {
		if (out != null) {			
			try {
				out.write(data);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void run() {
		super.run();
		
		running = true;
		
		 try {
			 
			 System.out.println("S: Connecting...");
			 ServerSocket serverSocket = new ServerSocket(port);
			
			 Socket client = serverSocket.accept();
			 System.out.println("S: Receiving...");
			 
			 try {
				 DataInputStream in = new DataInputStream(client.getInputStream());
				 
				 out = client.getOutputStream();
				 
				 byte[] command = new byte[3];
				 while (in.read(command, 0, 3) != -1) {
					 
					 if (command.length != 0 && messageListener != null) {
						// sendMessage(message);
						 messageListener.messageReceived(command);
					 }
				 }
				 System.out.println("Stop");
				 
			 } catch (Exception e) {
				 System.out.println("S: Error");
				 e.printStackTrace();
			 } finally {
				 client.close();
				 serverSocket.close();
				 System.out.println("S: Done.");
			 }
			 
		 } catch (Exception e) {
			 System.out.println("S: Error");
			 e.printStackTrace();
		 } 
		
		
	}
		
	
	
}
