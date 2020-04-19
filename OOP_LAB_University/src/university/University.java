package university;

/*import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;*/
/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	private String name;
	private String rector;
	//LOG with WRITER
	/*
	private FileWriter fw = null;
	{
		try {
			fw=new FileWriter("university_log.txt");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	private PrintWriter writer = new PrintWriter(fw, true);*/
	protected static final int MAX_STUDENTI=1000;
	protected static final int MAX_CORSI=50;
	protected static final int PRIMO_STUD = 10000;
	protected static final int PRIMO_CORSO = 10;
	protected Studente[]	studenti;
	protected Corso[]	corsi;
	private int nCorsi=PRIMO_CORSO;
	private int nStudenti=PRIMO_STUD;
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name=name;
		studenti=new Studente[MAX_STUDENTI];
		corsi=new Corso[MAX_CORSI];
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last){
		rector=first+" "+last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return
	 */
	public int getNStud() {
		return nStudenti;
	}
	public int getNCorsi() {
		return nCorsi;
	}
	public String getRector(){
		return rector;
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return
	 */
	public int enroll(String first, String last){
		if((nStudenti-PRIMO_STUD)<MAX_STUDENTI) {
			studenti[nStudenti-PRIMO_STUD]=new Studente(first,last,nStudenti);
			//writer.println("New student enrolled: "+nStudenti+", "+first+" "+last); 		//LOG with WRITER
			return nStudenti++;
		}
		else	return -1;
	}
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		if(id-PRIMO_STUD>MAX_STUDENTI || id-PRIMO_STUD<0)	return "Non e' stato trovato nessuno studente con tale matricola";
		if(studenti[id-PRIMO_STUD]==null)
			return "Non e' stato trovato nessuno studente con tale matricola";
		else	return studenti[id-PRIMO_STUD].getStudent();
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		if((nCorsi-PRIMO_CORSO)<MAX_CORSI) {
			corsi[nCorsi-PRIMO_CORSO]=new Corso(title,teacher,nCorsi);
			//writer.println("New course activated: "+nCorsi+", " +title+" "+ teacher); 		//LOG with WRITER
			return nCorsi++;
		}
		else	return -1;
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		if(code-PRIMO_CORSO>MAX_CORSI || code-PRIMO_CORSO<0)	return "Non e' stato trovato nessuno corso con tale codice";	//EVITO UN POSSIBILE OUT OF BORDER
		if(corsi[code-PRIMO_CORSO]==null)	return "Non e' stato trovato nessuno corso con tale codice";
		else	return corsi[code-PRIMO_CORSO].getCourse();
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
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
		//writer.println("Student "+studentID+" signed up for course "+courseCode);			//LOG with WRITER
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		if(courseCode-PRIMO_CORSO>MAX_CORSI || courseCode-PRIMO_CORSO<0)	return "Corso non trovato";			//EVITO UN POSSIBILE OUT OF BORDER
		if(corsi[courseCode-PRIMO_CORSO]==null)	return "Corso non trovato";
		return corsi[courseCode-PRIMO_CORSO].listCourse();
	}

	/**
	 * Retrieves the study plan for a student
	 * 
	 * @param studentID id of the student
	 * @return list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		if(studentID-PRIMO_STUD>MAX_STUDENTI || studentID-PRIMO_STUD<0)	return "Studente non trovato";		//EVITO UN POSSIBILE OUT OF BORDER
		if(studenti[studentID-PRIMO_STUD]==null)	return "Studente non trovato";
		return studenti[studentID-PRIMO_STUD].listStudents();
	}
	
}
