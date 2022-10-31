package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.PatientManager;
import pojos.Hospital;
import pojos.Patient;

public class SQLitePatientManager implements PatientManager {

	private Connection c;

	public SQLitePatientManager(Connection c) {
		this.c = c;
	}

	@Override
	public void newPatient(Patient patient) {
		try {
			String sql = "INSERT INTO patient (name, surname , email , phone , address,weight, abo, rh, gender,dob, doctor) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, patient.getName());
			p.setString(2, patient.getSurname());
			p.setString(3, patient.getEmail());
			p.setString(4, patient.getPhone());
			p.setString(5, patient.getAddress());
			p.setFloat(6, patient.getWeight());
			p.setString(7, patient.getAbo());
			p.setString(8, patient.getRh());
			p.setString(9, patient.getGender());
			p.setDate(10, patient.getDob());
			p.setString(11, patient.getDoctor());
			p.executeUpdate();
			p.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Patient getPatient(String name, String surname) {

		String sql = "SELECT * FROM patient WHERE name = ? AND surname = ?";
		PreparedStatement p;
		try {
			p = c.prepareStatement(sql);
			p.setString(1, name);
			p.setString(2, surname);
			ResultSet result = p.executeQuery();
			int id = result.getInt("id");
			Date dob = result.getDate("Dob");
			String gender = result.getString("gender");
			float weight = result.getFloat("weight");
			String abo = result.getString("abo");
			String rh = result.getString("rh");
			String email = result.getString("email");
			String phone = result.getString("phone");
			String address = result.getString("address");
			String doctor = result.getString("doctor");
			Patient patient = new Patient(id, name, surname, email, phone, address, weight, abo, rh, gender, dob,doctor);
			return patient;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Patient> getAllPatients(int hospitalid) {
		List<Patient> patients = new ArrayList<Patient>();
		try {
			String sql = "SELECT p.* FROM patient AS p JOIN hospital_patient AS hp WHERE p.id = hp.patient_id AND hp.hospital_id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, hospitalid);
			ResultSet result = p.executeQuery();
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				String surname = result.getString("surname");
				Date dob = result.getDate("Dob");
				String gender = result.getString("gender");
				float weight = result.getFloat("weight");
				String abo = result.getString("abo");
				String rh = result.getString("rh");
				String email = result.getString("email");
				String phone = result.getString("phone");
				String address = result.getString("address");
				String doctor = result.getString("doctor");
				Patient patient = new Patient(id, name, surname, email, phone, address, weight, abo, rh, gender, dob, doctor);
				patients.add(patient);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return patients;

	}

	@Override
	public void updatePatient(Patient patient) {
		try {
			String sql = "UPDATE donor SET name=?, surname=?, dob=? , gender=?, weight=?, abo=?, rh=?, email=?, phone=?, address=?, doctor=? WHERE id=?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, patient.getName());
			p.setString(2, patient.getSurname());
			p.setDate(3, patient.getDob());
			p.setString(4, patient.getGender());
			p.setFloat(5, patient.getWeight());
			p.setString(6, patient.getAbo());
			p.setString(7, patient.getRh());
			p.setString(8, patient.getEmail());
			p.setString(9, patient.getPhone());
			p.setString(10, patient.getAddress());
			p.setInt(11, patient.getId());
			p.executeUpdate();
			p.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void tableManytoMany(int hospital_id, int patient_id) {
		try {
			String sql = "INSERT INTO hospital_patient (hospital_id, patient_id) " + "VALUES (?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, hospital_id);
			prep.setInt(2, patient_id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
