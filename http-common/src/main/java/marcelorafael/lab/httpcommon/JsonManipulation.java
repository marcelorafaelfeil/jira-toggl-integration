package marcelorafael.lab.httpcommon;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class JsonManipulation {
	private JsonManipulation() {
	}

	public static boolean isObject(String json) {
		return json != null && json.startsWith("{") && json.endsWith("}");
	}

	public static boolean isArray(String json) {
		return json != null && json.startsWith("[") && json.endsWith("]");
	}

	public static <T> T convertToObject(String json, Class<T> type) {
		return new Gson().fromJson(json, type);
	}

	public static <T> List<T> convertToList(String json, Class<T> type) {
		JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
		List<T> data = new ArrayList<>();
		jsonArray.forEach(j -> data.add(new Gson().fromJson(j.getAsJsonObject(), type)));
		return data;
	}

	public static String addToJsonString(String jsonString, String key, String value) {
		JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
		jsonObject.addProperty(key, value);
		return jsonObject.getAsString();
	}

	public static <T> String addToJsonString(String jsonString, String key, T value) {
		JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
		if (value instanceof String) {
			jsonObject.addProperty(key, (String) value);
		} else if (value instanceof Number) {
			jsonObject.addProperty(key, (Number) value);
		} else if (value instanceof Boolean) {
			jsonObject.addProperty(key, (Boolean) value);
		} else if (value instanceof Character) {
			jsonObject.addProperty(key, (Character) value);
		} else {
			throw new IllegalArgumentException("O tipo do argumento não é válido.");
		}
		return jsonObject.getAsString();
	}
}
