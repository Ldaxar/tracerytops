package tracerytops;

import java.util.List;

import org.json.JSONObject;

import tracerytops.drugs.DrugNameSource;
import tracerytops.util.JsonLoader;
import tracerytops.util.Mapper;

public class Start2 {

	public static void main(String[] args) {
		JSONObject aaObj = JsonLoader.loadFromFile("antAnatomy.json");
		JSONObject stemObj = JsonLoader.loadFromFile("stems.json");
		JSONObject symptomsObj = JsonLoader.loadFromFile("symptoms.json");
		List<String> antParts = Mapper.mapToAntAnatomy(aaObj);
		List<String> symptoms = Mapper.mapToSymptoms(symptomsObj);
		DrugNameSource dns = Mapper.mapToDrugNames(stemObj);
		
		Tracery tracery = new Tracery();
		
		tracery.addTag("bodyPart", antParts);
		tracery.addTag("prefix", dns.getPrefixes());
		tracery.addTag("suffix", dns.getSuffixes());
		tracery.addTag("symptom", symptoms);

		String origin = "Do you or any of your loved ones has a problem with #symptom# ?\n" + 
				"Experts say that you might have an issue with your #bodyPart#.\n" + 
				"There is help. There is #prefix##suffix#.\n" + 
				"Available at good pharmacies.\n" + 
				"";
		tracery.addToTag("origin", origin);
		System.out.println(tracery.toJson());
		
	}

}
