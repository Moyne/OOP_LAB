package clinic;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	private Map<String,Patient> patient=new TreeMap<>();
	private Map<Integer,Doctor> doctors=new TreeMap<>();
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		patient.put(ssn,new Patient(first,last,ssn));
	}

	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if(patient.containsKey(ssn)) 	return patient.get(ssn).toString();
		else 	throw new NoSuchPatient("Paziente non trovato");
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctors.put(docID,new Doctor(first,last,ssn,docID,specialization));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if(doctors.containsKey(docID)) 	return doctors.get(docID).toString();
		else 	throw new NoSuchDoctor("Dottore non trovato");
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Patient pat;
		Doctor doc;
		if(doctors.containsKey(docID)) 	doc=doctors.get(docID);
		else 	throw new NoSuchDoctor("Dottore non trovato");
		if(patient.containsKey(ssn)) 	pat=patient.get(ssn);
		else 	throw new NoSuchPatient("Paziente non trovato");
		doc.addPatientToDoctor(pat);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if(!patient.containsKey(ssn)) throw new NoSuchPatient("Paziente non trovato");
		for(Doctor a:doctors.values()) {
			if(a.assignedDoctorOf(ssn))	return a.getBadge();
		}
		throw new NoSuchDoctor("This patient is not associated with any doctor");
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		Doctor doc;
		if(doctors.containsKey(id)) 	doc=doctors.get(id);
		else 	throw new NoSuchDoctor("Dottore non trovato");
		return doc.getPatientsSsn();
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		List<String> data;
		try (BufferedReader br=new BufferedReader(reader)) {
			data = br.lines().collect(toList());
		} catch (IOException e) {
			throw e;
		}
		Pattern pat=Pattern.compile("(P[ ]*);([^;]+);([^;]+);([^;]+)");
		Pattern doc=Pattern.compile("(M[ ]*);([ ]*[0-9]+[ ]*);([^;]+);([^;]+);([^;]+);([^;]+)");
		for(int i=0;i<data.size();i++) {
			String x=data.get(i);
			Matcher matchDoc=doc.matcher(x);
			Matcher matchPat=pat.matcher(x);
			if(matchDoc.matches()) {
				this.addDoctor(matchDoc.group(3).trim(), matchDoc.group(4).trim(), matchDoc.group(5).trim(), Integer.parseInt(matchDoc.group(2).trim()), matchDoc.group(6).trim());
			}
			else if(matchPat.matches()) {
				this.addPatient(matchPat.group(2).trim(), matchPat.group(3).trim(), matchPat.group(4).trim());
			}
			else {
				System.err.println("LA RIGA: "+x+" NON E' CORRETTA E SARA' SALTATA");
			}
		}
	}




	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		//metodo a
//		return doctors.values().stream().filter(e->e.numPatients()==0).collect(Collector.of(ArrayList::new, (List<Doctor> a,Doctor b)->a.add(b), 
//				(List<Doctor> a,List<Doctor> b)->{a.addAll(b);return a;},a->{
//					List<Integer> b=new ArrayList<>();
//					a.sort((e1,e2)->{
//						int c=e1.getLast().compareTo(e2.getLast());
//						if(c!=0)	return c;
//						else return e1.getFirst().compareTo(e2.getFirst());
//					});
//					for(Doctor x:a)	b.add(x.getBadge());
//					return b;
//				}));
		//metodo b
		return doctors.entrySet().stream().filter(e->e.getValue().numPatients()==0).map(e->e.getKey()).collect(
				Collector.of(ArrayList::new, (List<Integer> a,Integer b)->a.add(b), 
				(List<Integer> a,List<Integer> b)->{a.addAll(b);return a;},a->{
					a.sort((e1,e2)->{
						Doctor e1Doc=doctors.get(e1);Doctor e2Doc=doctors.get(e2);
						int c=e1Doc.getLast().compareTo(e2Doc.getLast());
						if(c!=0)	return c;
						else return e1Doc.getFirst().compareTo(e2Doc.getFirst());
					});
					return a;
				}));
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		double x=doctors.values().stream().mapToDouble(e->e.numPatients()).average().getAsDouble();
		return doctors.entrySet().stream().filter(e->e.getValue().numPatients()>x).map(e->e.getKey()).collect(Collectors.toList());
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		return doctors.values().stream().map(e->new StringBuffer().append(String.format("%3d", e.numPatients())).append(" : ").append(e.getBadge())
				.append(" ").append(e.getLast()).append(" ").append(e.getFirst()).toString()).collect(Collector.of
						(ArrayList::new, (List<String> a,String b)->a.add(b), (List<String> a,List<String> b)->{a.addAll(b);return a;}
						, a->{a.sort((e1,e2)->e2.compareTo(e1));return a;}));
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		return doctors.values().stream().map(e->e.getSpecialization()).distinct().
				collect(Collector.of(ArrayList::new, (List<String> a,String b)->{
					int x=0;
					for(Doctor c: doctors.values()) if(c.getSpecialization().equals(b)) x+=c.numPatients();
					a.add(String.format("%3d",x)+" - "+b);
				}, (List<String> a,List<String> b)->{a.addAll(b);return a;}, a->{
					a.sort((e1,e2)->{
						int c=e2.substring(0, 4).compareTo(e1.substring(0,4));
						if(c!=0)	return c;
						return e1.substring(4).compareTo(e2.substring(4));});
					return a;}));
	}
	
}
