package restaurantChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chain {	
	private Map<String,Restaurant> restaurants=new HashMap<>();
		public void addRestaurant(String name, int tables) throws InvalidName{
			if(restaurants.containsKey(name)) throw new InvalidName();
			restaurants.put(name, new Restaurant(name,tables));
		}
		
		public Restaurant getRestaurant(String name) throws InvalidName{
			if(!restaurants.containsKey(name)) throw new InvalidName();
			return restaurants.get(name);
		}
		
		public List<Restaurant> sortByIncome(){
			return restaurants.values().stream().sorted((e1,e2)->Double.valueOf(e2.getIncome()).compareTo(Double.valueOf(e1.getIncome()))).collect(Collectors.toList());
		}
		
		public List<Restaurant> sortByRefused(){
			return restaurants.values().stream().sorted((e1,e2)->e1.getRefused()-e2.getRefused()).collect(Collectors.toList());
		}
		
		public List<Restaurant> sortByUnusedTables(){
			return restaurants.values().stream().sorted((e1,e2)->e1.getUnusedTables()-e2.getUnusedTables()).collect(Collectors.toList());
		}
}
