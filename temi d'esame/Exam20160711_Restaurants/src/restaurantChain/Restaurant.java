package restaurantChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Restaurant {
	
	private String name;
	private int tables;
	private Map<String,Double> menus=new HashMap<>();
	private Map<String,Integer> reservations=new HashMap<>();
	private Map<String,Order> orders=new HashMap<>();
	private int freeTables;
	private int refused=0;
	private double income=0;
	
	public Restaurant(String name,int tables) {
		this.name=name;
		this.tables=tables;
		this.freeTables=tables;
	}
	public String getName(){
		return name;
	}
	
	public void addMenu(String name, double price) throws InvalidName{
		if(menus.containsKey(name)) throw new InvalidName();
		menus.put(name, price);
	}
	
	public int reserve(String name, int persons) throws InvalidName{
		if(reservations.containsKey(name)) throw new InvalidName();
		int x=persons/4;
		int y=persons%4;
		if(y==0) {
			if(x>freeTables) {
				refused+=persons;
				return 0;
			}
			else {
				freeTables=freeTables-x;
				reservations.put(name, persons);
				return x;
			}
		}
		else {
			if((x+1)>freeTables) {
				refused+=persons;
				return 0;
			}
			else {
				freeTables=freeTables-(x+1);
				reservations.put(name, persons);
				return x+1;
			}
		}
	}
	
	public int getRefused(){
		return refused;
	}
	
	public int getUnusedTables(){
		return freeTables;
	}
	
	public boolean order(String name, String... menu) throws InvalidName{
		if(List.of(menu).stream().anyMatch(e->!menus.containsKey(e)))  throw new InvalidName();
		if(!reservations.containsKey(name)) throw new InvalidName();
		if(reservations.get(name)>menu.length) return false;
		orders.put(name, new Order(name,List.of(menu)));
		return true;
	}
	
	public List<String> getUnordered(){
		return reservations.keySet().stream().filter(e->!orders.containsKey(e)).sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
	}
	
	public double pay(String name) throws InvalidName{
		if(!reservations.containsKey(name)) throw new InvalidName();
		if(!orders.containsKey(name)) return 0;
		double x=orders.get(name).getMenus().stream().mapToDouble(e->menus.get(e)).sum();
		orders.get(name).setPaid();
		income+=x;
		return x;
	}
	
	public List<String> getUnpaid(){
		return orders.values().stream().filter(e->!e.getPaid()).map(e->e.getRef()).sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
	}
	
	public double getIncome(){
		return income;
	}

}
