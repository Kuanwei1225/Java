package ControlUI;

public enum DataID {
	COMMAND_SPD(0), MOTOR_SPD(1), CURRENT_A_PHASE(2), CURRENT_B_PHASE(3), CURRENT_C_PHASE(
			4), CURRENT_IQ(5), CURRENT_ID(6), CURRENT_TOTAL(7), DUTY_CYCLE(8), CURRENT_COMMAND(
			9), ROTOR_DEGREE_ID(10);

	private int value;

	private DataID(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
