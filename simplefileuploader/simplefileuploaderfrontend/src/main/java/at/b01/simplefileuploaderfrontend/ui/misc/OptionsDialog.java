package at.b01.simplefileuploaderfrontend.ui.misc;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class OptionsDialog extends Window implements ClickListener {

	private static final long serialVersionUID = -5855682266421304991L;
	final Button button1;
	final DialogCallback callback;

	public OptionsDialog(String captionText, String labelText,
			String buttonTextLeft, String buttonTextRight,
			DialogCallback callback) {
		final VerticalLayout mainLayout = new VerticalLayout();
		final HorizontalLayout buttonLayout = new HorizontalLayout();
		button1 = new Button(buttonTextLeft, this);
		final Button button2 = new Button(buttonTextRight, this);
		final Label label = new Label(labelText);

		button1.setWidth(50f, Unit.PERCENTAGE);
		button2.setWidth(50f, Unit.PERCENTAGE);

		buttonLayout.addComponent(button1);
		buttonLayout.addComponent(button2);
		buttonLayout.setWidth(100f, Unit.PERCENTAGE);
		buttonLayout.setSpacing(true);

		mainLayout.addComponent(label);
		mainLayout.addComponent(buttonLayout);

		setContent(mainLayout);

		setCaption(captionText);
		setModal(true);
		setResizable(false);
		setClosable(false);
		setDraggable(true);
		this.callback = callback;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		this.callback.onDialogResult(event.getComponent() == button1);
		this.close();
	}

	public interface DialogCallback {

		public void onDialogResult(boolean resultIsLeftButton);
	}
}
