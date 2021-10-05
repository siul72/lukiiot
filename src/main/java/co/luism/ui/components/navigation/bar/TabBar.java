package co.luism.ui.components.navigation.bar;

import co.luism.ui.MainView;
import co.luism.ui.components.FlexBoxLayout;
import co.luism.ui.components.navigation.tab.NaviTabs;
import co.luism.ui.util.LumoStyles;
import co.luism.ui.util.UIUtils;
import co.luism.ui.views.Home;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import static co.luism.ui.util.UIUtils.IMG_PATH;

@CssImport("./styles/components/tab-bar.css")
public class TabBar extends FlexBoxLayout {

	private String CLASS_NAME = "tab-bar";

	private Button menuIcon;
	private NaviTabs tabs;
	private Button addTab;
	private Image avatar;

	public TabBar() {
		setClassName(CLASS_NAME);

		menuIcon = UIUtils.createTertiaryInlineButton(VaadinIcon.MENU);
		menuIcon.addClassName(CLASS_NAME + "__navi-icon");
		menuIcon.addClickListener(e -> MainView.get().getNaviDrawer().toggle());

		avatar = new Image();
		avatar.setClassName(CLASS_NAME + "__avatar");
		avatar.setSrc(IMG_PATH + "avatar.png");

		ContextMenu contextMenu = new ContextMenu(avatar);
		contextMenu.setOpenOnClick(true);
		contextMenu.addItem("Settings", e -> this.onSettings());
		contextMenu.addItem("Log Out", e ->  this.onLogout());



		add(menuIcon, avatar);
	}

	public void onLogout() {
		Notification.show("Not implemented yet.", 3000,
				Notification.Position.BOTTOM_CENTER);
	}

	public void onSettings() {
		Notification.show("Not implemented yet.", 3000,
				Notification.Position.BOTTOM_CENTER);
	}
	public void setMenuIcon(Button menuIcon) {
		this.menuIcon = menuIcon;
	}

	/* === MENU ICON === */

	public Button getMenuIcon() {
		return menuIcon;
	}

	/* === TABS === */

	public void centerTabs() {
		tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
	}

	private void configureTab(Tab tab) {
		tab.addClassName(CLASS_NAME + "__tab");
	}



	public void addTabSelectionListener(
			ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
		tabs.addSelectedChangeListener(listener);
	}


	/* === ADD TAB BUTTON === */

}
