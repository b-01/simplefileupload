package at.b01.simplefileuploaderfrontend.controller;

import at.b01.simplefileuploaderfrontend.business.UserManager;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEventListener;
import at.b01.simplefileuploaderfrontend.model.UserData;
import at.b01.simplefileuploaderfrontend.ui.AdminView;
import at.b01.simplefileuploaderfrontend.ui.misc.Notification;
import at.b01.simplefileuploaderfrontend.ui.misc.OptionsDialog;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification.Type;

public class AdminController implements Controller {

	private final AdminView view;
	private final UserManager usermanager = UserManager.getInstance();

	public AdminController(AdminView view) {
		this.view = view;

		loadUserData();
		view.addAddUserClickListener(new AddUserClickListener());
		view.addRemUserClickListener(new RemUserClickListener());
		view.addSaveClickListener(new SaveClickListener());
		view.addCancelClickListener(new CancelClickListener());
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
	}

	private void loadUserData() {
		view.setTableData(usermanager.getUserContainer());
	}

	private class AddUserClickListener implements Button.ClickListener {

		private static final long serialVersionUID = -1962138853768913401L;

		@Override
		public void buttonClick(ClickEvent event) {
			view.enableDataPanel();
		}

	}

	private class RemUserClickListener implements Button.ClickListener,
			OptionsDialog.DialogCallback {

		private static final long serialVersionUID = -1598986289474983731L;

		@Override
		public void buttonClick(ClickEvent event) {
			UserData userData = view.getSelectedUserData();
			if (userData == null) {
				return;
			}

			OptionsDialog dialog = new OptionsDialog("Delete the user?",
					"Do you really want to remove the user \""
							+ userData.getUsername() + "\"?", "Yes", "No", this);
			view.getUI().addWindow(dialog);
			dialog.center();
		}

		@Override
		public void onDialogResult(boolean resultIsLeftButton) {
			if (resultIsLeftButton) {
				if (usermanager.removeUser(view.getSelectedUserData()
						.getUserId())) {
					loadUserData();
				} else {

					Notification.show("Error", "Could not remove the user!",
							Type.ERROR_MESSAGE);
				}
			}
		}

	}

	private class SaveClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 5604190206514506207L;

		@Override
		public void buttonClick(ClickEvent event) {
			if (!view.isInputValid()) {
				Notification.show("Error",
						"Please provide values for each field!",
						Type.ERROR_MESSAGE);
				return;
			}
			if (view.getPasswordIfMatch() == null) {
				Notification.show("Error", "Passwords do not match!",
						Type.ERROR_MESSAGE);
				view.clearPasswords();
				return;
			}
			if (!usermanager.addUser(view.getUsername(),
					view.getPasswordIfMatch())) {
				Notification.show("Error",
						"Error creating the user! Please try again later!",
						Type.ERROR_MESSAGE);
			} else {
				Notification.show("User created!", Type.TRAY_NOTIFICATION);
				loadUserData();
			}
			view.disableDataPanel();
		}

	}

	private class CancelClickListener implements Button.ClickListener {

		private static final long serialVersionUID = -6634124213958848885L;

		@Override
		public void buttonClick(ClickEvent event) {
			view.disableDataPanel();
		}

	}
}
