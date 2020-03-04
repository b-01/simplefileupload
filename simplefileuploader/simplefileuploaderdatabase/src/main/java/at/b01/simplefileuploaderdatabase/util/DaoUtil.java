package at.b01.simplefileuploaderdatabase.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DaoUtil {

	public static boolean closeSessionAndTransaction(Session s, Transaction tx) {
		if (tx != null) {
			tx.commit();
		}
		if (s != null) {
			s.close();
		}
		return true;
	}
}
