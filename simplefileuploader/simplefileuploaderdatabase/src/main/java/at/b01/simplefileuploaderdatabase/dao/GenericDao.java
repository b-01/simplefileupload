/**
 * 
 */
package at.b01.simplefileuploaderdatabase.dao;

import java.util.List;

/**
 * @author b01
 * 
 */
public interface GenericDao<T, E> {

	public List<T> findAll();

	public T findByID(E id);

	public boolean insertElement(T element);

	public boolean removeElement(T element);
}
