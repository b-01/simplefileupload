package at.b01.simplefileuploaderfrontend.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.b01.simplefileuploaderdatabase.dao.StorageDAO;
import at.b01.simplefileuploaderdatabase.entities.Storage;
import at.b01.simplefileuploaderdatabase.util.CryptoUtil;
import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;

import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;

public class StorageManager {

	// TODO: ADD LOGGING TO CLASS!
	// TODO: ADD JAVADOC TO CLASS!

	private static final Logger logger = LoggerFactory
			.getLogger(StorageManager.class);
	private static final StorageDAO storageDao = StorageDAO.getInstance();
	private static final StorageManager instance = new StorageManager();

	private StorageManager() {
		logger.debug("StorageManager instantiated!");
	}

	public static StorageManager getInstance() {
		return instance;
	}

	public String getFreeFileId(String filename) {
		// TODO: maybe make it private and remove the reference from
		// upload-controller.
		String uid = null;

		do {
			uid = CryptoUtil.generateStorageUid(filename);
		} while (storageDao.findByID(uid) != null);

		return uid;
	}

	private boolean moveFile(Path source, Path target) {
		// TODO: Maybe extract into Generic IO-Helper-Class?!
		try {
			Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
			return true;
		} catch (IOException e) {
			logger.error("Could not move the file [{}] to location [{}]!",
					source.toString(), target.toString(), e);
		}
		return false;
	}

	public boolean addStorageFile(Storage storage, File tempFile) {
		if (storageDao.insertElement(storage)) {
			File target = new File(
					ApplicationProperties.getInstance().FULL_STORAGE_PATH
							+ File.separator + storage.getUid());
			if (moveFile(tempFile.toPath(), target.toPath())) {
				return true;
			}

		}
		removeStorageFile(storage);
		return false;
	}

	public boolean removeStorageFile(Storage storage) {
		return removeStorageFile(storage.getUid());
	}

	public boolean removeStorageFile(String storageId) {
		if (storageId == null || storageId.isEmpty()
				|| !CryptoUtil.isValidStorageUid(storageId)) {
			logger.warn(
					"Cant get StorageFile! Reason: StorageId is not valid [id:{}]!",
					storageId);
			return false;
		}
		if (storageDao.removeElement(storageId)) {
			File stored_file = new File(
					ApplicationProperties.getInstance().FULL_STORAGE_PATH
							+ File.separator + storageId);
			if (stored_file.exists()) {
				if (!stored_file.delete()) {
					logger.warn("Could not delete stored file: {}",
							stored_file.getAbsolutePath());
				}
			}
			return true;
		}
		return false;
	}

	public boolean removeStorageFilesByUserId(Long userId) {
		List<Storage> list = storageDao.findAllByUserId(userId);
		if (list == null || list.isEmpty()) {
			// NO FILES EXIST, ALL OK
			return true;
		}

		boolean returnValue = true;
		for (Storage s : list) {
			// TODO: we'll get a warning when a file is not deletable, but maybe
			// there is a better solution?!
			if (!removeStorageFile(s)) {
				returnValue = false;
			}
		}
		// Returns false if one of the files could not be deleted. (But normally
		// this should not happen, so if it really does, it does not bother..).
		return returnValue;
	}

	public Long getFileCountByUserId(Long userId) {
		if (userId == null) {
			logger.warn("getFileCountByUserId - Userid is null!", userId);
			return 0L;
		}
		Long count = storageDao.findCountByUserid(userId);
		if (count == null) {
			logger.warn("Could not load filecount for userid={}", userId);
			return 0L;
		}
		return count;
	}

	public Storage getStorageFileById(String storageId) {
		if (!CryptoUtil.isValidStorageUid(storageId)) {
			logger.warn(
					"Cant get StorageFile! Reason: StorageId is not valid [id:{}]!",
					storageId);
			return null;
		}
		return storageDao.findByID(storageId);
	}

	public BeanContainer<String, Storage> getStorageContainer(Long userId) {
		List<Storage> list = storageDao.findAllByUserId(userId);
		BeanContainer<String, Storage> container = new BeanContainer<String, Storage>(
				Storage.class);
		container.setBeanIdResolver(new BeanIdResolver<String, Storage>() {

			private static final long serialVersionUID = 6074765523260701264L;

			@Override
			public String getIdForBean(Storage bean) {
				return bean.getUid();
			}
		});

		if (list == null) {
			return container;
		}

		container.addAll(list);

		return container;
	}
}
