package connection;

public abstract class Channel extends Thread {

	
	
	public abstract void sendMessage(byte[] data);
	public abstract void close();
	public abstract void run();
}
