package at.b01.simplefileuploaderfrontend.ui.header;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class LoginHeader extends Header {

	private static final long serialVersionUID = 4111509053377518L;

	private final TextField textUsername = new TextField();
	private final PasswordField textPassword = new PasswordField();
	private final Button buttonLogin = new Button("Login");

	public LoginHeader() {
		final HorizontalLayout headerLayout = new HorizontalLayout();
		final HorizontalLayout wrapperLayout = new HorizontalLayout();

		final Label img = new Label("Simple File Uploader");
		img.addStyleName("header-label");

		textUsername.setWidth(10, Unit.EM);
		textUsername.setMaxLength(50);
		textUsername.setCaption("Username");
		textPassword.setWidth(7, Unit.EM);
		textPassword.setCaption("Password");

		buttonLogin.setClickShortcut(KeyCode.ENTER);
		buttonLogin.setIcon(new ThemeResource("img/login.png"));

		wrapperLayout.addComponent(textUsername);
		wrapperLayout.addComponent(textPassword);
		wrapperLayout.addComponent(buttonLogin);
		wrapperLayout.setSpacing(true);

		wrapperLayout
				.setComponentAlignment(buttonLogin, Alignment.BOTTOM_RIGHT);

		headerLayout.addComponent(img);
		headerLayout.addComponent(wrapperLayout);

		headerLayout.setComponentAlignment(img, Alignment.MIDDLE_LEFT);
		headerLayout.setComponentAlignment(wrapperLayout,
				Alignment.MIDDLE_RIGHT);

		headerLayout.setWidth(100, Unit.PERCENTAGE);

		this.addComponent(headerLayout);
		this.setComponentAlignment(headerLayout, Alignment.MIDDLE_CENTER);

		this.setMargin(true);
		this.setSpacing(true);
		this.setWidth(100f, Unit.PERCENTAGE);
		this.setHeight(65f, Unit.PIXELS);
	}

	public String getUsername() {
		return textUsername.getValue();
	}

	public String getPassword() {
		return textPassword.getValue();
	}

	public void clearInput() {
		textUsername.setValue("");
		textPassword.setValue("");
	}

	public void addLoginClickListener(Button.ClickListener listener) {
		buttonLogin.addClickListener(listener);
	}
}
