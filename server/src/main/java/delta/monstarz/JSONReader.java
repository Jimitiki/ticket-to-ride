package delta.monstarz;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {
	public static JsonObject readJSON(String fileLocation) {
		JsonObject json = null;
		try
		{
			String contents = new String(Files.readAllBytes(Paths.get(fileLocation)));

			//Parse JSON
			JsonParser parser = new JsonParser();
			json = parser.parse(contents).getAsJsonObject();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return json;
	}
}
