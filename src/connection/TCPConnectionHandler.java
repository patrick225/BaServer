package connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPConnectionHandler implements Channel {

	
	OutputStream out;
	private static final int PACKETSIZE = 3;
	
	private Socket socket;
	private boolean running = false;
	
	protected OnMessageReceived messageListener;
	private StateListener stateListener;
	
	public TCPConnectionHandler(StateListener stateListener) {
		this.stateListener = stateListener;
	}
	
	public void setSocket (Socket socket) {
		this.socket = socket;
		new Thread(this).start();
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
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		running = true;
		System.out.println("thread started");

		try {
			 DataInputStream in = new DataInputStream(socket.getInputStream());
			 
			 out = socket.getOutputStream();
			 
			 stateListener.stateChanged(this, ConnectionManager.STATE_CONNECTED);
			 
			 byte[] pack = new byte[PACKETSIZE];
			 while (running && in.read(pack, 0, PACKETSIZE) != -1) {
				 
				 if (pack.length != 0 && messageListener != null) {
					 messageListener.messageReceived(pack);
				 }
			 }
			 
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 try {
				 System.out.println("Close socket!");
				socket.close();
				stateListener.stateChanged(this, ConnectionManager.STATE_DISCONNECTED);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}

}
