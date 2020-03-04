package at.b01.simplefileuploaderfrontend.controller.events;

import at.b01.simplefileuploaderfrontend.controller.Controller;

import com.vaadin.ui.CustomComponent;

public class ChangeViewEvent {

	public static int NOT_SET = -1;
	private Controller sender;
	private Class<? extends CustomComponent> requestedView;
	private int requestedViewInt;

	public ChangeViewEvent(Controller sender,
			Class<? extends CustomComponent> req, int reqInt) {
		this.sender = sender;
		this.requestedView = req;
		this.requestedViewInt = reqInt;
	}

	public ChangeViewEvent(Controller sender,
			Class<? extends CustomComponent> req) {
		this(sender, req, NOT_SET);
	}

	public ChangeViewEvent(Controller sender, int req) {
		this(sender, null, req);
	}

	public Controller getSender() {
		return sender;
	}

	public int getRequestedViewInt() {
		return requestedViewInt;
	}

	public Class<? extends CustomComponent> getRequestedView() {
		return requestedView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChangeViewEvent [sender=" + sender + ", requestedView="
				+ requestedView + ", requestedViewInt=" + requestedViewInt
				+ "]";
	}

}
