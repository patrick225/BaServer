package connection;


public abstract class OnMessageReceived {

	protected TCPConnector toRobot;
	public OnMessageReceived() {
		
	}
	
	protected byte[] getCommandToRobot(byte[] command) {
		
		byte[] toRobot = new byte[6];
		
		toRobot[0] = (byte) 0xFF; //startByte
		toRobot[1] = (byte) 0x00; //Camera
		toRobot[2] = command[2]; //shot
		toRobot[3] = command[0]; //left motor
		toRobot[4] = command[1]; //left motor
		toRobot[5] = (byte) ((byte) 0xFF - (( toRobot[1] + toRobot[2] + toRobot[3] + toRobot[4] ) % (byte) 0xFF));  //left motor
		
		return toRobot;
	}
	public abstract void messageReceived(byte[] data);
}
