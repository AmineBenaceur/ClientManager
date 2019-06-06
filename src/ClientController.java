import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;


/**
* Provides data fields and methods to access and store, fetch and modify client information from a database for a client Management software.
* functionalities include displaying clients, searching for clients, deleting and modifying clients.
* @author A.Benaceur
* @version 2.0
* @since March 21st, 2018
*/
public class ClientController {
	/*
	 * clients view GUI to print to.
	 */
	public static ClientView view;
	/*
	 * connection to the database.
	 */
	public  static  Connection jdbc_connection;
	/*
	 * statement to execute commands.
	 */
	public static  Statement statement;
	/*
	 * Database storage info.
	 */
	public static  String databaseName = "ClientDB", tableName = "clienttable";
	/*
	 * Database access info.
	 */
	public  static String connectionInfo = "jdbc:mysql://localhost:3306/",  
				  login          = "root",
				  password       = "root";
	
	/*
	 * constructs a clientController object.. 
	 * @param view the client management GUI to manage.
	 */
	public ClientController(ClientView view ) {
		this.view = view;
		try{
			// If this throws an error, make sure you have added the mySQL connector JAR to the project
			Class.forName("com.mysql.jdbc.Driver");
			
			// If this fails make sure your connectionInfo and login/password are correct
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	
		createDB();
		selectDB();
		createTable();
		importFromFile("clients.txt");
	}
	/*
	 *Creates database to store the clients.
	 */
	public void createDB()
	{
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + databaseName);
			System.out.println("Created Database " + databaseName);
		} 
		catch( SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 *Creates a table in database to store the clients.
	 */
	public void createTable()
	{
		String sql = "CREATE TABLE " + tableName + "(" +
				     "FIRSTNAME VARCHAR(20)NOT NULL, " +
				     "LASTNAME VARCHAR(20) NOT NULL, " + 
				     "ADRESS VARCHAR(50) NOT NULL, " + 
				     "PHONENUMBER CHAR(12) NOT NULL, " + 
				     "POSTALCODE CHAR(7) NOT NULL, " +  
				     "ID VARCHAR(12) NOT NULL, " + 
				     "ISRESIDENTIAL INT(1) NOT NULL, " + 
				     "PRIMARY KEY ( id ))";
		try{
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Created Table " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 *specifies the database to operate in.
	 */
	public void selectDB() 
	{
		String sql = "USE " + databaseName;
		try{
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Wokring in: " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}


	public static void printClients()
	{
		try {
			String sql = "SELECT * FROM " + tableName;
			statement = jdbc_connection.createStatement();
			ResultSet clients = statement.executeQuery(sql);
			System.out.println("Clients:");
			while(clients.next())
			{
				String fullName = clients.getString("FIRSTNAME") + " " + clients.getString("LASTNAME");
				String type = "";
				int res = clients.getInt("ISRESIDENTIAL");
				if ( res == 0)
					type = "COMMERCIAL";
				if ( res == 1) 
					type = "RESIDENTIAL";
				
				
				view.screen.append( String.format("%-30s", clients.getString("ID")) + " " +  String.format("%-30s", fullName) + " " + 
								   clients.getString("PHONENUMBER") + "  " + String.format("%-30s", type ) +"\n");
			}
			clients.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 *imports information about clients from a txt file and constructs client objects out of it to store into db.
	 */
	public void importFromFile( String fileName ) {
		try {	
			File file = new File (fileName);
			Scanner scan = new Scanner(file);
			String line = "";
			
			while(scan.hasNext()) {
				line = scan.nextLine();
				String[] parts = line.split(";");
				
				int type;
				if (parts[5].equalsIgnoreCase("RESIDENTIAL"))
					type = 0;
				else
					type = 1;
				
				Client temp = new Client(parts[0].trim() ,parts[1].trim(),parts[2].trim(), parts[4].trim() ,parts[3].trim(), Integer.toString(getRandomID()),type);
				System.out.println("POSTAL:" + ""+parts[3]);
				addClientToDB(temp);
			}
			scan.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	
	/*
	 *Creates a unique 8 digit id.
	 *@returns: unique id
	 */
	public int getRandomID() {
			Random ran = new Random();
			int num = ran.nextInt(9999999) + 1000000;
			return num;
		}
	/*
	 * searches the data base for a client by his/her id, name or phone.
	 * @param by which criteria to search for either "name", "id" or "phone" are acceptable.
	 * @param input, the information to match.
	 * @returns Client that is matching information, or null if nothing matching found.
	 */
	public static Client searchDB(String by, String input) {

		String sql = "";
		if ( by.equalsIgnoreCase("name")) {
			 sql = "SELECT * FROM  "+ tableName +"  WHERE FIRSTNAME= ? " ;
		}else if( by.equalsIgnoreCase("id")) {
			sql = "SELECT * FROM  "+ tableName +"  WHERE ID= ? " ;
		}else if( by.equalsIgnoreCase("phone")) {
			 sql = "SELECT * FROM  "+ tableName +"  WHERE PHONENUMBER= ? " ;
		}else {
			return null;
		}
			Client found=null;
		ResultSet client = null;
		try {
			PreparedStatement ps = jdbc_connection.prepareStatement(sql);
			
			ps.setString(1, input.trim() );
			client = ps.executeQuery();
			if(client.next())
			{
				found =  new Client(client.getString("FIRSTNAME"),
								client.getString("LASTNAME"), 
								client.getString("ADRESS"), 
								client.getString("PHONENUMBER"), 
								client.getString("POSTALCODE"), 
								client.getString("ID"), 
								client.getInt("ISRESIDENTIAL"));
			}
		
		} catch (SQLException k) { k.printStackTrace(); }
		
		
		return found;
	}
	/*
	 * adds a client to the database.
	 * @param client object to add to database.
	 */
	public static void addClientToDB( Client customer) {


		
		String sql = "INSERT INTO " + tableName + " VALUES ( ?, ?, ?, ?, ?, ?, ?);";
		try{
				PreparedStatement ps = jdbc_connection.prepareStatement(sql);
				int i=1;
				if (customer.isResidential)
					i = 0;
				
			ps.setString(1, customer.firstName);
			ps.setString(2, customer.lastName);
			ps.setString(3, customer.adress);
			ps.setString(4,customer.phoneNumber );
			ps.setString(5, customer.postalCode);
			ps.setString(6, customer.id);
			ps.setInt(7, i);
			
			ps.executeUpdate();
		
		}
		
		
		catch(SQLException k)
		{
			k.printStackTrace();
		}
		
		
		
	}
	/*
	 * deletes a client from the database.
	 * @param client object to delete.
	 */
	public static void deleteClientFromDB( Client gone ) {
		String sql = "DELETE * FROM  "+ tableName +"  WHERE ID= ? " ;
		try{
			PreparedStatement ps = jdbc_connection.prepareStatement(sql);
			ps.setString(1, gone.id );
			ps.executeUpdate();
	
	}
	
	
	catch(SQLException k)
	{
		k.printStackTrace();
	}
	
	
		
	}
}
