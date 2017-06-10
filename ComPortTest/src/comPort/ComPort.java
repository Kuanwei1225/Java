package comPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.*;

public class ComPort implements SerialPortEventListener {
	final int TIME_OUT = 2000;
	final int NUM_OF_DATA = 7;
	final int DATA_SIZE = 6;
	final int BUFF_SIZE = NUM_OF_DATA * DATA_SIZE;
	//final int BUFF_SIZE = 128;

	private SerialPort serialPort;
	private InputStream input;
	private OutputStream output;
	private int baudRate = 115200;
	private boolean isConnect = false;
	private byte[] buffer = new byte[BUFF_SIZE];
	DataInterface datainterface;

	InputStream inPort;

	// Search serial port
	@SuppressWarnings("rawtypes")
	public String listComPort() {
		try {
			Enumeration ports = CommPortIdentifier.getPortIdentifiers();
			CommPortIdentifier cpIdentifier = (CommPortIdentifier) ports
					.nextElement();
			return cpIdentifier.getName();
		} catch (Exception e) {
			return "Empty";
		}
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

	// initial linstener
	public void initListener() throws TooManyListenersException {
		this.serialPort.addEventListener(this);
		this.serialPort.notifyOnDataAvailable(true);
	}

	public void setDataInterface(DataInterface obj) {
		this.datainterface = obj;
	}

	@Override
	public void serialEvent(SerialPortEvent evnt) {

		try {
			if (evnt.getEventType() != SerialPortEvent.DATA_AVAILABLE)
				return;
			int len = this.input.read(buffer);
			System.out.println("len: " + len);
			if (len == this.BUFF_SIZE) {
				this.datainterface.setData(buffer);
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

}
