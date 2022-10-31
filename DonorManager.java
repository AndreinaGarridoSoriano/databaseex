package db.interfaces;

import java.sql.Date;
import java.util.List;

import pojos.Donor;

public interface DonorManager {
	public void newDonor (Donor donor);
	public Donor viewDonor (int donorid);
	public Donor getDonor (int bbid, String name, String surname);
	public List<Donor> getAllDonors (int bbid);
	public List<Donor> getDonorsByGender(String gender);
	public List<Donor> getDonorsByDOB(Date dob);
	public List<Donor> getDonorsByCity (String city);
	public List<Donor> getDonorsByPostCode (String zip);
	public List<Donor> getDonorsByABO (String abo);
	public void updateDonor (Donor donor);
}
