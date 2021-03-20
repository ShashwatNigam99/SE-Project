

import java.util.Vector;
import java.util.Iterator;

public class LaneProduct implements Cloneable {
	private Vector subscribers;

	public Vector getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Vector subscribers) {
		this.subscribers = subscribers;
	}

	/**
	* unsubscribe Method that unsubscribes an observer from this object
	* @param removing 	The observer to be removed
	*/
	public void unsubscribe(LaneObserver removing) {
		subscribers.remove(removing);
	}

	/**
	* publish Method that publishes an event to subscribers
	* @param event 	Event that is to be published
	*/
	public void publish(LaneEvent event) {
		if (subscribers.size() > 0) {
			Iterator eventIterator = subscribers.iterator();
			while (eventIterator.hasNext()) {
				((LaneObserver) eventIterator.next()).receiveLaneEvent(event);
			}
		}
	}

	public Object clone() throws CloneNotSupportedException {
		return (LaneProduct) super.clone();
	}
}