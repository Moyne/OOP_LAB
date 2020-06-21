package it.polito.oop.futsal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
    private Map<Integer,Field> fields=new HashMap<>();
    private Map<Integer,Associate> associates=new HashMap<>();
    private String openingTime;
    private String closingTime;
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
    }
    
    public void defineFields(Features... features) throws FutsalException {
    	if(List.of(features).stream().anyMatch(e->(e.heating || e.ac) && !e.indoor)) throw new FutsalException();
    	for(Features feature:features) {
	    	int id=fields.size()+1;
	    	fields.put(id, new Field(id,feature));
	    }
    }
    
    public long countFields() {
        return fields.size();
    }

    public long countIndoor() {
    	return fields.values().stream().filter(e->e.isIndoor()).count();
    }
    
    public String getOpeningTime() {
        return openingTime;
    }
    
    public void setOpeningTime(String time) {
    	this.openingTime=time;
    }
    
    public String getClosingTime() {
        return closingTime;
    }
    
    public void setClosingTime(String time) {
    	this.closingTime=time;
    }

    public int newAssociate(String first, String last, String mobile) {
        if(first==null || last==null || mobile==null) return -1;
        int id=associates.size()+1;
        associates.put(id, new Associate(id,first,last,mobile));
        return id;
    }
    
    public String getFirst(int partyId) throws FutsalException {
        if(!associates.containsKey(partyId)) throw new FutsalException();
        return associates.get(partyId).getFirst();
    }
    
    public String getLast(int associate) throws FutsalException {
    	if(!associates.containsKey(associate)) throw new FutsalException();
        return associates.get(associate).getLast();
    }
    
    public String getPhone(int associate) throws FutsalException {
    	if(!associates.containsKey(associate)) throw new FutsalException();
        return associates.get(associate).getPhone();
    }
    
    public int countAssociates() {
        return associates.size();
    }
    
    public String hourAfter(String time) {
    	int i=Integer.valueOf(time.substring(0,time.indexOf(':')))+1;
		return i+time.substring(2);
	}
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    	if(!associates.containsKey(associate) || !fields.containsKey(field)) throw new FutsalException();
    	if(this.hourAfter(time).compareTo(closingTime)>0 || !time.substring(2).equals(openingTime.substring(2))) throw new FutsalException();
    	if(openingTime.compareTo(time)>0) throw new FutsalException();
    	fields.get(field).book(time,associate);
    }

    public boolean isBooked(int field, String time) {
        return fields.get(field).isBooked(time);
    }
    

    public int getOccupation(int field) {
        return fields.get(field).getOccupation();
    }
    
    
    public List<FieldOption> findOptions(String time, Features required){
        return fields.entrySet().stream().filter(e->!e.getValue().isBooked(time) && e.getValue().hasRequired(required)).sorted((e1,e2)->{
        	int z=e2.getValue().getOccupation()-e1.getValue().getOccupation();
        	if(z==0) return e1.getKey()-e2.getKey();
        	return z;
        }).map(e->e.getValue()).collect(Collectors.toList());
    }
    
    public long countServedAssociates() {
        return fields.values().stream().flatMap(e->e.getAssociates().stream()).distinct().count();
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return fields.entrySet().stream().collect(Collectors.groupingBy(e->e.getKey(),Collectors.summingLong(e->e.getValue().getOccupation())));
    }
    
    public double occupation() {
        int x=Integer.valueOf(closingTime.substring(0, 2))-Integer.valueOf(openingTime.substring(0,2));
        if(closingTime.substring(2).compareTo(openingTime.substring(2))>0) x--;
        return (double) fields.values().stream().mapToInt(e->e.getOccupation()).sum()/(x*fields.size());
    }
    
}
