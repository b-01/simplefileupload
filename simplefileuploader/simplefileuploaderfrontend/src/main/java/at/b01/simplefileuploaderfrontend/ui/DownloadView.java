package at.b01.simplefileuploaderfrontend.ui;

import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class DownloadView extends CustomComponent {

	private static final long serialVersionUID = -4906763107537899166L;
	private final TextField textStorageUid = new TextField();
	private final TextField textFilename = new TextField();
	private final Button buttonCheckUid = new Button("Check Token");
	private final Button buttonNewToken = new Button("Enter new Token");
	private final Button buttonDownload = new Button("Download file!");
	private final VerticalLayout downloadLayout = new VerticalLayout();
	private final VerticalLayout storageUidLayout = new VerticalLayout();
	private final VerticalLayout centerAllLayout = new VerticalLayout();

	public DownloadView() {
		initGui();
		setDownloadPanelVisible(false);
	}

	private void initGui() {
		final VerticalLayout mainLayout = new VerticalLayout();
		final FormLayout layoutFileName = new FormLayout();
		final FormLayout layoutStorageUid = new FormLayout();
		final HorizontalLayout layoutButtons = new HorizontalLayout();

		textFilename.setReadOnly(true);
		textFilename.setCaption("Filename:");
		textFilename.setIcon(new ThemeResource("img/file.png"));

		textStorageUid.setCaption("File-Token:");
		textStorageUid
				.setColumns(ApplicationProperties.TEXTFIELD_TOKEN_COLUMNS);

		buttonDownload.setWidth(100f, Unit.PERCENTAGE);
		buttonDownload.setIcon(new ThemeResource("img/download.png"));
		buttonNewToken.setStyleName(BaseTheme.BUTTON_LINK);

		buttonCheckUid.setWidth(100f, Unit.PERCENTAGE);

		layoutFileName.addComponent(textFilename);
		layoutFileName.setMargin(true);
		layoutFileName.setSizeUndefined();

		layoutButtons.addComponent(buttonDownload);
		layoutButtons.addComponent(buttonNewToken);
		layoutButtons.setComponentAlignment(buttonDownload,
				Alignment.MIDDLE_CENTER);
		layoutButtons.setComponentAlignment(buttonNewToken,
				Alignment.MIDDLE_CENTER);
		layoutButtons.setSpacing(true);
		layoutButtons.setWidth(100f, Unit.PERCENTAGE);

		downloadLayout.addComponent(layoutFileName);
		downloadLayout.addComponent(layoutButtons);
		downloadLayout.setSpacing(true);
		downloadLayout.setSizeUndefined();

		layoutStorageUid.addComponent(textStorageUid);
		layoutStorageUid.addComponent(buttonCheckUid);
		layoutStorageUid.setMargin(true);
		layoutStorageUid.setSpacing(true);
		layoutStorageUid.setSizeUndefined();

		storageUidLayout.addComponent(layoutStorageUid);
		storageUidLayout.setSizeUndefined();

		centerAllLayout.setSizeUndefined();

		mainLayout.addComponent(centerAllLayout);
		mainLayout.setComponentAlignment(centerAllLayout,
				Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();

		setSizeFull();
		setCompositionRoot(mainLayout);

		addStyleName("views_generic_design");
		addStyleName("view_download");
	}

	public void setDownloadPanelVisible(boolean visible) {
		if (visible) {
			centerAllLayout.replaceComponent(storageUidLayout, downloadLayout);
			centerAllLayout.setComponentAlignment(downloadLayout,
					Alignment.TOP_CENTER);
		} else {
			centerAllLayout.replaceComponent(downloadLayout, storageUidLayout);
			centerAllLayout.setComponentAlignment(storageUidLayout,
					Alignment.TOP_CENTER);
		}
	}

	public void setStorageFileName(String filename) {
		textFilename.setReadOnly(false);
		textFilename.setValue(filename);
		textFilename.setReadOnly(true);
	}

	public String getToken() {
		return textStorageUid.getValue();
	}

	public void clearToken() {
		textStorageUid.setValue("");
	}

	public void addCheckUidClickListener(Button.ClickListener listener) {
		this.buttonCheckUid.addClickListener(listener);
	}

	public void setOnDemandDownloader(FileDownloader downloader) {
		downloader.extend(this.buttonDownload);
	}

	public void addNewTokenClickListener(Button.ClickListener listener) {
		this.buttonNewToken.addClickListener(listener);
	}
}
