package marcelorafael.lab.toggl.service;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;
import marcelorafael.lab.toggl.core.TogglService;
import marcelorafael.lab.toggl.model.TimeEntries;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

public class TogglEntriesService extends TogglService {
	private final URL URL = new URL("/me/time_entries");

	public List<TimeEntries> getTimeEntries(LocalDateTime since) {
		HttpParams params = new HttpParams();
		params.append("since", since.toInstant(ZoneOffset.UTC).getEpochSecond());
		TimeEntries[] timeEntries = this.run(this.URL, HttpMethod.GET, params, TimeEntries[].class);
		return Arrays.asList(timeEntries);
	}
}
