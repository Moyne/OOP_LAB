package rubrica;

public class Voce {
	private String name;
	private String surname;
	private String number;
	public Voce(String name,String surname, String number) {
		this.name=name;
		this.surname=surname;
		this.number=number;
	}
	public String getString() {
		return name+" "+surname+" "+number;
	}
	public boolean search(String search) {
		if(search.equals(this.name))	return true;
		if(search.equals(this.surname))	return true;
		if(search.equals(this.number))	return true;
		else	return false;
	}
	
}
