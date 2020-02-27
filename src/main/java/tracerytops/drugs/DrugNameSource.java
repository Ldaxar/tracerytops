package tracerytops.drugs;

import java.util.ArrayList;
import java.util.List;

public class DrugNameSource {
	private List<String> prefixes = new ArrayList<String>();
	private List<String> suffixes = new ArrayList<String>();
	
	public void addPrefix(String prefix) {
		prefixes.add(prefix);
	}
	
	public void addSuffix(String suffix) {
		suffixes.add(suffix);
	}

	public List<String> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(List<String> prefixes) {
		this.prefixes = prefixes;
	}

	public List<String> getSuffixes() {
		return suffixes;
	}

	public void setSuffixes(List<String> suffixes) {
		this.suffixes = suffixes;
	}
	
	
	
}
