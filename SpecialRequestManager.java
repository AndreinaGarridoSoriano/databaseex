package db.interfaces;


import java.util.List;

import pojos.SpecialRequest;

public interface SpecialRequestManager {
	public void newSpecialRequest(SpecialRequest sreq, int hosp_id, int bb_id);
	public SpecialRequest getSpecialRequestById(int id);
	public void updateSpecialRequest(SpecialRequest sreq);
	public List<SpecialRequest> getSpecialRequests(int hospitalId);
	public void tableManytoMany(int lastId, int id); 
}
