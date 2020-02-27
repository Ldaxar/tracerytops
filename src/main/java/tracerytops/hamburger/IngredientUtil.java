package tracerytops.hamburger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IngredientUtil {
	
	public List<Ingredient> generateFromTsv(String tsvFilePath, boolean skipHeader) {
		List<Ingredient> ingredients = null;
		int skip =0;
		if (skipHeader) skip = 1;
		try (Stream<String> stream = Files.lines(Paths.get(tsvFilePath)).skip(skip)) {
			ingredients = stream.map(line -> {
				
				String[] splittedLine = line.split("	");
				Ingredient ing = new Ingredient(splittedLine[0], splittedLine[1]);
				if (!splittedLine[2].isBlank()) { // add prefixes
					ing .addPrefixes(splittedLine[2].split(","));
				}
				if (!splittedLine[3].isBlank()) { // add suffixes
					ing.addSuffixes(splittedLine[3].split(","));
				}
				if (!splittedLine[4].isBlank()) //add tags
					ing.addTags(splittedLine[4].split(","));
				if (!splittedLine[5].isBlank())
					ing.setMultiplier(Integer.parseInt(splittedLine[5]));
				
				return ing;
				
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ingredients;
	}
	
	/**
	 * This method multiplies number of elements times tags.
	 * @param ingredients - List of ingredients that will serve as basis for combinatory explosion
	 * @return Map which key corresponds to tag and List corresponding to all the ingredients that belong to a tag.
	 */
	public Map<String, List<Ingredient>> splitIngredientsByCategory(List<Ingredient> ingredients) {
		//Class that will make sure that each tag has all ingredients necessary to create dish
		class CompletenessTracker {
			protected List<Ingredient> ingredients = new ArrayList<>();
			protected Set<String> presentIngredientCategories = new HashSet<>();
		}
		//spicy - list of ingredients, salty - list of ingredients etc.
		Map<String, CompletenessTracker> categorizedIngredients = new HashMap<>();
		//Map contains complete lists of ingredients for each class
		//For example there might be no spicy buns - therefore we default to all buns
		Map<String, List<Ingredient>> uniqueCategories = new HashMap<>();
		for (Ingredient ingredient : ingredients) {
			String currentIngredientClass = ingredient.getClassName();
			//If we didn't encounter e.g. vegetable - add it to map and create new list
			if (!uniqueCategories.containsKey(currentIngredientClass)) {
				uniqueCategories.put(currentIngredientClass, new ArrayList<>());
			}
			uniqueCategories.get(currentIngredientClass).add(ingredient);
			//Go over tag of each ingredient
			for (String tag : ingredient.getTags()) {
				//If no tag has been create (e.g. there is no "spicy" category) create one
				if (!categorizedIngredients.containsKey(tag)) {
					categorizedIngredients.put(tag, new CompletenessTracker());
				}
				categorizedIngredients.get(tag).ingredients.add(ingredient);
				categorizedIngredients.get(tag).presentIngredientCategories.add(ingredient.getClassName());
			}
		}
		//Check if each of tags has enough ingredients to build a complete dish
		//E.g. there might be no spicy buns. Therefore we make all "default" buns spicy by adding them
		//to list of spicy ingredients
		for (Map.Entry<String, CompletenessTracker> e : categorizedIngredients.entrySet()) {
			CompletenessTracker trackerForTag = e.getValue();
			for (Map.Entry<String, List<Ingredient>> uniqueEntry: uniqueCategories.entrySet()) {
				if (!trackerForTag.presentIngredientCategories.contains(uniqueEntry.getKey())) {
					trackerForTag.ingredients.addAll(uniqueEntry.getValue());
					trackerForTag.presentIngredientCategories.add(uniqueEntry.getKey());
				}
			}
		}
		//CompletnessTracker is redundant. Re-map it to List of ingredients 
		Map<String, List<Ingredient>> tagMap = categorizedIngredients.entrySet().stream()
					.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().ingredients));
		for (Map.Entry<String, List<Ingredient>> entry : tagMap.entrySet()) {
			List<Ingredient> newList = new ArrayList<Ingredient>();
			for (Ingredient ingredient : entry.getValue()) {
				Ingredient ni = ingredient.clone();
				String newClassName  = entry.getKey()+"_"+ni.getClassName();
				ni.setClassName(newClassName);
				newList.add(ni);
			}
			tagMap.replace(entry.getKey(), newList);
		}
		
		return tagMap;
	}
	
	
	
	public List<String> getUniquePrefixSuffixClasses(List<Ingredient> ingredients) {
		List<String> unique = new LinkedList<String>();
		for (Ingredient ing : ingredients) {
			if (!unique.contains(ing.getClassName()) && ing.isPrefixEligible()) {
				unique.add(ing.getClassName());
			}
		}
		return unique;
	}
}
