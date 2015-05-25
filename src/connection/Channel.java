package connection;

public interface Channel extends Runnable {

	public void registerMessageListener(OnMessageReceived messageListener);
	public void sendMessage(byte[] data);
	public void close();
}
