package warehouse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Warehouse {
	private Map<String,Product> products=new HashMap<>();
	private Map<String,Supplier> suppliers=new HashMap<>();
	private Map<String,Order> orders=new TreeMap<>();
	public Product newProduct(String code, String description){
		Product prod=new Product(code,description);
		products.put(code, prod);
		return prod;
	}
	
	public Collection<Product> products(){
		return products.values();
	}

	public Product findProduct(String code){
		return products.get(code);
	}

	public Supplier newSupplier(String code, String name){
		Supplier sup=new Supplier(code,name);
		suppliers.put(code, sup);
		return sup;
	}
	
	public Supplier findSupplier(String code){
		return suppliers.get(code);
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp)
		throws InvalidSupplier {
		if(!supp.hasProd(prod)) throw new InvalidSupplier();
		int nCode=orders.entrySet().stream().mapToInt(e->Integer.valueOf(e.getKey().substring(3))).max().orElse(0);
		nCode++;
		String code="ORD"+nCode;
		Order ord=new Order(code,prod,supp,quantity);
		prod.addOrder(ord);
		orders.put(code, ord);
		return ord;
	}

	public Order findOrder(String code){
		return orders.get(code);
	}
	
	public List<Order> pendingOrders(){
		return orders.values().stream().filter(e->!e.delivered()).sorted((e1,e2)->e1.getCode().compareTo(e2.getCode())).collect(Collectors.toList());
	}

	public Map<String,List<Order>> ordersByProduct(){
	    return orders.values().stream().collect(Collectors.groupingBy(e->e.getProductCode(), Collectors.toList()));
	}
	
	public Map<String,Long> orderNBySupplier(){
	    return suppliers.values().stream().collect(Collectors.groupingBy(e->e.getNome(), TreeMap::new, Collectors.summingLong
	    		(e->orders.values().stream().filter(d->d.getSuppName().equals(e.getNome()) && d.delivered()).count())));
	}
	
	public List<String> countDeliveredByProduct(){
	    return products.values().stream().sorted((e1,e2)->e2.nDeliveredOrders().compareTo(e1.nDeliveredOrders())).
	    		map(e->e.getCode()+" - "+e.nDeliveredOrders()).collect(Collectors.toList());
	}
}
