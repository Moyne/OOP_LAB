package groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Group {
	private String groupName;
	private String productName;
	private List<String> customers=new ArrayList<>();
	private List<String> suppliers=new ArrayList<>();
	private Map<String,Integer> bids=new HashMap<>();
	private Map<String,String> votes=new HashMap<>();
	public Group(String groupName,String productName,List<String> customers) {
		this.groupName=groupName;
		this.productName=productName;
		this.customers=customers;
	}
	public boolean hasCustomer(String customerName){
		return customers.contains(customerName);
	}
	public String getProduct() {
		return this.productName;
	}
	public void setSuppliers(List<String> suppliers) {
		this.suppliers=suppliers;
	}
	public boolean hasSupplier(String supplierName) {
		return suppliers.contains(supplierName);
	}
	public void addBid(String supplierName, int price) {
		bids.put(supplierName, price);
	}
	public String getBids() {
		return bids.entrySet().stream().sorted((e1,e2)->{
			if(e1.getValue().equals(e2.getValue())) return e1.getKey().compareTo(e2.getKey());
			return e1.getValue().compareTo(e2.getValue());
		}).map(e->e.getKey()+":"+e.getValue()).collect(Collectors.joining(","));
	}
	public boolean hasBid(String supplierName) {
		return bids.containsKey(supplierName);
	}
	public void addVote(String customerName, String supplierName) {
		votes.put(customerName, supplierName);
	}
	public String getVotes() {
		return suppliers.stream().filter(e->votes.values().stream().filter(d->d.equals(e)).count()!=0).sorted((e1,e2)->e1.compareTo(e2)).
				map(e->e+":"+votes.values().stream().filter(d->d.equals(e)).count()).collect(Collectors.joining(","));
	}
	public String getWinning() {
		if(bids.isEmpty()) return null;
		String winning=suppliers.stream().max((e1,e2)->{
			long z=votes.values().stream().filter(d->d.equals(e1)).count()-votes.values().stream().filter(d->d.equals(e2)).count();
			if(z!=0) return (int) z;
			else return bids.get(e2).compareTo(bids.get(e1));
		}).get();
		if(winning==null) return null;
		return winning+":"+votes.values().stream().filter(d->d.equals(winning)).count();	
	}
	public int nBids() {
		return bids.size();
	}
	public Integer getMaxPrice() {
		return bids.values().stream().mapToInt(e->e).max().orElse(0);
	}
	public Set<String> getSuppliersBids() {
		return bids.keySet();
	}
	public long nCustomers() {
		return (long) customers.size();
	}
}
