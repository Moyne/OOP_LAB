package clinic;

public class Patient {
	private String first;
	private String last;
	private String ssn;
	public Patient(String first,String last,String ssn) {
		this.first=first;
		this.last=last;
		this.ssn=ssn;
	}
	@Override
	public String toString() {
		return new StringBuffer().append(last).append(" ").append(first).append(" (").append(ssn).append(")").toString();
	}
	public String getSsn() {
		return ssn;
	}
}
