package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import connection.DBConnection;



public class Main {
    static final String USER = "root";
    static final String PASS = "19981998Bg";
	public static void instantiateJDBC() throws SQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String get_data_from_commands(String second_command) {
		if (second_command==null) {
			System.err.println("Wrong command!");
			usage();
			return null;
		}
		int start = second_command.lastIndexOf('(');
		int end = second_command.lastIndexOf(')');
		
		if (start == -1 || end == -1) {
			System.err.println("Wrong command!");
			usage();
			return null;
		}
		
		return second_command.substring(start+1, end);
	}
	
	public static void parse_commands(String buffer,DBConnection connection) throws SQLException{
		if (buffer == null) {
			System.err.println("There is nothing to parse! Something is wrong in the commands.");
			return;
		}
		
		// First split by empty space
		String[] subCommands = buffer.split(" ");
		
		if (subCommands.length<2) {
			System.err.println("Wrong command!");
			usage();
			return;
		}
		
		String first_part = subCommands[0];
		// Handle any possible space between ADD|REGISTER FARMER (...)
		String second_part = subCommands.length!=2? subCommands[1]+subCommands[2]:
			subCommands[1];

		if (first_part.equalsIgnoreCase("show")){
			if(second_part.equalsIgnoreCase("tables")) {
				connection.getDatabaseMetaData();
			} else {
				System.err.println("Wrong command!");
				usage();
			}
			
		} else if (first_part.equalsIgnoreCase("load")){
			if(second_part.equalsIgnoreCase("data")) {
				// TODO load data
			} else {
				System.err.println("Wrong command!");
				usage();
			}
			
		} else if (first_part.equalsIgnoreCase("query")){
			long query_number = Integer.parseInt(second_part);
			// TODO Run query $query_number
			
		} else if (first_part.equalsIgnoreCase("add")){
			if(second_part.startsWith("FARMERS") || second_part.startsWith("farmers")) {
				// TODO add farmers (DO transaction, end with either commit or abort)
				String data = get_data_from_commands(second_part);
				// parse data and insert rows
			} else if(second_part.startsWith("FARMER") || second_part.startsWith("farmer")) {
				// TODO add farmer
			} else if(second_part.startsWith("PRODUCTS") || second_part.startsWith("products")) {
				// TODO add products (DO transaction, end with either commit or abort)
			} else if(second_part.startsWith("PRODUCT") || second_part.startsWith("product")) {
				// TODO add product
			} else if(second_part.startsWith("MARKETS") || second_part.startsWith("markets")) {
				// TODO add markets (DO transaction, end with either commit or abort)
			} else if(second_part.startsWith("MARKET") || second_part.startsWith("market")) {
				// TODO add market
			} else {
				System.err.println("Wrong command!");
				usage();
			}
			
		} else if (first_part.equalsIgnoreCase("register")){
			if(second_part.startsWith("PRODUCTs") || second_part.startsWith("products")) {
				// TODO register products (DO transaction, end with either commit or abort)
			} else if(second_part.startsWith("PRODUCT") || second_part.startsWith("product")) {
				// TODO register product 
			} else {
				System.err.println("Wrong command!");
				usage();
			}
		} else {
			System.err.println("Wrong command!");
			usage();
		}
	}
	
	public static void usage() {
		System.out.println("Supported Commands: SHOW TABLES | LOAD DATA | QUERY # | ADD FARMER(...) |"
				+ " ADD PRODUCT(...) | ADD MARKET() | REGISTER PRODUCT(...) | ADD FARMERs(...) |"
				+ " ADD PRODUCTs(...) | ADD MARKETs() | REGISTER PRODUCTs(...)");
	}
	
	public static void main_loop(DBConnection database_connection) throws SQLException {
		
		System.out.println("Command line interface is initiated");
		usage();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String buffer = null;
		try {
			while(true) {
				buffer = reader.readLine();
				if(buffer.equalsIgnoreCase("exit") || buffer.equalsIgnoreCase("quit")){
					// we are done 
					System.out.println("Command line interface is closed.");
					return;
				} else {
					parse_commands(buffer,database_connection);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws SQLException {
		DBConnection database_connection = null;

		
		instantiateJDBC();
		
		if(args.length < 4) {
			System.err.println("Wrong number of arguments!");
			System.err.println("Usage: Main hostname database username password");
		}
		
		String url = "jdbc:mysql://localhost:3306/homework2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		
		try {
			database_connection = new DBConnection(url,USER,PASS);
			
			main_loop(database_connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(database_connection != null){
				database_connection.close();
			}
		}
		

	}

}
