package tracerytops.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tracerytops.Tracery;
/**
 * 
 * @author stargarth
 * Tracery builder is a main class to create/modify tracery out of Ingredient instances
 */
public class TraceryBuilder {
	IngredientUtil util = new IngredientUtil();
	/**
	 * 
	 * @param t - Current tracery object that will augmented with new prefixes and suffixes
	 * @param ingredients - List of ingredients that will serve as source material to create prefs and suffs
	 * @return Same tracery object as input t, but with preffs and suffs added
	 */
	public Tracery addPreffixesAndSuffixes(Tracery t, List<Ingredient> ingredients) {
		for (Ingredient ingredient : ingredients) {
			List<String> list = ingredient.generatePrefixes();
			if (!list.isEmpty())
				t.addToTag(ingredient.getClassName() + Modifiers.Prefix, list);

			list = ingredient.generateSuffixes();
			if (!list.isEmpty())
				t.addToTag(ingredient.getClassName() + Modifiers.Suffix, list);

		}
		return t;
	}
	/**
	 * 
	 * @param t - Current tracery object that will augmented with new prefix+suffix combinations
	 * 
	 * @param ingredients - List of ingredients that will serve as source material to create prefix+suffix combos.
	 * @param tag - Modifier that is used to name pref+suff combo. For instance: tag=spicy will result in "spicyprefixSuffixMix" name
	 * @return Same tracery object as input t, but with preffs+suffs combinations added
	 */
	public Tracery addSuffixPrefixMixes(Tracery t, List<Ingredient> ingredients, String tag) {
		String key = tag+Modifiers.prefixSuffixMix;
		List<String> uniqueClasses = util.getUniquePrefixSuffixClasses(ingredients);
		List<String> mixes = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < uniqueClasses.size(); i++) {
			String prefix = uniqueClasses.get(i);
			for (int j = i + 1; j < uniqueClasses.size(); j++) {
				String suffix = uniqueClasses.get(j);
				sb = addNonTerminal(sb, prefix + Modifiers.Prefix);
				sb.append(" ");
				sb = addNonTerminal(sb, suffix + Modifiers.Suffix);
				for (String string : uniqueClasses) {
					if (string != prefix && string != suffix) {
						sb.append(", ");
						sb = addNonTerminal(sb, string);
					}
				}
				mixes.add(sb.toString());
				sb = new StringBuilder();
			}
		}
		t.addTag(key, mixes);

		return t;
	}

	private StringBuilder addNonTerminal(StringBuilder sb, String toAdd) {
		sb.append('#');
		sb.append(toAdd);
		sb.append('#');
		return sb;
	}
	/**
	 * 
	 * @param t - Current tracery object that will augmented with new ingredients
	 * @param ingredients - List of ingredients that will be added to Tracery object
	 * @return Same tracery object as t, but with ingredients added
	 */
	public Tracery addIngredients(Tracery t, List<Ingredient> ingredients) {
		for (Ingredient ingredient : ingredients) {
			for (int i = 0; i < ingredient.getMultiplier(); i++) {
				t.addToTag(ingredient.getClassName(), ingredient.getName());
			}
		}
		return t;
	}
	
	public Tracery createResponseGrammar(Tracery t, Set<String> tags) {
		for (String tag : tags) {
			if (tag!="")
				t.addToTag(regexsize(tag), tag+Modifiers.prefixSuffixMix);
		}
		t.addToTag(".", Modifiers.prefixSuffixMix.toString());
		return t;
	}
	
	private String regexsize(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<s.length();i++) {
			Character c = s.charAt(i);
			sb.append('[');
			sb.append(Character.toUpperCase(c));
			sb.append('|');
			sb.append(Character.toLowerCase(c));
			sb.append(']');
		}
		return sb.toString();
	}
}
