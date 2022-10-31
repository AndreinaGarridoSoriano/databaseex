package db.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.interfaces.*;

public class SQLiteABDManager implements ADBManager {

	private Connection c;
	private BloodBankManager bloodBank;
	private BloodSampleManager bloodSample;
	private DonorManager donor;
	private HospitalManager hospital;
	private PatientManager patient;
	private RequestManager request;
	private SpecialRequestManager sprequest;

	public SQLiteABDManager() {
		super();
	}

	@Override
	public void connect() {
		
		try {
			Class.forName("org.sqlite.JDBC");

			this.c = DriverManager.getConnection("jdbc:sqlite:./LAST/bloodbank.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			bloodBank = new SQLiteBloodBankManager(c);
			bloodSample = new SQLiteBloodSampleManager(c);
			donor = new SQLiteDonorManager(c);
			hospital = new SQLiteHospitalManager(c);
			patient = new SQLitePatientManager(c);
			request = new SQLiteRequestManager(c);
			sprequest = new SQLiteSpecialRequestManager(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createTables() {
		Statement statement1;
		try {
			statement1 = c.createStatement();
			String table1 = "CREATE TABLE IF NOT EXISTS bloodbank " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " email   TEXT NOT NULL, " + " address  TEXT   NOT NULL, " + " phone TEXT  , "
					+ " capacity INTEGER NOT NULL, " + " city TEXT NOT NULL ," + "zip TEXT)";
			statement1.executeUpdate(table1);
			statement1 = c.createStatement();
			String table2 = "CREATE TABLE IF NOT EXISTS donor " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL, " + " surname TEXT NOT NULL, " + " email TEXT NOT NULL, " + " phone TEXT , "
					+ " address TEXT NOT NULL, " + " weight FLOAT NOT NULL, "
					+ " abo TEXT CHECK (abo = 'A' OR abo = 'B' OR abo = 'AB' OR abo = 'O'), "
					+ " rh TEXT CHECK (rh = '+' OR rh = '-'), " + "gender TEXT CHECK (gender = 'M' OR gender = 'F'), "
					+ " dob DATE NOT NULL)";
			statement1.executeUpdate(table2);
			statement1 = c.createStatement();
			String table3 = "CREATE TABLE IF NOT EXISTS bloodsample " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " hematocrit INTEGER NOT NULL, " + " plasma INTEGER NOT NULL, " + " platelets INTEGER NOT NULL, "
					+ " hdl INTEGER, " + " hemoglobin INTEGER NOT NULL, " + " dod DATE NOT NULL, "
					+ " donor_id INTEGER, " + " bloodbank_id INTEGER, "
					+ " FOREIGN KEY(donor_id) REFERENCES donor(id), "
					+ " FOREIGN KEY(bloodbank_id) REFERENCES bloodbank(id))";
			statement1.executeUpdate(table3);
			statement1 = c.createStatement();
			String table4 = "CREATE TABLE IF NOT EXISTS hospital " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL, " + " email TEXT NOT NULL, " + " phone TEXT , " + " address TEXT NOT NULL, "
					+ "zip TEXT, " + " city TEXT)";
			statement1.executeUpdate(table4);
			statement1 = c.createStatement();
			String table5 = "CREATE TABLE IF NOT EXISTS patient " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL, " + " surname TEXT NOT NULL, " + " email TEXT NOT NULL, " + " phone TEXT , "
					+ " address TEXT NOT NULL, " + " weight FLOAT NOT NULL, "
					+ " abo TEXT CHECK (abo = 'A' OR abo = 'B' OR abo = 'AB' OR abo = 'O'), "
					+ " rh TEXT CHECK (rh = '+' OR rh = '-'), " + "gender TEXT CHECK (gender = 'M' OR gender = 'F'), "
					+ " dob DATE NOT NULL, " + " doctor TEXT NOT NULL)";
			statement1.executeUpdate(table5);
			statement1 = c.createStatement();
			String table6 = "CREATE TABLE IF NOT EXISTS request " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " dor DATE NOT NULL, " + " durability INTEGER NOT NULL, " + " relevance INTEGER NOT NULL, "
					+ " volume INTEGER NOT NULL, " + " bloodbank_id INTEGER, " + " hospital_id INTEGER, "
					+ " FOREIGN KEY (bloodbank_id) REFERENCES bloodbank(id), "
					+ " FOREIGN KEY (hospital_id) REFERENCES hospital(id)  ON DELETE CASCADE ON UPDATE CASCADE)";
			statement1.executeUpdate(table6);
			statement1 = c.createStatement();
			String table7 = "CREATE TABLE IF NOT EXISTS sprequest " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " dor DATE NOT NULL, " + " durability INTEGER NOT NULL, " + " relevance INTEGER NOT NULL, "
					+ " volume INTEGER NOT NULL, " + "hemoglobin INTEGER," + "hematocrit INTEGER," + "platelets INTEGER," + "plasma INTEGER," + "hdl INTEGER," + 
					" bloodbank_id INTEGER, " + " hospital_id INTEGER, " + " FOREIGN KEY (bloodbank_id) REFERENCES bloodbank(id), "
					+ " FOREIGN KEY (hospital_id) REFERENCES hospital(id) ON DELETE CASCADE ON UPDATE CASCADE)";
			statement1.executeUpdate(table7);
			statement1 = c.createStatement();
			String table8 = "CREATE TABLE IF NOT EXISTS hospital_patient "
					+ " (hospital_id INTEGER REFERENCES hospital(id)  ON DELETE CASCADE ON UPDATE CASCADE , "
					+ " patient_id  INTEGER REFERENCES patient(id), " + " PRIMARY KEY(hospital_id,patient_id))";
			statement1.executeUpdate(table8);
			statement1 = c.createStatement();
			String table9 = "CREATE TABLE IF NOT EXISTS bloodbank_donor "
					+ " (bloodbank_id INTEGER REFERENCES bloodbank(id), " + " donor_id  INTEGER REFERENCES donor(id), "
					+ " PRIMARY KEY(bloodbank_id,donor_id))";
			statement1.executeUpdate(table9);
			statement1 = c.createStatement();
			String table10 = "CREATE TABLE IF NOT EXISTS request_patient "
					+ " (request_id INTEGER REFERENCES request(id), " + " patient_id  INTEGER REFERENCES patient(id), "
					+ " PRIMARY KEY(request_id,patient_id))";
			statement1.executeUpdate(table10);
			statement1 = c.createStatement();
			String table11 = "CREATE TABLE IF NOT EXISTS sprequest_patient "
					+ " (sprequest_id INTEGER REFERENCES request(id), " + " patient_id  INTEGER REFERENCES patient(id), "
					+ " PRIMARY KEY(sprequest_id,patient_id))";
			statement1.executeUpdate(table11);
			statement1.close();
		} catch (SQLException e) {
			System.out.println("error de execute");
			e.printStackTrace();
		}

	}

	public Connection getC() {
		return c;
	}

	public void setC(Connection c) {
		this.c = c;
	}

	public BloodBankManager getBloodBank() {
		return bloodBank;
	}

	public BloodSampleManager getBloodSample() {
		return bloodSample;
	}

	public DonorManager getDonor() {
		return donor;
	}

	public HospitalManager getHospital() {
		return hospital;
	}

	public PatientManager getPatient() {
		return patient;
	}

	public RequestManager getRequest() {
		return request;
	}

	public SpecialRequestManager getSprequest() {
		return sprequest;
	}

	@Override
	public int getLastId() {
		int result = 0;
		try {
			String query = "SELECT last_insert_rowid() AS lastId";
			PreparedStatement p = c.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			result = rs.getInt("lastId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
