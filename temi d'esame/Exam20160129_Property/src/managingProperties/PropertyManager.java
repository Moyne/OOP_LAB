package managingProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class PropertyManager {
	private Map<String,Integer> buildings=new HashMap<>();
	private Map<String,List<String>> owners=new HashMap<>();
	private Map<String,List<String>> professions=new HashMap<>();
	private Map<Integer,Request> requests=new HashMap<>();
	/**
	 * Add a new building 
	 */
	public void addBuilding(String building, int n) throws PropertyException {
		if(buildings.containsKey(building) || n<1 || n>100) throw new PropertyException();
		buildings.put(building, n);
	}

	public void addOwner(String owner, String... apartments) throws PropertyException {
		if(List.of(apartments).stream().anyMatch(e->{
			String[] app= e.split(":");
			if(!buildings.containsKey(app[0])) return true;
			if(buildings.get(app[0])< Integer.valueOf(app[1]) && Integer.valueOf(app[1])<1 && owners.values().stream().anyMatch(d->d.contains(e)))
				return true;
			return false;
			})) throw new PropertyException();
		if(owners.containsKey(owner)) throw new PropertyException();
		owners.put(owner, List.of(apartments));
	}

	/**
	 * Returns a map ( number of apartments => list of buildings ) 
	 * 
	 */
	public SortedMap<Integer, List<String>> getBuildings() {
		return buildings.entrySet().stream().collect(Collectors.groupingBy(e->e.getValue(), TreeMap::new, 
				Collector.of(ArrayList::new, (List<String> a,Entry<String,Integer> b)->a.add(b.getKey()), (a,b)->{a.addAll(b);return a;}, 
						a->{a.sort((e1,e2)->e1.compareTo(e2));return a;})));
	}

	public void addProfessionals(String profession, String... professionals) throws PropertyException {
		if(professions.containsKey(profession) || List.of(professionals).stream().anyMatch(e->
		professions.values().stream().anyMatch(d->d.contains(e)))) throw new PropertyException();
		professions.put(profession, List.of(professionals));
	}

	/**
	 * Returns a map ( profession => number of workers )
	 *
	 */
	public SortedMap<String, Integer> getProfessions() {
		return professions.entrySet().stream().collect(Collectors.groupingBy(e->e.getKey(), TreeMap::new, Collectors.summingInt(e->e.getValue().size())));
	}

	public int addRequest(String owner, String apartment, String profession) throws PropertyException {
		if(!owners.containsKey(owner) || !professions.containsKey(profession)) throw new PropertyException();
		if(!owners.get(owner).contains(apartment)) throw new PropertyException();
		int i=requests.keySet().size()+1;
		requests.put(i, new Request(owner,apartment,profession));
		return i;
	}

	public void assign(int requestN, String professional) throws PropertyException {
		if(!requests.containsKey(requestN)) throw new PropertyException();
		if(!professions.get(requests.get(requestN).getProfession()).contains(professional) || !requests.get(requestN).isPending()) throw new PropertyException();
		requests.get(requestN).assign(professional);
	}

	public List<Integer> getAssignedRequests() {
		return requests.entrySet().stream().filter(e->e.getValue().isAssigned()).map(e->e.getKey()).sorted().collect(Collectors.toList());
	}

	
	public void charge(int requestN, int amount) throws PropertyException {
		if(!requests.containsKey(requestN) || amount<0 || amount>1000) throw new PropertyException();
		if(!requests.get(requestN).isAssigned()) throw new PropertyException();
		requests.get(requestN).charge(amount);
	}

	/**
	 * Returns the list of request ids
	 * 
	 */
	public List<Integer> getCompletedRequests() {
		return requests.entrySet().stream().filter(e->e.getValue().isCompleted()).map(e->e.getKey()).sorted().collect(Collectors.toList());
	}
	
	/**
	 * Returns a map ( owner => total expenses )
	 * 
	 */
	public SortedMap<String, Integer> getCharges() {
		return requests.values().stream().filter(e->e.isCompleted()).collect(Collectors.groupingBy
				(e->e.getOwner(), TreeMap::new, Collectors.summingInt(e->e.getAmount())));
	}

	/**
	 * Returns the map ( building => ( profession => total expenses) ).
	 * Both buildings and professions are sorted alphabetically
	 * 
	 */
	public SortedMap<String, Map<String, Integer>> getChargesOfBuildings() {
		return requests.values().stream().filter(e->e.isCompleted()).collect(Collectors.groupingBy
				(e->List.of(e.getApartment().split(":")).get(0), TreeMap::new, Collectors.groupingBy
						(e->e.getProfessional(), TreeMap::new, Collectors.summingInt(e->e.getAmount()))));
	}

}
