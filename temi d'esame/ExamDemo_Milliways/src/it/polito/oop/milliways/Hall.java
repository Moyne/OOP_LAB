package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hall {

	private int id;
	private List<String> facilities=new ArrayList<>();
	public Hall(int id) {
		this.id=id;
	}
	public int getId() {
		return id;
	}

	public void addFacility(String facility) throws MilliwaysException {
		if(facilities.contains(facility)) throw new MilliwaysException();
		else facilities.add(facility);
	}

	public List<String> getFacilities() {
        return facilities.stream().sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
	}
	
	int getNumFacilities(){
        return -1;
	}

	public boolean isSuitable(Party party) {
		return party.getRequirements().stream().allMatch(e->facilities.contains(e));
	}

}
