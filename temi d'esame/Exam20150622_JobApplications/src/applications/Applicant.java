package applications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Applicant {
	private String name;
	private List<String> capabilities=new ArrayList<>();
	private boolean candidate;
	private String position;
	public Applicant(String name,List<String> capabilities) {
		this.name=name;
		this.capabilities=capabilities;
		this.candidate=false;
	}
	public List<String> getCapabilitiesNames(){
		return capabilities.stream().map(e->e.substring(0, e.indexOf(":"))).collect(Collectors.toList());
	}
	public boolean getCandidate() {
		return candidate;
	}
	public String getCapabiliesOrdered(){
		return this.capabilities.stream().sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.joining(","));
	}
	public void setPosition(String position) {
		this.candidate=true;
		this.position=position;
	}
	public List<String> getCapabilities(){
		return this.capabilities;
	}
}
