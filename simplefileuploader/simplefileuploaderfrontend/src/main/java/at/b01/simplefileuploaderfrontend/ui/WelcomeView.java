package at.b01.simplefileuploaderfrontend.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;

public class WelcomeView extends CustomComponent {

	private static final long serialVersionUID = 1984725369301249087L;
	private final NativeButton buttonRequestDownload = new NativeButton(
			"Request a file!");

	public WelcomeView() {
		initGui();
	}

	private void initGui() {
		final VerticalLayout mainLayout = new VerticalLayout();
		final HorizontalLayout layout = new HorizontalLayout();

		buttonRequestDownload.setHeight(15f, Unit.PERCENTAGE);
		buttonRequestDownload.setWidth(20f, Unit.PERCENTAGE);
		buttonRequestDownload.addStyleName("button-request-download");

		layout.addComponent(buttonRequestDownload);

		layout.setComponentAlignment(buttonRequestDownload,
				Alignment.MIDDLE_CENTER);

		layout.setHeight(80f, Unit.PERCENTAGE);
		layout.setWidth(80f, Unit.PERCENTAGE);
		layout.addStyleName("layout_welcome");

		mainLayout.addComponent(layout);
		mainLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();

		setCompositionRoot(mainLayout);
		setSizeFull();
		addStyleName("views_generic_design");
		addStyleName("view_welcome");
	}

	public void addRequestDownloadClickListener(Button.ClickListener listener) {
		buttonRequestDownload.addClickListener(listener);
	}
}
