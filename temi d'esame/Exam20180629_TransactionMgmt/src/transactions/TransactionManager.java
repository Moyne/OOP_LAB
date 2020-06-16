package transactions;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;

public class TransactionManager {
	private Map<String,Carrier> carriers=new HashMap<>();
	private Map<String,String> places=new HashMap<>();
	private Map<String,Notice> requests=new HashMap<>();
	private Map<String,Notice> offers=new HashMap<>();
	private Map<String,Transaction> transactions=new HashMap<>();
//R1
	public List<String> addRegion(String regionName, String... placeNames) { 
		List<String> newPlaces=List.of(placeNames).stream().distinct().filter(e->!places.containsKey(e)).collect(Collectors.toList());
		newPlaces.sort((e1,e2)->e1.compareTo(e2));
		newPlaces.stream().forEach(e->places.put(e, regionName));
		return newPlaces;
	}
	
	public List<String> addCarrier(String carrierName, String... regionNames) { 
		List<String> existingReg=List.of(regionNames).stream().distinct().filter(e->places.values().contains(e)).collect(Collectors.toList());
		existingReg.sort((e1,e2)->e1.compareTo(e2));
		carriers.put(carrierName, new Carrier(carrierName,existingReg));
		return existingReg;
	}
	
	public List<String> getCarriersForRegion(String regionName) { 
		return carriers.values().stream().filter(e->e.hasThisRegion(regionName)).map(e->e.getName()).sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
	}
	
//R2
	public void addRequest(String requestId, String placeName, String productId) 
			throws TMException {
		if(requests.containsKey(requestId) || (!places.containsKey(placeName))) throw new TMException();
		else requests.put(requestId, new Notice(requestId,placeName,productId));
	}
	
	public void addOffer(String offerId, String placeName, String productId) 
			throws TMException {
		if(offers.containsKey(offerId) || (!places.containsKey(placeName))) throw new TMException();
		else offers.put(offerId, new Notice(offerId,placeName,productId));
	}
	

//R3

	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) 
			throws TMException {
		if(!requests.get(requestId).getProductId().equals(offers.get(offerId).getProductId())) throw new TMException();
		if((!carriers.get(carrierName).hasThisRegion(places.get(requests.get(requestId).getPlaceName()))) ||
				(!carriers.get(carrierName).hasThisRegion(places.get(offers.get(offerId).getPlaceName())))) throw new TMException();
		if(transactions.containsKey(transactionId)) throw new TMException();
		if(transactions.values().stream().anyMatch(e->(e.getOfferId().equals(offerId) || e.getRequestId().equals(requestId)))) throw new TMException();
		transactions.put(transactionId, new Transaction(transactionId,carrierName,requestId,offerId));
	}
	
	public boolean evaluateTransaction(String transactionId, int score) {
		if((score<1) || (score>10) || (!transactions.containsKey(transactionId))) return false;
		else {
			transactions.get(transactionId).setScore(score);
			return true;
		}
	}
	
//R4
	public SortedMap<Long, List<String>> deliveryRegionsPerNT() {
		return transactions.values().stream().map(e->places.get(requests.get(e.getRequestId()).getPlaceName())).distinct().collect
				(Collectors.groupingBy(e->transactions.values().stream().filter
						(d->places.get(requests.get(d.getRequestId()).getPlaceName()).equals(e)).count(),()->{
				return new TreeMap<Long,List<String>>(Collections.reverseOrder());
			}, Collector.of(
					ArrayList::new,(List<String>a,String b)->a.add(b),(List<String>a,List<String> b)->{a.addAll(b);return a;},a->{
						a.sort((e1,e2)->e1.compareTo(e2));
						return a;
					})));
	}
	
	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return transactions.values().stream().filter(e->e.getScore()>minimumScore).collect(Collectors.groupingBy(e->e.getCarrier(), TreeMap::new, Collectors.summingInt(e->e.getScore())));
	}
	
	public SortedMap<String, Long> nTPerProduct() {
		return transactions.values().stream().collect(Collectors.groupingBy(e->requests.get(e.getRequestId()).getProductId(), TreeMap::new, Collectors.counting()));
	}
	
	
}

