package at.b01.simplefileuploaderfrontend.controller;

import at.b01.simplefileuploaderfrontend.business.StorageManager;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEventListener;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewHandler;
import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;
import at.b01.simplefileuploaderfrontend.model.RootModel;
import at.b01.simplefileuploaderfrontend.ui.OverviewView;
import at.b01.simplefileuploaderfrontend.ui.misc.LinkPopup;
import at.b01.simplefileuploaderfrontend.ui.misc.Notification;
import at.b01.simplefileuploaderfrontend.ui.misc.OptionsDialog;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification.Type;

public class OverviewController implements Controller {

	private final ChangeViewHandler changeViewHandler = new ChangeViewHandler(
			this);
	private final OverviewView view;
	private final RootModel model;
	private final StorageManager storageManager = StorageManager.getInstance();

	public OverviewController(OverviewView view, RootModel model) {
		this.view = view;
		this.model = model;

		loadStorageData();

		this.view.addAddStorageClickListener(new AddStorageClickListener());
		this.view.addRemStorageClickListener(new RemStorageClickListener());
		this.view.addGetLinkClickListener(new GetLinkClickListener());

	}

	@Override
	public CustomComponent getView() {
		return this.view;
	}

	@Override
	public void viewEntered() {
		loadStorageData();
	}

	@Override
	public void addChangeViewEventListener(ChangeViewEventListener listener) {
		changeViewHandler.addChangeViewEventListener(listener);
	}

	private void loadStorageData() {
		this.view.setTableData(storageManager.getStorageContainer(this.model
				.getUserId()));
	}

	private class AddStorageClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 2475069517486515828L;

		@Override
		public void buttonClick(ClickEvent event) {
			changeViewHandler.fireChangeViewEvent(Views.UPLOAD);
		}

	}

	private class RemStorageClickListener implements Button.ClickListener,
			OptionsDialog.DialogCallback {

		private static final long serialVersionUID = -2916215922739895021L;

		@Override
		public void buttonClick(ClickEvent event) {
			if (view.getSelectedStorageId() == null) {
				return;
			}

			OptionsDialog dialog = new OptionsDialog("Delete the file?",
					"Do you really want to remove this stored file?", "Yes",
					"No", this);
			view.getUI().addWindow(dialog);
			dialog.center();
		}

		@Override
		public void onDialogResult(boolean resultIsLeftButton) {
			if (resultIsLeftButton) {
				if (storageManager.removeStorageFile(view
						.getSelectedStorageId())) {
					loadStorageData();

					Notification.show("File deleted!", Type.TRAY_NOTIFICATION);

				} else {
					Notification.show("Error",
							"Could not remove the stored file!",
							Type.ERROR_MESSAGE);
				}
			}
		}

	}

	private class GetLinkClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 2461243521318662070L;

		@Override
		public void buttonClick(ClickEvent event) {
			String id = view.getSelectedStorageId();
			if (id == null) {
				return;
			}

			id = ApplicationProperties.getInstance().getProperty(
					ApplicationProperties.Property.URL_BASE)
					+ "#" + id;

			LinkPopup popup = new LinkPopup("Download link",
					"Below is the link to download the file", id);
			view.getUI().addWindow(popup);
			popup.center();
		}

	}

}
