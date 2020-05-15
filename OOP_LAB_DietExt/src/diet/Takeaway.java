package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {
	private List<Restaurant> restaurant=new ArrayList<>();
	private List<User> user=new ArrayList<>();
	private List<Order> order=new ArrayList<>();
	
	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		if(r==null) {
			System.err.println("Il seguente ristorante non esiste");
			return;
		}
		restaurant.add(r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		List<String> names=new ArrayList<>();
		for(Restaurant e:restaurant) {
			names.add(e.getName());
		}
		return names;
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User userToAdd= new User(firstName,lastName,email,phoneNumber);
		user.add(userToAdd);
		return userToAdd;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users(){
		List<User> sortedUsers=user;
		sortedUsers.sort((e1,e2)->{
			int i=e1.getLastName().compareTo(e2.getLastName());
			if(i!=0) return i;
			else return e1.getFirstName().compareTo(e2.getFirstName());
		});
		return sortedUsers;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Restaurant r=null;
		for(Restaurant a:restaurant) {
			if(a.getName().equals(restaurantName)) {
				r=a;
				break;
			}
		}
		if(r==null || user==null) {
			System.err.println("Ristorante o user non trovato!");
			return null;
		}
		Order a=new Order(user,r,h,m);
		r.addOrder(a);
		order.add(a);
		return a;
	}
	
	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		List<Restaurant> openedR=new ArrayList<>();
		String[] timeR=time.split(":");
		int hourR=Integer.parseInt(timeR[0]);
		int minR=Integer.parseInt(timeR[1]);
		for(Restaurant r: restaurant) {
			String[] hours = r.getHours();
			boolean end=false;
			boolean inRange=false;
			for(int i=0;i<hours.length && end==false && inRange==false;i++) {
				if(i%2==0)	{
					String[] timeS=hours[i].split(":");
					int hAp=Integer.parseInt(timeS[0]);
					int mAp=Integer.parseInt(timeS[1]);
					if(hourR<=hAp && (hourR<hAp || minR<mAp))	end=true;
				}
				else {
					String[] timeS=hours[i].split(":");
					int hAp=Integer.parseInt(timeS[0]);
					int mAp=Integer.parseInt(timeS[1]);
					if(hourR<hAp || (hourR<=hAp && minR<mAp)) inRange=true;
				}
			}
			if(inRange)	openedR.add(r);
		}
		openedR.sort((e1,e2)->e1.getName().compareTo(e2.getName()));
		return openedR;
	}

}
