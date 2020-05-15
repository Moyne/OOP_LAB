package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {
	private List<NutritionalElement> rawMats=new ArrayList<>();
	private List<NutritionalElement> products=new ArrayList<>();
	private List<NutritionalElement> recipes=new ArrayList<>();
	
	private class Material implements NutritionalElement{
		private String name;
		private double calories;
		private double proteins;
		private double carbs;
		private double fat;
		private boolean per100g;
		public Material(String name, double calories, double proteins, double carbs, double fat,boolean per100g) {
			this.name=name;
			this.calories=calories;
			this.proteins=proteins;
			this.carbs=carbs;
			this.fat=fat;
			this.per100g=per100g;
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

		@Override
		public boolean per100g() {
			return per100g;
		}
	}
	/**
	 * Define a new raw material.
	 * 
	 * The nutritional values are specified for a conventional 100g amount
	 * @param name 		unique name of the raw material
	 * @param calories	calories per 100g
	 * @param proteins	proteins per 100g
	 * @param carbs		carbs per 100g
	 * @param fat 		fats per 100g
	 */
	public void defineRawMaterial(String name,
									  double calories,
									  double proteins,
									  double carbs,
									  double fat){
		rawMats.add(new Material(name,calories,proteins,carbs,fat,true));
	}
	
	/**
	 * Retrieves the collection of all defined raw materials
	 * 
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials(){
		List<NutritionalElement> sortedRawMats=rawMats;
		sortedRawMats.sort((e1,e2)->e1.getName().compareTo(e2.getName()));
		return sortedRawMats;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * 
	 * @param name  name of the raw material
	 * 
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name){
		for(NutritionalElement a: rawMats) {
			if(a.getName().equals(name))	return a;
		}
		System.err.println("Materiale non trovato");
		return null;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * 
	 * @param name 		unique name of the product
	 * @param calories	calories for a product unit
	 * @param proteins	proteins for a product unit
	 * @param carbs		carbs for a product unit
	 * @param fat 		fats for a product unit
	 */
	public void defineProduct(String name,
								  double calories,
								  double proteins,
								  double carbs,
								  double fat){
		products.add(new Material(name,calories,proteins,carbs,fat,false));
	}
	
	/**
	 * Retrieves the collection of all defined products
	 * 
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products(){
		List<NutritionalElement> sortedProducts=products;
		sortedProducts.sort((e1,e2)->e1.getName().compareTo(e2.getName()));
		return sortedProducts;
	}
	
	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name){
		for(NutritionalElement a: products) {
			if(a.getName().equals(name))	return a;
		}
		System.err.println("Prodotto confezionato non trovato");
		return null;
	}
	
	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * 
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe recipe=new Recipe(name,this);
		recipes.add(recipe);
		return recipe;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * 
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes(){
		List<NutritionalElement> sortedRecipes=recipes;
		sortedRecipes.sort((e1,e2)->e1.getName().compareTo(e2.getName()));
		return sortedRecipes;
	}
	
	/**
	 * Retrieves a specific recipe, given its name
	 * 
	 * @param name  name of the recipe
	 * 
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name){		
		for(NutritionalElement a: recipes) {
			if(a.getName().equals(name))	return a;
		}
		System.err.println("Ricetta non trovata");
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		return new Menu(name,this);
	}
	
}
