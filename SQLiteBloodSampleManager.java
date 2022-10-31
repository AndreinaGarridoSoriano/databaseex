package db.sqlite;

import java.sql.*;
import java.util.List;

import db.interfaces.BloodSampleManager;
import pojos.BloodSample;

public class SQLiteBloodSampleManager implements BloodSampleManager {
	private Connection c;

	public SQLiteBloodSampleManager(Connection c) {
		this.c = c;
	}

	@Override
	public void newSample(BloodSample sample) {
		try {
			String sql = "INSERT INTO bloodsample (hematocrit, plasma , platelets , hdl, hemoglobin , dod) "
					+ "VALUES (?,?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, sample.getHematocrit());
			prep.setInt(2, sample.getPlasma());
			prep.setInt(3, sample.getPlatelets());
			prep.setInt(4, sample.getHdl());
			prep.setInt(5, sample.getHemoglobin());
			prep.setDate(6, sample.getDod());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public BloodSample getSample(int id) {
		BloodSample newBloodSample = null;
		try {
			String sql = "SELECT * FROM bloodsample  WHERE id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			int newId = rs.getInt("id");
			int hematocrit = rs.getInt("hematocrit");
			int plasma = rs.getInt("plasma");
			int platelets = rs.getInt("platelets");
			int hdl = rs.getInt("hdl");
			int hemoglobin = rs.getInt("hemoglobin");
			Date dod = rs.getDate("dod");
			newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSample;
	}

	@Override
	public List<BloodSample> getSamples(int bloodBankId, int cont) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = null;
			switch (cont) {
			case 0:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “A” AND rh = “+”)";
				break;
			case 1:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “A” AND rh = “-”)";
				break;
			case 2:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “B” AND rh = “+”)";
				break;
			case 3:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “B” AND rh = “-”)";
				break;
			case 4:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “AB” AND rh = “+”)";
				break;
			case 5:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “AB” AND rh = “-”)";
				break;
			case 6:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “O” AND rh = “+”)";
				break;
			case 7:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “O” AND rh = “-”)";
			}
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}

	@Override
	public List<BloodSample> getSamples(int bloodBankId, String abo, String rh) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ALL (SELECT id FROM donor WHERE abo= “?” AND rh = “?”)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			p.setString(2, abo);
			p.setString(3, rh);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}

	@Override
	public List<BloodSample> getSamples(int bloodBankId, Date min_dod, Date max_dod) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND dod BETWEEN ? AND ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			p.setDate(2, min_dod);
			p.setDate(3, max_dod);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}

	@Override
	public List<BloodSample> getSamples1(int bloodBankId, int min_value, int max_value, int option) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = null;
			switch (option) {
			case 0:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hemoglobin BETWEEN ? AND ?";
				break;
			case 1:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hematocrit BETWEEN ? AND ?";
				break;
			case 2:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND platelets BETWEEN ? AND ?";
				break;
			case 3:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND plasma BETWEEN ? AND ?";
			}
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			p.setInt(2, min_value);
			p.setInt(3, max_value);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}

	@Override
	public List<BloodSample> getSamples2(int bloodBankId, int min_value1, int max_value1, int min_value2,
			int max_value2, int option) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = null;
			switch (option) {
			case 0:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hemoglobin BETWEEN ? AND ? AND hematocrit BETWEEN ? AND ?";
				break;
			case 1:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ?  AND hemoglobin BETWEEN ? AND ? AND platelets BETWEEN ? AND ?";
				break;
			case 2:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ?  AND hemoglobin BETWEEN ? AND ? AND plasma BETWEEN ? AND ?";
				break;
			case 3:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hematocrit BETWEEN ? AND ? AND platelets BETWEEN ? AND ?";
				break;
			case 4:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hematocrit BETWEEN ? AND ? AND plasma BETWEEN ? AND ?";
				break;
			case 5:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND platelets BETWEEN ? AND ? AND plasma BETWEEN ? AND ?";
			}
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			p.setInt(2, min_value1);
			p.setInt(3, max_value1);
			p.setInt(4, min_value2);
			p.setInt(5, max_value2);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}

	@Override
	public List<BloodSample> getSamples3(int bloodBankId, int min_value1, int max_value1, int min_value2,
			int max_value2, int min_value3, int max_value3, int option) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = null;
			switch (option) {
			case 0:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hemoglobin BETWEEN ? AND ? AND hematocrit BETWEEN ? AND ? AND platelets BETWEEN ? AND ?";
				break;
			case 1:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ?  AND hemoglobin BETWEEN ? AND ? AND hematocrit BETWEEN ? AND ? AND plasma BETWEEN ? AND ?";
				break;
			case 2:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ?  AND hemoglobin BETWEEN ? AND ? AND platelets BETWEEN ? AND ? AND plasma BETWEEN ? AND ?";
				break;
			case 3:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hematocrit BETWEEN ? AND ? AND platelets BETWEEN ? AND ? AND plasma BETWEEN ? AND ?";
				break;
			}
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			p.setInt(2, min_value1);
			p.setInt(3, max_value1);
			p.setInt(4, min_value2);
			p.setInt(5, max_value2);
			p.setInt(6, min_value3);
			p.setInt(7, max_value3);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}

	@Override
	public List<BloodSample> getSamples4(int bloodBankId, int min_value1, int max_value1, int min_value2,
			int max_value2, int min_value3, int max_value3, int min_value4, int max_value4) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = null;
			sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hemoglobin BETWEEN ? AND ? AND hematocrit BETWEEN ? AND ? AND platelets BETWEEN ? AND ? AND plasma BETWEEN ? AND ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			p.setInt(2, min_value1);
			p.setInt(3, max_value1);
			p.setInt(4, min_value2);
			p.setInt(5, max_value2);
			p.setInt(6, min_value3);
			p.setInt(7, max_value3);
			p.setInt(8, min_value4);
			p.setInt(9, max_value4);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}

	@Override
	public List<BloodSample> getSample(int bloodBankId, int value, int option) {
		List<BloodSample> newBloodSamples = null;
		BloodSample newBloodSample = null;
		try {
			String sql = null;
			switch (option) {
			case 0:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hemoglobin = ?";
				break;
			case 1:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND hematocrit = ?";
				break;
			case 2:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND platelets = ?";
				break;
			case 3:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND plasma = ?";
				break;
			case 4:
				sql = "SELECT * FROM bloodsample  WHERE bloodbank_id = ? AND donor_id = ?";
			}
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, bloodBankId);
			p.setInt(2, value);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int newId = rs.getInt("id");
				int hematocrit = rs.getInt("hematocrit");
				int plasma = rs.getInt("plasma");
				int platelets = rs.getInt("platelets");
				int hdl = rs.getInt("hdl");
				int hemoglobin = rs.getInt("hemoglobin");
				Date dod = rs.getDate("dod");
				newBloodSample = new BloodSample(newId, hematocrit, plasma, platelets, hdl, hemoglobin, dod);
				newBloodSamples.add(newBloodSample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newBloodSamples;
	}
}
