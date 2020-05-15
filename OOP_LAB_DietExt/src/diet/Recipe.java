package diet;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    private String name;
    private Food owner;
    private double calories;
	private double proteins;
	private double carbs;
	private double fat;
	private double quantity=0;
    private Map<String,Double> ingredients=new LinkedHashMap<>();
    public Recipe(String name,Food owner) {
    	this.name=name;
    	this.owner=owner;
    }

	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		NutritionalElement element=owner.getRawMaterial(material);
		if(element==null) return this;
		ingredients.put(material, quantity);
		this.quantity+=quantity;
		updateValues();
		return this;
	}
	public void updateValues() {
		Set<Entry<String,Double>> ing=ingredients.entrySet();
		Iterator<Entry<String,Double>> it=ing.iterator();
		double newCalories=0;
		double newProteins=0;
		double newCarbs=0;
		double newFat=0;
		while(it.hasNext()) {
			Entry<String,Double> el=it.next();
			NutritionalElement element=owner.getRawMaterial(el.getKey());
			newCalories+=element.getCalories()*el.getValue()/quantity;
			newProteins+=element.getProteins()*el.getValue()/quantity;
			newCarbs+=element.getCarbs()*el.getValue()/quantity;
			newFat+=element.getFat()*el.getValue()/quantity;
		}
		calories=newCalories;	proteins=newProteins;	carbs=newCarbs;	fat=newFat;
		return;
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getCalories() {
		return calories;
	}

	@Override
	public double getProteins() {
		return proteins;
	}

	@Override
	public double getCarbs() {
		return carbs;
	}

	@Override
	public double getFat() {
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		Set<Entry<String,Double>> ing=ingredients.entrySet();
		Iterator<Entry<String,Double>> it=ing.iterator();
		StringBuffer x=new StringBuffer();
		while(it.hasNext()) {
			Entry<String,Double> el=it.next();
			x.append(el.getKey()+" : "+el.getValue()+"\n");
		}
		return x.toString();
	}
}
