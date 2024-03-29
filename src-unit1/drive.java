import Model.ControlDesk;
import ViewControl.ControlDeskView;

/*
* drive serves as the startup file for the application.
*/

public class drive {

	public static void main(String[] args) {

		final int NUM_LANES = 3;
		final int MAX_PATRONS_PER_ALLEY = 10;

		// Create a control desk and run the GUI
		ControlDesk controlDesk = new ControlDesk(NUM_LANES);
		ControlDeskView controlDeskView = new ControlDeskView( controlDesk, MAX_PATRONS_PER_ALLEY);
		controlDesk.addObserver(controlDeskView);
	}
}
