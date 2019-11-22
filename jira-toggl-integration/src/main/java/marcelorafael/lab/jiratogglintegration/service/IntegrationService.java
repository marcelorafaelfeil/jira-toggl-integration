package marcelorafael.lab.jiratogglintegration.service;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.jira.model.JiraIssue;
import marcelorafael.lab.jira.model.ResponseWorklog;
import marcelorafael.lab.jira.payload.WorklogPayload;
import marcelorafael.lab.jira.service.SearchTaskService;
import marcelorafael.lab.jira.service.WorklogService;
import marcelorafael.lab.jiratogglintegration.utils.TemporalUtil;
import marcelorafael.lab.jiratogglintegration.core.ConfigurationProperties;
import marcelorafael.lab.jiratogglintegration.utils.TogglUtils;
import marcelorafael.lab.toggl.model.TimeEntries;
import marcelorafael.lab.toggl.service.TogglEntriesService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class IntegrationService {
	private final String SINCE = ConfigurationProperties.get("toggl.since");

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
					WorklogPayload worklogPayload = new WorklogPayload();
					worklogPayload.setStarted(ZonedDateTime.ofInstant(t.getStart().toInstant(), ZoneId.systemDefault()));
					worklogPayload.setComment("Apontamento integrado Toggl.");
					worklogPayload.setTimeSpentSeconds(t.getDuration());
					worklogService.addWorklog(code, worklogPayload);
					log.info("{} -> APONTADO", t.getDescription());
				} else {
					log.info("{} -> JÁ EXISTE APONTAMENTO", t.getDescription());
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
