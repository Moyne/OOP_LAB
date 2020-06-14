package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TheoryChapter {
	private String title;
	private int numPages;
	private String text;
	private List<Topic> topics=new ArrayList<>();
	public TheoryChapter(String title,int numPages,String text) {
		this.title=title;
		this.numPages=numPages;
		this.text=text;
	}
    public String getText() {
		return text;
	}

    public void setText(String newText) {
    	this.text=newText;
    }


	public List<Topic> getTopics() {
        return topics.stream().distinct().sorted((e1,e2)->e1.toString().compareTo(e2.toString())).collect(Collectors.toList());
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
    	this.title=newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages=newPages;
    }
    
    public void addTopic(Topic topic) {
    	topics.add(topic);
    	List<Topic> subTopics=topic.getSubTopics();
    	for(int i=0;i<subTopics.size();i++)	this.addTopic(subTopics.get(i));
    }
    
}
