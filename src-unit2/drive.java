public class drive {

	public static ControlDesk getNewControlDesk( int numLanes, int framesAllowed, boolean tieBreakerAllowed) {
		ControlDesk controlDesk = new ControlDesk( numLanes, framesAllowed, tieBreakerAllowed);
		return controlDesk;
	}

	public static void main(String[] args) {

		InitialConfigView icv = new InitialConfigView();
		icv.getGameParameters();
		int numLanes = icv.numLanes;
		int maxPatronsPerParty = icv.maxPatrons;
		int framesAllowed = icv.numFrames;
		boolean tieBreakerAllowed = icv.tieBreakerAllowed;

//		Alley a = new Alley( numLanes );
//		ControlDesk controlDesk = new ControlDesk( numLanes );//a.getControlDesk();
		ControlDesk controlDesk = getNewControlDesk(numLanes, framesAllowed, tieBreakerAllowed);

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}
