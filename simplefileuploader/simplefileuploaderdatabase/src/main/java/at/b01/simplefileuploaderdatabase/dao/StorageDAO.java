/**
 * 
 */
package at.b01.simplefileuploaderdatabase.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.b01.simplefileuploaderdatabase.entities.Storage;
import at.b01.simplefileuploaderdatabase.util.DaoUtil;
import at.b01.simplefileuploaderdatabase.util.SessionProvider;

/**
 * @author b01
 * 
 */
public class StorageDAO implements GenericDao<Storage, String> {

	private static final Logger logger = LoggerFactory
			.getLogger(StorageDAO.class);

	private static final StorageDAO instance = new StorageDAO();

	public static StorageDAO getInstance() {
		return instance;
	}

	private StorageDAO() {

	}

	public Long findCountByUserid(Long id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Query q = session.getNamedQuery("findFilecountByUserid");
			q.setLong("id", id);
			Long count = (Long) q.uniqueResult();

			return count;
		} catch (Exception ex) {
			logger.error("Could not load the filecount from the database!", ex);
			return null;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public List<Storage> findAll() {
		logger.warn("Method not implemented!");
		return Collections.emptyList();
	}

	public List<Storage> findAllByUserId(Long userId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Query q = session.getNamedQuery("findFilesByUserid");
			q.setParameter("id", userId);
			@SuppressWarnings("unchecked")
			List<Storage> returnedList = (List<Storage>) q.list();

			return returnedList;
		} catch (Exception ex) {
			logger.error("Could not load the storage files from the database!",
					ex);
			return null;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public Storage findByID(String uid) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			return (Storage) session.get(Storage.class, uid);

		} catch (Exception ex) {
			logger.error("Could not load the storage element with id: '{}'!",
					uid, ex);
			return null;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public boolean insertElement(Storage storage) {
		if (storage == null || storage.getName() == null
				|| storage.getUserid() == null || storage.getName().isEmpty()
				|| storage.getUid() == null || storage.getUid().isEmpty()) {
			logger.error("Cannot insert storage element!");
			return false;
		}

		if (!storage.validate()) {
			logger.error("A storage property is longer than allowed!");
			return false;
		}

		if (findByID(storage.getUid()) != null) {
			logger.error("GUUID does exist in database! [UUID={}]",
					storage.getUid());
			return false;
		}

		if (UserDAO.getInstance().findByID(storage.getUserid()) == null) {
			logger.error("Associated UserId ({}) does not exists!",
					storage.getUserid());
			return false;
		}

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Object o = session.merge(storage);

			if (o != null) {
				return true;
			}
			logger.error(
					"Could not insert the storage file into the database! {}",
					storage);
			return false;
		} catch (Exception ex) {
			logger.error(
					"Could not insert the storage file into the database! {}",
					storage, ex);
			return false;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}

	public boolean removeElement(Storage storage) {
		if (storage == null) {
			logger.error("Storage file is not removeable!");
			return false;
		}
		return removeElement(storage.getUid());
	}

	public boolean removeElement(String uid) {
		Storage s = null;
		if (uid == null || (s = findByID(uid)) == null) {
			logger.error("Storage file is not removeable!");
			return false;
		}

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionProvider.getInstance().getNewSession();
			tx = session.beginTransaction();

			Query q = session.getNamedQuery("removeFileById");
			q.setParameter("storageid", s.getUid());
			if (q.executeUpdate() >= 1) {
				return true;
			}
			logger.error("Could not remove the file from the database! {}", s);
			return false;
		} catch (Exception ex) {
			logger.error("Could not remove the file from the database! {}", s,
					ex);
			return false;
		} finally {
			DaoUtil.closeSessionAndTransaction(session, tx);
		}
	}
}
