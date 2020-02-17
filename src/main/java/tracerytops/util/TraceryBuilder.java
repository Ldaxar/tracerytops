package tracerytops.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import tracerytops.Tracery;

public class TraceryBuilder {
	IngredientUtil util = new IngredientUtil();

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

	public Tracery addSuffixPrefixMixes(Tracery t, List<Ingredient> ingredients) {
		String key = "prefixSuffixMix";
		StringBuilder sb = new StringBuilder();
		HashSet<String> processedPrefixes = new HashSet<>();
		HashSet<String> processedSuffixes = new HashSet<>();
		System.out.println(ingredients.size());
		for (int i = 0; i < ingredients.size(); i++) {
			Ingredient prefixIngredient = ingredients.get(i);
			if (prefixIngredient.isPrefixEligible() && !processedPrefixes.contains(prefixIngredient.getClassName())) {
				processedPrefixes.add(prefixIngredient.getClassName());
				for (int j = i; j < ingredients.size(); j++) {
					Ingredient suffixIngredient = ingredients.get(j);
					if (suffixIngredient.getClassName() == prefixIngredient.getClassName())
						continue;
					if (suffixIngredient.isPrefixEligible()
							&& !processedSuffixes.contains(suffixIngredient.getClassName())) {

						processedSuffixes.add(suffixIngredient.getClassName());
						sb.append('#');
						sb.append(prefixIngredient.getClassName());
						sb.append(Modifiers.Prefix);
						sb.append("# #");
						sb.append(suffixIngredient.getClassName());
						sb.append(Modifiers.Suffix);
						sb.append("#");
						System.out.println(sb.toString());
						sb = new StringBuilder();
					}
				}
				processedSuffixes = new HashSet<String>();

			}

		}

		return t;
	}

	public Tracery addSuffixPrefixMixes2(Tracery t, List<Ingredient> ingredients) {
		String key = "prefixSuffixMix";
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
				System.out.println(sb.toString());
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

	public Tracery addIngredients(Tracery t, List<Ingredient> ingredients) {
		for (Ingredient ingredient : ingredients) {
			for (int i = 0; i < ingredient.getMultiplier(); i++) {
				t.addToTag(ingredient.getClassName(), ingredient.getName());
			}
		}
		return t;
	}

	public Tracery addTaggedIngredients(Tracery t, List<Ingredient> ingredients) {
		for (Ingredient ingredient : ingredients) {
			for (String tag : ingredient.getTags()) {
				t.addToTag(ingredient.getClassName() + tag, ingredient.getName());
			}
		}
		return t;
	}
}
