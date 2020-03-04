package at.b01.simplefileuploaderfrontend.ui;

import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class UploadView extends CustomComponent {

	private static final long serialVersionUID = -4906763107537899166L;
	private final ProgressBar progressBar = new ProgressBar(0.0f);
	private final Upload fileUploader = new Upload();
	private final TextField textFieldDownloadLink = new TextField();
	private final Button buttonNewUpload = new Button("Upload new file!");
	private final Panel panelInformation = new Panel();

	public UploadView() {
		initGui();
		setPanelInformationVisible(false);
		setProgressBarEnabled(false);
	}

	private void initGui() {
		final VerticalLayout mainLayout = new VerticalLayout();
		final VerticalLayout centerAllLayout = new VerticalLayout();
		final VerticalLayout panelLayout = new VerticalLayout();
		final VerticalLayout centerPanelLayout = new VerticalLayout();
		final Panel panelUpload = new Panel();

		fileUploader.setButtonCaption("Upload!");
		fileUploader.setCaption("Select a file to upload");

		progressBar.setWidth(100f, Unit.PERCENTAGE);

		centerPanelLayout.addComponent(fileUploader);
		centerPanelLayout.addComponent(progressBar);
		centerPanelLayout.setSpacing(true);
		centerPanelLayout.setMargin(true);

		panelLayout.addComponent(centerPanelLayout);
		panelLayout.setSizeFull();
		panelLayout.setComponentAlignment(centerPanelLayout,
				Alignment.MIDDLE_CENTER);

		panelUpload.setCaption("Upload a file");
		panelUpload.setContent(panelLayout);
		panelUpload.setSizeUndefined();

		final VerticalLayout panelInfoLayout = new VerticalLayout();
		final VerticalLayout centerPanelInfoLayout = new VerticalLayout();
		final Label labelInfotext = new Label(
				"You can download your file using the link below!");

		buttonNewUpload.setSizeFull();
		textFieldDownloadLink.setSizeFull();
		textFieldDownloadLink
				.setColumns(ApplicationProperties.TEXTFIELD_TOKEN_COLUMNS);
		textFieldDownloadLink.setReadOnly(true);

		centerPanelInfoLayout.addComponent(labelInfotext);
		centerPanelInfoLayout.addComponent(textFieldDownloadLink);
		centerPanelInfoLayout.addComponent(buttonNewUpload);
		centerPanelInfoLayout.setSpacing(true);
		centerPanelInfoLayout.setMargin(true);

		panelInfoLayout.addComponent(centerPanelInfoLayout);
		panelInfoLayout.setSizeUndefined();
		panelInfoLayout.setComponentAlignment(centerPanelInfoLayout,
				Alignment.MIDDLE_CENTER);

		panelInformation.setCaption("Download Information");
		panelInformation.setContent(panelInfoLayout);
		panelInformation.setSizeUndefined();

		centerAllLayout.addComponent(panelUpload);
		centerAllLayout.addComponent(panelInformation);
		centerAllLayout
				.setComponentAlignment(panelUpload, Alignment.TOP_CENTER);
		centerAllLayout.setComponentAlignment(panelInformation,
				Alignment.BOTTOM_CENTER);
		centerAllLayout.setSpacing(true);
		centerAllLayout.setSizeUndefined();

		mainLayout.addComponent(centerAllLayout);
		mainLayout.setComponentAlignment(centerAllLayout,
				Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();

		setSizeFull();
		setCompositionRoot(mainLayout);

		addStyleName("views_generic_design");
		addStyleName("view_upload");

	}

	public void setPanelInformationVisible(boolean visible) {
		panelInformation.setVisible(visible);
	}

	public void setFileUploadEnabled(boolean enabled) {
		fileUploader.setEnabled(enabled);
	}

	public void setProgressBarEnabled(boolean enabled) {
		progressBar.setEnabled(enabled);
	}

	public void setProgress(float value) {
		progressBar.setValue(value);
	}

	public void setUploadReceiver(Receiver receiver) {
		fileUploader.setReceiver(receiver);
	}

	public void setDownloadLink(String link) {
		textFieldDownloadLink.setReadOnly(false);
		textFieldDownloadLink.setValue(link);
		textFieldDownloadLink.setReadOnly(true);
	}

	public void addProgressListener(ProgressListener listener) {
		fileUploader.addProgressListener(listener);
	}

	public void addSucceededListener(SucceededListener listener) {
		fileUploader.addSucceededListener(listener);
	}

	public void addStartedListener(StartedListener listener) {
		fileUploader.addStartedListener(listener);
	}

	public void addFailedListener(FailedListener listener) {
		fileUploader.addFailedListener(listener);
	}

	public void addNewUploadClickListener(Button.ClickListener listener) {
		buttonNewUpload.addClickListener(listener);
	}
}
