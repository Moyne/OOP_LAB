package applications;

import java.util.*;
import java.util.stream.Collectors;

public class HandleApplications {
	private Map<String,Skill> skills=new HashMap<>();
	private Map<String,Position> positions=new HashMap<>();
	private Map<String,Applicant> applicants=new HashMap<>();
	public void addSkills(String... names) throws ApplicationException {
		if(List.of(names).stream().anyMatch(e->skills.containsKey(e))) throw new ApplicationException();
		List.of(names).stream().forEach(e->skills.put(e, new Skill(e)));
	}
	public void addPosition(String name, String... skillNames) throws ApplicationException {
		if(positions.containsKey(name) || List.of(skillNames).stream().anyMatch(e->!skills.containsKey(e))) throw new ApplicationException();
		Position x=new Position(name,List.of(skillNames));
		List.of(skillNames).stream().forEach(e->skills.get(e).addPosition(x));
		positions.put(name, x);
	}
	public Skill getSkill(String name) {
		if(!skills.containsKey(name))return null;
		return skills.get(name);
	}
	public Position getPosition(String name) {
		if(!positions.containsKey(name))return null;
		return positions.get(name);
	}
	
	public void addApplicant(String name, String capabilities) throws ApplicationException {
		if(applicants.containsKey(name)) throw new ApplicationException();
		List<String> cap=List.of(capabilities.split(","));
		if(cap.stream().anyMatch(e->!skills.containsKey(e.substring(0,e.indexOf(":"))) || Integer.valueOf(e.substring(e.indexOf(":")+1)) > 10
				|| Integer.valueOf(e.substring(e.indexOf(":")+1)) < 1)) throw new ApplicationException();
		applicants.put(name, new Applicant(name,cap));
	}
	public String getCapabilities(String applicantName) throws ApplicationException {
		if(!applicants.containsKey(applicantName)) throw new ApplicationException();
		return applicants.get(applicantName).getCapabiliesOrdered();
	}
	
	public void enterApplication(String applicantName, String positionName) throws ApplicationException {
		if(!positions.containsKey(positionName) || !applicants.containsKey(applicantName)) throw new ApplicationException();
		if(applicants.get(applicantName).getCandidate() || !applicants.get(applicantName).getCapabilitiesNames().containsAll(positions.get(positionName).getSkills())) throw new ApplicationException();
		applicants.get(applicantName).setPosition(positionName);
		positions.get(positionName).addCandidate(applicantName);
	}
	
	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		if(!positions.containsKey(positionName) || !applicants.containsKey(applicantName)) throw new ApplicationException();
		if(!positions.get(positionName).getApplicants().contains(applicantName) || positions.get(positionName).hasWinner()) throw new ApplicationException();
		int x=applicants.get(applicantName).getCapabilities().stream().filter(e->positions.get(positionName).getSkills().contains(e.substring(0, e.indexOf(":"))))
				.mapToInt(e->Integer.valueOf(e.substring(e.indexOf(":")+1))).sum();
		if(x<(positions.get(positionName).getSkills().size()*6)) throw new ApplicationException();
		positions.get(positionName).setWinner(applicantName);
		return x;
	}
	
	public SortedMap<String, Long> skill_nApplicants() {
		return applicants.values().stream().flatMap(e->e.getCapabilitiesNames().stream()).collect(Collectors.groupingBy(e->e, TreeMap::new, Collectors.counting()));
	}
	public String maxPosition() {
		return positions.values().stream().max((e1,e2)->e1.getApplicants().size()-e2.getApplicants().size()).get().getName();
	}
}

