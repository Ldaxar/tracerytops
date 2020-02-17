package tracerytops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Tracery {
	
	private Map<String,List<String>> tagsMap = new HashMap<String, List<String>>();
	
	@SuppressWarnings("unchecked")
	public Tracery(String jsonString) {
		JSONObject jo = new JSONObject(jsonString);
		Map<String,List<String>> complete = new HashMap<>();
		
		jo.toMap().entrySet().parallelStream().forEach(e -> {
			complete.put(e.getKey(), (List<String>)e.getValue());
		});
		
		tagsMap = complete;
	}
	
	public Tracery () {
		
	}
		
	public List<String> getTag(String key) {
		return tagsMap.get(key); 
	}
	
	public void addToTag(String key, String newEntry) {
		if (tagsMap.containsKey(key)) {
			tagsMap.get(key).add(newEntry); 
		}
		else {
			List<String> newTag = new ArrayList<>();
			newTag.add(newEntry);
			tagsMap.put(key, newTag);
		}
	}
	
	public void addToTag(String key, List<String> newEntry) {
		if (tagsMap.containsKey(key)) {
			tagsMap.get(key).addAll(newEntry); 
		}
		else {
			tagsMap.put(key, newEntry);
		}
	}
	
	public void addTag(String key, List<String> values) {
		tagsMap.put(key, values);
	}

	
	public String getJsonString() {
		return new JSONObject(tagsMap).toString();
	}
}
