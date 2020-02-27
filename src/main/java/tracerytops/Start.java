package tracerytops;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import tracerytops.hamburger.Ingredient;
import tracerytops.hamburger.IngredientUtil;
import tracerytops.hamburger.TraceryBurgerBuilder;

public class Start {
	//This main method expects path to ingredients tsv file in args[0]
	public static void main(String[] args) {
		if (args.length<1) {
			args = new String[1];
			args[0] = "ingredients.tsv";
		}
		IngredientUtil iu = new IngredientUtil();
		//Read ingredients from file and construct java list of ingredients from it
		//Ingredient object is base for any tracery generation
		List<Ingredient> ingredients = iu.generateFromTsv(args[0], true);
		//Multiply each ingredient by tags (e.g. spicy, slaty etc)
		Map<String, List<Ingredient>> map = iu.splitIngredientsByCategory(ingredients);
		//Dish that has nothing special about it has a key that is an empty string
		map.put("", ingredients);
		//Tracery builder is a main class to create/modify tracery out of ingredients
		TraceryBurgerBuilder tb = new TraceryBurgerBuilder();
		Tracery t = new Tracery();
		//Generate content for each tag. E.g spicy_veggies, spicy_meat, spicy_buns...
		for (Map.Entry<String, List<Ingredient>> e : map.entrySet()) {
			tb.addIngredients(t, e.getValue());
			tb.addPreffixesAndSuffixes(t, e.getValue());
			tb.addSuffixPrefixMixes(t, e.getValue(),e.getKey());
		}
		//Add origin, so CBDQ can generate tweets
		t.addToTag("origin", "This wonderful burger with #prefixSuffixMix#");
		//Create response grammar
		Tracery rt = new Tracery();
		tb.createResponseGrammar(rt, map.keySet());
		System.out.println(rt.toJson());
		System.out.println(t.toJson());
	}
	
	//Small util to generate Tracery object from text file, which contains JSON
	public static Tracery createTraceryFromJsonFile(String path) {
		StringBuilder sb = new StringBuilder();
		
		try(Stream<String> stream = Files.lines(Paths.get(path))) {
			stream.forEach(s -> sb.append(s).append("\n"));
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }
		return new Tracery(sb.toString());
	}
}
