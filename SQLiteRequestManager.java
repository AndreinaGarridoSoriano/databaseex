package db.sqlite;

import java.sql.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.RequestManager;
import pojos.Request;
import pojos.SpecialRequest;

public class SQLiteRequestManager implements RequestManager {
	private Connection c;
	String sql;
	PreparedStatement p;
	ResultSet result;

	public SQLiteRequestManager(Connection c) {
		this.c = c;
	}

	@Override
	public void newRequest(Request req, int hosp_id, int bb_id) {
		try {
			sql = "INSERT INTO request (volume, dor, durability, relevance, bloodbank_id ,hospital_id) VALUES ( ?, ?, ?, ?, ?, ?);";
			p = c.prepareStatement(sql);
			int vol=req.getVolume();
			p.setInt(1, vol);
			p.setDate(2, req.getDor());
			p.setInt(3, req.getDurability());
			p.setInt(4, req.getRelevance());
			p.setInt(5, bb_id);
			p.setInt(6, hosp_id);
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Request getRequestById(int id) {
		int volume;
		Date dor;
		int durability;
		int relevance;
		Request req = null;
		try {
			sql = "SELECT * FROM request WHERE id LIKE ?";
			p = c.prepareStatement(sql);
			p.setInt(1, id);
			result = p.executeQuery();
			while (result.next()) {
				volume = result.getInt("volume");
				dor = result.getDate("dor");
				durability = result.getInt("durability");
				relevance = result.getInt("relevance");

				req = new Request(volume, dor, durability, relevance);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return req;
	}

	@Override
	public List<Request> getRequestsByRelevance(int relevance) {
		List<Request> reqs = new ArrayList<Request>();
		int id;
		int volume;
		Date dor;
		int durability;
		Request req;
		try {
			sql = "SELECT * FROM request WHERE relevance LIKE ?";
			p = c.prepareStatement(sql);
			p.setInt(1, relevance);
			result = p.executeQuery();
			while (result.next()) {
				id = result.getInt("id");
				volume = result.getInt("volume");
				dor = result.getDate("dor");
				durability = result.getInt("durability");

				req = new Request(volume, dor, durability, relevance);
				reqs.add(req);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return reqs;
	}

	@Override
	public void updateRequest(Request req) {
		try {
			sql = "UPDATE request SET volume=?, dor=?, durability=?, relevance=? WHERE id=?";
			p = c.prepareStatement(sql);
			p.setInt(1, req.getVolume());
			p.setDate(2, req.getDor());
			p.setInt(3, req.getDurability());
			p.setInt(4, req.getRelevance());
			p.setInt(5, req.getId());
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Request> getRequests(int hospitalId) {
		int id;
		int volume;
		Date dor;
		int durability;
		Request req;
		List<Request> reqs = new ArrayList<Request>();
		try {
			sql = "SELECT * FROM request WHERE hospital_id LIKE ?";
			p = c.prepareStatement(sql);
			p.setInt(1, hospitalId);
			result = p.executeQuery();
			while (result.next()) {
				id = result.getInt("id");
				volume = result.getInt("volume");
				dor = result.getDate("dor");
				durability = result.getInt("durability");
				int relevance = result.getInt("relevance");
				req = new Request(id, volume, dor, durability, relevance);
				reqs.add(req);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return reqs;
	}

	@Override
	public void tableManytoMany(int lastId, int patient_id) {
		try {
			String sql = "INSERT INTO request_patient (request_id, patient_id) " + "VALUES (?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, lastId);
			prep.setInt(2, patient_id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
