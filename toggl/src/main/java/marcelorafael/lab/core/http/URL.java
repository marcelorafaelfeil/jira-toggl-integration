package marcelorafael.lab.core.http;

import java.util.HashMap;
import java.util.Set;

public class URL {
	private String url;
	private HashMap<String, String> parameters;

	public URL(String url) {
		this.url = url;
		this.parameters = new HashMap<>();
	}

	public URL append(String key, String value) {
		this.parameters.put(key, value);
		return this;
	}

	public URL append(String key, Long value) {
		this.parameters.put(key, value.toString());
		return this;
	}

	public String getURL() {
		this.bindURL();
		return this.url;
	}

	private void bindURL() {
		this.parameters.keySet().forEach(p -> this.url = this.url.replace("{"+p+"}", this.parameters.get(p)));
	}
}
