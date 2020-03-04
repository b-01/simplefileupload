package at.b01.simplefileuploaderfrontend.main;

import java.io.File;
import java.io.FilenameFilter;
import java.util.UUID;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;

public class SimpleFileUploaderFrontendServlet extends VaadinServlet implements
		SessionInitListener, SessionDestroyListener {

	private static final long serialVersionUID = 3099428133413854880L;
	private static final Logger logger = LoggerFactory
			.getLogger(SimpleFileUploaderFrontendServlet.class);

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();

		// Load the properties
		// ApplicationProperties.getInstance();

		getService().addSessionInitListener(this);
		getService().addSessionDestroyListener(this);
	}

	@Override
	public void sessionInit(SessionInitEvent event) throws ServiceException {
		String prefix = UUID.randomUUID().toString().substring(0, 8);
		logger.debug("Setting Prefix: '{}'", prefix);
		event.getSession().setAttribute(ApplicationProperties.TMPFILE_PREFIX,
				prefix);
	}

	@Override
	public void sessionDestroy(SessionDestroyEvent event) {
		logger.debug("Session destroyed! Current Prefix: {}", (String) event
				.getSession()
				.getAttribute(ApplicationProperties.TMPFILE_PREFIX));
		// Remove all temporary files which have been created by the user during
		// the session
		File[] fileList = new File(System.getProperty("java.io.tmpdir"))
				.listFiles(new TempFilenameFilter((String) event.getSession()
						.getAttribute(ApplicationProperties.TMPFILE_PREFIX)));

		for (File f : fileList) {
			f.delete();
		}
	}

	private class TempFilenameFilter implements FilenameFilter {

		private String prefix;

		public TempFilenameFilter(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.startsWith(this.prefix);
		}

	}

}
