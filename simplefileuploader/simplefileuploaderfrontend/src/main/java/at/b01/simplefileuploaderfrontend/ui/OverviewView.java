package at.b01.simplefileuploaderfrontend.ui;

import at.b01.simplefileuploaderdatabase.entities.Storage;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class OverviewView extends CustomComponent {

	private static final long serialVersionUID = 1486371033525684620L;
	private final Table tableStorageFiles = new Table();
	private final Button buttonRemStorage = new Button();
	private final Button buttonAddStorage = new Button();
	private final Button buttonGetLink = new Button("Get download link");

	public OverviewView() {
		initGui();
	}

	private void initGui() {
		final VerticalLayout mainLayout = new VerticalLayout();
		final VerticalLayout centerAllLayout = new VerticalLayout();
		final HorizontalLayout buttonLayout = new HorizontalLayout();

		tableStorageFiles.setSelectable(true);
		tableStorageFiles.setMultiSelect(false);
		tableStorageFiles.setEditable(false);
		tableStorageFiles.setNullSelectionAllowed(true);
		tableStorageFiles.setSortAscending(true);
		tableStorageFiles.setSortEnabled(true);
		tableStorageFiles.setSizeUndefined();

		buttonGetLink.setWidth(90f, Unit.PERCENTAGE);
		buttonAddStorage.setWidth(100f, Unit.PERCENTAGE);
		buttonRemStorage.setWidth(100f, Unit.PERCENTAGE);

		buttonAddStorage.setIcon(new ThemeResource("img/add.png"), "Add File");
		buttonRemStorage.setIcon(new ThemeResource("img/remove.png"),
				"Remove File");

		buttonLayout.addComponent(buttonAddStorage);
		buttonLayout.addComponent(buttonGetLink);
		buttonLayout.addComponent(buttonRemStorage);
		buttonLayout.setMargin(true);
		buttonLayout.setSpacing(true);
		buttonLayout.setComponentAlignment(buttonGetLink,
				Alignment.MIDDLE_CENTER);
		buttonLayout.setComponentAlignment(buttonAddStorage,
				Alignment.MIDDLE_CENTER);
		buttonLayout.setComponentAlignment(buttonRemStorage,
				Alignment.MIDDLE_CENTER);

		buttonLayout.setExpandRatio(buttonGetLink, 4);
		buttonLayout.setExpandRatio(buttonAddStorage, 1);
		buttonLayout.setExpandRatio(buttonRemStorage, 1);

		buttonLayout.setWidth(100f, Unit.PERCENTAGE);

		centerAllLayout.addComponent(tableStorageFiles);
		centerAllLayout.addComponent(buttonLayout);
		centerAllLayout.setSizeUndefined();

		mainLayout.addComponent(centerAllLayout);
		mainLayout.setComponentAlignment(centerAllLayout,
				Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();

		setSizeFull();
		setCompositionRoot(mainLayout);

		addStyleName("views_generic_design");
		addStyleName("view_overview");
	}

	@SuppressWarnings("unchecked")
	public String getSelectedStorageId() {
		Object o = tableStorageFiles.getValue();
		if (o == null) {
			return null;
		}
		return ((BeanContainer<String, Storage>) tableStorageFiles
				.getContainerDataSource()).getItem(o).getBean().getUid();
	}

	public void setTableData(BeanContainer<String, Storage> c) {
		tableStorageFiles.setContainerDataSource(null);
		tableStorageFiles.setContainerDataSource(c);
		tableStorageFiles.setVisibleColumns(new Object[] { "name", "uid" });
	}

	public void addAddStorageClickListener(Button.ClickListener listener) {
		buttonAddStorage.addClickListener(listener);
	}

	public void addRemStorageClickListener(Button.ClickListener listener) {
		buttonRemStorage.addClickListener(listener);
	}

	public void addGetLinkClickListener(Button.ClickListener listener) {
		buttonGetLink.addClickListener(listener);
	}
}
