package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {
	private String title;
	private int numPages;
	private List<Question> questions=new ArrayList<>();
	public ExerciseChapter(String title,int numPages) {
		this.title=title;
		this.numPages=numPages;
	}
	
    public List<Topic> getTopics() {
        return questions.stream().map(e->e.getMainTopic()).distinct().sorted((e1,e2)->e1.toString().compareTo(e2.toString())).collect(Collectors.toList());
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
    

	public void addQuestion(Question question) {
		questions.add(question);
	}	
}
