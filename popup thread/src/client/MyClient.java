package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MyClient implements Runnable {

	private final String IP_ADDRESS = "127.0.0.1";
	private final int PORT = 1234;
	private int threadNumber;

	public MyClient(int number) {
		this.threadNumber = number;
	}

	@Override
	public void run() {
		Socket mySocket = new Socket();
		InetSocketAddress isa = new InetSocketAddress(this.IP_ADDRESS, this.PORT);
		try {
			mySocket.connect(isa, 10000);
			InputStream in = mySocket.getInputStream();
			OutputStream out = mySocket.getOutputStream();
			System.out.println("thread " + this.threadNumber + " start");
			out.write(this.threadNumber);
			int data = in.read();
			System.out.println("thread " + this.threadNumber + " get data" + data);
			out.close();
			in.close();
			mySocket.close();
		} catch (IOException e) {
			System.out.println("thread " + this.threadNumber + "  " + e.getMessage());
		}
	}
	public static void main (String[] args) {
		for(int i = 0; i < 10000; i++) {
			new Thread(new MyClient(i)).start();
		}
	}
}
