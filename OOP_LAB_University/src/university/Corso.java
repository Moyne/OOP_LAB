package university;

public class Corso extends Exam{
	private String title;
	private String teacher;
	private int code;
	private Studente[]	studentiCorso;
	private final static int MAX_STUDENTI_CORSO=100;
	public Corso(String title,String teacher,int code) {
		this.code=code;
		this.teacher=teacher;
		this.title=title;
		studentiCorso=new Studente[MAX_STUDENTI_CORSO];
	}
	public void courseNewExam(int studentID,int vote) {
		super.registerExam(studentID, vote);
	}
	public float getCourseAvg() {
		float x=super.getAvg();
		if(x==-1)	return -1;
		else return x;
	}
	public int getCode() {
		return this.code;
	}
	public String getTitle() {
		return title;
	}
	public String getCourse() {
		return code+","+title+","+teacher;
	}
	public void newStudent(Studente studente) {
		for(int i=0;i<studentiCorso.length;i++) {
			if(studentiCorso[i]==null) {
				studentiCorso[i]=studente;
				break;
			}
		}
	}
	public String listCourse() {
		String list="";
		for(int i=0;i<studentiCorso.length;i++) {
			if(studentiCorso[i]==null) {
				break;
			}
			list+= studentiCorso[i].getStudent()+"\n";
		}
		return list;
	}
	
}
