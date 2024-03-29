package Model;

import java.util.*;

public class Lane extends Observable implements Observer, Runnable {

	private Party party;
	private Pinsetter setter;
	private HashMap scores;
	private boolean gameIsHalted;
	private boolean partyAssigned;
	private boolean gameFinished;
	private Iterator bowlerIterator;
	private int ball;
	private int bowlIndex;
	private int frameNumber;
	private boolean tenthFrameStrike;
	private int[][] cumulScores;
	private boolean canThrowAgain;
	private int gameNumber;
	private Bowler currentThrower;			// = the thrower who just took a throw
	private ScoreCalculator sc;


	public Lane() {
		this.setter = new Pinsetter();
		this.setter.addObserver(this);
		this.scores = new HashMap();
		this.gameIsHalted = false;
		this.partyAssigned = false;
		this.gameNumber = 0;
		(new Thread(this, "Lane Thread")).start();
	}

	public void run() {
		while (true) {
			if (partyAssigned && !gameFinished) {	// we have a party on this lane, so next bower can take a throw
				while (gameIsHalted) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {}
				}
				if (bowlerIterator.hasNext()) {
					currentThrower = (Bowler)bowlerIterator.next();
					canThrowAgain = true;
					tenthFrameStrike = false;
					ball = 0;
					while (canThrowAgain) {
						setter.ballThrown();		// simulate the thrower's ball hiting
						ball++;
					}
					if (frameNumber == 9){
						try{
							Date date = new Date();
							String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
							ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, new Integer(cumulScores[bowlIndex][9]).toString());
						} catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
					}
					setter.reset();
					bowlIndex++;
				} else {
					frameNumber++;
					resetBowlerIterator();
					bowlIndex = 0;
					if (frameNumber > 9) {
						publish();
						gameFinished = true;
						gameNumber++;
					}
				}
			}
			else if (partyAssigned && gameFinished) {
				publish();
			}
			try {
				Thread.sleep(10);
			} catch (Exception e) {}
		}
	}

	public void clearLane(){
		party = null;
		partyAssigned = false;
	}

	public void resetBowlerIterator() {
		bowlerIterator = (party.getMembers()).iterator();
	}
  
	public void resetScores() {
		Iterator bowlIt = (party.getMembers()).iterator();
		while ( bowlIt.hasNext() ) {
			int[] toPut = new int[25];
			for ( int i = 0; i != 25; i++){
				toPut[i] = -1;
			}
			scores.put( bowlIt.next(), toPut );
		}
		gameFinished = false;
		frameNumber = 0;
	}
  
	public void assignParty( Party theParty ) {
		party = theParty;
		resetBowlerIterator();
		partyAssigned = true;
		cumulScores = new int[party.getMembers().size()][10];
		this.sc = new ScoreCalculator(cumulScores);
		gameNumber = 0;
		resetScores();
	}
  
	private void markScore( Bowler Cur, int frame, int ball, int score ){
		int[] curScore;
		int index =  ( (frame - 1) * 2 + ball);
		curScore = (int[]) scores.get(Cur);
		curScore[ index - 1] = score;
		scores.put(Cur, curScore);
		sc.calculateGame((int[])this.scores.get(Cur), this.bowlIndex, this.frameNumber, ball, score);
		publish();
	}

	public int[][] getFinalScores(){
		return(cumulScores);
	}

	public int getGameNumber(){
		return(this.gameNumber);
	}

	public boolean isPartyAssigned() {
		return partyAssigned;
	}

	public boolean isGameFinished() {
		return gameFinished;
	}

	public void publish(){
		this.setChanged();
		this.notifyObservers();
	}

	public void pauseGame() {
		gameIsHalted = true;
		publish();
	}

	public void unPauseGame() {
		gameIsHalted = false;
		publish();
	}

	public Party getParty() {
		return party;
	}

	public HashMap getScores() {
		return scores;
	}

	public boolean isGameIsHalted() {
		return gameIsHalted;
	}

	public int getBall() {
		return ball;
	}

	public int getBowlIndex() {
		return bowlIndex;
	}
  
	public int getFrameNumber() {
		return frameNumber;
	}

	public int[][] getCumulScores() {
		return cumulScores;
	}

	public Bowler getCurrentThrower() {
		return currentThrower;
	}

	public Pinsetter getPinsetter() {
		return setter;
	}

	@Override
	public void update(Observable o, Object arg) {
		PinsetterEvent pe = (PinsetterEvent)arg;
		if(pe.pinsDownOnThisThrow() < 0){ // this is not a real throw
			return;
		}
		markScore(currentThrower, frameNumber + 1, pe.getThrowNumber(), pe.pinsDownOnThisThrow());
		// next logic handles the ?: what conditions dont allow them another throw?
		// handle the case of 10th frame first
		if (frameNumber == 9) {
			if (pe.totalPinsDown() == 10) {
				setter.resetPins();
				if(pe.getThrowNumber() == 1) {
					tenthFrameStrike = true;
				}
			}
			if ((pe.totalPinsDown() != 10) && (pe.getThrowNumber() == 2 && tenthFrameStrike == false) || pe.getThrowNumber() == 3) {
				canThrowAgain = false;
			}
		} else if (pe.pinsDownOnThisThrow() == 10 || pe.getThrowNumber() == 2) {		// threw a strike
			canThrowAgain = false;
		}
	}
}
