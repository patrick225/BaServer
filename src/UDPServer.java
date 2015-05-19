import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;


public class UDPServer extends Thread {
	
	private final int PACKETSIZE = 1024;
	private final int PORT = 7890;

	private DatagramSocket serverSocket;
	private InetAddress clientAddress;
	private int clientPort;
	private OnMessageReceived messageListener;
	
	private ArrayList<byte[]> picData;
	

	public UDPServer(OnMessageReceived messageListener) {
		
		this.messageListener = messageListener;
		
	}
	
	public void sendMessage(byte[] data) {
		data = new byte[1024];
		for(int i=0;i<data.length;i++) {
			data[i] = new Byte("1");
		}
		if (serverSocket != null) {
			try {
				DatagramPacket dataPacket = new DatagramPacket(data, data.length, clientAddress, clientPort);
				serverSocket.send(dataPacket);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		
		super.run();
		
		try {
			
			System.out.println("Waiting...");
			
			serverSocket = new DatagramSocket(PORT);
			DatagramPacket rec = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
			while(true) {
				rec = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
				serverSocket.receive(rec);
				
				clientAddress = rec.getAddress();
				clientPort = rec.getPort();
				
				messageListener.messageReceived(null);
				if(rec.getData()[0] == 0x01 ) {
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
