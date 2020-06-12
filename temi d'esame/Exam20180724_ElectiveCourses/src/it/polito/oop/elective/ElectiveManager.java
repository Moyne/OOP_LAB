package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {

	private Map<String,Course> courses=new TreeMap<>();
	private Map<String,Student> students=new TreeMap<>();
	private List<Notifier> notifiers=new ArrayList<>();
    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        courses.put(name, new Course(name,availablePositions));
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        return (SortedSet<String>) courses.keySet();
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, 
                                  double gradeAverage){
        if(students.containsKey(id))	students.get(id).setAvg(gradeAverage);
        students.put(id, new Student(id,gradeAverage));
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return students.keySet();
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        return students.entrySet().stream().filter(e->{
        	if(e.getValue().getAvg()<inf || e.getValue().getAvg()>sup) return false;
        	else return true;
        }).map(e->e.getKey()).collect(Collectors.toList());
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
        if(!students.containsKey(id)) throw new ElectiveException("Errore: studente non esistente");
        if(courses.size()>3 || courses.size()<1) throw new ElectiveException("Errore: numero di corsi passati errato");
        if(courses.stream().filter(e->this.courses.containsKey(e)).collect(Collectors.counting())!=courses.size()) throw new ElectiveException("Errore: corsi passati non esistenti o non definiti");
        students.get(id).setReq(courses);
        notifiers.stream().forEach(e->e.requestReceived(id));
        for(int i=0;i<courses.size();i++) {
        	this.courses.get(courses.get(i)).update(i);
        }
        return courses.size();
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
        return courses.values().stream().collect(Collectors.groupingBy((Course e)->e.getName(),
        		Collector.of(ArrayList::new, (List<Long> a,Course b)->a.addAll(b.prefCourse()), 
        				(List<Long> a,List<Long> b)->{a.addAll(b);return a;}, (List<Long> a)->{return a;})));
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
      //METODO A-> DALLO STUDENTE MIGLIORO INIZIO AD ASSEGNARE TUTTI GLI STUDENTI SE POSSIBILE ALLA PRIMA SCELTA E POI CONTINUO NELLA STESSA MANIERA CON LE SECONDE E TERZE SCELTE
//      List<String> studentsPerGrade=students.entrySet().stream().sorted((e1,e2)->e2.getValue().getAvg().compareTo(e1.getValue().getAvg())).map(e->e.getKey()).collect(Collectors.toList());
//      System.out.println("\nStudents ordered: "+studentsPerGrade);
//      for(int i=0;i<3;i++) {
//    	  for(int j=0;j<studentsPerGrade.size();j++) {
//    		  String studId=studentsPerGrade.get(j);
//    		  if(students.get(studId).getNReq(i)!=null) {
//    			  Course x=courses.get(students.get(studId).getNReq(i));
//    			  if(x.nStudents()<x.getNPlaces()) {
//    				  x.assignStudent(studId);
//        			  students.get(studId).addCourse(x.getName());
//        			  notifiers.stream().forEach(e->e.assignedToCourse(studId, x.getName()));
//    			  }
//    		  }
//    	  }
//      }
      //METODO B-> IL CORSO PRENDE SOLO GLI STUDENTI MIGLIORI
//      for(Course a: courses.values()) {
//    	  int nPlaces=a.getNPlaces();
//    	  int i=0;
//    	  int j=0;
//    	  while(j<nPlaces && i<studentsPerGrade.size()) {
//    		  if(students.get(studentsPerGrade.get(i)).isReq(a.getName())) {
//    			  String studId=studentsPerGrade.get(i);
//    			  a.assignStudent(studId);
//    			  students.get(studId).addCourse(a.getName());
//    			  notifiers.stream().forEach(e->e.assignedToCourse(studId, a.getName()));
//    			  j++;
//    		  }
//    		  i++;
//    	  }
//      }
    	//METODO C-> UNO STUDENTE PUO' STARE IN UN SOLO CORSO
    	 List<String> studentsPerGrade=students.entrySet().stream().sorted((e1,e2)->e2.getValue().getAvg().compareTo(e1.getValue().getAvg())).map(e->e.getKey()).collect(Collectors.toList());
         System.out.println("\nStudents ordered: "+studentsPerGrade);
         for(int i=0;i<3;i++) {
       	  for(int j=0;j<studentsPerGrade.size();j++) {
       		  String studId=studentsPerGrade.get(j);
       		  if(students.get(studId).getNReq(i)!=null && students.get(studId).getNFollowing()==0) {
       			  Course x=courses.get(students.get(studId).getNReq(i));
       			  if(x.nStudents()<x.getNPlaces()) {
       				  x.assignStudent(studId);
           			  students.get(studId).addCourse(x.getName());
           			  notifiers.stream().forEach(e->e.assignedToCourse(studId, x.getName()));
       			  }
       		  }
       	  }
         }
      return students.values().stream().filter(e->e.getNFollowing()==0).collect(Collectors.counting());
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        return courses.values().stream().collect(Collectors.groupingBy(e->e.getName(), 
        		Collector.of(ArrayList::new, (List<String> a,Course b)->a.addAll(b.getStudents()), 
        				(List<String> a,List<String> b)->{a.addAll(b);return a;}, 
        				(List<String> a)->{
        					a.sort((e1,e2)->students.get(e2).getAvg().compareTo(students.get(e1).getAvg()));
        					return a;
        				})));
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        notifiers.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        return (double) (students.values().stream().filter(e->e.isFollowingHisNReq(choice)).count())/students.size();
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return students.entrySet().stream().filter(e->e.getValue().getNFollowing()==0).map(e->e.getKey()).collect(Collectors.toList());
    }
    
}
