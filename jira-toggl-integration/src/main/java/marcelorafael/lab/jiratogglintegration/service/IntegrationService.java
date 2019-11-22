package marcelorafael.lab.jiratogglintegration.service;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.jira.model.ResponseWorklog;
import marcelorafael.lab.jira.payload.WorklogPayload;
import marcelorafael.lab.jira.service.WorklogService;
import marcelorafael.lab.jiratogglintegration.core.ConfigurationProperties;
import marcelorafael.lab.jiratogglintegration.core.ExecutionServices;
import marcelorafael.lab.jiratogglintegration.core.SystemTray;
import marcelorafael.lab.jiratogglintegration.utils.TemporalUtil;
import marcelorafael.lab.jiratogglintegration.utils.TogglUtils;
import marcelorafael.lab.toggl.model.TimeEntries;
import marcelorafael.lab.toggl.service.TogglEntriesService;

import javax.swing.*;
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

	public void executeIntegration() {
		while(!(ExecutionServices.isToClose)) {
			if (ExecutionServices.isAllowToSync()) {
				this.doIntegration();
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
						// TODO: Descomentar
						// worklogService.addWorklog(code, worklogPayload);
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
}
