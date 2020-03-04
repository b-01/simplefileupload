package at.b01.simplefileuploaderfrontend.model;

import at.b01.simplefileuploaderdatabase.entities.User;

public class RootModel {

	private boolean isLoggedIn = false;
	private User user = null;

	public RootModel() {

	}

	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}

	public boolean isAdmin() {
		if (user != null && user.getUsername().equals("admin")) {
			return true;
		}
		return false;
	}

	public boolean login(User u) {
		if (this.isLoggedIn) {
			return false;
		}
		this.isLoggedIn = true;
		this.user = u;
		return true;
	}

	public boolean logout() {
		if (this.isLoggedIn) {
			this.user = null;
			this.isLoggedIn = false;
			return true;
		}
		return false;
	}

	public String getUsername() {
		return this.user.getUsername();
	}

	public Long getUserId() {
		return this.user.getId();
	}
}
