package at.b01.simplefileuploaderfrontend.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import at.b01.simplefileuploaderdatabase.entities.Storage;
import at.b01.simplefileuploaderfrontend.business.InputSanitizer;
import at.b01.simplefileuploaderfrontend.business.StorageManager;
import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEventListener;
import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;
import at.b01.simplefileuploaderfrontend.model.RootModel;
import at.b01.simplefileuploaderfrontend.ui.UploadView;
import at.b01.simplefileuploaderfrontend.ui.misc.Notification;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class UploadController implements Controller {

	private final StorageManager storageManager = StorageManager.getInstance();
	private final RootModel model;
	private final UploadView view;
	private final String prefix;
	private File currentTemporaryFile = null;

	public UploadController(UploadView view, RootModel model) {
		this.view = view;
		this.model = model;
		this.prefix = (String) UI.getCurrent().getSession()
				.getAttribute(ApplicationProperties.TMPFILE_PREFIX);

		UploadHandler handler = new UploadHandler();
		this.view.addFailedListener(handler);
		this.view.addStartedListener(handler);
		this.view.addProgressListener(handler);
		this.view.addSucceededListener(handler);
		this.view.addNewUploadClickListener(new NewUploadClickListener());
		this.view.setUploadReceiver(handler);
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

	private void setTemporaryFile(File tmpFile) {
		this.currentTemporaryFile = tmpFile;
	}

	private void clearUpload() {
		if (this.currentTemporaryFile != null) {
			if (this.currentTemporaryFile.exists()) {
				this.currentTemporaryFile.delete();
				this.currentTemporaryFile = null;
			}
		}
	}

	private void prepareNewUpload() {
		clearUpload();
		view.setPanelInformationVisible(false);
		view.setFileUploadEnabled(true);
	}

	private void uploadFinished(String filename) {
		filename = InputSanitizer.getInstance().sanitize(filename);
		if (filename == null || filename.isEmpty()) {
			prepareNewUpload();
			Notification.show("Error", "Filename not valid!",
					Type.ERROR_MESSAGE);
			return;
		}

		String storage_uid = storageManager.getFreeFileId(filename);
		Storage storage = new Storage(model.getUserId(), storage_uid, filename);

		if (storageManager.addStorageFile(storage, this.currentTemporaryFile)) {
			view.setPanelInformationVisible(true);
			String baseUrl = ApplicationProperties.getInstance().getProperty(
					ApplicationProperties.Property.URL_BASE);
			if (!baseUrl.endsWith("/")) {
				baseUrl = baseUrl + "/";
			}
			view.setDownloadLink(baseUrl + "#" + storage_uid);
			return;
		}
		prepareNewUpload();
		Notification.show("Error", "Error storing the file!",
				Type.ERROR_MESSAGE);
	}

	private class UploadHandler implements Receiver, ProgressListener,
			StartedListener, FailedListener, SucceededListener {

		private static final long serialVersionUID = -4692330120614718451L;
		private boolean warningDisplayed;

		public UploadHandler() {
			warningDisplayed = false;
		}

		@Override
		public void uploadStarted(StartedEvent event) {
			view.setProgressBarEnabled(true);
			view.setFileUploadEnabled(false);
			view.setProgress(0f);
			view.getUI().setPollInterval(500);
		}

		@Override
		public void uploadFailed(FailedEvent event) {
			view.setProgressBarEnabled(false);
			view.setFileUploadEnabled(true);
			view.setProgress(0f);
			view.getUI().setPollInterval(-1);

			clearUpload();

			Notification.show("Error", "Upload failed!", Type.ERROR_MESSAGE);
		}

		@Override
		public void uploadSucceeded(SucceededEvent event) {
			view.setProgressBarEnabled(false);
			view.setProgress(0f);
			view.getUI().setPollInterval(-1);

			uploadFinished(event.getFilename());

			Notification.show("Info", "Upload finished!",
					Type.HUMANIZED_MESSAGE);
		}

		@Override
		public void updateProgress(long readBytes, long contentLength) {
			if (contentLength == -1 && !warningDisplayed) {
				Notification.show("WARNING",
						"Can't determine filesize. Please be patient!",
						Type.WARNING_MESSAGE);
				warningDisplayed = true;
				return;
			}
			view.setProgress((float) ((float) readBytes / (float) contentLength));
		}

		@Override
		public OutputStream receiveUpload(String filename, String mimeType) {
			FileOutputStream fos = null;
			try {
				File file = File.createTempFile(prefix, ".tmp");
				setTemporaryFile(file);
				fos = new FileOutputStream(file);
			} catch (final java.io.IOException e) {
				// TODO: add logging info
				Notification.show("Error", "Could not create temporary file!",
						Type.ERROR_MESSAGE);
				return null;
			}
			return fos;
		}

	}

	private class NewUploadClickListener implements Button.ClickListener {

		private static final long serialVersionUID = -7445701991217182385L;

		@Override
		public void buttonClick(ClickEvent event) {
			prepareNewUpload();
		}

	}
}
