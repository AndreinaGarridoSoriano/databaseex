package db.interfaces;

import java.sql.Connection;

public interface ADBManager {
	public void connect();
	public void disconnect();
	public void createTables();
	public BloodBankManager getBloodBank();
	public BloodSampleManager getBloodSample();
	public DonorManager getDonor();
	public HospitalManager getHospital();
	public PatientManager getPatient();
	public RequestManager getRequest();
	public SpecialRequestManager getSprequest();
	public Connection getC();
	public int getLastId();

}
