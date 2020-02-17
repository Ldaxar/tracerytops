package tracerytops.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
