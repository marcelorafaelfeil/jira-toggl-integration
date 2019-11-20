package marcelorafael.lab.jira.core;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.httpcommon.*;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
public class JiraCore {
	private final String URL_JIRA = "https://jira.hbsis.com.br/rest/api";
	private final String JIRA_VERSION = "2";
	private String result;
	private String command;
	private String username;
	private String password;
	private String uri;
	private HttpMethod method;
	private HttpParams params;

	public JiraCore(String username, String password, String uri, HttpMethod method) {
		this.params = new HttpParams();
		this.command = "";
		this.username = username;
		this.password = password;
		this.uri = uri;
		this.method = method;
	}

	public JiraCore(String username, String password, URL uri, HttpMethod method) {
		this(username, password, uri.getURL(), method);
	}


	public void setParameters(HttpParams params) {
		this.params = params;
		this.params.setMethod(this.method);
	}

	private void generateCommand() {
		StringBuilder command = new StringBuilder();
		command.append("curl -u ");
		command.append("username marcelo.feil:'Qual é o som?'").append(" ");

		if (HttpMethod.POST.equals(this.method)) {
			command.append("-H").append(" ");
			command.append("Content-Type:").append(" ");
			command.append("application/json").append(" ");

			command.append("-d").append(" ");
			command.append(this.params.getParams());
		}

		command.append("-X").append(" ");
		command.append(this.method.name()).append(" ");
		command.append(URL_JIRA).append("/");
		command.append(JIRA_VERSION);
		command.append(this.uri);

		if (HttpMethod.GET.equals(this.method)) {
			command.append(this.params.getParams());
		}

		this.command = command.toString();
	}

	public void execute() {
		this.processRequest();
	}

	private void processRequest() {
		HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_1_1)
				.build();

		// Basic bWFyY2Vsby5mZWlsOlF1YWwg6SBvIHNvbT8
		// Basic bWFyY2Vsby5mZWlsOlF1YWwg6SBvIHNvbT8
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.header("Authorization", AuthUtils.generateBasicAuthToken(this.username, this.password))
				.uri(URI.create(URL_JIRA + "/" + JIRA_VERSION + "/issue/UP-411"))
				.GET()
				.build();

		try {
			HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			log.info("Enviado com sucesso.  [response={}]", response.body());
		} catch (Exception e) {
			log.error("Erro ao enviar a requisição.");
		}
	}

	private void processReturn(InputStream inputStream) {



	}

	public String getResultAsString() {
		return this.result;
	}

	public <T> T getResultAsObject(Class<T> type) {
		return JsonManipulation.convertToObject(this.result, type);
	}

	public <T> List<T> getResultAsList(Class<T> type) {
		return JsonManipulation.convertToList(this.result, type);
	}
}