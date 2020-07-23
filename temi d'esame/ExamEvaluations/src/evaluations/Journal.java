package evaluation;

public class Journal {
	private int level;
	private String name;
	private String discipline;
	public Journal(String name,String discipline,int level) {
		this.name=name;
		this.discipline=discipline;
		this.level=level;
	}
	public String getName() {
		return this.name;
	}
	public String getDiscipline() {
		return this.discipline;
	}
	public int getLevel() {
		return level;
	}
}