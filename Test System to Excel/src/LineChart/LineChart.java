package LineChart;

import java.awt.Color;
import java.awt.GradientPaint;

import javax.swing.JFrame;


//import thds.ChartThrd;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ControlUI.DataID;

public class LineChart extends JFrame {
	private static final long serialVersionUID = 1L;
	private XYSeriesCollection dataset = new XYSeriesCollection();
	private XYSeries[] xySeriesArray;

	public LineChart(String appTitle, String chartTitle, int sizeX, int sizeY) {
		super(appTitle);
		new XYSeriesCollection();
		// based on the dataset we create the chart
		JFreeChart chart = createLineChart(chartTitle);
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(sizeX, sizeY));
		// add it to our application
		setContentPane(chartPanel);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private JFreeChart createLineChart(String title) {
		JFreeChart Chart = ChartFactory.createXYLineChart(title, // Title
				"x-axis", // x-axis Label
				"y-axis", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		Chart.setBackgroundPaint(new GradientPaint(0, 0, new Color(163, 202,
				214), 0, 300, new Color(252, 203, 252)));
		return Chart;
	}

	public void showChart() {
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Create Series array
	 * 
	 * @param numOfSeries
	 */
	public void createXYSeriesArray(int numOfSeries) {
		this.xySeriesArray = new XYSeries[numOfSeries+1];
	}

	public void createXYSeries(int index, String seriesName) {
		this.xySeriesArray[index] = new XYSeries(seriesName);
	}

	public void createXYSeries(DataID id, String seriesName) {
		this.xySeriesArray[id.getValue()] = new XYSeries(seriesName);
	}

	/**
	 * Add series to data base
	 */
	public void addXYSeriesToDataset(XYSeries series) {
		this.dataset.addSeries(series);
	}

	public void addXYSeriesToDataset(int seriesIndex) {
		this.dataset.addSeries(this.xySeriesArray[seriesIndex]);
	}

	public void addXYSeriesToDataset(DataID id) {
		this.dataset.addSeries(this.xySeriesArray[id.getValue()]);
	}

	/**
	 * Remove series from data base
	 */
	public void removeXYSeriesfromDataset(XYSeries series) {
		this.dataset.removeSeries(series);
	}

	public void removeXYSeriesfromDataset(int seriesIndex) {
		this.dataset.removeSeries(this.xySeriesArray[seriesIndex]);
	}

	public void removeXYSeriesfromDataset(DataID id) {
		this.dataset.removeSeries(this.xySeriesArray[id.getValue()]);
	}

	/**
	 * Add data to xy series
	 * 
	 * @param index
	 *            of array
	 * @param xData
	 * @param yData
	 */
	public void addDataToXYSeries(int index, double xData, double yData) {
		try {
		this.xySeriesArray[index].add(xData, yData);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Index out of bounds. Index: " + index);
		} catch (IllegalArgumentException e) {
			System.out.println("Index out of bounds. Index: " + index);
		}
	}
}