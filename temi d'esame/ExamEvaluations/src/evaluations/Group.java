package evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Group {
	private String name;
	private List<String> disciplines=new ArrayList<>();
	private List<String> members=new ArrayList<>();
	public Group(String name,String[] disciplines) {
		this.name=name;
		for(String x:disciplines) this.disciplines.add(x);
	}
	public void setMembers(String[] memberNames) {
		for(String x:memberNames) this.members.add(x);
	}
	public List<String> getMembersOrdered(){
		if(members.size()==0) return new ArrayList<>();
		return members.stream().sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
	}
	public List<String> getMembers(){
		return this.members;
	}
	public boolean hasInterest(String discipline) {
		return this.disciplines.contains(discipline);
	}
	public String getName() {
		return this.name;
	}
}