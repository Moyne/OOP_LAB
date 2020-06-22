package restaurantChain;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private String reference;
	private List<String> menus=new ArrayList<>();
	private boolean paid;
	public Order(String reference,List<String> menus) {
		this.reference=reference;
		this.menus=menus;
		this.paid=false;
	}
	public boolean getPaid() {
		return paid;
	}
	public List<String> getMenus(){
		return menus;
	}
	public String getRef() {
		return reference;
	}
	public void setPaid() {
		paid=true;
	}
}
