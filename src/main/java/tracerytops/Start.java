package tracerytops;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import tracerytops.util.Ingredient;
import tracerytops.util.IngredientUtil;
import tracerytops.util.TraceryBuilder;

public class Start {

	
	public static void main(String[] args) {
		//System.out.println(args[1]);
		List<Ingredient> ingredients = new IngredientUtil().generateFromTsv(args[1]);
		Tracery t = buildTracery(ingredients);
		t.addToTag("origin", "This wonderful burger with #prefixSuffixMix#");
		System.out.println(t.getJsonString());

	}
	
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
	
	public static Tracery buildTracery(List<Ingredient> ingredients) {
		TraceryBuilder tb = new TraceryBuilder();
		Tracery t = new Tracery();
		long start = System.currentTimeMillis();
		t = tb.addPreffixesAndSuffixes(t, ingredients);
		long end = System.currentTimeMillis();
		System.out.println("Time: "+(end-start));
		
		tb.addIngredients(t, ingredients);
		
		tb.addSuffixPrefixMixes2(t, ingredients);
		return t;
	}
	
	public static void run1(String[] args) {
		Tracery tracery = createTraceryFromJsonFile(args[0]);
		System.out.println(tracery.getTag("drink"));
		tracery.addToTag("drink", "chujo");
		System.out.println(tracery.getTag("drink"));
		System.out.println(tracery.getJsonString());
	}
	
	
	
	
	

}
