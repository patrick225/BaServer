package connection;

import java.net.Socket;

public interface ConnectionListener {

	
	public void newConnection(Socket socket);
}
