package LineChart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.jfree.data.xy.XYSeries;

import ControlUI.DataID;
import SaveToExcel.ExportDataThrd;
import SerialPort.DataInterface;

public class LineChartPlotThrd extends LineChart implements Runnable {

	private static final long serialVersionUID = 1L;
	private final int SLEEP_TIME = 20;
	private final double D = (SLEEP_TIME / 1000.0);
	private final double SAVE_DATA_FREQ = 0.5;
	// mcu set_data_frequency / SAVE_DATA_FREQ
	private final int SAVE_EXCEL_BUF_SIZE = (int) (100 / SAVE_DATA_FREQ) + 1;
	private final int NUM_OF_EXPORT_DATA = 7;

	private XYSeries[] ds;
	private Timer timer;
	private ExportDataThrd<Double> exportData;
	private int numOfSeries, seriesCount = 0;
	private boolean runFlag = false;
	private long tt = 0;
	private float[] buffer;
	private DataInterface dataInterface;

	public LineChartPlotThrd(String appTitle, String chartTitle, int xSize,
			int ySize, int numOfSeries, DataInterface obj) {
		super(appTitle, chartTitle, xSize, ySize);
		this.numOfSeries = numOfSeries;
		this.buffer = new float[numOfSeries + 1];
		this.dataInterface = obj;
		// initial save data object
		Double[][] exportDataBuf = new Double[this.NUM_OF_EXPORT_DATA][this.SAVE_EXCEL_BUF_SIZE];
		this.exportData = new ExportDataThrd<>(exportDataBuf, "test.csv");
		this.exportData.setClearData(0.0);
		this.ds = new XYSeries[numOfSeries];
		this.createXYSeriesArray(numOfSeries);
	}

	{
		// Save to Excel
		timer = new Timer((int) (1000 / this.SAVE_DATA_FREQ), null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(exportData).start();
			}
		});
	}

	/*
	 * Add XYSeries and the object will update series data.
	 */
	public void addXYSeriesToUpdateThrd(XYSeries series) {
		this.ds[this.seriesCount++] = series;
	}

	/*
	 * If flag == true, it will update data immediately.
	 */
	public void setUpdateFlag(boolean flag) {
		this.runFlag = flag;
		if (flag) {
			this.timer.start();
		} else {
			this.timer.stop();
			this.exportData.run();
		}
	}

	public void closeExportTerminal() {
		this.exportData.closeObj();
	}

	public float getData(DataID id) {
		return this.buffer[id.getValue()];
	}

	public float getData(int index) {
		return this.buffer[index];
	}


	@Override
	public void run() {
		try {
			while (true)
				while (this.runFlag) {
					this.dataInterface.getData(this.buffer);
					if (this.buffer == null)
						throw new NullPointerException();
					for (int i = 0; i < this.numOfSeries; i++) {
						this.addDataToXYSeries(i, (double) tt * D, buffer[i]);
					}
					this.exportData.addData(0,
							(double) (this.getData(DataID.CURRENT_A_PHASE)));
					this.exportData.addData(1,
							(double) (this.getData(DataID.CURRENT_B_PHASE)));
					this.exportData.addData(2,
							(double) (this.getData(DataID.CURRENT_C_PHASE)));
					this.exportData.addData(3,
							(double) (this.getData(DataID.CURRENT_IQ)));
					this.exportData.addData(4,
							(double) (this.getData(DataID.DUTY_CYCLE)));
					this.exportData.addData(5,
							(double) (this.getData(DataID.CURRENT_TOTAL)));
							
					tt++;
					Thread.sleep(SLEEP_TIME);
				}
		} catch (NullPointerException e) {
			System.out.println("Plot buffer error!!");
			this.buffer = new float[this.numOfSeries];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
