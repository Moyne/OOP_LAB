package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Restaurant {
	private Map<String,Race> races=new HashMap<>();
	private Map<Integer,Hall> halls=new LinkedHashMap<>();
	private Map<Party,Hall> groupsHalls=new HashMap<>();
    public Restaurant() {
	}
	
	public Race defineRace(String name) throws MilliwaysException{
	    if(races.containsKey(name))	throw new MilliwaysException();
	    else {
	    	Race x=new Race(name);
	    	races.put(name, x);
	    	return x;
	    }
	}
	
	public Party createParty() {
	    return new Party();
	}
	
	public Hall defineHall(int id) throws MilliwaysException{
		if(halls.containsKey(id))	throw new MilliwaysException();
	    else {
	    	Hall x=new Hall(id);
	    	halls.put(id, x);
	    	return x;
	    }
	}

	public List<Hall> getHallList() {
		return  halls.values().stream().collect(Collectors.toList());
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
        if(!hall.isSuitable(party)) throw new MilliwaysException();
        else {
        	groupsHalls.put(party, hall);
        	return hall;
        }
	}

	public Hall seat(Party party) throws MilliwaysException {
		Optional<Hall> optHall=halls.values().stream().filter(e->e.isSuitable(party)).findFirst();
		if(optHall.isPresent()) {
			groupsHalls.put(party, optHall.get());
			return optHall.get();
		}
		else throw new MilliwaysException();
	}

	public Map<Race, Integer> statComposition() {
		return groupsHalls.keySet().stream().map(e->e.getDescription()).collect(Collector.of
				(()->{return new TreeMap<Race,Integer>((e1,e2)->e1.getName().compareTo(e2.getName()));},
						(Map<Race,Integer>a,Map<String,Integer> b)->{
				b.forEach((k,v)->a.merge
						(races.get(k), v, (e1,e2)->e1+e2));}, 
						(Map<Race,Integer>a,Map<Race,Integer>b)->{
							b.entrySet().stream().forEach(e->a.merge(e.getKey(),
									e.getValue(), (e1,e2)->e1+e2));
							return a;
						}, a->a));
	}

	public List<String> statFacility() {
        return halls.values().stream().map(e->e.getFacilities()).collect(Collector.of(ArrayList::new, (List<String>a,List<String> b)->
        b.stream().filter(e->!a.contains(e)).forEach(e->a.add(e))
        		, (a,b)->{b.stream().filter(e->!a.contains(e)).forEach(e->a.add(e));return a;}, a->{
        			a.sort((e1,e2)->{
        				int i=0;int j=0;
        				for(Hall x: halls.values()) {
        					if(x.getFacilities().contains(e1)) i++;
        					if(x.getFacilities().contains(e2)) j++;
        				}
        				if(i==j) return e1.compareTo(e2);
        				else return (j-i);
        			});
        			return a;
        		}));
	}
	
	public Map<Integer,List<Integer>> statHalls() {
        return halls.values().stream().collect(Collectors.groupingBy(e->e.getFacilities().size(),TreeMap::new, 
        		Collector.of(
        				ArrayList::new,(List<Integer>a,Hall b)->a.add(b.getId()),(a,b)->{a.addAll(b);return a;},
        				a->{a.sort((e1,e2)->e1-e2);return a;
        				})));
	}

}
