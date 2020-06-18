package delivery;

public class Customer {
	private String name;
	private String address;
	private String phone;
	private String email;
	public Customer (String name, String address, String phone, String email) {
		this.name=name;
		this.address=address;
		this.phone=phone;
		this.email=email;
	}
	public String getEmail() {
		return this.email;
	}
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String getPhone() {
		return this.phone;
	}
	@Override
	public String toString() {
		return this.getName()+", "+this.getAddress()+", "+this.getPhone()+", "+this.getEmail();
	}
}
