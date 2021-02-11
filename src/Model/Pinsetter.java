package Model;

import java.util.*;
import java.lang.Boolean;

public class Pinsetter extends Observable {

	private Random rnd;
	private boolean foul;
	private int throwNumber;
	private boolean[] pins; 
	
	private void sendEvent(int jdpins) {	// send events when our state is changd 
		this.setChanged();
		this.notifyObservers(new PinsetterEvent(pins, foul, throwNumber, jdpins));
		
	}
  
	public Pinsetter() {
		pins = new boolean[10];
		rnd = new Random();
		foul = false;
		reset();
	}
  
	public void ballThrown() {	// simulated event of ball hits sensor
		int count = 0;
		foul = false;
		double skill = rnd.nextDouble();
		for (int i=0; i <= 9; i++) {
			if (pins[i]) {
				double pinluck = rnd.nextDouble();
				if (pinluck <= .04){ 
					foul = true;
				}
				if ( ((skill + pinluck)/2.0 * 1.2) > .5 ){
					pins[i] = false;
				} 
				if (!pins[i]) {		// this pin just knocked down
					count++;
				}
			}
		}

		try {
			Thread.sleep(500);				// pinsetter is where delay will be in a real game
		} catch (Exception e) {}
		sendEvent(count);
		throwNumber++;
	}

	public void reset() {
		foul = false;
		throwNumber = 1;
		resetPins();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		
		sendEvent(-1);
	}

	public void resetPins() {
		for (int i=0; i <= 9; i++) {
			pins[i] = true;
		}
	}		

	public Random getRnd() {
		return rnd;
	}

	public boolean[] getPins() {
		return pins;
	}

	public boolean isFoul() {
		return foul;
	}

	public int getThrowNumber() {
		return throwNumber;
	}
}
