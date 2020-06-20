package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Supplier {
	private String code;
	private String name;
	private List<Product> products=new ArrayList<>();
	public Supplier(String code, String name) {
		this.code=code;
		this.name=name;
	}

	public String getCodice(){
		return code;
	}

	public String getNome(){
		return name;
	}
	
	public void newSupply(Product product){
		products.add(product);
		product.addSupplier(this);
	}
	
	public List<Product> supplies(){
		return products.stream().sorted((e1,e2)->e1.getDescription().compareTo(e2.getDescription())).collect(Collectors.toList());
	}

	public boolean hasProd(Product prod) {
		return products.contains(prod);
	}
}
