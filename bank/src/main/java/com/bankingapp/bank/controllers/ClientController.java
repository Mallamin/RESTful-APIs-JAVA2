package com.bankingapp.bank.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapp.bank.EncryptDecrypt;
import com.bankingapp.bank.dto.ObjectDto;
import com.bankingapp.bank.dto.SecondDto;
import com.bankingapp.bank.models.Client;
import com.bankingapp.bank.models.Operations;
import com.bankingapp.bank.models.Transaction;
import com.bankingapp.bank.repos.ClientRepo;
import com.bankingapp.bank.repos.FundsRepo;

@RestController
public class ClientController {

	@Autowired
	private ClientRepo clientRepo;
	@Autowired
	private FundsRepo fundsRepo;

	@PostMapping("/client")
	public ResponseEntity<String> saveClient(@RequestBody Client client) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			Statement stmt = con.createStatement();
			String sql = "Select userid,password from client where userid=" + client.getUserID() + "";

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == true) {

				return new ResponseEntity<>("Client Already exist", HttpStatus.NO_CONTENT);
			} else {

				{

					EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
					encryptDecrypt.initFromStrings("4DENNYkXykVwITEy2iT3xQ==", "CIA7OqdEvTiQ5LLF");
					String encryptedMessage = encryptDecrypt.encrypt(client.getPassword());
					System.err.println("encrypted Message= " + encryptedMessage);

					client.setPassword(encryptedMessage);
					System.out.println("encrypted Message= " + encryptedMessage);

					clientRepo.save(client);
					return new ResponseEntity<>("Client saved", HttpStatus.OK);

				}
			}
		}

		catch (Exception e) {
			System.out.print(e);
		}
		return new ResponseEntity<>("connection established", HttpStatus.OK);
	}

	// Secondo fase

	@PostMapping("/login")
	public ResponseEntity<String> loginClient(@RequestBody ObjectDto objectDto) {
		int userID = 0;
		String password = "";
		String decryptedMessage = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			Statement stmt = con.createStatement();

//			String sql = "Select userid,password from client where userid='" + objectDto.getUserID()
//					+ "' and password='" + objectDto.getPassword() + "'";

			String sql = "Select userid,password from client where userid=" + objectDto.getUserID();

			ResultSet rs = stmt.executeQuery(sql);

			// System.out.println("encrypted Message= " + encryptedMessage);

			if (rs.next() == false) {
				// handle empty set: throw error or return
				return new ResponseEntity<>("Please login!", HttpStatus.OK);
			} else {
				do {

					userID = rs.getInt("userid");
					password = rs.getString("password");

					EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
					encryptDecrypt.initFromStrings("4DENNYkXykVwITEy2iT3xQ==", "CIA7OqdEvTiQ5LLF");
					Client client = new Client();
					// String encryptedMessage = encryptDecrypt.encrypt("Encryptedpassword");
					decryptedMessage = encryptDecrypt.decrypt(password);
					// client.setPassword(decryptedMessage);
					System.err.println("Decrypted message= " + decryptedMessage);

				} while (rs.next());

			}

		} catch (

		Exception e) {
			System.out.print(e);
		}

		if ((userID == objectDto.getUserID()) && (decryptedMessage.equals(objectDto.getPassword()))) {

			return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
		} else
			return new ResponseEntity<>("connection established", HttpStatus.OK);

	}

	@GetMapping("/funds/{userID}")
	public ResponseEntity<String> printFunds(@PathVariable int userID) {

		try {
			// String x = " ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			Statement stmt = con.createStatement();

			String sql = "Select funds,userid from client where userid='" + userID + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next() == false) {
				// handle empty set: throw error or return
				return new ResponseEntity<>("rs empty", HttpStatus.NO_CONTENT);
			} else {
				do {
					int funds = rs.getInt("funds");

					// x = String.valueOf(funds);

					// return new ResponseEntity<>("Client : " + userID + "Euro: " + x,
					// HttpStatus.OK);
					return new ResponseEntity<>("Here is the money:" + " " + funds + " " + "Euros", HttpStatus.OK);

				} while (rs.next());

			}
		} catch (

		Exception e) {
			System.out.print(e);
		}
		return new ResponseEntity<>("connection established", HttpStatus.OK);

	}

	// Retrieving a specific Client
	@GetMapping("/clients/{id}")
	public ResponseEntity<String> retrieveOneClientInfo(@PathVariable long id) {
		Optional<Client> client = clientRepo.findById(id);
		if (client.isEmpty()) {
			return new ResponseEntity<>("CLient not found", HttpStatus.OK);

		}
		return new ResponseEntity<>("client" + client.toString(), HttpStatus.OK);

	}

//Retrieving all clients from the database
	@GetMapping("/clients")
	public List<Client> retrieveAllClients() {
		return clientRepo.findAll();

	}

	@PostMapping("/client/funds")
	public ResponseEntity<String> withdrawFunds(@RequestBody Transaction transaction) {

		String sql = " ";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			Statement stmt = con.createStatement();

			// ADDED

			Optional<Client> client = clientRepo.findById(Long.valueOf(transaction.getUserID()));
			if (client.isEmpty()) {

				return new ResponseEntity<>("Client not found", HttpStatus.CONFLICT);

			} else {

				if (transaction.isAction() == false) {

					sql = "update funds set money= money+ " + transaction.getCurrency() + " where fundsid= "
							+ transaction.getUserID() + "";
				} else {

					sql = "update funds set money= money- " + transaction.getCurrency() + " where fundsid = "
							+ transaction.getUserID() + " ";

				}

				String cql = "Select * from funds where fundsid=" + transaction.getUserID() + "";

				ResultSet rs = stmt.executeQuery(cql);

				if (rs.next() == true) {

					do {
						int fundsID = rs.getInt("fundsid");
						int money = rs.getInt("money");

						if (transaction.isAction() == false) {

							sql = "update funds set money= money+ " + transaction.getCurrency() + " where fundsid= "
									+ transaction.getUserID() + "";

						} else

						{
							if (money < transaction.getCurrency()) {

								return new ResponseEntity<>("not enough funds", HttpStatus.NOT_ACCEPTABLE);
							}
						}
					} while (rs.next());

				}
				stmt.execute(sql);
				debitCredit(transaction.getUserID(), transaction.getCurrency(), transaction.isAction());
				stmt.execute(cql);

			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	public void debitCredit(int userID, int currency, boolean action) {
		String sql = " ";
		// ADDED
		Statement stmt = null;

		DateTimeFormatter dateString = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			if (action == false) {

				sql = "INSERT INTO operations (userid, action, debit,date) VALUES (" + userID + ", " + action + ", "
						+ currency + ",'" + dateString.format(now) + " ')";

			} else {
				sql = "INSERT INTO operations (userid, action, credit, date) VALUES (" + userID + ", " + action + ", "
						+ currency + ",'" + dateString.format(now) + "')";
				;

//				

//				
			}

			stmt.execute(sql);

		} catch (Exception e) {
			System.out.println(e);

		}

	}

////Creare un api GET dove passando come parametro l'id del cliente mi ritorna tutta la lista delle operazioni
	@GetMapping("/operations/{userID}")
	public ResponseEntity<Object> listOfOperations(@PathVariable int userID) {
		// List<Operations> ope = new ArrayList<Operations>();
		Operations ope = new Operations();
		List<Object> list = new ArrayList<>();

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			Statement stmt = con.createStatement();

			String sql = "Select credit,debit from operations where userid='" + userID + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next() == false)

				return new ResponseEntity<>("rs empty", HttpStatus.NO_CONTENT);

			else {
				do {
					int debit = rs.getInt("debit");
					int credit = rs.getInt("credit");

					ope = new Operations(debit, credit);

					list.add(ope);

				} while (rs.next());

				return new ResponseEntity<>("Here are all the operations:" + " " + list + "", HttpStatus.OK);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

//Sviluppare un api che permette la modifica delle informazioni del cliente... esempio modifica del telefono, indirizzo...

	@PutMapping("/client/{id}")
	public String updateClient(@PathVariable long id, @RequestBody Client client) {
		Client updatedClient = clientRepo.findById(id).get();
		updatedClient.setPhone(client.getPhone());
		updatedClient.setCity(client.getCity());
		updatedClient.setPassword(client.getPassword());
		updatedClient.setEmail(client.getEmail());
		clientRepo.save(updatedClient);
		return "Updated...";
	}

//sviluppare un api che inserito l'id del cliente cancella il cliente il relativo fondo e le sue operazioni

//	@DeleteMapping("/client/funds/operations/{id}")
//	public String deleteClient(@PathVariable long id) {
//		Optional<Client> deleteClient = clientRepo.findById(id);
//		if (deleteClient.isEmpty()) {
//			throw new RuntimeException("Client not found");
//		}
//
//		clientRepo.delete(deleteClient.get());
//		return "Deleted client with the id:  " + id;
//	}

	@DeleteMapping("/client/funds/operations/{id}")
	public String deleteClient(@PathVariable long id) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			Statement stmt = con.createStatement();

			String sql = "DELETE client, funds, operations FROM client INNER JOIN funds INNER JOIN operations WHERE client.userid = "
					+ id + " AND funds.fundsid = client.userid AND operations.userid = client.userid";
			stmt.execute(sql);

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

//creare un api molto simile a quella dei funds, che simula un bonifico, quindi come parametro dell'api ci sara un oggetto che contiene il bonifico

	@PostMapping("/client/transfers")
	public ResponseEntity<String> transferFunds(@RequestBody Transaction transaction) {
		String cql = "";
		String sql = " ";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			Statement stmt = con.createStatement();

			Optional<Client> client = clientRepo.findById(Long.valueOf(transaction.getUserID()));
			if (client.isEmpty()) {

				return new ResponseEntity<>("Client not found", HttpStatus.CONFLICT);

			} else {
//			controllare se i soldi del bonifico sono minori del denaro su funds	

				// update funds
				// insert delle variabili di transfer in operations

				sql = "Select * from funds where fundsid=" + transaction.getUserID() + "";

				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next() == true) {

					do {
						int fundsID = rs.getInt("fundsid");
						int money = rs.getInt("money");

						if (money > transaction.getCurrency()) {

							sql = "update funds set money= money- " + transaction.getCurrency() + " where fundsid = "
									+ transaction.getUserID() + " ";

							cql = "INSERT INTO operations (userid, action, transfers) VALUES ("
									+ transaction.getUserID() + ", " + transaction.isAction() + ", "
									+ transaction.getCurrency() + ")";

						}

					} while (rs.next());

				}
				stmt.execute(sql);
				stmt.execute(cql);
				// debitCredit(transaction.getUserID(), transaction.getCurrency(),
				// transaction.isAction());

			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

//An api where you  pass a date and a user id and gives you all transactions between that date and today

	@PostMapping("/transactions")
 

	public ResponseEntity<Object> listOfTransactionsFromaGivenDateToNow(@RequestBody SecondDto secondDto) throws SQLException {

		DateTimeFormatter dateString = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();

		Operations operations = new Operations();
		List<Object> listOfTransactions = new ArrayList<>();
		Client client=new Client();
		// SecondDto =new SecondDto();
		
		
		Connection con = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		Date dbDate = null;
		
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(
					"jdbc:mysql://ec2-44-242-158-192.us-west-2.compute.amazonaws.com:3306/esercitazione_jalloh",
					"jalloh", "Particle123!");
			 stmt = con.createStatement();

			 sql = "Select credit,debit,date from operations where userid='" + secondDto.getUserID() +  "'and  date>= '" + secondDto.getDate() + "'";

			 rs = stmt.executeQuery(sql);

			if (rs.next() == false)

				return new ResponseEntity<>("Data does not exist", HttpStatus.NO_CONTENT);

			else {
				do {
					int debit = rs.getInt("debit");
					int credit = rs.getInt("credit");
					 dbDate = rs.getDate("date");

					operations = new Operations(debit, credit, dbDate);

					listOfTransactions.add(operations);
					
					
				
			

				} while (rs.next());

				return new ResponseEntity<>(" The transactions related to the requested client between "
						+ secondDto.getDate() + " and "  +date+ " are:" + " " + listOfTransactions.toString() + "",
						HttpStatus.OK);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
		

	}
}
