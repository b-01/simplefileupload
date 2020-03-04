package at.b01.simplefileuploaderfrontend.controller.events;

import java.util.List;
import java.util.Vector;

import at.b01.simplefileuploaderfrontend.controller.Controller;

public class ChangeViewHandler {

	private final List<ChangeViewEventListener> changeViewListeners;
	private final Controller sender;

	public ChangeViewHandler(Controller sender) {
		changeViewListeners = new Vector<ChangeViewEventListener>(2);
		this.sender = sender;
	}

	public void fireChangeViewEvent(int viewId) {
		ChangeViewEvent event = new ChangeViewEvent(sender, viewId);

		for (ChangeViewEventListener l : changeViewListeners) {
			l.changeViewRequested(event);
		}
	}

	public void addChangeViewEventListener(ChangeViewEventListener listener) {
		if (!changeViewListeners.contains(listener)) {
			changeViewListeners.add(listener);
		}
	}

	public void removeChangeViewEventListener(ChangeViewEventListener listener) {
		if (changeViewListeners.contains(listener)) {
			changeViewListeners.remove(listener);
		}
	}

}
