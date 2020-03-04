package at.b01.simplefileuploaderfrontend.controller;

public class Views {

	public static final int ADMIN = 0;
	public static final int WELCOME = 1;
	public static final int UPLOAD = 2;
	public static final int DOWNLOAD = 3;
	public static final int OVERVIEW = 4;

	// XXX: UPDATE IF NEW VIEW IS ADDED!
	private static final int MIN = ADMIN;
	private static final int MAX = OVERVIEW;

	public static String getPageTitle(int view) {
		switch (view) {

		case Views.ADMIN:
			return "Adminview - Simple File Uploader";
		case Views.DOWNLOAD:
			return "Downloadview - Simple File Uploader";
		case Views.UPLOAD:
			return "Uploadview - Simple File Uploader";
		case Views.OVERVIEW:
			return "Overview - Simple File Uploader";
		case Views.WELCOME:
			return "Simple File Uploader";
		default:
			return "Simple File Uploader";
		}
	}

	public static boolean isValidViewId(int view) {
		return (view >= MIN && view <= MAX);
	}

	public static boolean isRestrictedView(int view) {
		return (view == Views.ADMIN || view == Views.UPLOAD || view == Views.OVERVIEW);
	}
}
