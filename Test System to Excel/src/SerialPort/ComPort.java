package SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import gnu.io.*;

public class ComPort implements SerialPortEventListener {
	final int TIME_OUT = 250;
	final int SLEEP_TIME = 1;
	//final int NUM_OF_DATA = 7;
	//final int DATA_SIZE = 6;
	//final int BUFF_SIZE = NUM_OF_DATA * DATA_SIZE;

	private SerialPort serialPort;
	private InputStream input;
	private OutputStream output;
	private int baudRate = 115200;
	private boolean isConnect = false;
	private byte[] buffer;
	DataInterface dataInterface;

	public ComPort(int buffSize) {
		buffer = new byte[buffSize];
	}
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
		CommPort commPort = portIdentifier.open(this.getClass().getName(),
				TIME_OUT);
		// this.serialPort = (SerialPort) portIdentifier.open("ComPort",
		// TIME_OUT);
		this.serialPort = (SerialPort) commPort;
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
	}/*
	 * public void resetListener() throws TooManyListenersException {
	 * 
	 * this.serialPort.removeEventListener();
	 * this.serialPort.addEventListener(this); }
	 */

	public void setDataTransferInterface(DataInterface obj) {
		this.dataInterface = obj;
	}

	public void writeDataToSerialPort(byte[] writeData) {
		try {
			this.output.write(writeData);
			this.output.flush();
		} catch (IOException e) {
			System.out.println("Comport write error !!");
		}
	}
	@Override
	public void serialEvent(SerialPortEvent evnt) {

		try {
			if (evnt.getEventType() != SerialPortEvent.DATA_AVAILABLE)
				return;
			this.input.read(this.buffer);
			this.dataInterface.setData(this.buffer);
			Thread.sleep(SLEEP_TIME);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, e.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*
	 * public void clearBuffer(byte[] buffer){ for(int i=0; i<buffer.length;
	 * i++){ buffer[i] = (byte)0; } }
	 */
}
