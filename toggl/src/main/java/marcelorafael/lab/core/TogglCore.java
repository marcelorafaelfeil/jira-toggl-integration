package marcelorafael.lab.core;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.core.http.HttpMethod;
import marcelorafael.lab.core.http.JsonManipulation;

import java.io.InputStream;
import java.util.Scanner;

@Slf4j
public class TogglCore {
	private final String URL_TOGGL = "https://toggl.com/api";
	private final String TOGGL_VERSION = "v9";
	private String result;

	public void processRequest(String token, String uri, HttpMethod method, TogglParams params) {
		try {
			String command = this.generateCommand(token, uri, method);
			Process process = Runtime.getRuntime().exec(command);
			this.processReturn(process.getInputStream());
		} catch (Exception e) {
			log.error("Erro interno ao buscar as informações do toggl. [uri={}, token={}]", uri, token);
		}
	}

	private String generateCommand(String token, String uri, HttpMethod method) {
		StringBuilder command = new StringBuilder();
		command.append("curl -v -u ");
		command.append(token);
		command.append(":api_token");
		// command.append(this.addParams());
		command.append("-X");
		command.append(method.name());
		command.append(URL_TOGGL).append("/");
		command.append(TOGGL_VERSION);
		command.append(uri);

		return null;
	}

	private void processReturn(InputStream inputStream) {
		StringBuilder data = new StringBuilder();
		try(Scanner scanner = new Scanner(inputStream)) {
			while(scanner.hasNext()) {
				data.append(scanner.nextLine());
			}
		}
		this.result = data.toString();
	}

	public String getResultAsString() {
		return this.result;
	}

	public <T> T getResultAsObject(Class<T> type) {
		return JsonManipulation.convertToObject(this.result, type);
	}
}
