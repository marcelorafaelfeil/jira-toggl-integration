package marcelorafael.lab.httpcommon;

public class HttpParams {

	private HttpMethod method;
	private String params;

	public HttpParams() {
		this.params = "";
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public HttpParams append(String key, String value) {
		this.addParam(key, value);
		return this;
	}

	public HttpParams append(String key, Number value) {
		this.addParam(key, value);
		return this;
	}

	public HttpParams append(String key, Boolean value) {
		this.addParam(key, value);
		return this;
	}

	public HttpParams append(String key, Character value) {
		this.addParam(key, value);
		return this;
	}

	private void addParam(String key, Object value) {
		if (HttpMethod.POST.equals(this.method) || HttpMethod.PUT.equals(this.method)) {
			this.params = JsonManipulation.addToJsonString(this.params, key, value);
		} else {
			if ("".equals(this.params)) {
				this.params = "?";
			}
			if (this.params.startsWith("?")) {
				this.params += key + "=" + value;
			} else if (!this.params.startsWith("?")) {
				this.params += "&" + key + "=" + value;
			} else {
				throw new IllegalArgumentException("Não foi possível adicionar um query param");
			}
		}
	}

	public String getParams() {
		return this.params;
	}
}
