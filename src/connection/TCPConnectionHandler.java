package connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPConnectionHandler extends Channel{

	
	OutputStream out;
	private static final int PACKETSIZE = 3;
	
	private Socket socket;
	private OnMessageReceived messageListener;
	private boolean running = false;
	
	public TCPConnectionHandler(Socket socket) {
		this.socket = socket;		 
	}

	public void registerMessageListener(OnMessageReceived messageListener) {
		this.messageListener = messageListener;
	}
	
	
	
	@Override
	public void sendMessage(byte[] data) {

		if (out != null) {
			
			try {
				out.write(data);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	
	@Override
	public void close() {
		running = false;
	}

	@Override
	public void run() {
		running = true;

		try {
			 DataInputStream in = new DataInputStream(socket.getInputStream());
			 
			 out = socket.getOutputStream();
			 
			 byte[] pack = new byte[PACKETSIZE];
			 while (running) {
				 if (in.available() > 0) {
					 in.read(pack, 0, PACKETSIZE);
					 if (pack.length != 0 && messageListener != null) {
						 messageListener.messageReceived(pack);
					 }
				 }
			 }
			 
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}

}
