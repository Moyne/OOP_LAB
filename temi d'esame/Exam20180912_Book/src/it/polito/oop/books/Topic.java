package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;

public class Topic {
	private String keyword;
	private List<Topic> subTopics=new ArrayList<Topic>();
	public Topic(String keyword) {
		this.keyword=keyword;
	}
	
	public String getKeyword() {
        return keyword;
	}
	@Override
	public String toString() {
	    return keyword;
	}

	public boolean addSubTopic(Topic topic) {
        if(subTopics.contains(topic))	return false;
        subTopics.add(topic);	return true;
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        List<Topic> subTopOrd=subTopics;
        subTopOrd.sort((e1,e2)->e1.toString().compareTo(e2.toString()));
        return subTopOrd;
	}
}
