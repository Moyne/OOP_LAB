package it.polito.oop.books;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Assignment {
	private String id;
	private ExerciseChapter chapter;
	private Map<Question,Double> questionsScores=new HashMap<>();
	public Assignment(String id,ExerciseChapter chapter) {
		this.id=id;
		this.chapter=chapter;
	}
    public String getID() {
        return id;
    }

    public ExerciseChapter getChapter() {
        return chapter;
    }

    public double addResponse(Question q,List<String> answers) {
    	if(q==null) return -1;
        double score=(double) (q.numAnswers()-(answers.size()-answers.stream().filter(e->q.getCorrectAnswers().contains(e)).count())
        		-(q.getCorrectAnswers().size()-answers.stream().filter(e->q.getCorrectAnswers().contains(e)).count()))/q.numAnswers();
        questionsScores.put(q, score);
        return score;
    }
    
    public double totalScore() {
        return questionsScores.values().stream().mapToDouble(e->e).sum();
    }

}
