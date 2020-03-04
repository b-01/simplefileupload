package at.b01.simplefileuploaderfrontend.ui;

import at.b01.simplefileuploaderfrontend.business.InputSanitizer;
import at.b01.simplefileuploaderfrontend.model.UserData;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AdminView extends CustomComponent {

	private static final long serialVersionUID = -2863177265343420171L;
	private final Table tableUsers = new Table();
	private final Button buttonAddUser = new Button();
	private final Button buttonRemUser = new Button();
	private final Button buttonSave = new Button("Add");
	private final Button buttonCancel = new Button("Cancel");
	private final Panel panelDisplay = new Panel();
	private final TextField textUsername = new TextField("Username");
	private final PasswordField textPassword1 = new PasswordField("Password");
	private final PasswordField textPassword2 = new PasswordField("Password2");

	public AdminView() {
		initGui();
		disableDataPanel();
	}

	private void initGui() {
		final HorizontalLayout mainLayout = new HorizontalLayout();
		final HorizontalLayout buttonLayout = new HorizontalLayout();
		final HorizontalLayout centerDataLayout = new HorizontalLayout();
		final HorizontalLayout dataButtonLayout = new HorizontalLayout();
		final VerticalLayout dataLayout = new VerticalLayout();
		final HorizontalLayout centerDataPanelLayout = new HorizontalLayout();
		final HorizontalLayout centerAllLayout = new HorizontalLayout();
		final VerticalLayout tableLayout = new VerticalLayout();

		tableUsers.setSizeFull();
		tableUsers.setSelectable(true);
		tableUsers.setMultiSelect(false);
		tableUsers.setEditable(false);
		tableUsers.setNullSelectionAllowed(true);
		tableUsers.setSortAscending(true);
		tableUsers.setSortEnabled(true);

		textUsername.setRequired(true);
		textPassword1.setRequired(true);
		textPassword2.setRequired(true);

		textUsername.setInputPrompt("Username eg. 'user'");

		// TODO: INPUT VALIDATION && MAX ALLOWED CHARS

		textUsername.setRequiredError("Please enter a value!");
		textPassword1.setRequiredError("Please enter a value!");
		textPassword2.setRequiredError("Please enter a value!");

		buttonAddUser.setWidth(100f, Unit.PERCENTAGE);
		buttonRemUser.setWidth(100f, Unit.PERCENTAGE);
		buttonSave.setWidth(100f, Unit.PERCENTAGE);
		buttonCancel.setWidth(100f, Unit.PERCENTAGE);

		buttonAddUser.setIcon(new ThemeResource("img/add.png"), "Add User");
		buttonRemUser.setIcon(new ThemeResource("img/remove.png"),
				"Remove User");
		buttonSave.setIcon(new ThemeResource("img/ok.png"), "OK");
		buttonCancel.setIcon(new ThemeResource("img/cancel.png"), "Cancel");

		buttonLayout.addComponent(buttonAddUser);
		buttonLayout.addComponent(buttonRemUser);
		buttonLayout.setMargin(true);
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth(100f, Unit.PERCENTAGE);

		tableLayout.addComponent(tableUsers);
		tableLayout.addComponent(buttonLayout);
		tableLayout.setSizeFull();
		tableLayout.setExpandRatio(tableUsers, 5);
		tableLayout.setExpandRatio(buttonLayout, 1);

		dataButtonLayout.addComponent(buttonSave);
		dataButtonLayout.addComponent(buttonCancel);
		dataButtonLayout.setSpacing(true);

		dataLayout.addComponent(textUsername);
		dataLayout.addComponent(textPassword1);
		dataLayout.addComponent(textPassword2);
		dataLayout.addComponent(dataButtonLayout);
		dataLayout.setSpacing(true);
		dataLayout.setSizeUndefined();

		centerDataLayout.addComponent(dataLayout);
		centerDataLayout.setComponentAlignment(dataLayout,
				Alignment.MIDDLE_CENTER);
		centerDataLayout.setSizeFull();

		panelDisplay.setCaption("User information");
		panelDisplay.setContent(centerDataLayout);
		panelDisplay.setWidth(80f, Unit.PERCENTAGE);
		panelDisplay.setHeight(80f, Unit.PERCENTAGE);

		centerDataPanelLayout.addComponent(panelDisplay);
		centerDataPanelLayout.setComponentAlignment(panelDisplay,
				Alignment.MIDDLE_CENTER);
		centerDataPanelLayout.setSizeFull();

		centerAllLayout.addComponent(tableLayout);
		centerAllLayout.addComponent(centerDataPanelLayout);
		centerAllLayout.setWidth(70f, Unit.PERCENTAGE);
		centerAllLayout.setHeight(50f, Unit.PERCENTAGE);

		mainLayout.addComponent(centerAllLayout);
		mainLayout.setComponentAlignment(centerAllLayout,
				Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();

		setCompositionRoot(mainLayout);
		setSizeFull();

		addStyleName("views_generic_design");
		addStyleName("view_admin");
	}

	public boolean isInputValid() {
		return (textUsername.isValid() && textPassword1.isValid() && textPassword2
				.isValid());
	}

	public String getUsername() {
		return InputSanitizer.getInstance().sanitize(textUsername.getValue());
	}

	public String getPasswordIfMatch() {
		if (textPassword1.getValue().equals(textPassword2.getValue())) {
			return textPassword1.getValue();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public UserData getSelectedUserData() {
		Object o = tableUsers.getValue();
		if (o == null) {
			return null;
		}
		return ((BeanContainer<Long, UserData>) tableUsers
				.getContainerDataSource()).getItem(o).getBean();
	}

	public void clearPanel() {
		textUsername.setValue("");
		clearPasswords();
	}

	public void clearPasswords() {
		textPassword1.setValue("");
		textPassword2.setValue("");
	}

	public void setTableData(BeanContainer<Long, UserData> c) {
		tableUsers.setContainerDataSource(null);
		tableUsers.setContainerDataSource(c);
		tableUsers.setVisibleColumns(new Object[] { "username", "files" });
	}

	public void enableDataPanel() {
		panelDisplay.setEnabled(true);
	}

	public void disableDataPanel() {
		clearPanel();
		panelDisplay.setEnabled(false);
	}

	public void addAddUserClickListener(Button.ClickListener listener) {
		buttonAddUser.addClickListener(listener);
	}

	public void addRemUserClickListener(Button.ClickListener listener) {
		buttonRemUser.addClickListener(listener);
	}

	public void addSaveClickListener(Button.ClickListener listener) {
		buttonSave.addClickListener(listener);
	}

	public void addCancelClickListener(Button.ClickListener listener) {
		buttonCancel.addClickListener(listener);
	}

}
