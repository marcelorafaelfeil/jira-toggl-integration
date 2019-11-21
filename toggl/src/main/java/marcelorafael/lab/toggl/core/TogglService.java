package marcelorafael.lab.toggl.core;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;

public abstract class TogglService {
	private TogglCore togglCore;

	protected <T>T run(URL url, HttpMethod method, HttpParams params, Class<T> type) {
		this.togglCore = new TogglCore(TogglConfiguration.TOKEN, url, method);

		if (params != null) {
			this.togglCore.setParameters(params);
		}

		this.togglCore.execute();
		return this.togglCore.getResultAsObject(type);
	}

	protected <T> T run(URL url, HttpMethod method, Class<T> type) {
		return this.run(url, method, null, type);
	}
}
