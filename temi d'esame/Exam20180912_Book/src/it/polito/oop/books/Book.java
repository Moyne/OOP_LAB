package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Book {
	private Map<String,Topic> topics=new HashMap<>();
	private List<Question> questions=new ArrayList<>();
	private List<ExerciseChapter> exerciseChapters=new ArrayList<>();
	private List<TheoryChapter> theoryChapters=new ArrayList<>();
    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
	    if(keyword.equals("") || keyword==null) throw new BookException();
	    if(topics.containsKey(keyword))	return topics.get(keyword);
	    topics.put(keyword, new Topic(keyword));
	    return topics.get(keyword);
	}

	public Question createQuestion(String question, Topic mainTopic) {
        Question q=new Question(question,mainTopic);
        questions.add(q);
        return q;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        TheoryChapter tChap=new TheoryChapter(title,numPages,text);
		theoryChapters.add(tChap);
		return tChap;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		ExerciseChapter eChap=new ExerciseChapter(title,numPages);
		exerciseChapters.add(eChap);
		return eChap;
	}

	public List<Topic> getAllTopics() {
        List<Topic> allTopics=new ArrayList<>();
        exerciseChapters.stream().map(e->e.getTopics()).forEach(e->allTopics.addAll(e));
        theoryChapters.stream().map(e->e.getTopics()).forEach(e->allTopics.addAll(e));
        return allTopics.stream().distinct().sorted((e1,e2)->e1.toString().compareTo(e2.toString())).collect(Collectors.toList());
	}

	public boolean checkTopics() {
        List<Topic> allTopics=new ArrayList<>();
        theoryChapters.stream().map(e->e.getTopics()).forEach(e->allTopics.addAll(e));
        return exerciseChapters.stream().map(e->e.getTopics()).allMatch(e->allTopics.containsAll(e));
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
        return new Assignment(ID,chapter);
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return questions.stream().collect(Collectors.groupingBy(e->e.numAnswers(),Collectors.toList()));
    }
}
