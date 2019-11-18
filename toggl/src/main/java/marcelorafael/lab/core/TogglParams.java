package marcelorafael.lab.core;

import marcelorafael.lab.core.http.HttpMethod;

public class TogglParams {

	private HttpMethod method;
	private String params;

	public TogglParams(HttpMethod method) {
		this.method = method;
	}

	public void append(String key, String value) {
		this.addParam(key, value);
	}

	public void addParam(String key, Object value) {
		if (HttpMethod.POST.equals(this.method) || HttpMethod.PUT.equals(this.method)) {
			if (value instanceof String) {

			}
		}
	}
}
