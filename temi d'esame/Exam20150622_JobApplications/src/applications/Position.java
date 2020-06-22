package applications;

import java.util.*;
import java.util.stream.Collectors;

public class Position {
	private String name;
	private List<String> skills=new ArrayList<>();
	private List<String> candidates=new ArrayList<>();
	private boolean hasWin=false;
	private String winner;
	public Position(String name, List<String> skills) {
		this.name=name;
		this.skills=skills;
	}

	public String getName() {return name;}
	
	public List<String> getApplicants() {
		return candidates.stream().sorted((e1,e2)->e1.compareTo(e2)).collect(Collectors.toList());
	}
	public List<String> getSkills(){
		return this.skills;
	}
	public String getWinner() {
		if(!hasWin) return null;
		return winner;
	}
	public boolean hasWinner() {
		return hasWin;
	}
	public void addCandidate(String applicantName) {
		candidates.add(applicantName);
	}

	public void setWinner(String applicantName) {
		this.winner=applicantName;
		this.hasWin=true;
	}
}