package SerialPort;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import ControlUI.DataID;

public class DataTransferInterfaceV2 extends DataTransferInterface {

	public DataTransferInterfaceV2(int inputBufferSize, int numOfOutputData) {
		super(inputBufferSize, numOfOutputData);
	}

	@Override
	public void dacodeData(byte[] inputBuffer, float[] outputData) {
		StringTokenizer pToken = new StringTokenizer(new String(inputBuffer,
				StandardCharsets.UTF_8), "s");
		while (pToken.hasMoreTokens()) {
			try {
				StringTokenizer sToken = new StringTokenizer(
						pToken.nextToken(), ":");
				int index = Integer.parseInt(sToken.nextToken());
				int r = Integer.parseInt(sToken.nextToken());
				float s = (float) Integer.parseInt(sToken.nextToken());
				float data = (float) (r + s / 1000.0);
				index--;
				if (isDataCorrect(data, index)) {
					System.out.println("Data: "+index + "=" +data);
					outputData[index] = data;
				}
			} catch (NoSuchElementException e) {
				// System.out.println("no element");
			} catch (NumberFormatException e) {

			}
		}
	}

}
