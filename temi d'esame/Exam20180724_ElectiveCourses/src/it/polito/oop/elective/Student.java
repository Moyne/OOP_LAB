package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String id;
	private double avg;
	private List<String> req=new ArrayList<>();
	private List<String> following=new ArrayList<>();
	private int nFollowing=0;
	public Student(String id,double avg) {
		this.id=id;
		this.avg=avg;
	}
	public void setReq(List<String> courses) {
		this.req=courses;
	}
	public void setAvg(double avg) {
		this.avg=avg;
	}
	public Double getAvg() {
		return avg;
	}
	public boolean isReq(String name) {
		//System.out.println(this.id+" follows "+name+" ? "+req.contains(name));
		return req.contains(name);
	}
	public int getNFollowing() {
		return nFollowing;
	}
	public void addCourse(String name) {
		nFollowing++;
		following.add(name);
	}
	public boolean isFollowingHisNReq(int choice) {
		if(req==null || following==null) return false;
		if(req.size()<choice) return false;
		return following.contains(req.get(choice-1));
	}
	public String getNReq(int n) {
		if(req==null ) return null;
		if((req.size()-1)<n) return null;
		return req.get(n);
	}
}
