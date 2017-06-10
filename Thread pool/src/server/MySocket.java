package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MySocket implements Runnable {

	private Socket mySocket;
	private int threadNumber;

	public MySocket(Socket s, int number) {
		this.mySocket = s;
		this.threadNumber = number;
	}

	@Override
	public void run() {
		OutputStream out;
		try {
			InputStream in = this.mySocket.getInputStream();
			out = this.mySocket.getOutputStream();
			this.mySocket.setSoTimeout(5000);
			int data = in.read();
			for(int i = 0; i < 1e4; i++);
			System.out.println("thread " + Thread.currentThread().getName() + " is running");
			out.write(data);
			out.close();
			in.close();
			this.mySocket.close();
		} catch (IOException e) {
			System.out.println("Thread error: " + this.threadNumber + " " + e.getMessage());
		}
	}
}
