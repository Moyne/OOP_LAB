package university;

public class Studente extends Exam {
	private String firstName;
	private String lastName;
	private int matricola;
	private static final int MAX_CORSI_SEGUITI=25;
	private int nCorsiSeguiti;
	private Corso[]	corsiSeguiti;
	public Studente(String first,String last,int matricola){
		this.firstName=first;
		this.lastName=last;
		this.matricola=matricola;
		corsiSeguiti=new Corso[MAX_CORSI_SEGUITI];
	}
	public void studentNewExam(int courseCode,int vote) {
		super.registerExam(courseCode, vote);
	}
	public String getFirst() {
		return firstName;
	}
	public String getLast() {
		return lastName;
	}
	public float score() {
		float x=(float)super.getNExams();
		if(x==0)	return 0;
		float y=(x/nCorsiSeguiti)*10;
		float z=super.getAvg();
		return z+y;
	}
	public float getStudentAvg() {
		float x=super.getAvg();
		if(x==-1)	return -1;
		else return x;
	}
	public int getMatricola() {
		return this.matricola;
	}
	public String getStudent() {
		return matricola+" "+firstName+" "+lastName;
	}
	public void newCourse(Corso corso) {
		for(int i=0;i<corsiSeguiti.length;i++) {
			if(corsiSeguiti[i]==null) {
				corsiSeguiti[i]=corso;
				nCorsiSeguiti++;
				break;
			}
		}
	}
	public String listStudents() {
		String list="";
		for(int i=0;i<corsiSeguiti.length;i++) {
			if(corsiSeguiti[i]==null) {
				break;
			}
			list+=corsiSeguiti[i].getCourse()+"\n";
		}
		return list;
	}
	public boolean folllowCourse(int courseCode) {
		for(int i=0;i<corsiSeguiti.length;i++) {
			if(corsiSeguiti[i]==null)	return false;
			if(corsiSeguiti[i].getCode()==courseCode)	return true;
		}
		return false;
	}
}
