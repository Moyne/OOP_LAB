package it.polito.oop.futsal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.polito.oop.futsal.Fields.Features;

public class Field implements FieldOption{
	private Fields.Features features;
	private int id;
	private Map<Integer,String> bookedAt=new HashMap<>();
	public Field(int id,Fields.Features features) {
		this.id=id;
		this.features=features;
	}
	public boolean isIndoor() {
		return features.indoor;
	}
	public void book(String time, int associate) throws FutsalException{
		if(bookedAt.values().stream().anyMatch(e->(e.compareTo(time)<0) && (this.hourAfter(e).compareTo(time)>0))) throw new FutsalException();
		bookedAt.put(associate, time);
	}
	public String hourAfter(String time) {
		int i=Integer.valueOf(time.substring(0,time.indexOf(':')))+1;
		return i+time.substring(2);
	}
	public boolean isBooked(String time) {
		return bookedAt.values().stream().anyMatch(e->(e.compareTo(time)<=0) && (this.hourAfter(e).compareTo(time)>=0));
	}
	@Override
	public int getField() {
		return this.id;
	}
	@Override
	public int getOccupation() {
		return bookedAt.size();
	}
	
	public boolean hasRequired(Features required) {
		if(required.indoor) {
			if(features.indoor) {
				if(required.ac) if(!features.ac) return false;
				if(required.heating) if(!features.heating) return false;
			}
			else return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return id+" - occ: "+bookedAt.size();
	}
	public Set<Integer> getAssociates() {
		return bookedAt.keySet();
	}
}
