package Model;

import java.util.Vector;
 
public class Queue {
	private Vector v;
	
	public Queue() {
		v = new Vector();
	}
	
	public Object next() {
		return v.remove(0);
	}

	public void add(Object o) {
		v.addElement(o);
	}
	
	public boolean hasMoreElements() {
		return v.size() != 0;
	}

	public Vector asVector() {
		return v;
	}
	
}
