package at.b01.simplefileuploaderfrontend.ui.misc;

import at.b01.simplefileuploaderfrontend.model.ApplicationProperties;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseListener;

public class LinkPopup extends Window implements CloseListener {

	private static final long serialVersionUID = -5855682266421304991L;

	public LinkPopup(String captionText, String labelText, String linkToDisplay) {
		final VerticalLayout mainLayout = new VerticalLayout();
		final Label labelDescription = new Label();
		final TextField textLink = new TextField();

		labelDescription.setValue(labelText);
		textLink.setColumns(ApplicationProperties.TEXTFIELD_TOKEN_COLUMNS);
		textLink.setValue(linkToDisplay);

		mainLayout.addComponent(labelDescription);
		mainLayout.addComponent(textLink);

		setContent(mainLayout);

		setCaption(captionText);
		setModal(true);
		setResizable(true);
		setClosable(true);
		setDraggable(true);
	}

	@Override
	public void windowClose(CloseEvent e) {
		// if (e.getComponent() == this) {
		// this.close();
		// }
	}
}
