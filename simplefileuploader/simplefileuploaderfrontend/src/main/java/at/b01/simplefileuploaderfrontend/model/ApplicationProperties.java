package at.b01.simplefileuploaderfrontend.model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

	public final class Property {
		public static final String STORAGE_ROOT_DIR = "storage.root_directory";
		public static final String STORAGE_DIR = "storage.storage_directory";
		public static final String DB_USERNAME = "db.username";
		public static final String DB_PASSWORD = "db.password";
		public static final String URL_BASE = "url.base";
	}

	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationProperties.class);
	private static final String propertyPath = "at/b01/simplefileuploaderfrontend/config/configuration.xml";
	private final Properties properties;
	private static final ApplicationProperties instance = new ApplicationProperties();

	// unchangeable properties used in the project
	public static final String TMPFILE_PREFIX = "TMPFILE_PREFIX";
	public static final int TEXTFIELD_TOKEN_COLUMNS = 40;
	public final String FULL_STORAGE_PATH;

	private ApplicationProperties() {
		logger.debug("ApplicationProperties loaded!");
		this.properties = loadProperties();
		this.FULL_STORAGE_PATH = getProperty(Property.STORAGE_ROOT_DIR)
				+ File.separator + getProperty(Property.STORAGE_DIR);
	}

	public static ApplicationProperties getInstance() {
		return instance;
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	private Properties loadProperties() {
		Properties p = new Properties();
		try {
			p.loadFromXML(this.getClass().getClassLoader()
					.getResourceAsStream(propertyPath));
			return p;
		} catch (IOException e) {
			logger.error("Could not load properties!", e);
			return null;
		}
	}
}
