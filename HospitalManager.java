package db.interfaces;

import pojos.Hospital;

public interface HospitalManager {
	public void newHospital (Hospital hospital);
	public Hospital getHospitalByID (int id);
	public void updateHospital (Hospital hospital);
	public void deleteHospital (int id);
}
