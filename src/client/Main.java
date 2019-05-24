package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.SQLException;

import connection.DBConnection;



public class Main {

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
        String second_part =subCommands[1];
		// Handle any possible space between ADD|REGISTER FARMER (...)
        if(subCommands.length>2) {
            for (int ii = 2; ii < subCommands.length; ii++) {
                second_part+=" "+subCommands[ii];
            }
	    }

		if (first_part.equalsIgnoreCase("show")){
			if(second_part.equalsIgnoreCase("tables")) {
				connection.getDatabaseMetaData();


			} else {
				System.err.println("Wrong command!");
				usage();
			}
			
		} else if (first_part.equalsIgnoreCase("load")){
			if(second_part.equalsIgnoreCase("data")) {
				boolean farmerCommit = connection.csvAdder("farmers.csv");
				boolean marketCommit = connection.csvAdder("markets.csv");
                boolean productCommit = connection.csvAdder("products.csv");
				boolean registerCommit = connection.csvAdder("registers.csv");
				boolean buysCommit = connection.csvAdder("buys.csv");
				boolean producesCommit = connection.csvAdder("produces.csv");

                if(farmerCommit && marketCommit && productCommit && buysCommit && registerCommit && producesCommit ) {
                    connection.commitTransaction();
                    System.out.println("Values are successfully loaded");

                }else{
                    System.err.println("Values cannot be inserted");
                    connection.abort();
                }




			} else {
				System.err.println("Wrong command!");
				usage();
			}
			
		} else if (first_part.equalsIgnoreCase("query")){
			long query_number = Integer.parseInt(second_part);
			// TODO Run query $query_number
			
		} else if (first_part.equalsIgnoreCase("add")){
			if(second_part.startsWith("FARMERS") || second_part.startsWith("farmers")) {
				String[] farmers = second_part.split(":");
				boolean isCommitable=false;
				for (String farmer : farmers) {
				    String dataOfOneFarmer = get_data_from_commands(farmer);
				    isCommitable= connection.insertFarmer(dataOfOneFarmer);
				    if(!isCommitable){
				        connection.abort();
				        System.err.println("Farmers cannot be inserted");
				        break;
				    }
				}
				if (isCommitable) {
                    connection.commitTransaction();
                    System.out.println("Farmers are successfully inserted");
                }
			} else if(second_part.startsWith("FARMER") || second_part.startsWith("farmer")) {

					String dataOfFarmer = get_data_from_commands(second_part);
					boolean isCommitable = connection.insertFarmer(dataOfFarmer);
					if(isCommitable) {
                        connection.commitTransaction();
                        System.out.println("Farmer is successfully inserted");
                    }else{
					    connection.abort();
                        System.err.println("Farmer cannot be inserted");
					}

			} else if(second_part.startsWith("PRODUCTS") || second_part.startsWith("products")) {
				String[] products = second_part.split(":");
				boolean isCommitable=false;
                for (String product:products) {
                    String dataOfOneProduct = get_data_from_commands(product);
                    isCommitable=connection.insertProduct(dataOfOneProduct);
                    if(!isCommitable){
                        connection.abort();
                        System.err.println("Products cannot be inserted");
                        break;
                    }
                }
                if(isCommitable) {
                    connection.commitTransaction();
                    System.out.println("Products are successfully inserted");
                }
			} else if(second_part.startsWith("PRODUCT") || second_part.startsWith("product")) {
				String dataOfProduct = get_data_from_commands(second_part);
				boolean isCommitable=connection.insertProduct(dataOfProduct);
				if(isCommitable) {
                    connection.commitTransaction();
                    System.out.println("Product is inserted successfully ");
                }else {
                    connection.abort();
                    System.err.println("Product cannot be inserted");
                }
			} else if(second_part.startsWith("MARKETS") || second_part.startsWith("markets")) {
				String[] markets =second_part.split(":");
                boolean isCommitable=false;
				for (String market:markets) {
                    String dataOfOneMarket=get_data_from_commands(market);
				    isCommitable=connection.insertMarket(dataOfOneMarket);
                    if(!isCommitable){
                        connection.abort();
                        System.err.println("Markets cannot be inserted");
                        break;
                    }
				}
                if(isCommitable){
                    connection.commitTransaction();
                    System.out.println("Markets are inserted successfully");
                }
			} else if(second_part.startsWith("MARKET") || second_part.startsWith("market")) {
				String dataOfMarket=get_data_from_commands(second_part);
				boolean isCommitable=connection.insertMarket(dataOfMarket);
			    if(isCommitable) {
                    connection.commitTransaction();
                    System.out.println("Market ");
                }else{
				    connection.abort();
                    System.err.println("Market cannot be inserted");
				}

			} else {
				System.err.println("Wrong command!");
				usage();
			}

		} else if (first_part.equalsIgnoreCase("register")){
			if(second_part.startsWith("PRODUCTs") || second_part.startsWith("products")) {
				String[] registers= second_part.split(":");
				boolean isCommitable=false;
				for(String register:registers){
				    String dataOfOneRegister=get_data_from_commands(register);
				    isCommitable=connection.insertRegister(get_data_from_commands(dataOfOneRegister));
				    if(!isCommitable){
				        connection.abort();
				        System.err.println("Products cannot be registered");
				        break;
                    }
				}
                if (isCommitable) {
                    connection.commitTransaction();
                    System.out.println("Products are registered successfully");
                }
			} else if(second_part.startsWith("PRODUCT") || second_part.startsWith("product")) {
				String dataOfRegister = get_data_from_commands(second_part);
			    boolean isCommitable=connection.insertRegister(dataOfRegister);
                if(isCommitable) {
                    connection.commitTransaction();
                    System.out.println("Product is registered successfully");
                }else{
					connection.abort();
					System.err.println("Product cannot be registered");
				}

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



		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String buffer;
		try {
			while(true) {
                usage();
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
		
		String url = "jdbc:mysql://"+args[0]+"/"+args[1]+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		
		try {
			database_connection = new DBConnection(url,args[2],args[3]);
            System.out.println("Command line interface is initiated");

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
