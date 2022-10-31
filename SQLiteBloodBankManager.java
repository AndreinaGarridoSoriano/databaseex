package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import db.interfaces.BloodBankManager;
import pojos.BloodBank;

public class SQLiteBloodBankManager implements BloodBankManager {
	private Connection c;
	String sql;
	PreparedStatement p;
	ResultSet result;

	public SQLiteBloodBankManager(Connection c) {
		this.c = c;
	}

	@Override
	public void newBloodBank(BloodBank bb) {
		try {
			sql = "INSERT INTO bloodbank (email, address, phone, capacity, city, zip) VALUES (?, ?, ?, ?, ?, ?);";
			p = c.prepareStatement(sql);
			p.setString(1, bb.getEmail());
			p.setString(2, bb.getAddress());
			p.setString(3, bb.getPhone());
			p.setInt(4, bb.getCapacity());
			p.setString(5, bb.getCity());
			p.setString(6, bb.getZip());
			p.executeUpdate();
			p.close();
			}catch(Exception ex){
			ex.printStackTrace();
	}}

	@Override
	public BloodBank getBloodBankByID(int id) {
		String email;
		String address;
		String phone;
		int capacity;
		String city;
		String zip;
		BloodBank bb = null;
		try {
		sql = "SELECT * FROM bloodbank WHERE id LIKE ?";
		p = c.prepareStatement(sql);
		p.setInt(1, id);
		result = p.executeQuery();
		while(result.next()) {
		email = result.getString("email");
		address = result.getString("address");
		phone = result.getString("phone");
		capacity = result.getInt("capacity");
		city = result.getString("city");
		zip = result.getString("zip");

		bb = new BloodBank(id, email, address, phone, capacity, city, zip);
		}
		}catch (Exception ex) {
		ex.printStackTrace();
		}
		return bb;
	}


	@Override
	public void updateBloodBank(BloodBank bb) {
		try {
			sql = "UPDATE bloodbank SET email=?, address=?, phone=?, capacity=?, city=?, zip=? WHERE id=?";
			p = c.prepareStatement(sql);
			p.setString(1, bb.getEmail());
			p.setString(2, bb.getAddress());
			p.setString(3, bb.getPhone());
			p.setInt(4, bb.getCapacity());
			p.setString(5, bb.getCity());
			p.setString(6, bb.getZip());
			p.setInt(7, bb.getId());
			p.executeUpdate();
			p.close();
			}catch(Exception ex) {
			ex.printStackTrace();
			}
	}

	@Override
	public void deleteBloodBank(int id) {
		try {
			sql = "DELETE FROM bloodbank WHERE id=?";
			p = c.prepareStatement(sql);
			p.setInt(1, id);
			p.executeUpdate();
			p.close();
			}catch(Exception ex) {
			ex.printStackTrace();
			}
			}
		
	
}
