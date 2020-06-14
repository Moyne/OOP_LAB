package it.polito.oop.books;

import java.util.Set;
import java.util.TreeSet;

public class Question {
	private String question;
	private Topic mainTopic;
	private long numAnswers=0;
	private Set<String> correctAnswers=new TreeSet<>();
	private Set<String> wrongAnswers=new TreeSet<>();
	
	public Question(String question, Topic mainTopic) {
		this.question=question;
		this.mainTopic=mainTopic;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public Topic getMainTopic() {
		return mainTopic;
	}

	public void addAnswer(String answer, boolean correct) {
		if(correct)	correctAnswers.add(answer);
		else wrongAnswers.add(answer);
		numAnswers++;
	}
	
    @Override
    public String toString() {
        return question+" ("+mainTopic+")";
    }

	public long numAnswers() {
	    return numAnswers;
	}

	public Set<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public Set<String> getIncorrectAnswers() {
        return wrongAnswers;
	}
}
