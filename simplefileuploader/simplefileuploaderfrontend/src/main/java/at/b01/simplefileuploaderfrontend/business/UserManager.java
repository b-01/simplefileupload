package at.b01.simplefileuploaderfrontend.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.b01.simplefileuploaderdatabase.dao.UserDAO;
import at.b01.simplefileuploaderdatabase.entities.User;
import at.b01.simplefileuploaderdatabase.util.CryptoUtil;
import at.b01.simplefileuploaderfrontend.model.UserData;

import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;

public class UserManager {

	private static final Logger logger = LoggerFactory
			.getLogger(UserManager.class);
	private static final UserDAO userDao = UserDAO.getInstance();
	private static final UserManager instance = new UserManager();

	private UserManager() {
		logger.debug("Usermanager instantiated!");
	}

	public static UserManager getInstance() {
		return instance;
	}

	public User login(String username, String password) {
		User u = userDao.findByUsername(username);
		if (u == null) {
			return null;
		}
		String pw = CryptoUtil.generatePassword(password, u.getSalt());

		if (pw.equals(u.getPassword())) {
			return u;
		}
		return null;
	}

	public BeanContainer<Long, UserData> getUserContainer() {
		List<User> list = userDao.findAll();
		BeanContainer<Long, UserData> container = new BeanContainer<Long, UserData>(
				UserData.class);
		container.setBeanIdResolver(new BeanIdResolver<Long, UserData>() {

			private static final long serialVersionUID = 6074765523260701264L;

			@Override
			public Long getIdForBean(UserData bean) {
				return bean.getUserId();
			}
		});

		if (list == null) {
			return container;
		}

		for (User u : list) {
			Long count = StorageManager.getInstance().getFileCountByUserId(
					u.getId());
			container.addBean(new UserData(u.getId(), InputSanitizer
					.getInstance().sanitize(u.getUsername()), count));
		}

		return container;
	}

	public boolean addUser(String username, String password) {
		return userDao.insertElement(new User(username, password));
	}

	public boolean removeUser(Long userId) {
		// XXX: any better idea?
		StorageManager.getInstance().removeStorageFilesByUserId(userId);
		if (userDao.removeElement(userId)) {
			return true;
		}
		return false;
	}
}
