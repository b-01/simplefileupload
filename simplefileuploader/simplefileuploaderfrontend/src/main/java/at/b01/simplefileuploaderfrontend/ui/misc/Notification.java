package at.b01.simplefileuploaderfrontend.ui.misc;

import com.vaadin.server.Page;

public class Notification extends com.vaadin.ui.Notification {

	private static final long serialVersionUID = -4273064484734395631L;
	// default delay is 4 seconds
	private static final int DEFAULT_DELAY = 4000;

	public Notification(String caption, String description, Type type,
			boolean htmlContentAllowed) {
		super(caption, description, type, htmlContentAllowed);
		setErrorType();
	}

	public Notification(String caption, String description, Type type) {
		super(caption, description, type);
		setErrorType();
	}

	public Notification(String caption, String description) {
		super(caption, description);
		setErrorType();
	}

	public Notification(String caption, Type type) {
		super(caption, type);
		setErrorType();
	}

	public Notification(String caption) {
		super(caption);
		setErrorType();
	}

	private void setErrorType() {
		if (super.getStyleName() != null
				&& super.getStyleName().equals("error")) {
			super.setDelayMsec(DEFAULT_DELAY);
		}
	}

	public static void show(String caption) {
		new Notification(caption).show(Page.getCurrent());
	}

	public static void show(String caption, Type type) {
		new Notification(caption, type).show(Page.getCurrent());
	}

	public static void show(String caption, String description, Type type) {
		new Notification(caption, description, type).show(Page.getCurrent());
	}

}
