package evaluation;

import java.util.ArrayList;
import java.util.List;

public class Paper {
	private String name;
	private String journalName;
	private List<String> membersNames=new ArrayList<>();
	public Paper(String name,String journalName,String[] membersNames) {
		this.journalName=journalName;
		this.name=name;
		for(String member:membersNames) this.membersNames.add(member);
	}
	public boolean hasAuthor(String author) {
		return this.membersNames.contains(author);
	}
	public String getTitle() {
		return this.name;
	}
	public String getJournal() {
		return this.journalName;
	}
	public int getNauthors(){
		return membersNames.size();
	}
}