package delivery;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import delivery.Delivery.OrderStatus;

public class Order {
	private int customerId;
	private Map<Item,Integer> items=new LinkedHashMap<>();
	private int prepTime;
	private int takeCharge;
	private int deliveryTime;
	OrderStatus orderStatus;
	public Order(int customerId) {
		this.customerId=customerId;
		this.orderStatus=OrderStatus.NEW;
		this.takeCharge=5;
		this.deliveryTime=15;
	}
	public int addItem(Item search, int qty) {
		items.merge(search, qty, (e1,e2)->e1+e2);
		return items.get(search);
	}
	public List<String> showOrder(){
		return items.entrySet().stream().map(e->e.getKey().getDesc()+", "+e.getValue().toString()).collect(Collectors.toList());
	}
	public double totalOrder() {
		return items.entrySet().stream().mapToDouble(e->(double)e.getKey().getPrice()*e.getValue()).sum();
	}
	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}
	public int confirm() {
		this.orderStatus=OrderStatus.CONFIRMED;
		prepTime=items.keySet().stream().mapToInt(e->e.getPrepTime()).max().orElse(0);
		return takeCharge+ prepTime +deliveryTime;
	}
	public int start() {
		this.orderStatus=OrderStatus.PREPARATION;
		return prepTime +deliveryTime;
	}
	public int deliver() {
		this.orderStatus=OrderStatus.ON_DELIVERY;
		return deliveryTime;
	}
	public void complete() {
		this.orderStatus=OrderStatus.DELIVERED;
	}
	public int getCustomerId() {
		return this.customerId;
	}
}
