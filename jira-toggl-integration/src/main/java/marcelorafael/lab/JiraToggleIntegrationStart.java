package marcelorafael.lab;

import marcelorafael.lab.core.TogglCore;
import marcelorafael.lab.core.TogglParams;
import marcelorafael.lab.core.http.HttpMethod;
import marcelorafael.lab.core.http.URL;
import marcelorafael.lab.model.TogglEntries;

import java.time.Instant;
import java.util.List;

public class JiraToggleIntegrationStart {
	public static void main(String[] args) {
		URL url = new URL("/me/time_entries");
		TogglCore togglCore = new TogglCore("e8a6111980097c538ef06b2b1c089973", url, HttpMethod.GET);
		togglCore.setParameters(new TogglParams().append("since", Instant.now().minusSeconds(500000).getEpochSecond()));
		togglCore.execute();
		TogglEntries[] data = togglCore.getResultAsObject(TogglEntries[].class);
	}
}
