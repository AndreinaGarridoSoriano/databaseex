package db.interfaces;

import java.util.List;

import pojos.*;

public interface RequestManager {
	public void newRequest(Request req,int hosp_id,int bb_id);
	public Request getRequestById(int id);
	public List<Request> getRequestsByRelevance(int relevance);
	public void updateRequest(Request req);
    public List<Request> getRequests(int hospitalId);
	public void tableManytoMany(int lastId, int id); 
}

