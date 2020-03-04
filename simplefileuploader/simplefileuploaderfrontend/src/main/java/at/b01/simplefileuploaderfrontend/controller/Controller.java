package at.b01.simplefileuploaderfrontend.controller;

import at.b01.simplefileuploaderfrontend.controller.events.ChangeViewEventListener;

import com.vaadin.ui.CustomComponent;

public interface Controller {

	public CustomComponent getView();

	public void viewEntered();

	public void addChangeViewEventListener(ChangeViewEventListener listener);
}
