package comPort;

public class DataInterface {
	private byte[] buffer;
	private float[] data;
	private int numOfData;
	private int dataSize;
	private boolean haveData = false;

	public DataInterface(int numOfData, int dataSize) {
		this.numOfData = numOfData;
		this.dataSize = dataSize;
		this.data = new float[numOfData];
	}

	/*
	 * Set data method, user should be check rawData size is correct
	 */
	public synchronized void setData(byte[] rawData) {
		while (this.haveData == true) {
			try {
				// have data, set data wait.
				// System.out.println("setData wait!!");
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("setData Error!!");
				e.printStackTrace();
			}
		}
		try {
			if (rawData.length != this.dataSize * this.numOfData)
				throw new DataSizeExcepton();
			this.buffer = rawData;
			this.dacodeData();
			this.haveData = true;
			this.notify();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * The method will put data to dataBuffer.
	 */
	public synchronized void getData(float[] dataBuffer) {
		while (this.haveData == false) {
			try {
				// do not have data, get data wait.
				// System.out.println("getData wait!!");
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("getData Error!!");
				e.printStackTrace();
			}
		}
		for (int i = 0; i < this.numOfData; i++) {
			dataBuffer[i] = this.data[i];
		}
		this.haveData = false;
		this.notify();
	}

	private void dacodeData() {
		for (int i = 0; i < this.numOfData; i++) {
			int startIndex = this.dataSize * i;
			if (getCheckByte(startIndex) == this.buffer[startIndex + 5]) {
				int dataIndex = (int) this.buffer[startIndex + 4];
				this.data[dataIndex] = java.nio.ByteBuffer
						.wrap(this.buffer, startIndex, 4)
						.order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
			}
		}
	}

	private byte getCheckByte(int index) {
		return (byte) (this.buffer[index] ^ this.buffer[index + 1]
				^ this.buffer[index + 2] ^ this.buffer[index + 3] ^ this.buffer[index + 4]);
	}

	/*
	 * private byte getCheckByte(int index) { return (byte) ((this.buffer[index]
	 * ^ this.buffer[index + 1]) | (this.buffer[index + 2] & this.buffer[index +
	 * 3])); }
	 */
	private class DataSizeExcepton extends Exception {
		private static final long serialVersionUID = 1L;

		private DataSizeExcepton() {
			super("Data Size Error!");
		}

		private DataSizeExcepton(String message) {
			super(message);
		}
	}
}
