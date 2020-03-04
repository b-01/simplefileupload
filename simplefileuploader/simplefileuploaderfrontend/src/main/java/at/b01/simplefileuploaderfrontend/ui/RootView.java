package at.b01.simplefileuploaderfrontend.ui;

import at.b01.simplefileuploaderfrontend.ui.header.Header;

import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class RootView extends VerticalLayout {

	private static final long serialVersionUID = 3666627987099972415L;
	private CustomComponent content = null;
	private Header header = null;

	public RootView() {
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		addStyleName("views_generic_design");
		addStyleName("view_main");
	}

	private void updateExpandRatio() {
		if (getComponentCount() == 2) {
			setExpandRatio(getComponent(1), 1f);
		}
	}

	public void changeContent(CustomComponent view) {
		if (content == null) {
			content = view;
			addComponent(view, 1);
			updateExpandRatio();
			return;
		}
		replaceComponent(content, view);
		content = view;
		updateExpandRatio();
	}

	public void setHeader(Header h) {
		if (header == null) {
			header = h;
			addComponentAsFirst(header);
			updateExpandRatio();
			return;
		}
		replaceComponent(header, h);
		header = h;
		updateExpandRatio();
	}

	public void addUriFragmentChangedListener(
			UriFragmentChangedListener listener) {
		this.getUI().getPage().addUriFragmentChangedListener(listener);
	}
}
