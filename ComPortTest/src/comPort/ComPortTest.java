package comPort;

import java.util.TooManyListenersException;

public class ComPortTest {
	public static void main(String[] args) {
		// ComPortDevice ports = new ComPortDevice();
		final int DATA_SIZE = 6;
		final int NUM_OF_DATA = 7;
		ComPort ports = new ComPort();
		DataInterface dataInterface = new DataInterface(NUM_OF_DATA, DATA_SIZE);
		GetDataTest test = new GetDataTest(dataInterface, NUM_OF_DATA);
		try {
			System.out.println(ports.listComPort());
			ports.connectPort(ports.listComPort());
			ports.setDataInterface(dataInterface);
			ports.initIOStream();
			ports.initListener();
			new Thread(test).start();
			// ports.createReceiveThrd();
		} catch (TooManyListenersException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
