package tracerytops.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Ingredient {
	private int multiplier=1;
	private String className ;
	private String name;
	private List<String> prefixes = new LinkedList<>();
	private List<String> suffixes = new LinkedList<>();
	private List<String> tags = new LinkedList<>();
	
	public Ingredient(String className, String name) {
		this.className = className.toLowerCase();
		this.name = name.toLowerCase();

	}
	
	public void addPrefix(String prefix) {
		prefixes.add(prefix.toLowerCase());
	}
	
	public void addPrefixes(String[] prefixes) {
		for (String prefix : prefixes)
			this.prefixes.add(prefix.strip().toLowerCase());
	}
	
	public void addSuffix(String suffix) {
		suffixes.add(suffix.toLowerCase());
	}
	
	public void addSuffixes(String[] suffixes) {
		for (String suffix: suffixes)
			this.suffixes.add(suffix.strip().toLowerCase());
	}
	
	public void addTag(String tag) {
		this.tags.add(tag.toLowerCase());
	}
	
	public void addTags(String[] tags) {
		for (String tag:tags)
			this.tags.add(tag.strip().toLowerCase());
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public List<String> generatePrefixes() {
		List<String> generatedPrefixes = new ArrayList<String>();
		if (!prefixes.isEmpty()) {
			for (String prefix : prefixes) {
				StringBuilder sb = new StringBuilder(name);
				sb.append(" has to be called ");
				sb.append('"');
				sb.append(Character.toUpperCase(prefix.charAt(0)));
				sb.append(prefix.substring(1));
				generatedPrefixes.add(sb.toString());
			}
		}
		return generatedPrefixes;
	}
	
	public List<String> generateSuffixes() {
		List<String> generatedSufixes = new ArrayList<String>();
		if (!suffixes.isEmpty()) {
			for (String suffix : suffixes) {
				StringBuilder sb = new StringBuilder(" ");
				sb.append(Character.toUpperCase(suffix.charAt(0)));
				sb.append(suffix.substring(1));
				sb.append('"');
				sb.append(" since it contains ");
				sb.append(name);
				generatedSufixes.add(sb.toString());
			}
		}
		return generatedSufixes;
	}
	
	public boolean isPrefixEligible() {
		return (!prefixes.isEmpty() && !suffixes.isEmpty());
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public List<String> getPrefixes() {
		return prefixes;
	}

	public List<String> getSuffixes() {
		return suffixes;
	}
	
	
	
	
	
}
