package marcelorafael.lab.core.http;

import com.google.gson.Gson;

public class JsonManipulation {
	private JsonManipulation() {}

	public static boolean isObject(String json) {
		return json != null && json.startsWith("{") && json.endsWith("}");
	}

	public static boolean isArray(String json) {
		return json != null && json.startsWith("[") && json.endsWith("]");
	}

	public static <T> T convertToObject(String json, Class<T> type) {
		return new Gson().fromJson(json, type);
	}
}
