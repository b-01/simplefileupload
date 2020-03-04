package at.b01.simplefileuploaderfrontend.controller.events;

import java.util.EventListener;

public interface ChangeViewEventListener extends EventListener {

	public void changeViewRequested(ChangeViewEvent event);
}
