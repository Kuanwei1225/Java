package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MyServer implements Runnable{

	private ServerSocket mySocket;
	private final int PORT = 1234;
	private int count = 0;

	public MyServer() {
		try {
			this.mySocket = new ServerSocket(this.PORT);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		Socket socket;
		System.out.println("Server socket create successful!!");
		Executor  executor = Executors.newFixedThreadPool(100);
		while (true) {
			//
			// release memory
			socket = null;
			try {
				synchronized (this.mySocket) {
					socket = this.mySocket.accept();
				}
				executor.execute(new MySocket(socket, count));
				count++;
			} catch (IOException e) {
				System.out.println("Server error " + e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		new MyServer().run();
	}
}
