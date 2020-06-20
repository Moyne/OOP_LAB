package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Product {
	private String code;
	private String description;
	private int quantity;
	private List<Supplier> suppliers=new ArrayList<>();
	private List<Order> orders=new ArrayList<>();
	public Product(String code,String description) {
		this.code=code;
		this.description=description;
		this.quantity=0;
	}
	
	public String getCode(){
		return this.code;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setQuantity(int quantity){
		this.quantity=quantity;
	}

	public void decreaseQuantity(){
		this.quantity--;
	}

	public int getQuantity(){
		return this.quantity;
	}
	
	public void addSupplier(Supplier supplier) {
		suppliers.add(supplier);
	}
	
	public List<Supplier> suppliers(){
		return suppliers.stream().sorted((e1,e2)->e1.getNome().compareTo(e2.getNome())).collect(Collectors.toList());
	}

	public List<Order> pendingOrders(){
		return orders.stream().filter(e->!e.delivered()).sorted((e1,e2)->e1.getCode().compareTo(e2.getCode())).collect(Collectors.toList());
	}
	public Long nDeliveredOrders() {
		return orders.stream().filter(e->e.delivered()).count();
	}
	public void addOrder(Order order) {
		orders.add(order);
	}
}
