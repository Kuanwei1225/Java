package comPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import gnu.io.*;

public class ComPortDevice {
	final int TIME_OUT = 2000;

	private SerialPort serialPort;
	private InputStream input;
	private OutputStream output;
	private int baudRate = 115200;
	private boolean isConnect = false;
	private ReceiveThrd receiveThrd;

	InputStream inPort;

	// Search serial port
	@SuppressWarnings("rawtypes")
	public String listComPort() throws NoSuchElementException {
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier cpIdentifier = (CommPortIdentifier) ports
				.nextElement();
		return cpIdentifier.getName();
	}

	// open serial port
	public void connectPort(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		this.serialPort = (SerialPort) portIdentifier.open("ComPort", TIME_OUT);
		// setup connection parameters
		this.serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		this.isConnect = true;
	}

	// close serial port
	public void disconnectPort() throws IOException {
		this.serialPort.removeEventListener();
		this.serialPort.close();
		this.input.close();
		this.output.close();
		this.isConnect = false;
	}

	public boolean isConnected() {
		return this.isConnect;
	}

	// Initial input/output port
	public void initIOStream() throws Exception {
		this.input = this.serialPort.getInputStream();
		this.output = this.serialPort.getOutputStream();
	}
	public void createReceiveThrd(){
		this.receiveThrd = new ReceiveThrd(this.input);
		new Thread(this.receiveThrd).start();
	}
}
