package connection;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class TCPConnectionService extends Thread {

	private DatagramSocket socket; 
	private int port;
	
	public TCPConnectionService(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		
		try {
			socket = new DatagramSocket(port);
			byte[] buffer = new byte[0];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			while (true) {
				socket.receive(packet);					
				socket.send(packet);
				
				System.out.println("Got new IP-Request");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			socket.close();
		}
		
	}
}
