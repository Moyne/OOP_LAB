package groups;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class GroupHandling {
//R1	
	private Map<String,List<String>> products=new TreeMap<>();
	private Map<String,Group> groups=new TreeMap<>();
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if(products.containsKey(productTypeName)) throw new GroupHandlingException("This product already exists");
		products.put(productTypeName, List.of(supplierNames));
	}
	
	public List<String> getProductTypes (String supplierName) {
		return products.entrySet().stream().filter(e->e.getValue().contains(supplierName)).map(e->e.getKey()).collect(Collectors.toList());
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if(groups.containsKey(name) || !products.containsKey(productName)) throw new GroupHandlingException("Can't add the group because it exists already or the specified product doesn't exists");
		groups.put(name, new Group(name,productName,List.of(customerNames)));
	}
	
	public List<String> getGroups (String customerName) {
        return groups.entrySet().stream().filter(e->e.getValue().hasCustomer(customerName)).map(e->e.getKey()).collect(Collectors.toList());
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		if(!groups.containsKey(groupName)) throw new GroupHandlingException("This group does not exists");
		if(!products.get(groups.get(groupName).getProduct()).containsAll(List.of(supplierNames))) throw new GroupHandlingException("Some suppliers does not supply the product of the group");
		groups.get(groupName).setSuppliers(List.of(supplierNames));
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		if(!groups.containsKey(groupName)) throw new GroupHandlingException("This group does not exists");
		if(!groups.get(groupName).hasSupplier(supplierName)) throw new GroupHandlingException("This group does not have this supplier");
		groups.get(groupName).addBid(supplierName,price);
	}
	
	public String getBids (String groupName) {
		if(!groups.containsKey(groupName)) return null;
        return groups.get(groupName).getBids();
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		if(!groups.containsKey(groupName)) throw new GroupHandlingException("This group does not exists");
		if(!groups.get(groupName).hasSupplier(supplierName) || !groups.get(groupName).hasCustomer(customerName)) throw new GroupHandlingException("This group does not have this supplier or this customer");
		if(!groups.get(groupName).hasBid(supplierName)) throw new GroupHandlingException();
		groups.get(groupName).addVote(customerName,supplierName);
	}
	
	public String  getVotes (String groupName) {
		if(!groups.containsKey(groupName)) return null;
		return groups.get(groupName).getVotes();
	}
	
	public String getWinningBid (String groupName) {
		if(!groups.containsKey(groupName)) return null;
		return groups.get(groupName).getWinning();
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
        return groups.values().stream().filter(e->e.nBids()!=0).collect(Collectors.groupingBy(e->e.getProduct(), 
        		TreeMap::new, Collector.of(ArrayList::new, (List<Integer> a,Group b)->a.add(b.getMaxPrice()), (a,b)->{a.addAll(b);return a;},
        				a->{a.sort((e1,e2)->e2.compareTo(e1));return a.get(0);})));
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
		return groups.values().stream().flatMap(e->e.getSuppliersBids().stream()).distinct().
				collect(Collectors.groupingBy(e->groups.values().stream().
				map(d->d.getSuppliersBids()).flatMap(d->d.stream()).filter(d->d.equals(e)).mapToInt(d->1).sum(),
        		()->{ return new TreeMap<>((e1,e2)->e2.compareTo(e1));}, 
        		Collector.of(ArrayList::new, (List<String> a,String b)->a.add(b), (a,b)->{a.addAll(b);return a;},
        				(List<String> a)->{a.sort((e1,e2)->e1.compareTo(e2));return a;})));
	}
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
        return groups.values().stream().collect(Collectors.groupingBy(e->e.getProduct(),
        		TreeMap::new, Collectors.summingLong(e->e.nCustomers())));
	}
	
}
