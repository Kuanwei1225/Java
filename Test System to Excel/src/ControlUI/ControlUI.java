package ControlUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.Timer;

import LineChart.LineChartPlotThrd;
import SerialPort.*;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ControlUI {

	private final int NUM_OF_SERIES = 11;
	//private final int DATA_SIZE = 7;
	private JFrame frmControlUI;
	private ComPort comPort = new ComPort(256);
	// private ComPortDev comPort = new ComPortDev();
	private DataInterface dataIntreface = new DataTransferInterfaceV2_1(
			256, NUM_OF_SERIES);
	private LineChartPlotThrd xyChart = new LineChartPlotThrd("Test System",
			"Test System", 1200, 800, NUM_OF_SERIES, dataIntreface);
	private JTextField txtFSpdCommand;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlUI window = new ControlUI();
					window.frmControlUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ControlUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmControlUI = new JFrame();
		frmControlUI.setTitle("Control UI");
		frmControlUI.setBounds(100, 100, 451, 420);
		frmControlUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmControlUI.getContentPane().setLayout(null);
		this.setChart();
		this.xyChart.showChart();
		this.setSerialPort();

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 142, 362);
		frmControlUI.getContentPane().add(panel);

		final JCheckBox chckbxComSpd = new JCheckBox("Command Speed");
		chckbxComSpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxComSpd.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.COMMAND_SPD);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.COMMAND_SPD);
				}
			}
		});
		chckbxComSpd.setSelected(true);
		panel.add(chckbxComSpd);

		final JCheckBox chckbxMotorSpd = new JCheckBox("Motor Speed");
		chckbxMotorSpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxMotorSpd.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.MOTOR_SPD);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.MOTOR_SPD);
				}
			}
		});
		chckbxMotorSpd.setSelected(true);
		panel.add(chckbxMotorSpd);

		final JCheckBox chckbxAPhaseCurreny = new JCheckBox("A Phase Current");
		chckbxAPhaseCurreny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxAPhaseCurreny.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.CURRENT_A_PHASE);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.CURRENT_A_PHASE);
				}
			}
		});
		chckbxAPhaseCurreny.setSelected(true);
		panel.add(chckbxAPhaseCurreny);

		final JCheckBox chckbxBPhaseCurrent = new JCheckBox("B Phase Current");
		chckbxBPhaseCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxBPhaseCurrent.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.CURRENT_B_PHASE);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.CURRENT_B_PHASE);
				}
			}
		});
		chckbxBPhaseCurrent.setSelected(true);
		panel.add(chckbxBPhaseCurrent);

		final JCheckBox chckbxCPhaseCurrent = new JCheckBox("C Phase Current");
		chckbxCPhaseCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxCPhaseCurrent.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.CURRENT_C_PHASE);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.CURRENT_C_PHASE);
				}
			}
		});
		chckbxCPhaseCurrent.setSelected(true);
		panel.add(chckbxCPhaseCurrent);

		final JCheckBox chckbxIq = new JCheckBox("Iq current");
		chckbxIq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxIq.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.CURRENT_IQ);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.CURRENT_IQ);
				}
			}
		});
		chckbxIq.setSelected(true);
		panel.add(chckbxIq);

		final JCheckBox chckbxId = new JCheckBox("Id Current");
		chckbxId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxId.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.CURRENT_ID);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.CURRENT_ID);
				}
			}
		});
		chckbxId.setSelected(true);
		panel.add(chckbxId);

		final JCheckBox chckbxTotal = new JCheckBox("Total Current");
		chckbxTotal.setSelected(true);
		chckbxTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxTotal.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.CURRENT_TOTAL);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.CURRENT_TOTAL);
				}
			}
		});
		panel.add(chckbxTotal);

		final JCheckBox chckbxDuty = new JCheckBox("Duty cycle");
		chckbxDuty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxDuty.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.DUTY_CYCLE);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.DUTY_CYCLE);
				}
			}
		});
		chckbxDuty.setSelected(true);
		panel.add(chckbxDuty);

		final JCheckBox chckbxCurrenrCommand = new JCheckBox("Current Command");
		chckbxCurrenrCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxCurrenrCommand.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.CURRENT_COMMAND);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.CURRENT_COMMAND);
				}
			}
		});
		chckbxCurrenrCommand.setSelected(true);
		panel.add(chckbxCurrenrCommand);
		
		JCheckBox chckbxRotorDrgree = new JCheckBox("Rotor degree");
		chckbxRotorDrgree.setSelected(true);
		chckbxRotorDrgree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxRotorDrgree.isSelected()) {
					xyChart.addXYSeriesToDataset(DataID.ROTOR_DEGREE_ID);
				} else {
					xyChart.removeXYSeriesfromDataset(DataID.ROTOR_DEGREE_ID);
				}
			}
		});
		panel.add(chckbxRotorDrgree);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					comPort.disconnectPort();
					xyChart.closeExportTerminal();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					System.exit(0);
				}
			}
		});
		btnClose.setBounds(338, 356, 87, 23);
		frmControlUI.getContentPane().add(btnClose);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(161, 10, 98, 148);
		frmControlUI.getContentPane().add(panel_4);

		JPanel panel_1 = new JPanel();
		panel_4.add(panel_1);

		JLabel lbl1 = new JLabel("Speed:");
		panel_1.add(lbl1);

		final JLabel lblSpeed = new JLabel("0.125");
		panel_1.add(lblSpeed);

		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2);

		JLabel lbl2 = new JLabel("Current:");
		panel_2.add(lbl2);

		final JLabel lblcurrent = new JLabel("0.1");
		panel_2.add(lblcurrent);

		JPanel panel_3 = new JPanel();
		panel_4.add(panel_3);

		JLabel lbl3 = new JLabel("Duty:");
		panel_3.add(lbl3);

		final JLabel lblDuty = new JLabel("0.01");
		panel_3.add(lblDuty);

		final Timer showDatatimer = new Timer(200, null);
		showDatatimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DecimalFormat df = new DecimalFormat("##.###");
				float data = xyChart.getData(DataID.MOTOR_SPD);
				lblSpeed.setText(df.format(data));
				data = (float) xyChart.getData(DataID.CURRENT_TOTAL);
				lblcurrent.setText(df.format(data));
				data = xyChart.getData(DataID.DUTY_CYCLE);
				lblDuty.setText(df.format(data));
			}
		});

		final JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnStart.getText() == "START") {
					xyChart.setUpdateFlag(true);
					btnStart.setText("STOP");
					showDatatimer.start();
				} else {
					xyChart.setUpdateFlag(false);
					showDatatimer.stop();
					btnStart.setText("START");
				}
			}
		});
		btnStart.setBounds(338, 323, 87, 23);
		frmControlUI.getContentPane().add(btnStart);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(269, 10, 156, 238);
		frmControlUI.getContentPane().add(panel_5);

		JLabel lblNewLabel = new JLabel("Speed command");
		panel_5.add(lblNewLabel);

		txtFSpdCommand = new JTextField();
		txtFSpdCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_5.add(txtFSpdCommand);
		txtFSpdCommand.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendDataToSerialPort( txtFSpdCommand.getText(), DataID.COMMAND_SPD);
			}
		});
		panel_5.add(btnSend);

	}

	/**
	 * The method is used to send data to serial port. You can use the method to
	 * control MCU.
	 * 
	 * @param data
	 * @param dataID
	 */
	private void sendDataToSerialPort(String data, DataID id) {
		try {
			//byte[] sendPackage;
			//float sendData = Float.valueOf(data);
			//sendPackage = this.dataIntreface.getSendPackage(id, sendData);
			//this.comPort.writeDataToSerialPort(sendPackage);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, e.toString());
		}
	}

	private void setSerialPort() {
		try {
			this.comPort.connectPort(this.comPort.listComPort());
			this.comPort.initIOStream();
			this.comPort.setDataTransferInterface(dataIntreface);
			this.comPort.initListener();
			// this.comPort.createReceiveDataThrd(dataIntreface);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, e.toString());
		}
	}

	private void setChart() {
		this.xyChart.createXYSeriesArray(NUM_OF_SERIES);
		this.xyChart.createXYSeries(0, "Command Speed");
		this.xyChart.createXYSeries(1, "Motor Speed");
		this.xyChart.createXYSeries(2, "A Phase Current");
		this.xyChart.createXYSeries(3, "B Phase Current");
		this.xyChart.createXYSeries(4, "C Phase Current");
		this.xyChart.createXYSeries(5, "Iq current");
		this.xyChart.createXYSeries(6, "Id current");
		this.xyChart.createXYSeries(7, "Total current");
		this.xyChart.createXYSeries(8, "Duty cycle");
		this.xyChart.createXYSeries(9, "Current command");
		this.xyChart.createXYSeries(10, "Rotor degree");
		// this.xyChart.createXYSeries(10, "test");
		 this.xyChart.addXYSeriesToDataset(7);
		 this.xyChart.addXYSeriesToDataset(9);
		// this.xyChart.addXYSeriesToDataset(4);
		/*for (int i = 0; i < this.NUM_OF_SERIES; i++) {
			this.xyChart.addXYSeriesToDataset(i);
		}*/
		new Thread(this.xyChart).start();
		this.xyChart.setUpdateFlag(false);
	}
}
