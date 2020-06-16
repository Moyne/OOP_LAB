package transactions;

import java.util.ArrayList;
import java.util.List;

public class Carrier {
	private String name;
	private List<String> regionNames=new ArrayList<>();
	public Carrier(String name,List<String> regionNames) {
		this.name=name;
		this.regionNames=regionNames;
	}
	public boolean hasThisRegion(String regionName) {
		return regionNames.contains(regionName);
	}
	public String getName() {
		return this.name;
	}
}
