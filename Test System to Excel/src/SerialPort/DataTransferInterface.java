package SerialPort;

import java.nio.ByteBuffer;

import ControlUI.DataID;

public class DataTransferInterface extends DataInterface {

	private int dataSize;

	public DataTransferInterface(int inputBufferSize, int numOfOutputData,
			int dataSize) {
		super(inputBufferSize, numOfOutputData);
		this.dataSize = dataSize;
	}
	public DataTransferInterface(int inputBufferSize, int numOfOutputData) {
		super(inputBufferSize, numOfOutputData);
	}

	@Override
	public void dacodeData(byte[] inputBuffer, float[] outputData) {
		float tmp = 0;
		int len = outputData.length;
		for (int i = 0; i < len; i++) {
			int startIndex = this.dataSize * i;
			if (getCheckByte(inputBuffer, startIndex) != inputBuffer[startIndex + 6])
				continue;
			int dataIndex = 10 * (int) inputBuffer[startIndex + 4]
					+ (int) inputBuffer[startIndex + 5];
			if (dataIndex > 0 && dataIndex <= len) {
				dataIndex--;
				tmp = java.nio.ByteBuffer.wrap(inputBuffer, startIndex, 4)
						.order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
				if (this.isDataCorrect(tmp, dataIndex))
					outputData[dataIndex] = tmp;
			}
		}
	}

	private byte getCheckByte(byte[] buffer, int index) {
		int tmp = buffer[index] ^ buffer[index + 1] ^ buffer[index + 2]
				^ buffer[index + 3]^ buffer[index + 4]^ buffer[index + 5];
		return (byte) tmp;
	}

	public boolean isDataCorrect(float data, int index) {
		final float MAX_SPEED = (float) 500.0;
		final float MAX_CURRENT = (float) 25.0;

		if (index >= 0 && index <= 1) {
			// check motor speed is correct
			if (data >= 0 && data <= MAX_SPEED)
				return true;
		} else if (index >= 2 && index < 8) {
			// check current value is correct
			if (data >= -MAX_CURRENT && data <= MAX_CURRENT)
				return true;
		} else if (index == 8) {
			// check duty cycle
			// if (data > 0 && data < 1)
			return true;
		} else if (index > 8 && index <= 10) {
			// current command
			// if (data > 0 && data < 20)
			return true;
		}
		return false;
	}

	public byte[] getSendPackage(DataID id, double data) {
		byte[] buf = new byte[dataSize], floatToByte;
		int dataId = id.getValue(), i;
		floatToByte = ByteBuffer.allocate(4).putFloat((float)data).array();
		for(i=0; i < 4; i++) {
			buf[i] = floatToByte[i];
		}
		buf[i++] = (byte)(dataId/10);
		buf[i++] = (byte)(dataId%10);
		buf[i] = getCheckByte(buf, 0);
		return buf;
	}
}
