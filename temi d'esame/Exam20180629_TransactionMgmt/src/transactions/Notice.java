package transactions;

public class Notice {
	private String id;
	private String placeName;
	private String productId;
	public Notice(String id, String placeName, String productId) {
		this.id=id;
		this.placeName=placeName;
		this.productId=productId;
	}
	public String getProductId() {
		return this.productId;
	}
	public String getPlaceName() {
		return this.placeName;
	}
}
