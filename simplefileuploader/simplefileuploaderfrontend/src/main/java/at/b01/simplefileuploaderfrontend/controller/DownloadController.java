package at.b01.simplefileuploaderfrontend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import at.b01.simplefileuploaderdatabase.entities.Storage;
import at.b01.simplefileuploaderfrontend.business.StorageManager;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEventListener;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewHandler;
import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;
import at.b01.simplefileuploaderfrontend.ui.DownloadView;
import at.b01.simplefileuploaderfrontend.ui.misc.Notification;
import at.b01.simplefileuploaderfrontend.ui.misc.OnDemandFileDownloader;
import at.b01.simplefileuploaderfrontend.ui.misc.OnDemandFileDownloader.OnDemandStreamResource;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification.Type;

public class DownloadController implements Controller {

	private final ChangeViewHandler changeViewHandler = new ChangeViewHandler(
			this);
	private final DownloadView view;
	private Storage currentStorageFile = null;
	private OnDemandDownloader downloadProvider = new OnDemandDownloader();

	public DownloadController(DownloadView view) {
		this.view = view;

		this.view.addCheckUidClickListener(new CheckUidClickListener());
		this.view.addNewTokenClickListener(new NewTokenClickListener());
		this.view.setOnDemandDownloader(new OnDemandFileDownloader(
				downloadProvider));
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

	public void setStorageUid(String storageUid) {
		if (storageUid == null || storageUid.isEmpty()) {
			Notification.show("Info", "Please provide a token!",
					Type.HUMANIZED_MESSAGE);
			return;
		}

		Storage storageFile = StorageManager.getInstance().getStorageFileById(
				storageUid);
		if (storageFile == null) {
			Notification.show("Error", "Provided token is not valid!",
					Type.ERROR_MESSAGE);
			this.view.setDownloadPanelVisible(false);
			changeViewHandler.fireChangeViewEvent(Views.OVERVIEW);
			return;
		}
		this.view.setStorageFileName(storageFile.getName());
		this.currentStorageFile = storageFile;
		this.view.clearToken();
		this.downloadProvider.setFilename(this.currentStorageFile.getName(),
				this.currentStorageFile.getUid());
		this.view.setDownloadPanelVisible(true);
	}

	private class CheckUidClickListener implements Button.ClickListener {

		private static final long serialVersionUID = 5057366373415592584L;

		@Override
		public void buttonClick(ClickEvent event) {
			String t = view.getToken();
			if (t.startsWith("#")) {
				t = t.substring(1);
			}
			setStorageUid(t);
		}

	}

	private class NewTokenClickListener implements Button.ClickListener {

		private static final long serialVersionUID = -9098474016539922956L;

		@Override
		public void buttonClick(ClickEvent event) {
			currentStorageFile = null;
			view.setStorageFileName("");
			view.setDownloadPanelVisible(false);
		}

	}

	private class OnDemandDownloader implements OnDemandStreamResource {

		private static final long serialVersionUID = -1238691773640397621L;
		private String filename = null;
		private String storedName = null;

		public OnDemandDownloader() {
		}

		@Override
		public InputStream getStream() {
			try {
				FileInputStream fis = new FileInputStream(
						ApplicationProperties.getInstance().FULL_STORAGE_PATH
								+ File.separator + storedName);
				return fis;
			} catch (FileNotFoundException ex) {
				return null;
			}
		}

		public void setFilename(String filename, String storedName) {
			this.filename = filename;
			this.storedName = storedName;
		}

		@Override
		public String getFilename() {
			return filename;
		}

	}
}
