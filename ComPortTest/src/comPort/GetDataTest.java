package comPort;

public class GetDataTest implements Runnable {
	private DataInterface dataInterface;
	private int numOfData;
	private float[] data;

	public GetDataTest(DataInterface obj, int numOfData) {
		this.dataInterface = obj;
		this.numOfData = numOfData;
		this.data = new float[numOfData];
	}

	@Override
	public void run() {
		while (true) {

			try {
				this.dataInterface.getData(this.data);
				for (int i = 0; i < this.numOfData; i++)
					System.out.println(this.data[i]);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
