package university;

import java.util.Arrays;
import java.util.logging.Logger;

public class UniversityExt extends University {
	
	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}
	public String getName() {
		return "poli";
	}
	@Override
	public int enroll(String first, String last) {
		int x=super.enroll(first, last);
		if(x==-1)	return -1;
		else {
			logger.info("New student enrolled: "+x+", "+first+" "+last);
			return x;
		}
	}
	@Override
	public int activate(String title,String teacher) {
		int x=super.activate(title, teacher);
		if(x==-1)	return -1;
		else {
			logger.info("New course activated: "+x+", "+title+" "+teacher);
			return x;
		}
	}
	@Override
	public void register(int studentID,int courseCode) {
		if(studentID-PRIMO_STUD>MAX_STUDENTI || courseCode-PRIMO_CORSO>MAX_CORSI || studentID-PRIMO_STUD<0 || courseCode-PRIMO_CORSO<0) {		//EVITO UN POSSIBILE OUT OF BORDER
			System.err.println("Errore nei parametri passati");
			return;
		}
		if(studenti[studentID-PRIMO_STUD]==null || corsi[courseCode-PRIMO_CORSO]==null) {
			System.err.println("Errore nei parametri passati");
			return;
		}
		studenti[studentID-PRIMO_STUD].newCourse(corsi[courseCode-PRIMO_CORSO]);
		corsi[courseCode-PRIMO_CORSO].newStudent(studenti[studentID-PRIMO_STUD]);
		logger.info("Student "+studentID+" signed up for course "+courseCode);
	}
	/**
	 * Record an exam
	 * 
	 * @param studentID id of the student
	 * @param courseCode code of the course
	 * @vote vote of the exam
	 */
	public void exam(int studentId, int courseID, int grade) {
		if(studentId-PRIMO_STUD>MAX_STUDENTI || courseID-PRIMO_CORSO>MAX_CORSI || studentId-PRIMO_STUD<0 || courseID-PRIMO_CORSO<0) {		//EVITO UN POSSIBILE OUT OF BORDER
			System.err.println("Errore: parametri errati");
			return;
		}
		if(studenti[studentId-PRIMO_STUD]==null || corsi[courseID-PRIMO_CORSO]==null || grade<0 || grade>30) {
			System.err.println("Errore: parametri errati");
			return;
		}
		if(studenti[studentId-PRIMO_STUD].folllowCourse(courseID)) {
			studenti[studentId-PRIMO_STUD].studentNewExam(courseID, grade);
			corsi[courseID-PRIMO_CORSO].courseNewExam(studentId, grade);
			logger.info("Student "+studentId+" took an exam in course "+courseID+" with grade "+grade);
			//writer.println("Student "+studentId+" took an exam in course "+courseID+" and won "+grade+" points"); //LOG with WRITER
			return;
		}
		else {
			System.err.println("Lo studente "+studentId+" non segue il corso "+courseID);
			return;
		}
		
	}
	/**
	 * Return the average vote of the student
	 * 
	 * @param studentID id of the student
	 * @return the average vote of the student
	 */
	public String studentAvg(int studentId) {
		if(studentId-PRIMO_STUD>MAX_STUDENTI ||studentId-PRIMO_STUD<0)	return "Studente non trovato";		//EVITO UN POSSIBILE OUT OF BORDER
		if(studenti[studentId-PRIMO_STUD]==null)	return "Studente non trovato";
		else{
			float x=studenti[studentId-PRIMO_STUD].getStudentAvg();
			if(x==-1) return "Student "+studentId+" hasn't taken any exams";
			else	return "Student "+studentId+" : "+x;
		}
	}
	/**
	 * Return the average vote of the course
	 * 
	 * @param courseCode code of the course
	 * @return the average vote of the course
	 */
	public String courseAvg(int courseId) {
		if(courseId-PRIMO_STUD>MAX_CORSI || courseId-PRIMO_CORSO<0)	return "Corso non trovato";	//EVITO UN POSSIBILE OUT OF BORDER
		if(corsi[courseId-PRIMO_CORSO]==null)	return "Corso non trovato";
		else {
			float x= corsi[courseId-PRIMO_CORSO].getCourseAvg();
			if(x==-1) return "No student has taken the exam in "+corsi[courseId-PRIMO_CORSO].getTitle();
			else	return "The average for the course "+corsi[courseId-PRIMO_CORSO].getTitle()+" is: "+x;
		}
	}
	/**
	 * This method is used to have the list of the three best students ordered
	 * @return String from a StringBuffer(using .toString()) with the three students
	 */
	public String topThreeStudents() {
		Studente[] studentiTopThree=new Studente[super.getNStud()-PRIMO_STUD];
		StringBuffer topThree=new StringBuffer();
		for(int i=0;i<super.getNStud()-PRIMO_STUD;i++) {
			studentiTopThree[i]=studenti[i];
		}
		Arrays.sort(studentiTopThree,(Object o1,Object o2)->{
			Studente s1=(Studente) o1;
			Studente s2=(Studente) o2;
			float x=s2.score()-s1.score();	if(x>0 || x==0)	return 1; else return -1;
		});
		for(int i=0;i<studentiTopThree.length;i++) {
			topThree.append(studentiTopThree[i].getFirst()+" "+studentiTopThree[i].getLast()+" : "+studentiTopThree[i].score()+"\n");
			if(i==2)	break;
		}
		return topThree.toString();
	}
}
