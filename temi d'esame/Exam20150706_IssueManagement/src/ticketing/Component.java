package ticketing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Component {
	private Map<String,Component> subComponents=new HashMap<>();
	private String name;
	public Component(String name) {
		this.name=name;
	}
	public boolean hasSub(String name) {
		return this.subComponents.containsKey(name);
	}
	public Component getSub(String name) {
		return this.subComponents.get(name);
	}
	public void addSub(String name) {
		this.subComponents.put(name, new Component(name));
	}
	public Set<String> getSub() {
		return this.subComponents.keySet();
	}
	public String getName() {
		return name;
	}
}
