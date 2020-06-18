package delivery;

public class Item {
	private String description;
	private double price;
	private String category;
	private int prepTime;
	public Item (String description, double price, String category, int prepTime) {
		this.description=description;
		this.price=price;
		this.category=category;
		this.prepTime=prepTime;
	}
	public String getDesc() {
		return this.description;
	}
	@Override
	public String toString() {
		return "["+category+"] "+description+" : "+String.format("%.2f", price);
	}
	public double getPrice() {
		return price;
	}
	public int getPrepTime() {
		return prepTime;
	}
}
