package db.interfaces;

import java.util.List;

import pojos.Patient;

public interface PatientManager {
	public void newPatient (Patient patient);
	public Patient getPatient (String name, String surname);
	public List<Patient> getAllPatients (int hosptalid);
	public void updatePatient (Patient patient);
	public void tableManytoMany(int id, int id2);
}
