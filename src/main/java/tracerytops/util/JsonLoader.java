package tracerytops.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONObject;

public class JsonLoader {
	
	public static JSONObject loadFromFile(String path) {
		StringBuilder jsonBuilder = new StringBuilder();
		
		try(Stream<String> stream = Files.lines(Paths.get(path))) {
			stream.forEach(s -> jsonBuilder.append(s).append("\n"));
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return new JSONObject(jsonBuilder.toString());
	}
}
