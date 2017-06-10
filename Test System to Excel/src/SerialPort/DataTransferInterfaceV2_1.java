package SerialPort;

public class DataTransferInterfaceV2_1 extends DataInterface {

	private final int outputLen;

	public DataTransferInterfaceV2_1(int inputBufferSize, int numOfOutputData) {
		super(inputBufferSize, numOfOutputData);
		outputLen = numOfOutputData;
	}

	@Override
	public void dacodeData(byte[] inputBuffer, float[] outputData) {
		try {
			byte[] pData = new byte[20];
			for (int i = 0, j = 0; i < this.outputLen; i++) {
				for (int k = 0; (char) inputBuffer[j] != '\n'; k++, j++) {
					pData[k] = inputBuffer[j];
					//System.out.println("numByte:" + k);
				}
				j++;
				int dataIndex = 10 * (int) pData[0] + (int) pData[1];
				if (dataIndex > 0 && dataIndex <= outputLen) {
					dataIndex--;
					float tmp = java.nio.ByteBuffer.wrap(pData, 2, 4)
							.order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
					if(isDataCorrect(dataIndex, tmp))
						outputData[dataIndex] = tmp;
						//System.out.println(dataIndex + " = " + tmp);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	/**
	 * check decode data is in the right range
	 * @param index
	 * @param data
	 * @return true or false
	 */
	public boolean isDataCorrect(int index, float data) {
		final float MAX_SPEED = (float) 400.0;
		final float MAX_CURRENT = (float) 15.0;

		if (index >= 0 && index <= 1) {
			// check motor speed is correct
			if (data >= 0 && data <= MAX_SPEED)
				return true;
		} else if (index >= 2 && index <= 8) {
			// check current value is correct
			if (data >= -MAX_CURRENT && data <= MAX_CURRENT)
				return true;
		} else if (index == 20) {
			// check duty cycle
			// if (data > 0 && data < 1)
			return true;
		} else if (index > 8 && index <= 10) {
			// current command
			if (data > 0 && data < 20)
			return true;
		}
		return false;
	}
}
