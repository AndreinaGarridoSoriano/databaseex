package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.HospitalManager;
import pojos.Hospital;

public class SQLiteHospitalManager implements HospitalManager {

	private Connection c;
	String sql;
	PreparedStatement p;
	ResultSet result;

	public SQLiteHospitalManager(Connection c) {
		this.c = c;
	}

	@Override
	public void newHospital(Hospital hospital) {
		try {
			sql = "INSERT INTO hospital (email, address, phone, name, city, zip) VALUES (?, ?, ?, ?, ?, ?);";
			p = c.prepareStatement(sql);
			p.setString(1, hospital.getEmail());
			p.setString(2, hospital.getAddress());
			p.setString(3, hospital.getPhone());
			p.setString(4, hospital.getName());
			p.setString(5, hospital.getCity());
			p.setString(6, hospital.getZip());
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Hospital getHospitalByID(int id) {
		String email;
		String address;
		String phone;
		String name;
		String city;
		String zip;
		Hospital hospital = null;
		try {
			sql = "SELECT * FROM hospital WHERE id LIKE ?";
			p = c.prepareStatement(sql);
			p.setInt(1, id);
			result = p.executeQuery();
			while (result.next()) {
				email = result.getString("email");
				address = result.getString("address");
				phone = result.getString("phone");
				name = result.getString("name");
				city = result.getString("city");
				zip = result.getString("zip");

				hospital = new Hospital(id, email, address, phone, name, city, zip);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hospital;
	}

	@Override
	public void updateHospital(Hospital hospital) {
		try {
			sql = "UPDATE hospital SET email=?, address=?, phone=?, name=?, city=?, zip=? WHERE id=?";
			p = c.prepareStatement(sql);
			p.setString(1, hospital.getEmail());
			p.setString(2, hospital.getAddress());
			p.setString(3, hospital.getPhone());
			p.setString(4, hospital.getName());
			p.setString(5, hospital.getCity());
			p.setString(6, hospital.getZip());
			p.setInt(7, hospital.getId());
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteHospital(int id) {
		try {
			sql = "DELETE FROM hospital WHERE id =?";
			p = c.prepareStatement(sql);
			p.setInt(1, id);
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
