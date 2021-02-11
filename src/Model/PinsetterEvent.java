package Model;

public class PinsetterEvent {

	private boolean[] pinsStillStanding;
	private boolean foulCommited;
	private int throwNumber;
	private int pinsDownThisThrow;

	public PinsetterEvent(boolean[] ps, boolean foul, int tn, int pinsDownThisThrow) {
		pinsStillStanding = new boolean[10];
		for (int i=0; i <= 9; i++) {
			pinsStillStanding[i] = ps[i];
		}
		foulCommited = foul;
		throwNumber = tn;
		this.pinsDownThisThrow = pinsDownThisThrow;
	}

	public boolean pinKnockedDown(int i) {
		return !pinsStillStanding[i];
	}
	
	public int pinsDownOnThisThrow() {
		return pinsDownThisThrow;
	}
	
	public int totalPinsDown() {
		int count = 0;
		for (int i=0; i <= 9; i++) {
			if (pinKnockedDown(i)) {
				count++;
			}
		}
		return count;
	}
	
	public boolean isFoulCommited() {
		return foulCommited;
	}

	public int getThrowNumber() {
		return throwNumber;
	}
};

