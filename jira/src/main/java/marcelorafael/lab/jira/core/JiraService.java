package marcelorafael.lab.jira.core;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;

public abstract class JiraService {
	private JiraCore jiraCore;

	protected <T>T run(URL url, HttpMethod method, HttpParams params, Class<T> type) {
		this.jiraCore = new JiraCore(JiraConfiguration.USERNAME, JiraConfiguration.PASSWORD, url, method);

		if (params != null) {
			this.jiraCore.setParameters(params);
		}

		this.jiraCore.execute();
		return this.jiraCore.getResultAsObject(type);
	}

	protected <T> T run(URL url, HttpMethod method, Class<T> type) {
		return this.run(url, method, null, type);
	}
}
