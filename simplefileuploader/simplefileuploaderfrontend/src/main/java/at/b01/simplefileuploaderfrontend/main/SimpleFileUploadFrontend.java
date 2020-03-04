package at.b01.simplefileuploaderfrontend.main;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.b01.simplefileuploaderdatabase.util.EncapsulatedDatabaseException;
import at.b01.simplefileuploaderdatabase.util.SessionProvider;
import at.b01.simplefileuploaderfrontend.controller.RootController;
import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;
import at.b01.simplefileuploaderfrontend.model.ApplicationProperties.Property;
import at.b01.simplefileuploaderfrontend.model.RootModel;
import at.b01.simplefileuploaderfrontend.ui.RootView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("simplefileupload")
@Title("Simple File Uploader")
@PreserveOnRefresh
public class SimpleFileUploadFrontend extends UI {

	// TODO: Implement length checks (DB)!
	// TODO: Implement user input sanitation!

	private static final long serialVersionUID = 560516914906127482L;
	private static final Logger logger = LoggerFactory
			.getLogger(SimpleFileUploadFrontend.class);

	public SimpleFileUploadFrontend() {
		logger.info("Application started!");

		File dir = new File(ApplicationProperties.getInstance().getProperty(
				Property.STORAGE_ROOT_DIR));
		if (!dir.exists()) {
			logger.error("Storage root directory does not exist! Please create it first!");
			return;
		}

		dir = new File(ApplicationProperties.getInstance().FULL_STORAGE_PATH);
		if (!dir.exists()) {
			logger.warn(
					"Storage directory does not exist! Trying to create it! [{}]",
					dir.getAbsolutePath());

			if (!dir.mkdir()) {
				logger.error("Could not create the storage directory! Please ensure that the root folder is writeable and/or create the folder on your own!");
				return;
			}
			logger.debug("Done creating storage directory!");
		} else {
			logger.info("Storage directory exists!");
		}

		try {
			SessionProvider.initialize(ApplicationProperties.getInstance()
					.getProperty(Property.DB_USERNAME), ApplicationProperties
					.getInstance().getProperty(Property.DB_PASSWORD));
		} catch (EncapsulatedDatabaseException ex) {
			logger.error("Error initializing the database connection!", ex);
		}

	}

	@Override
	protected void init(VaadinRequest request) {
		logger.debug("Init called!");

		final RootView view = new RootView();

		setContent(view);
		setSizeFull();

		final RootModel model = new RootModel();
		final RootController controller = new RootController(view, model);
	}
}
