package tracerytops.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import tracerytops.drugs.DrugNameSource;

public class Mapper {
	
	
	
	public static List<String> mapToAntAnatomy(JSONObject jo) {
		List<String> antParts = new ArrayList<>();
		
		JSONArray jArr = jo.getJSONArray("parts");
		for (Object object : jArr) {
			antParts.add((String) object);
		}
		return antParts;
	}
	
	public static List<String> mapToSymptoms(JSONObject jo) {
		List<String> antParts = new ArrayList<>();
		
		JSONArray jArr = jo.getJSONArray("symptoms");
		for (Object object : jArr) {
			antParts.add((String) object);
		}
		return antParts;
	}
	
	public static DrugNameSource mapToDrugNames(JSONObject jo) {
		DrugNameSource dns = new DrugNameSource();
		JSONArray jArr = jo.getJSONArray("stems");
		
		for (Object object : jArr) {
			String stem = (String) object;
			if (stem.startsWith("-")) {
				if (!stem.endsWith("-"))
					dns.addSuffix(stem.substring(1));
			}
			else 
				dns.addPrefix(Character.toUpperCase(stem.charAt(0))+stem.substring(1,stem.length()-1));
		}
		
		return dns;
		
	}
}
