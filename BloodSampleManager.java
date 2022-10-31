package db.interfaces;

import java.sql.Date;
import java.util.List;

import pojos.*;

public interface BloodSampleManager {
	
	public void newSample(BloodSample sample);
	public BloodSample getSample(int id);
	public List<BloodSample> getSamples(int bloodBankId, int cont);
	public List<BloodSample> getSamples(int bloodBankId, String abo, String rh);
	public List<BloodSample> getSamples(int bloodBankId, Date min_dod, Date max_dod);
	public List<BloodSample> getSamples1(int bloodBankId, int min_value,int max_value, int option);
	public List<BloodSample> getSamples2(int bloodBankId, int min_value1, int max_value1, int min_value2,int max_value2,int option);
	public List<BloodSample> getSamples3(int bloodBankId, int min_value1, int max_value1, int min_value2,int max_value2,int min_value3,int max_value3,int option);
	public List<BloodSample> getSamples4(int bloodBankId, int min_value1, int max_value1, int min_value2,int max_value2,int min_value3,int max_value3,int min_value4,int max_value4);
	public List<BloodSample> getSample(int bloodBankId, int value,int option );

}
