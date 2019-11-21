package marcelorafael.lab.jiratogglintegration.core;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
public class ConfigurationProperties {
	private static Properties props;

	private static Properties loadProperties() {
		ConfigurationProperties.props = new Properties();
		try(FileInputStream file = new FileInputStream("./config/configuration.properties")) {
			ConfigurationProperties.props.load(new InputStreamReader(file, Charset.forName(StandardCharsets.UTF_8.displayName())));
			return ConfigurationProperties.props;
		} catch (IOException e) {
			log.error("Erro ao abrir o arquivo.", e);
			return null;
		}
	}

	public static String get(String key) {
		if (props == null) {
			ConfigurationProperties.loadProperties();
		}
		return ConfigurationProperties.props.get(key).toString();
	}
}
