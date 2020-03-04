package at.b01.simplefileuploaderfrontend.controller;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.b01.simplefileuploaderdatabase.entities.User;
import at.b01.simplefileuploaderdatabase.util.CryptoUtil;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEvent;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEventListener;
import at.b01.simplefileuploaderfrontend.model.RootModel;
import at.b01.simplefileuploaderfrontend.ui.AdminView;
import at.b01.simplefileuploaderfrontend.ui.DownloadView;
import at.b01.simplefileuploaderfrontend.ui.OverviewView;
import at.b01.simplefileuploaderfrontend.ui.RootView;
import at.b01.simplefileuploaderfrontend.ui.UploadView;
import at.b01.simplefileuploaderfrontend.ui.WelcomeView;

import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;

public class RootController implements ChangeViewEventListener {

	private static final Logger logger = LoggerFactory
			.getLogger(RootController.class);
	private final RootView ui;
	private final RootModel model;
	private final HeaderController headerController = new HeaderController(this);
	private final Hashtable<Integer, Controller> views = new Hashtable<Integer, Controller>(
			5);
	private Controller currentController;

	public RootController(RootView ui, RootModel model) {
		this.ui = ui;
		this.model = model;

		this.ui.addUriFragmentChangedListener(new UriFragmentListener());

		this.ui.setHeader(headerController.getLoginHeader());
		changeView(Views.WELCOME);

		handleUriFragment(Page.getCurrent().getUriFragment());
	}

	public RootModel getModel() {
		return model;
	}

	public RootView getView() {
		return ui;
	}

	@Override
	public void changeViewRequested(ChangeViewEvent event) {
		changeView(event.getRequestedViewInt());
	}

	public void changeView(int view) {
		// view value valid?
		if (!Views.isValidViewId(view)) {
			logger.warn("ViewID is not valid!");
			return;
		}
		// restricted view? requires logged in
		if (Views.isRestrictedView(view) && !this.model.isLoggedIn()) {
			logger.warn("User not allowed to view view={}", view);
			return;
		}

		// admin view? requires admin flag
		if (view == Views.ADMIN && !this.model.isAdmin()) {
			logger.warn("User not allowed to view adminview!");
			return;
		}

		Controller o = null;

		if (views.containsKey(view)) {
			o = views.get(view);
		} else {
			switch (view) {

			case Views.ADMIN:
				o = new AdminController(new AdminView());
				break;
			case Views.DOWNLOAD:
				o = new DownloadController(new DownloadView());
				break;
			case Views.UPLOAD:
				o = new UploadController(new UploadView(), model);
				break;
			case Views.OVERVIEW:
				o = new OverviewController(new OverviewView(), model);
				break;
			case Views.WELCOME:
				o = new WelcomeController(new WelcomeView());
				break;
			}
		}

		if (o != null) {
			o.addChangeViewEventListener(this);
			// store the controller in the dictionary.
			views.put(view, o);
			// update the browser title
			Page.getCurrent().setTitle(Views.getPageTitle(view));
			// eventually show the view
			this.ui.changeContent(o.getView());
			// inform the view, that it has been loaded
			o.viewEntered();
			// set the current controller
			this.currentController = o;
		}
	}

	public boolean login(User u) {
		if (this.model.login(u)) {
			this.ui.setHeader(headerController.getLogoutHeader());
			changeView(Views.OVERVIEW);
			return true;
		}
		return false;
	}

	public boolean logout() {
		if (this.model.isLoggedIn()) {
			this.model.logout();
			this.ui.setHeader(headerController.getLoginHeader());
			this.views.clear();
			changeView(Views.WELCOME);
			return true;
		}
		return false;
	}

	private void handleUriFragment(String fragment) {
		if (fragment == null || fragment.isEmpty()
				|| !CryptoUtil.isValidStorageUid(fragment)) {
			return;
		}
		changeView(Views.DOWNLOAD);
		// we know that the current controller is a download controller now!
		((DownloadController) this.currentController).setStorageUid(fragment);
	}

	private class UriFragmentListener implements UriFragmentChangedListener {

		private static final long serialVersionUID = 4334004601545880004L;

		@Override
		public void uriFragmentChanged(UriFragmentChangedEvent event) {
			handleUriFragment(event.getUriFragment());
		}

	}

}
