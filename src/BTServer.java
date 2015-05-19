import javax.bluetooth.*;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
public class BTServer {

	
	
	public BTServer() {
		// TODO Auto-generated constructor stub
		LocalDevice ld = null;
		
		StreamConnectionNotifier notifier;
		StreamConnection conn = null;
		try {
			ld = LocalDevice.getLocalDevice();
			
			ld.setDiscoverable(DiscoveryAgent.GIAC);
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
