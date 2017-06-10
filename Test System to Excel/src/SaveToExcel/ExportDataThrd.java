package SaveToExcel;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class ExportDataThrd<T extends Number> implements Runnable {
	private T[][] buffer;
	private T value;
	private int[] count;
	private PrintStream pout;
	private PrintWriter out;

	public ExportDataThrd(T[][] buffer, String fileNiam) {
		try {
			this.setBuffer(buffer);
			this.pout = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(fileNiam, false)));
			this.out = new PrintWriter(pout, true);
			this.count = new int[buffer.length];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setBuffer(T[][] buf) {
		this.buffer = buf;
	}

	public void setClearData(T data) {
		this.value = data;
	}

	public void addData(int index, T data) {
		this.buffer[index][this.count[index]] = data;
		count[index]++;
	}

	/*
	 * public void clearBuffer(T data) { try { for (int i = 0; i <
	 * this.buffer.length; i++) { this.buffer[i] = data; } } catch (Exception e)
	 * { e.printStackTrace(); System.out.println("clearBuffer error"); } }
	 */
	public void closeObj() {
		this.out.close();
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i<this.count[0]; i++) {
				for (int j = 0; j < buffer.length; j++) {
					if (i < this.count[j]) {
						this.out.print(this.buffer[j][i] + ", ");
						this.buffer[j][i] = this.value;
					}
				}
				this.out.println();
			}
			for(int i=0; i<this.count.length; i++) {
				this.count[i] = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("To Excel Error");
		}
	}
}
