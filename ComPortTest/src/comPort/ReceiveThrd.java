package comPort;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class ReceiveThrd implements Runnable {
	final int TIME_OUT = 2000;
	final int NUM_OF_DATA = 7;
	final int BUFF_SIZE = NUM_OF_DATA * 5;

	private SerialPort serialPort;
	private InputStream input;
	private OutputStream output;
	private byte[] buffer = new byte[BUFF_SIZE];
	InputStream inPort;

	public ReceiveThrd(InputStream in) {
		this.input = in;
	}

	public void decodeData() {
		/*
		 * for(int i=0; i<this.NUM_OF_DATA;i++){ if (this.buffer[5*i+4] ==
		 * this.getCheckData(this.buffer, 5*i)) this.data[i] =
		 * java.nio.ByteBuffer.wrap(buffer, 5*i, 5*i+4)
		 * .order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
		 * System.out.println(this.data[i]); }
		 */

		if (this.buffer[4] == this.getCheckData(this.buffer, 0)) {
			float ff;
			ff = java.nio.ByteBuffer.wrap(this.buffer, 0, 4)
					.order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
			System.out.println("data1:" + ff);
		}
		if (this.buffer[9] == this.getCheckData(this.buffer, 5)) {
			float ff;
			ff = java.nio.ByteBuffer.wrap(this.buffer, 5, 4)
					.order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
			System.out.println("data2:" + ff);
		}
		if (this.buffer[14] == this.getCheckData(this.buffer, 10)) {
			float ff;
			ff = java.nio.ByteBuffer.wrap(this.buffer, 10, 4)
					.order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
			System.out.println("data3:" + ff);
		}
		System.out.println("end!!");
	}
/*
	private float byteArrayToFloat(byte[] b, int index) {
		return b[index + 0] & 0xFF | (b[index + 1] & 0xFF) << 8
				| (b[index + 2] & 0xFF) << 16 | (b[index + 3] & 0xFF) << 24;
	}
*/
	private byte getCheckData(byte[] buf, int index) {
		return (byte) ((buf[index] ^ buf[index + 1]) | (buf[index + 2] & buf[index + 3]));
	}

	@Override
	public void run() {
		try {
			int len = -1;
			while (true) {
				// if stream is not bound in.read() method returns -1
				//while ((len = this.input.read(buffer)) > -1) {
					//this.input.read(buffer, 0, BUFF_SIZE);
					len = this.input.read(buffer);
					System.out.println("len: " + len);
					if (len == 35)
						decodeData();
				//}
				// wait 10ms when stream is broken and check again
				Thread.sleep(10);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
