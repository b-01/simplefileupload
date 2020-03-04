package at.b01.simplefileuploaderfrontend.ui.header;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class LogoutHeader extends Header {

	private static final long serialVersionUID = -3135617831142049876L;

	final Button buttonOverview = new Button("Overview");
	final Button buttonUpload = new Button("Upload");
	final Button buttonDownload = new Button("Download");
	final Button buttonAdmin = new Button("Admin-Page");
	final Button buttonLogout = new Button("Logout");

	final Label labelUsername = new Label();

	public LogoutHeader(boolean isAdmin) {

		final Label labelLogged = new Label("User: ");
		final Image imageUser = new Image();

		final HorizontalLayout layoutUsername = new HorizontalLayout();
		final VerticalLayout layoutUsernameAndButton = new VerticalLayout();
		final HorizontalLayout layoutNavigation = new HorizontalLayout();

		buttonOverview.setStyleName(BaseTheme.BUTTON_LINK);
		buttonUpload.setStyleName(BaseTheme.BUTTON_LINK);
		buttonDownload.setStyleName(BaseTheme.BUTTON_LINK);
		buttonAdmin.setStyleName(BaseTheme.BUTTON_LINK);

		buttonOverview.setIcon(new ThemeResource("img/bullet.png"));
		buttonUpload.setIcon(new ThemeResource("img/bullet.png"));
		buttonDownload.setIcon(new ThemeResource("img/bullet.png"));
		buttonAdmin.setIcon(new ThemeResource("img/bullet.png"));
		buttonLogout.setIcon(new ThemeResource("img/logout.png"));

		imageUser.setSource(new ThemeResource("img/user.png"));
		imageUser.setAlternateText("Username");
		imageUser.setDescription("Username");
		imageUser.addStyleName("icon-size");
		labelLogged.setSizeUndefined();
		labelUsername.setSizeUndefined();

		/*
		 * USERNAME DISPLAY
		 */
		layoutUsername.addComponent(imageUser);
		layoutUsername.addComponent(labelUsername);
		layoutUsername.setSpacing(true);

		layoutUsername
				.setComponentAlignment(imageUser, Alignment.MIDDLE_CENTER);
		layoutUsername.setComponentAlignment(labelUsername,
				Alignment.MIDDLE_CENTER);
		/*
		 * USERNAME WITH LOGOUT BUTTON
		 */
		layoutUsernameAndButton.addComponent(layoutUsername);
		layoutUsernameAndButton.addComponent(buttonLogout);
		layoutUsernameAndButton.setSpacing(true);
		layoutUsernameAndButton.setComponentAlignment(layoutUsername,
				Alignment.MIDDLE_CENTER);
		layoutUsernameAndButton.setComponentAlignment(buttonLogout,
				Alignment.MIDDLE_CENTER);
		/*
		 * ALL BUTTONS
		 */
		layoutNavigation.addComponent(buttonOverview);
		layoutNavigation.addComponent(buttonUpload);
		layoutNavigation.addComponent(buttonDownload);
		layoutNavigation.setComponentAlignment(buttonDownload,
				Alignment.MIDDLE_CENTER);
		layoutNavigation.setComponentAlignment(buttonOverview,
				Alignment.MIDDLE_CENTER);
		layoutNavigation.setComponentAlignment(buttonUpload,
				Alignment.MIDDLE_CENTER);
		if (isAdmin) {
			layoutNavigation.addComponent(buttonAdmin);
			layoutNavigation.setComponentAlignment(buttonAdmin,
					Alignment.MIDDLE_CENTER);
		}
		layoutNavigation.addComponent(layoutUsernameAndButton);
		layoutNavigation.setComponentAlignment(layoutUsernameAndButton,
				Alignment.MIDDLE_CENTER);

		layoutNavigation.setSpacing(true);
		layoutNavigation.setWidth(100f, Unit.PERCENTAGE);
		/*
		 * MAIN LAYOUT
		 */
		this.addComponent(layoutNavigation);
		this.setComponentAlignment(layoutNavigation, Alignment.MIDDLE_CENTER);

		this.setSpacing(true);
		this.setMargin(true);
		this.setWidth(100f, Unit.PERCENTAGE);
		this.setHeight(70f, Unit.PIXELS);

	}

	public void setUsername(String username) {
		labelUsername.setValue(username);
	}

	public void addOverviewClickListener(Button.ClickListener listener) {
		buttonOverview.addClickListener(listener);
	}

	public void addUploadClickListener(Button.ClickListener listener) {
		buttonUpload.addClickListener(listener);
	}

	public void addDownloadClickListener(Button.ClickListener listener) {
		buttonDownload.addClickListener(listener);
	}

	public void addAdminClickListener(Button.ClickListener listener) {
		buttonAdmin.addClickListener(listener);
	}

	public void addLogoutClickListener(Button.ClickListener listener) {
		buttonLogout.addClickListener(listener);
	}

}
