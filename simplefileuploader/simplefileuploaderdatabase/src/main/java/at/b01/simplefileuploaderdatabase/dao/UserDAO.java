/**
 * 
 */
package at.b01.simplefileuploaderdatabase.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.b01.simplefileuploaderdatabase.entities.User;
import at.b01.simplefileuploaderdatabase.util.DaoUtil;
import at.b01.simplefileuploaderdatabase.util.SessionProvider;

/**
 * @author b01
 * 
 */
public class UserDAO implements GenericDao<User, Long> {

	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	private static final UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	private UserDAO() {

	}

	public List<User> findAll() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Query q = session.getNamedQuery("findAllUsers");
			@SuppressWarnings("unchecked")
			List<User> returnedList = (List<User>) q.list();

			return returnedList;
		} catch (Exception ex) {
			logger.error("Could not load the users from the database!", ex);
			return null;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public User findByID(Long id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			return (User) session.get(User.class, id);
		} catch (Exception ex) {
			logger.error("Could not load the user with id: '{}'!", id, ex);
			return null;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public User findByUsername(String username) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Query q = session.getNamedQuery("findUserByUsername");
			q.setParameter("username", username);
			User returnedObject = (User) q.uniqueResult();

			return returnedObject;
		} catch (Exception ex) {
			logger.error("Could not load the user: '{}'!", username, ex);
			return null;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public boolean insertElement(User user) {
		if (user == null || user.getPassword() == null
				|| user.getSalt() == null || user.getUsername() == null
				|| user.getPassword().isEmpty() || user.getSalt().isEmpty()
				|| user.getUsername().isEmpty()) {
			logger.error("Cannot insert user!");
			return false;
		}

		if (!user.validate()) {
			logger.error("A user property is longer than allowed!");
			return false;
		}

		if (findByUsername(user.getUsername()) != null) {
			logger.error("User already exists!");
			return false;
		}

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Object o = session.merge(user);

			if (o != null) {
				return true;
			}
			logger.error("Could not insert the user to the database!");
			return false;
		} catch (Exception ex) {
			logger.error("Could not insert the user to the database!", ex);
			return false;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public boolean removeElement(Long userId) {
		User u = null;
		if (userId == null || (u = findByID(userId)) == null) {
			logger.error("User is not removeable!");
			return false;
		}

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Query q = session.getNamedQuery("removeUserById");
			q.setParameter("userid", u.getId());
			if (q.executeUpdate() >= 1) {
				return true;
			}
			logger.error("Could not remove the users in the database!");
			return false;
		} catch (Exception ex) {
			logger.error("Could not remove the users in the database!", ex);
			return false;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public boolean removeElement(User user) {
		if (user == null) {
			logger.error("User is not removeable!");
			return false;
		}
		return removeElement(user.getId());
	}

	public boolean updateUser(User user) {
		logger.info("updateUser(User user) not implemented!");
		return false;
	}

}
