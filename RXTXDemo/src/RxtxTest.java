//package john.comm;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;

public class RxtxTest {
	public void list() {
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();

		while (ports.hasMoreElements()) {
			CommPortIdentifier cpIdentifier = (CommPortIdentifier) ports
					.nextElement();
			System.out.println(cpIdentifier.getName());
		}
	}

	public static void main(String[] args) {
		new RxtxTest().list();
	}
}
