package managingProperties;

public class Request {
	private String owner;
	private String apartment;
	private String profession;
	private String professional;
	private RequestStatus requestStatus;
	private int amount;
	public static enum RequestStatus { PENDING, ASSIGNED, COMPLETED} ;
	public Request(String owner, String apartment, String profession) {
		this.owner=owner;
		this.apartment=apartment;
		this.profession=profession;
		this.requestStatus=RequestStatus.PENDING;
	}
	public String getProfession() {
		return this.profession;
	}
	public boolean isPending() {
		return requestStatus.equals(RequestStatus.PENDING);
	}
	public boolean isAssigned() {
		return requestStatus.equals(RequestStatus.ASSIGNED);
	}
	public boolean isCompleted() {
		return requestStatus.equals(RequestStatus.COMPLETED);
	}
	public void assign(String professional) {
		this.professional=professional;
		this.requestStatus=RequestStatus.ASSIGNED;
	}
	public void charge(int amount) {
		this.amount=amount;
		this.requestStatus=RequestStatus.COMPLETED;
	}
	public int getAmount() {
		return amount;
	}
	public String getOwner() {
		return owner;
	}
	public String getApartment() {
		return this.apartment;
	}
	public String getProfessional() {
		return this.professional;
	}

}
