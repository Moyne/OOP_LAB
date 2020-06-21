package it.polito.oop.futsal;

public class Associate {
	private int id;
	private String firstName;
	private String lastName;
	private String phone;
	public Associate(int id,String firstName,String lastName,String phone) {
		this.id=id;
		this.firstName=firstName;
		this.lastName=lastName;
		this.phone=phone;
	}
	public String getFirst() {
		return this.firstName;
	}
	public String getLast() {
		return this.lastName;
	}
	public String getPhone() {
		return this.phone;
	}
}
