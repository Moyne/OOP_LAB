package diet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	private String name;
	private Food owner;
	private double calories;
	private double proteins;
	private double carbs;
	private double fat;
	private Map<String,Double> recipes=new HashMap<>();
	private List<String> products=new ArrayList<>();
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu(String name,Food owner) {
		this.name=name;
		this.owner=owner;
	}
	public Menu addRecipe(String recipe, double quantity) {
		NutritionalElement ricetta=owner.getRecipe(recipe);
		if(ricetta==null)	return this;
		recipes.put(recipe, quantity);
		calories+=ricetta.getCalories()*quantity/100;
		proteins+=ricetta.getProteins()*quantity/100;
		carbs+=ricetta.getCarbs()*quantity/100;
		fat+=ricetta.getFat()*quantity/100;
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		NutritionalElement prodotto=owner.getProduct(product);
		if(prodotto==null)	return this;
		products.add(product);
		calories+=prodotto.getCalories();
		proteins+=prodotto.getProteins();
		carbs+=prodotto.getCarbs();
		fat+=prodotto.getFat();
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
