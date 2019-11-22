package marcelorafael.lab.jiratogglintegration.core;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.jiratogglintegration.core.system.menuactions.CloseAction;
import marcelorafael.lab.jiratogglintegration.core.system.menuactions.SyncAction;

import java.awt.*;

@Slf4j
public class SystemTray {
	public static void initialize() {
		if (!java.awt.SystemTray.isSupported()) {
			log.warn("System Tray is not supported!");
			return;
		}

		final java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();

		Image trayIconImage = Toolkit.getDefaultToolkit().getImage("config/integrator.gif");

		final TrayIcon trayIcon = new TrayIcon(trayIconImage, "Toggl Jira Integration 2");
		trayIcon.setImageAutoSize(true);
		trayIcon.setPopupMenu(getMenu());

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			log.error("TrayIcon could not be added.");
		}
	}

	private static PopupMenu getMenu() {
		final PopupMenu popupMenu = new PopupMenu();
		MenuItem syncItem = new MenuItem("Sincronizar");
		MenuItem closeItem = new MenuItem("Fechar");

		closeItem.addActionListener(new CloseAction());
		syncItem.addActionListener(new SyncAction());

		popupMenu.add(syncItem);
		popupMenu.addSeparator();
		popupMenu.add(closeItem);

		return popupMenu;
	}
}
