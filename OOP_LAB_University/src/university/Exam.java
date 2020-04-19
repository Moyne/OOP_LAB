package university;
/**
 * This class defines an exam, it is the superclass of Studente and Corso, it includes a list of exams
 * @author Moyne
 *
 */
public class Exam {
	private float avg=0;
	private int nExams=0;
	private class Exams{
		private int code;
		private int vote;
		private Exams next;
		public Exams(int code,int vote) {
			this.code=code;
			this.vote=vote;
			this.next=head;
		}
	}
	private Exams head=null;
	/**
	 * This method can be used by Studente and Corso to add an exam to their list
	 * @param code this code is the code of the student(if it used by class Corso) or the course(if it used by Studente)
	 * @param vote this is the vote of the exam
	 */
	public void registerExam(int code,int vote) {
		Exams temp=new Exams(code,vote);
		avg=((avg*nExams)+vote)/(nExams+1);
		nExams++;
		head=temp;
	}
	/**
	 * This method is used to have the average vote from the list
	 * @return it return the average if the list is not empty, if it is it return -1
	 */
	public float getAvg() {
		if(nExams==0)	return -1;
		else return avg;
	}
	/**
	 * This method give to the client the number of exams in the list
	 * @return the number of exams
	 */
	public int getNExams() {
		return nExams;
	}
}
