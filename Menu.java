package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.*;
import db.jpa.JPAUserManager;
import db.sqlite.*;
import pojos.*;
import pojos.user.Role;
import pojos.user.User;

public class Menu {
	static BufferedReader reader;
	static BufferedReader console;
	static int option;

	private static ADBManager dbManager;
	private static HospitalManager HospitalManager;
	private static BloodBankManager BBManager;
	private static BloodSampleManager BSManager;
	private static DonorManager DonorManager;
	private static PatientManager PatientManager;
	private static RequestManager RequestManager;
	private static SpecialRequestManager SRequestManager;
	private static UserManager userManager;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {

		dbManager = new SQLiteABDManager();
		dbManager.connect();
		HospitalManager = dbManager.getHospital();
		BBManager = dbManager.getBloodBank();
		BSManager = dbManager.getBloodSample();
		DonorManager = dbManager.getDonor();
		PatientManager = dbManager.getPatient();
		RequestManager = dbManager.getRequest();
		SRequestManager = dbManager.getSprequest();
	
		dbManager.createTables();
		
		userManager = new JPAUserManager();
		userManager.connect();
		

		reader = new BufferedReader(new InputStreamReader(System.in));
		console = new BufferedReader(new InputStreamReader(System.in));


		boolean op = true;
		while (op) {
			System.out.println("What do you want to do?");
			System.out.println("1. Create new role");
			System.out.println("2. Create new user");
			System.out.println("3. Login");
			System.out.println("0. Exit");
			option = Integer.parseInt(reader.readLine());
			switch (option) {
			case 1:
				newRole();
				break;
			case 2:
				newUser();
				break;
			case 3:
				login();
				break;
			case 0:
				dbManager.disconnect();
				userManager.disconnect();
				return;
			default:
				System.out.println("Invalid option");
				System.out.println("Enter option again");
				System.out.println(" ");
				break;
			}
		}
	}

	public static void newRole() throws Exception {
		System.out.println("Enter role info:");
		System.out.print("Role name:");
		String roleName = console.readLine();
		Role role = new Role(roleName);
		userManager.createRole(role);
	}

	public static void newUser() throws Exception {
		System.out.println("Enter user info:");
		System.out.print("Username:");
		String username = console.readLine();
		System.out.print("Password:");
		String password = console.readLine();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		List<Role> roles = userManager.getRoles();
		for (Role role : roles) {
			System.out.println(role);
		}
		System.out.println("Select role:");
		int roleId = Integer.parseInt(console.readLine());
		Role selectedRole = userManager.getRole(roleId);
		User user = new User(username, hash, selectedRole);
		userManager.createUser(user);
	}

	private static void login() throws Exception {
		System.out.println("Enter your information:");
		System.out.print("Username:");
		String username = console.readLine();
		System.out.print("Password:");
		String password = console.readLine();
		User user = userManager.checkPassword(username, password);
		if (user == null) {
			System.out.println("Error: user information mismatch.");
		} else if (user.getRole().getRole().equalsIgnoreCase("Blood Bank")) {
			bloodBankMenu();
		} else if (user.getRole().getRole().equalsIgnoreCase("Hospital")) {
			hospitalMenu();
		} else
			System.out.println("Invalid information: non-existing user");
	}

	private static void hospitalMenu() throws NumberFormatException, IOException {

		System.out.println("What do you want to do?");
		System.out.println("1. Login ");
		System.out.println("2. Sign up");

		int option = Integer.parseInt(reader.readLine());
		switch (option) {
		case 1:

			System.out.println("Enter your hospital's id");
			int id = Integer.parseInt(reader.readLine());
			hospitalXMenu(id);
			break;
		case 2:
			System.out.println("Enter your hospital's info");
			System.out.println("name:");
			String name = reader.readLine();
			System.out.println("email:");
			String email = reader.readLine();
			System.out.println("phone:");
			String phone = reader.readLine();
			System.out.println("address:");
			String address = reader.readLine();
			System.out.println("city:");
			String city = reader.readLine();
			System.out.println("zip:");
			String zip = reader.readLine();
			Hospital hospital = new Hospital(email, address, phone, name, city, zip);
			HospitalManager.newHospital(hospital);
			System.out.println("Hospital created!");
			break;
		default:
			System.out.println("Invalid option");
			System.out.println("Enter option again");
			System.out.println(" ");
			break;
		}
	}

	private static void hospitalXMenu(int id) throws NumberFormatException, IOException {
		Hospital hospital;
		System.out.println("What do you want to do?");
		System.out.println("1. View profile info");
		System.out.println("2. Update profile info");
		System.out.println("3. Make blood request");
		System.out.println("4. View all requests");
		System.out.println("5. Insert a new patient");
		System.out.println("6. View all patients");
		System.out.println("7. View specific patient");
		System.out.println("8. Delete account");

		int option = Integer.parseInt(reader.readLine());
		switch (option) {
		case 1:
			hospital = HospitalManager.getHospitalByID(id);
			System.out.println(hospital);

			break;
		case 2:
			HospitalManager.updateHospital(updateHospital(id));
			System.out.println("hospital updated");
			break;
		case 3:
			makeRequest(id);
			break;
		case 4:
			List<Request> req = RequestManager.getRequests(id);
			List<SpecialRequest> spreq = SRequestManager.getSpecialRequests(id);
			System.out.println(req);
			System.out.println(spreq);
			break;
		case 5:
			insertPatient(id);
			break;
		case 6:
			List<Patient> patients = PatientManager.getAllPatients(id);
			System.out.println(patients);
			break;
		case 7:
			System.out.println("Enter pacient's name and surname:");
			String name = reader.readLine();
			String surname = reader.readLine();
			Patient pacient = PatientManager.getPatient(name, surname);
			System.out.println(pacient);
			break;
		case 8:
			System.out.println("Are you sure you want to delete your hospital's account?");
			System.out.println("1. yes");
			System.out.println("2. no");
			int op = Integer.parseInt(reader.readLine());
			if (op == 1) {
				HospitalManager.deleteHospital(id);
				System.out.println("Account succesfully deleted!");
			}
			break;
		default:
			System.out.println("Invalid option");
			System.out.println("Enter option again");
			System.out.println(" ");
			break;
		}
	}

	private static void insertPatient(int id) throws IOException {
		System.out.println("Enter your the patient's info");
		System.out.println("name:");
		String name = reader.readLine();
		System.out.println("surname:");
		String surname = reader.readLine();
		System.out.println("email:");
		String email = reader.readLine();
		System.out.println("phone:");
		String phone = reader.readLine();
		System.out.println("address:");
		String address = reader.readLine();
		System.out.println("weight:");
		Float weight = Float.parseFloat(reader.readLine());
		System.out.println("abo(A/B/AB/O):");
		String abo = reader.readLine();
		System.out.println("rh(-/+):");
		String rh = reader.readLine();
		System.out.println("gender(F/M):");
		String gender = reader.readLine();
		System.out.println("Date of birth(yyyy-mm-dd):");
		String date = reader.readLine();
		LocalDate dob = LocalDate.parse(date, formatter);
		System.out.println("Doctor in charge:");
		String doctor = reader.readLine();
		Patient patient = new Patient(name, surname, email, phone, address, weight, abo, rh, gender, Date.valueOf(dob),
				doctor);
		PatientManager.newPatient(patient);
		patient = PatientManager.getPatient(name, surname);
		System.out.println("Patient created!");
		PatientManager.tableManytoMany(id, patient.getId());
		System.out.println("relationship");
	}

	private static void makeRequest(int hosp_id) throws NumberFormatException, IOException {

		System.out.println("Enter id of the bloodbank that you wish to make the request to:");
		int bb_id = Integer.parseInt(reader.readLine());
		System.out.println("Choose type of request:");
		System.out.println("1. Standard");
		System.out.println("2. Special");
		System.out.println("3. Emergency");
		int option = Integer.parseInt(reader.readLine());
		switch (option) {
		case 1:
			System.out.println("Enter info request's info:");
			System.out.println("Enter name and surname of the pacient:");
			String name = reader.readLine();
			String surname = reader.readLine();
			Patient patient = PatientManager.getPatient(name, surname);
			System.out.println("Enter current date(yyyy-mm-dd):");
			String date = reader.readLine();
			LocalDate dor = LocalDate.parse(date, formatter);
			System.out.println("Enter volume of blood(ml):");
			int vol = Integer.parseInt(reader.readLine());
			System.out.println("Enter number of weeks you need the blood to last:");
			int durability = Integer.parseInt(reader.readLine());

			Request req = new Request(vol, Date.valueOf(dor), durability, 0);

			RequestManager.newRequest(req, hosp_id, bb_id);
			RequestManager.tableManytoMany(dbManager.getLastId(), patient.getId());
			break;
		case 2:
			System.out.println("Enter info request's info:");
			System.out.println("Enter name and surname of the pacient:");
			String sname = reader.readLine();
			String ssurname = reader.readLine();
			Patient spatient = PatientManager.getPatient(sname, ssurname);
			System.out.println("Enter current date(yyyy-mm-dd):");
			String sdate = reader.readLine();
			LocalDate sdor = LocalDate.parse(sdate, formatter);
			System.out.println("Enter volume of blood(ml):");
			int svolume = Integer.parseInt(reader.readLine());
			System.out.println("Enter number of weeks you need the blood to last:");
			int sdurability = Integer.parseInt(reader.readLine());
			System.out.println("plasma:");
			int plasma = Integer.parseInt(reader.readLine());
			System.out.println("platelets:");
			int platelets = Integer.parseInt(reader.readLine());
			System.out.println("hemoglobin:");
			int hemoglobin = Integer.parseInt(reader.readLine());
			System.out.println("hematocrit:");
			int hematocrit = Integer.parseInt(reader.readLine());
			System.out.println("hdl:");
			int hdl = Integer.parseInt(reader.readLine());

			SpecialRequest sreq = new SpecialRequest(svolume, Date.valueOf(sdor), sdurability, 0, plasma, platelets,
					hdl, hemoglobin, hematocrit);

			SRequestManager.newSpecialRequest(sreq, hosp_id, bb_id);
			SRequestManager.tableManytoMany(dbManager.getLastId(), spatient.getId());
			break;
		case 3:
			ArrayList<Patient> patients = new ArrayList<>();
			Patient epatient;
			System.out.println("Enter request's info:");
			System.out.println("Enter number of patients");
			int count = Integer.parseInt(reader.readLine());
			for (int i = 1; i <= count; i++) {
				System.out.println("Enter name and surname of the pacient " + i + ":");
				String ename = reader.readLine();
				String esurname = reader.readLine();
				epatient = PatientManager.getPatient(ename, esurname);
				patients.add(epatient);
			}
			System.out.println("Enter current date(yyyy-mm-dd):");
			String edate = reader.readLine();
			LocalDate edor = LocalDate.parse(edate, formatter);
			System.out.println("Enter total volume of blood(ml):");
			int evol = Integer.parseInt(reader.readLine());
			Request ereq = new Request(evol, Date.valueOf(edor), 1, 100);
			RequestManager.newRequest(ereq, hosp_id, bb_id);
			for (int i = 0; i < count; i++) {
				RequestManager.tableManytoMany(dbManager.getLastId(), patients.get(i).getId());
			}
			break;
		default:
			break;
		}
	}

	private static Hospital updateHospital(int id) throws NumberFormatException, IOException {
		String txt;
		Hospital hospital = null;
		while (true) {
			System.out.println("Which parameter do you want to change?");
			System.out.println("1.name");
			System.out.println("2.email");
			System.out.println("3.phone");
			int option = Integer.parseInt(reader.readLine());
			switch (option) {
			case 1:
				System.out.println("Enter name:");
				txt = reader.readLine();
				hospital = HospitalManager.getHospitalByID(id);
				hospital.setName(txt);
				break;
			case 2:
				System.out.println("Enter email:");
				txt = reader.readLine();
				hospital = HospitalManager.getHospitalByID(id);
				hospital.setEmail(txt);
				break;
			case 3:
				System.out.println("Enter phone:");
				txt = reader.readLine();
				hospital = HospitalManager.getHospitalByID(id);
				hospital.setPhone(txt);
				break;
			default:
				break;
			}
			return hospital;
		}
	}

	private static void bloodBankMenu() throws NumberFormatException, IOException {
		System.out.println("What do you want to do?");
		System.out.println("1. Login ");
		System.out.println("2. Sign up");

		int option = Integer.parseInt(reader.readLine());
		switch (option) {
		case 1:

			break;
		case 2:
			System.out.println("Enter your bloodbank's info");
			System.out.println("email:");
			String email = reader.readLine();
			System.out.println("phone:");
			String phone = reader.readLine();
			System.out.println("capacity:");
			int capacity = Integer.parseInt(reader.readLine());
			System.out.println("address:");
			String address = reader.readLine();
			System.out.println("city:");
			String city = reader.readLine();
			System.out.println("zip:");
			String zip = reader.readLine();
			BloodBank bloodbank = new BloodBank(email, address, phone, capacity, city, zip);
			BBManager.newBloodBank(bloodbank);
			break;
		default:
			System.out.println("Invalid option");
			System.out.println("Enter option again");
			System.out.println(" ");
			break;
		}
	}
}
