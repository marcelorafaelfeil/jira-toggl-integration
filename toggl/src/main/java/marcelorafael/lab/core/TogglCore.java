package marcelorafael.lab.core;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.core.http.HttpMethod;
import marcelorafael.lab.core.http.JsonManipulation;
import marcelorafael.lab.core.http.URL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TogglCore {
	private final String URL_TOGGL = "https://www.toggl.com/api";
	private final String TOGGL_VERSION = "v9";
	private String result;
	private String command;
	private String token;
	private String uri;
	private HttpMethod method;
	private TogglParams params;

	public TogglCore(String token, String uri, HttpMethod method) {
		log.info("TESTE");
		this.params = new TogglParams();
		this.command = "";
		this.token = token;
		this.uri = uri;
		this.method = method;
	}

	public TogglCore(String token, URL uri, HttpMethod method) {
		this(token, uri.getURL(), method);
	}

	public void setParameters(TogglParams params) {
		this.params = params;
		this.params.setMethod(this.method);
	}

	private void processRequest() {
		try {
			log.info("Executando comando [command={}]", this.command);
			log.debug("Executando comando [command={}]", this.command);
			Process process = Runtime.getRuntime().exec(this.command);

			this.processReturn(process.getInputStream());
		} catch (Exception e) {
			log.error("Erro interno ao buscar as informações do toggl. [uri={}, token={}]", uri, token);
		}
	}

	private void generateCommand() {
		StringBuilder command = new StringBuilder();
		command.append("curl -u ");
		command.append(this.token);
		command.append(":api_token").append(" ");

		if (HttpMethod.POST.equals(this.method)) {
			command.append("-H").append(" ");
			command.append("Content-Type:").append(" ");
			command.append("application/json").append(" ");

			command.append("-d").append(" ");
			command.append(this.params.getParams());
		}

		command.append("-X").append(" ");
		command.append(this.method.name()).append(" ");
		command.append(URL_TOGGL).append("/");
		command.append(TOGGL_VERSION);
		command.append(this.uri);

		if (HttpMethod.GET.equals(this.method)) {
			command.append(this.params.getParams());
		}

		this.command = command.toString();
	}

	public void execute() {
		this.generateCommand();
		this.processRequest();
	}

	private void processReturn(InputStream inputStream) {
		StringBuilder data = new StringBuilder();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		try {
			data.append(bufferedReader.readLine());
		} catch (Exception e) {
			log.error("Não foi possível ler a linha.");
		}

		/*try(Scanner scanner = new Scanner(inputStream)) {
			scanner.useDelimiter("\\A");
			while(scanner.hasNext()) {
				data.append(scanner.next());
			}
		} catch (Exception e) {
			log.error("Erro!", e);
		}*/
		this.result = data.toString();
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
