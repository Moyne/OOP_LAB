package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Party {
	private Map<Race,Integer> groups=new HashMap<>();
    public void addCompanions(Race race, int num) {
    	groups.merge(race, num, (e1,e2)->e1+e2);
	}

	public int getNum() {
        return groups.values().stream().mapToInt(e->e).sum();
	}

	public int getNum(Race race) {
	    return groups.get(race);
	}

	public List<String> getRequirements() {
	    return groups.keySet().stream().map(e->e.getRequirements()).collect(Collector.of(ArrayList::new,
	    		(List<String> a,List<String> b)->a.addAll(b), (List<String> a,List<String> b)->{a.addAll(b);return a;},
	    		a->{return a.stream().distinct().sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());}));
	}
	
	public Map<String,Integer> getDescription(){
	    return groups.entrySet().stream().collect(Collectors.groupingBy(e->e.getKey().getName(), Collectors.summingInt(e->e.getValue())));
    }

}
