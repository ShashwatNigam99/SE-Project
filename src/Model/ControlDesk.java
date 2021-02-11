package Model;

import java.util.*;
import java.io.*;

public class ControlDesk extends Observable implements Runnable {

	private HashSet<Lane> lanes;

	private Queue partyQueue;

	private int numLanes;

	public ControlDesk(int numLanes) {
		this.numLanes = numLanes;
		lanes = new HashSet(numLanes);
		partyQueue = new Queue();
		for (int i = 0; i < numLanes; i++) {
			lanes.add(new Lane());
		}
		(new Thread(this, "Control Desk Thread")).start();
	}

	public void run() {
		while (true) {
			assignLane();
			try {
				Thread.sleep(250);
			} catch (Exception e) {}
		}
	}

	private Bowler registerPatron(String nickName) {
		Bowler patron = null;
		try {
			patron = BowlerFile.getBowlerInfo(nickName);
		} catch (FileNotFoundException e) {
			System.err.println("Error..." + e);
		} catch (IOException e) {
			System.err.println("Error..." + e);
		}
		return patron;
	}


	public void assignLane() {
		Iterator it = lanes.iterator();
		while (it.hasNext() && partyQueue.hasMoreElements()) {
			Lane curLane = (Lane) it.next();

			if (curLane.isPartyAssigned() == false) {
				System.out.println("ok... assigning this party");
				curLane.assignParty(((Party) partyQueue.next()));
			}
		}
		publish();
	}

	public void addPartyQueue(Vector partyNicks) {
		Vector partyBowlers = new Vector();
		for (int i = 0; i < partyNicks.size(); i++) {
			Bowler newBowler = registerPatron(((String) partyNicks.get(i)));
			partyBowlers.add(newBowler);
		}
		Party newParty = new Party(partyBowlers);
		partyQueue.add(newParty);
		publish();
	}

	public Vector getPartyQueue() {
		Vector displayPartyQueue = new Vector();
		for ( int i=0; i < ( (Vector)partyQueue.asVector()).size(); i++ ) {
			String nextParty =
					((Bowler) ((Vector) ((Party) partyQueue.asVector().get( i ) ).getMembers())
							.get(0))
					.getNickName() + "'s Party";
			displayPartyQueue.addElement(nextParty);
		}
		return displayPartyQueue;
	}

	public int getNumLanes() {
		return numLanes;
	}

	public void publish() {
		this.setChanged();
		this.notifyObservers();
	}

	public HashSet getLanes() {
		return lanes;
	}
}
