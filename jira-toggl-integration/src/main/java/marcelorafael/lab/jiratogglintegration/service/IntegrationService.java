package marcelorafael.lab.jiratogglintegration.service;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.jira.model.ResponseWorklog;
import marcelorafael.lab.jira.payload.WorklogPayload;
import marcelorafael.lab.jira.service.WorklogService;
import marcelorafael.lab.jiratogglintegration.core.ConfigurationProperties;
import marcelorafael.lab.jiratogglintegration.core.ExecutionServices;
import marcelorafael.lab.jiratogglintegration.utils.TemporalUtil;
import marcelorafael.lab.jiratogglintegration.utils.TogglUtils;
import marcelorafael.lab.toggl.model.TimeEntries;
import marcelorafael.lab.toggl.service.TogglEntriesService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class IntegrationService {
	private final String SINCE = ConfigurationProperties.get("toggl.since");
	private final String REFRESH_TIME = ConfigurationProperties.get("integration.refresh-time");

	public void executeIntegration() {
		LocalDateTime timeToRefresh = LocalDateTime.now();
		this.systemTray();
		while(!(ExecutionServices.isToClose)) {
			if (timeToRefresh.isBefore(LocalDateTime.now())) {
				this.doIntegration();

				timeToRefresh = timeToRefresh.plus(TemporalUtil.getTimeOfUnit(REFRESH_TIME), TemporalUtil.getUnit(REFRESH_TIME));
			}
		}
	}

	public void doIntegration() {
		TogglEntriesService togglEntriesService = new TogglEntriesService();
		List<TimeEntries> timeEntries = togglEntriesService.getTimeEntries(LocalDateTime.now().minus(TemporalUtil.getTimeOfUnit(SINCE), TemporalUtil.getUnit(SINCE)));

		WorklogService worklogService = new WorklogService();

		timeEntries.forEach(t -> {
			String code = TogglUtils.getIssueCode(t.getDescription());
			// Primeiramente busca a tarefa

			ResponseWorklog responseWorklogs = worklogService.getAllWorklogsOfIssue(code);

			if (responseWorklogs != null) {
				// Se não houver apontamento
				if (!this.hasMark(responseWorklogs, t)) {
					if(t.getServerDeletedAt() == null) {
						WorklogPayload worklogPayload = new WorklogPayload();
						worklogPayload.setStarted(ZonedDateTime.ofInstant(t.getStart().toInstant(), ZoneId.systemDefault()));
						worklogPayload.setComment("Apontamento integrado Toggl.");
						worklogPayload.setTimeSpentSeconds(t.getDuration());
						worklogService.addWorklog(code, worklogPayload);
						log.info("{} -> APONTADO", t.getDescription());
					}
				}
			}
		});
	}

	private Boolean hasMark(ResponseWorklog responseWorklogs, TimeEntries t) {
		Boolean hasMark = false;
		if (responseWorklogs.getWorklogs() != null) {
			for (int i = 0; i < responseWorklogs.getWorklogs().size(); i++) {
				ResponseWorklog.Worklog w = responseWorklogs.getWorklogs().get(i);
				hasMark = TemporalUtil.equalsZoneDateTimeAndDate(w.getStarted(), t.getStart());
				if (hasMark) {
					break;
				}
			}
		}
		return hasMark;
	}

	private void systemTray() {
		if (!SystemTray.isSupported()) {
			log.warn("System Tray is not supported!");
			return;
		}

		final SystemTray tray = SystemTray.getSystemTray();

		Image trayIconImage = Toolkit.getDefaultToolkit().getImage("config/integrator.ico");

		final PopupMenu popupMenu = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(trayIconImage, "Toggl Jira Integration");
		trayIcon.setImageAutoSize(true);

		// MenuItem aboutItem = new MenuItem("Sobre");
		MenuItem closeItem = new MenuItem("Fechar");
		closeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				ExecutionServices.isToClose = true;
				System.exit(0);
			}
		});

		// popupMenu.add(aboutItem);
		// popupMenu.addSeparator();
		popupMenu.add(closeItem);

		trayIcon.setPopupMenu(popupMenu);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			log.error("TrayIcon could not be added.");
		}
	}
}
