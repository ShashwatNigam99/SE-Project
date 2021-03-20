

import java.util.Vector;

public class EndGameReportProduct {
	private Vector retVal;
	private int result;

	public Vector getRetVal() {
		return retVal;
	}

	public void setRetVal(Vector retVal) {
		this.retVal = retVal;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Vector getResult() {
		while (result == 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
			}
		}
		return retVal;
	}
}