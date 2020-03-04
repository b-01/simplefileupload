package at.b01.simplefileuploaderfrontend.controller;

import at.b01.simplefileuploaderdatabase.entities.User;
import at.b01.simplefileuploaderfrontend.business.UserManager;
import at.b01.simplefileuploaderfrontend.ui.header.LoginHeader;
import at.b01.simplefileuploaderfrontend.ui.header.LogoutHeader;
import at.b01.simplefileuploaderfrontend.ui.misc.Notification;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class HeaderController {

	private static final UserManager usermanager = UserManager.getInstance();
	private LoginHeader headerLogin;
	private LogoutHeader headerLogout;
	private final RootController controller;

	public HeaderController(RootController controller) {
		this.controller = controller;
	}

	private void newLoginHeader(LoginHeader lh) {
		if (lh == null) {
			this.headerLogin = new LoginHeader();
		} else {
			this.headerLogin = lh;
		}
		this.headerLogin.addLoginClickListener(new LoginButtonClickListener());
	}

	private void newLogoutHeader(LogoutHeader lh) {
		if (lh == null) {
			this.headerLogout = new LogoutHeader(this.controller.getModel()
					.isAdmin());
		} else {
			this.headerLogout = lh;
		}

		this.headerLogout.setUsername(this.controller.getModel().getUsername());

		this.headerLogout
				.addOverviewClickListener(new OverviewButtonClickListener());
		this.headerLogout
				.addUploadClickListener(new UploadButtonClickListener());
		this.headerLogout
				.addAdminClickListener(new AdminPageButtonClickListener());
		this.headerLogout
				.addLogoutClickListener(new LogoutButtonClickListener());
		this.headerLogout
				.addDownloadClickListener(new DownloadButtonClickListener());
	}

	public LoginHeader getLoginHeader() {
		if (this.headerLogin != null) {
			this.headerLogin.clearInput();
		} else {
			newLoginHeader(null);
		}
		// remove logout header - to prevent information disclosure
		this.headerLogout = null;
		return this.headerLogin;
	}

	public LogoutHeader getLogoutHeader() {
		if (this.headerLogout == null) {
			newLogoutHeader(null);
		}
		return this.headerLogout;

	}

	private class LoginButtonClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 7063471547769626194L;

		@Override
		public void buttonClick(ClickEvent event) {
			String username = headerLogin.getUsername();
			String pw = headerLogin.getPassword();

			if (username == null || username.isEmpty()) {
				Notification.show("Warning", "Please provide a username!",
						Type.WARNING_MESSAGE);
				return;
			}
			if (pw == null || pw.isEmpty()) {
				Notification.show("Warning", "Please provide a password!",
						Type.WARNING_MESSAGE);
				return;
			}

			User u = usermanager.login(username, pw);
			if (u == null) {
				Notification.show("Error", "Wrong user credentials",
						Type.ERROR_MESSAGE);
				headerLogin.clearInput();
			} else {
				headerLogin.clearInput();
				controller.login(u);
			}
		}
	}

	private class OverviewButtonClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 4344129770972087973L;

		@Override
		public void buttonClick(ClickEvent event) {
			controller.changeView(Views.OVERVIEW);
		}

	}

	private class UploadButtonClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 4344129770972087974L;

		@Override
		public void buttonClick(ClickEvent event) {
			controller.changeView(Views.UPLOAD);
		}

	}

	private class DownloadButtonClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 4344129770972087971L;

		@Override
		public void buttonClick(ClickEvent event) {
			controller.changeView(Views.DOWNLOAD);
		}

	}

	private class AdminPageButtonClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 4344129770972087975L;

		@Override
		public void buttonClick(ClickEvent event) {
			controller.changeView(Views.ADMIN);
		}

	}

	private class LogoutButtonClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 4344129770972087972L;

		@Override
		public void buttonClick(ClickEvent event) {
			controller.logout();
		}

	}
}
