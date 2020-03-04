/**
 * 
 */
package at.b01.simplefileuploaderdatabase.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author b01
 * 
 */
public class SessionProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(SessionProvider.class);
	private static SessionProvider instance;
	private final SessionFactory sessionFactory;

	/**
	 * 
	 * @param username
	 * @param password
	 * @throws EncapsulatedDatabaseException
	 */
	private SessionProvider(final String username, final String password)
			throws EncapsulatedDatabaseException {
		sessionFactory = createConnection(username, password);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws EncapsulatedDatabaseException
	 */
	public static synchronized SessionProvider initialize(
			final String username, final String password)
			throws EncapsulatedDatabaseException {
		if (instance == null) {
			instance = new SessionProvider(username, password);
		}
		return instance;
	}

	/**
	 * 
	 * @return
	 * @throws NullPointerException
	 */
	public static synchronized SessionProvider getInstance()
			throws NullPointerException {
		if (instance == null) {
			throw new NullPointerException(
					"Can not load Instance, call initialize(String, String) first!");
		}
		return instance;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws EncapsulatedDatabaseException
	 */
	private SessionFactory createConnection(final String username,
			final String password) throws EncapsulatedDatabaseException {
		try {
			SessionFactory tmp = null;
			Configuration configuration = new Configuration()
					.configure(
							"at/b01/simplefileuploaderdatabase/config/hibernate.cfg.xml")
					.setProperty("hibernate.connection.username", username)
					.setProperty("hibernate.connection.password", password);

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			tmp = configuration.buildSessionFactory(serviceRegistry);
			if (logger.isDebugEnabled()) {
				logger.debug("SessionFactory creation successfull");
			}
			return tmp;
		} catch (Exception ex) {
			throw new EncapsulatedDatabaseException(ex);
		}
	}

	/**
	 * 
	 * @return
	 * @throws EncapsulatedDatabaseException
	 */
	public Session getSession() throws EncapsulatedDatabaseException {
		try {
			if (sessionFactory == null || sessionFactory.isClosed()) {
				if (logger.isDebugEnabled()) {
					logger.debug("SessionFactory null or closed");
				}
				throw new EncapsulatedDatabaseException(
						"SessionFactory null or closed...");
			}
			return sessionFactory.getCurrentSession();
		} catch (Exception ex) {
			throw new EncapsulatedDatabaseException(ex);
		}
	}

	/**
	 * 
	 * @return
	 * @throws EncapsulatedDatabaseException
	 */
	public Session getNewSession() throws EncapsulatedDatabaseException {
		if (sessionFactory == null || sessionFactory.isClosed()) {
			if (logger.isDebugEnabled()) {
				logger.debug("SessionFactory null or closed");
			}
			throw new EncapsulatedDatabaseException(
					"SessionFactory null or closed...");
		}
		return sessionFactory.openSession();
	}
}
