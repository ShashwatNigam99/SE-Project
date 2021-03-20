package Model;

import java.util.*;

public class Party {

    private Vector myBowlers;
	
    public Party( Vector bowlers ) {
        myBowlers = new Vector(bowlers);
    }

    public Vector getMembers() {
        return myBowlers;
    }

}
