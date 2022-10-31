package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.interfaces.SpecialRequestManager;
import pojos.Request;
import pojos.SpecialRequest;

public class SQLiteSpecialRequestManager implements SpecialRequestManager {
	private Connection c;
	String sql;
	PreparedStatement p;
	ResultSet result;

	public SQLiteSpecialRequestManager(Connection c) {
		this.c = c;
	}

	@Override
	public void newSpecialRequest(SpecialRequest sreq, int hosp_id, int bb_id) {
		try {
			sql = "INSERT INTO sprequest (volume, dor, durability, relevance, plasma, platelets, hdl, hemoglobin, hematocrit,bloodbank_id ,hospital_id) VALUES (?, ?,?,?, ?, ?, ?, ?, ?, ?, ?);";
			p = c.prepareStatement(sql);
			p.setInt(1, sreq.getVolume());
			p.setDate(2, sreq.getDor());
			p.setInt(3, sreq.getDurability());
			p.setInt(4, sreq.getRelevance());
			p.setInt(5, sreq.getPlasma());
			p.setInt(6, sreq.getPlatelets());
			p.setInt(7, sreq.getHdl());
			p.setInt(8, sreq.getHemoglobin());
			p.setInt(9, sreq.getHematocrit());
			p.setInt(10, bb_id);
			p.setInt(11, hosp_id);
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public SpecialRequest getSpecialRequestById(int id) {
		SpecialRequest sreq = null;
		try {
			sql = "SELECT * FROM request WHERE id LIKE ?";
			p = c.prepareStatement(sql);
			p.setInt(1, id);
			result = p.executeQuery();
			while (result.next()) {
				int volume = result.getInt("volume");
				Date dor = result.getDate("dor");
				int durability = result.getInt("durability");
				int relevance = result.getInt("relevance");
				int plasma = result.getInt("plasma");
				int platelets = result.getInt("platelets");
				int hdl = result.getInt("hdl");
				int hemoglobin = result.getInt("hemoglobin");
				int hematocrit = result.getInt("hematocrit");
				sreq = new SpecialRequest(id, volume, dor, durability, relevance, plasma, platelets, hdl, hemoglobin,
						hematocrit);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sreq;
	}

	@Override
	public void updateSpecialRequest(SpecialRequest sreq) {
		try {
			sql = "UPDATE request SET volume=?, dor=?, durability=?, relevance=?, plasma=?, platelets=?, hdl=?, hemoglobin=?, hematocrit=? WHERE id=?";
			p = c.prepareStatement(sql);
			p.setInt(1, sreq.getVolume());
			p.setDate(2, sreq.getDor());
			p.setInt(3, sreq.getDurability());
			p.setInt(4, sreq.getRelevance());
			p.setFloat(5, sreq.getPlasma());
			p.setInt(6, sreq.getPlatelets());
			p.setFloat(7, sreq.getHdl());
			p.setFloat(8, sreq.getHemoglobin());
			p.setFloat(9, sreq.getHematocrit());
			p.setInt(10, sreq.getId());
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public List<SpecialRequest> getSpecialRequests(int hospitalId) {
		int id;
		int volume;
		Date dor;
		int durability;
		int plasma;
		int platelets;
		int hdl;
		int hemoglobin;
		int hematocrit;
		SpecialRequest spreq;
		List<SpecialRequest> spreqs = new ArrayList<SpecialRequest>();
		try {
			sql = "SELECT * FROM sprequest WHERE hospital_id LIKE ?";
			p = c.prepareStatement(sql);
			p.setInt(1, hospitalId);
			result = p.executeQuery();
			while (result.next()) {
				id = result.getInt("id");
				volume = result.getInt("volume");
				dor = result.getDate("dor");
				durability = result.getInt("durability");
				int relevance = result.getInt("relevance");
				hdl = result.getInt("hdl");
				hematocrit = result.getInt("hematocrit");
				hemoglobin = result.getInt("hemoglobin");
				plasma = result.getInt("plasma");
				platelets = result.getInt("platelets");
				spreq = new SpecialRequest(id, volume, dor, durability, relevance, plasma, platelets, hdl, hemoglobin,
						hematocrit);
				spreqs.add(spreq);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return spreqs;
	}

	@Override
	public void tableManytoMany(int lastId, int patient_id) {
		try {
			String sql = "INSERT INTO sprequest_patient (sprequest_id, patient_id) " + "VALUES (?,?);";
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