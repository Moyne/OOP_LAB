package applications;

import java.util.*;


public class Skill {
	private String name;
	private List<Position> positions=new ArrayList<>();
	public Skill(String name) {
		this.name=name;
	}
	
	public String getName() {return name;}
	public List<Position> getPositions() {return positions;}

	public void addPosition(Position position) {
		positions.add(position);
	}
}