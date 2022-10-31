package db.interfaces;

import pojos.BloodBank;

public interface BloodBankManager {	
	public void newBloodBank (BloodBank bb);
	public BloodBank getBloodBankByID (int id);
	public void updateBloodBank (BloodBank bb);
	public void deleteBloodBank (int id);
}
