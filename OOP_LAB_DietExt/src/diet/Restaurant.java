package diet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	private String name;
	private Food food;
	private String[] hours;
	private Map<String,Menu> menu=new HashMap<>();
	private List<Order> order=new ArrayList<>();
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.name=name;
		this.food=food;
	}
	
	
	public String[] getHours() {
		return hours;
	}
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		if(hm.length%2!=0) {
			System.err.println("Errore: non è presente un'ora di chiusura per il ristorante");
			return;
		}
		hours=hm;
	}
	
	public void addOrder(Order a) {
		order.add(a);
	}
	
	public Menu getMenu(String name) {
		return menu.get(name);
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu a=new Menu(name,this.food);
		menu.put(name, a);
		return a;
	}

	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		return 	order.stream().filter((o)->o.getStatus()==status).sorted((o1,o2)->{
			int i=o1.getUserName().compareTo(o2.getUserName());
			if(i!=0)	return i;
			else return o1.time().compareTo(o2.time());
		}).map(o->o.toString()).collect(Collectors.joining());
	}
}
