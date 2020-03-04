package at.b01.simplefileuploaderfrontend.controller;

import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEventListener;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewHandler;
import at.b01.simplefileuploaderfrontend.ui.WelcomeView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;

public class WelcomeController implements Controller {

	private final WelcomeView view;
	private final ChangeViewHandler changeViewHandler = new ChangeViewHandler(
			this);

	public WelcomeController(WelcomeView view) {
		this.view = view;

		this.view
				.addRequestDownloadClickListener(new RequestDownloadClickListener());
	}

	@Override
	public CustomComponent getView() {
		return this.view;
	}

	@Override
	public void viewEntered() {
	}

	@Override
	public void addChangeViewEventListener(ChangeViewEventListener listener) {
		changeViewHandler.addChangeViewEventListener(listener);
	}

	private class RequestDownloadClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 6496725365757273667L;

		@Override
		public void buttonClick(ClickEvent event) {
			changeViewHandler.fireChangeViewEvent(Views.DOWNLOAD);
		}
	}
}
