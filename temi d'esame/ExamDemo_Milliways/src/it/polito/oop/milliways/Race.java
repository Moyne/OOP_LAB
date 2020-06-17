package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Race {
    private String name;
    private List<String> requirements=new ArrayList<>();
    public Race(String name) {
    	this.name=name;
    }
	public void addRequirement(String requirement) throws MilliwaysException {
		if(requirements.contains(requirement)) throw new MilliwaysException();
		else requirements.add(requirement);
	}
	
	public List<String> getRequirements() {
        return requirements.stream().sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
	}
	
	public String getName() {
        return name;
	}
	@Override
	public String toString() {
		return this.name;
	}
}
