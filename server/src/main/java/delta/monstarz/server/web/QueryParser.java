package delta.monstarz.server.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class QueryParser {
	public static Map<String, String> parseQuery(String query) {
		try {
			Map<String, String> params = new HashMap<>();
			for (String param : query.split("&")) {
				param = URLDecoder.decode(param, "UTF-8");
				String pair[] = param.split("=");
				if (pair.length > 1) {
					params.put(pair[0], pair[1]);
				} else {
					params.put(pair[0], "");
				}
			}
			return params;
		}
		catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
