package clinic;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Doctor {
	private String first;
	private String last;
	private String ssn;
	private int badge;
	private String specialization;
	private Map<String,Patient> patient=new TreeMap<>();
	public Doctor(String first,String last,String ssn,int badge,String specialization) {
		this.first=first;
		this.last=last;
		this.ssn=ssn;
		this.badge=badge;
		this.specialization=specialization;
	}
	public void addPatientToDoctor(Patient a) {
		patient.put(a.getSsn(),a);
	}
	@Override
	public String toString() {
		return new StringBuffer().append(last).append(" ").append(first).append(" (").append(ssn).append(") [")
				.append(badge).append("]:").append(specialization).toString();
	}
	public boolean assignedDoctorOf(String ssn) {
		return patient.containsKey(ssn);
	}
	public int getBadge() {
		return badge;
	}
	public List<String> getPatientsSsn(){
		return patient.entrySet().stream().map(e->e.getKey()).collect(Collectors.toList());
	}
	public int numPatients() {
		return patient.size();
	}
	public String getFirst() {
		return first;
	}
	public String getLast() {
		return last;
	}
	public String getSpecialization() {
		return specialization;
	}
}
