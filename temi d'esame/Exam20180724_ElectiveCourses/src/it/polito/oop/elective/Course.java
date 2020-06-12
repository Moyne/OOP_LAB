package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private List<Long> pref=new ArrayList<>();
	private String name;
	private int nPlaces;
	private int nStudents=0;
	private List<String> idAssign=new ArrayList<>();
	public Course(String name,int nPlaces) {
		this.name=name;
		this.nPlaces=nPlaces;
		for(int i=0;i<3;i++) pref.add((long) 0);
	}
	public int nStudents() {
		return nStudents;
	}
	public void update(int i) {
		long val=pref.get(i);
		val++;	pref.set(i, val);
	}
	public List<Long> prefCourse(){
		return pref;
	}
	public String getName() {
		return this.name;
	}
	public void assignStudent(String id) {
		idAssign.add(id);
		nStudents++;
	}
	public int getNPlaces() {
		return this.nPlaces;
	}
	public List<String> getStudents(){
		return idAssign;
	}
}
