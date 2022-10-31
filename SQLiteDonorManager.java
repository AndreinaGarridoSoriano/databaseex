package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import db.interfaces.DonorManager;
import pojos.Donor;

public class SQLiteDonorManager implements DonorManager {
	private Connection c;
	PreparedStatement p; 
	ResultSet result;

	public SQLiteDonorManager (Connection c) {
		this.c = c;
	}

	@Override
	public void newDonor(Donor donor) {
		try {
			String sql = "INSERT INTO donor (name, surname , dob , gender , weight, abo, rh, email, phone, city, zip, address) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
			p = c.prepareStatement(sql);
			p.setString(1, donor.getName());
			p.setString(2, donor.getSurname());
			p.setDate(3, donor.getDob());
			p.setString(4, donor.getGender());
			p.setFloat(5, donor.getWeight());
			p.setString(6, donor.getAbo());
			p.setString(7, donor.getRh());
			p.setString(8, donor.getEmail());
			p.setString(9, donor.getPhone());
			p.setString(10, donor.getCity());
			p.setString(11, donor.getZip());
			p.setString(12, donor.getAddress());
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
	}

	@Override
	public Donor viewDonor(int donorid) {
		Donor donor = new Donor();
		try {
			String sql = "SELECT * FROM donor WHERE id LIKE ?";
			p = c.prepareStatement(sql);
			p.setInt(1, donorid );
			result = p.executeQuery();
				String name = result.getString("name");
				String surname = result.getString("surname");
				Date dob = result.getDate("Dob");
				String gender = result.getString("gender");
				float weight = result.getFloat("weight");
				String abo = result.getString("abo");
				String rh = result.getString("rh");
				String email = result.getString("email");
				String phone = result.getString("phone");
				String city = result.getString("city");
				String zip = result.getString("zip code");
				String address = result.getString("address");
				donor = new Donor(donorid, name, surname , dob , gender , weight, abo, rh, email, phone, city, zip, address);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return donor;
	}

	@Override
	public Donor getDonor (int bbid, String name, String surname) {
		Donor donor = new Donor();
		try {
			String sql = "asjdnosdijdsoigjmoeijgm";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, "%" + bbid + name + surname + "%");
			ResultSet result = p.executeQuery();
			
		
				int id = result.getInt("int");
				Date dob = result.getDate("Dob");
				String gender = result.getString("gender");
				float weight = result.getFloat("weight");
				String abo = result.getString("abo");
				String rh = result.getString("rh");
				String email = result.getString("email");
				String phone = result.getString("phone");
				String city = result.getString("city");
				String zip = result.getString("zip code");
				String address = result.getString("address");

				 donor = new Donor(id, name, surname , dob , gender , weight, abo, rh, email, phone, city, zip, address);
			
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return donor;
	}

	@Override
	public List<Donor> getAllDonors(int bbid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Donor> getDonorsByGender(String gender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Donor> getDonorsByDOB(Date dob) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Donor> getDonorsByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Donor> getDonorsByPostCode(String zip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Donor> getDonorsByABO(String abo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDonor(Donor donor) {
		// TODO Auto-generated method stub
		
	}
}
