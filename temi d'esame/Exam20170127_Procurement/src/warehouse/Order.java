package warehouse;

public class Order {
	private String code;
	private Product prod;
	private Supplier supp;
	private int quantity;
	private boolean delivered;
	public Order(String code, Product prod, Supplier supp, int quantity) {
		this.code=code;
		this.prod=prod;
		this.supp=supp;
		this.quantity=quantity;
		this.delivered=false;
	}
	public String getCode(){
		return code;
	}
	
	public boolean delivered(){
		return delivered;
	}

	public void setDelivered() throws MultipleDelivery {
		if(delivered) throw new MultipleDelivery();
		this.delivered=true;
		prod.setQuantity(prod.getQuantity()+quantity);
	}
	
	public String getProductCode() {
		return this.prod.getCode();
	}
	
	public String getSuppName() {
		return this.supp.getNome();
	}
	public String toString(){
		return "Order "+code+" for "+quantity+" of "+prod.getCode()+" : "+prod.getDescription()+" from "+supp.getNome();
	}
}
