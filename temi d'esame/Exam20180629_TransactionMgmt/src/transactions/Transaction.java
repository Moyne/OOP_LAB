package transactions;

public class Transaction {
	private String transactionId;
	private String carrierName;
	private String requestId;
	private String offerId;
	private int score;
	public Transaction(String transactionId, String carrierName, String requestId, String offerId) {
		this.transactionId=transactionId;
		this.carrierName=carrierName;
		this.requestId=requestId;
		this.offerId=offerId;
		this.score=0;
	}
	public void setScore(int score) {
		this.score=score;
	}
	public String getCarrier() {
		return this.carrierName;
	}
	public int getScore() {
		return this.score;
	}
	public String getRequestId() {
		return this.requestId;
	}
	public String getOfferId() {
		return this.offerId;
	}
}
