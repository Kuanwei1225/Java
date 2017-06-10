package SerialPort;

import ControlUI.DataID;

public abstract class  DataInterface {

	private byte[] inputBuffer;
	private float[] outputData;
	private boolean haveData = false;

	public DataInterface(int inputBufferSize, int numOfOutputData) {
		this.outputData = new float[numOfOutputData];
		this.inputBuffer = new byte[inputBufferSize];
	}

	public synchronized void setData(byte[] rawData) {
		try {
			int len = rawData.length;
			for (int i = 0; i <len ; i++) {
				this.inputBuffer[i] = rawData[i];
			}
			this.haveData = true;
			this.notify();
		} catch (Exception e) {
			System.out.println("Set data error!!");
			e.printStackTrace();
		}
	}

	public synchronized void getData(float[] dataBuffer) {
		while (!this.haveData) {
			try {
				// do not have data, get data wait.
				System.out.println("getData wait!!");
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("getData Error!!");
				e.printStackTrace();
			}
		}
		this.dacodeData(this.inputBuffer, this.outputData);
		for (int i = 0; i < this.outputData.length; i++) {
			dataBuffer[i] = this.outputData[i];
		}
		this.haveData = false;
		this.notify();
	}

	public float getData(DataID id) {
		return this.outputData[id.getValue()];
	}
	
	public float getData(int index) {
		return this.outputData[index];
	}
	
	public abstract void dacodeData(byte[] inputBuffer, float[] outputData);

}
