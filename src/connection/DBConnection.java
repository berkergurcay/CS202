package connection;

import Entities.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBConnection {

    private Connection conn;


    public DBConnection(String url, String username, String password) throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
    }
    public void commitTransaction() throws SQLException {
        conn.commit();

    }
    public void abort() throws SQLException{
        conn.rollback();
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }





    /**
     * A method to send select statement to the underlying DBMS (e.g., "select * from Table1")
     *
     * @param query_statement A query to run on the underlying DBMS
     * @return Resultset the query result.
     */
    public ResultSet send_query(String query_statement) {
            // Feel free to make them Class attributes
            Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query_statement);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
        return rs;

    }

    public void getDatabaseMetaData() throws SQLException {
        try {

            DatabaseMetaData dbmd = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables("homework2", null, "%", types);
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String table,String values) throws SQLException {

        String sql;
        Statement statement = conn.createStatement();

        if (!values.contains("&")) {

            sql = "INSERT INTO " + table + " VALUES" + values;


            statement.executeUpdate(sql);

        }else{
            String[] splitOfFields = values.split("&");

            for (String fields:splitOfFields){
                sql = "INSERT INTO " + table + " VALUES" + fields;

                statement.executeUpdate(sql);

            }
        }
    }

    public void insertData(String sql) throws SQLException{
        Statement statement = conn.createStatement();

        statement.executeUpdate(sql);
    }


    public boolean csvAdder(String filepath) throws SQLException {

        BufferedReader br = null;
        String line = "";

        boolean isCommitable=false;

        try {
            br = new BufferedReader(new FileReader(filepath));
            br.readLine();

            while ((line = br.readLine()) != null) {

                line = line.replace(";", ",");
                if(filepath.equals("farmers.csv")) {
                    isCommitable=insertFarmer(line);
                    if (!isCommitable) {

                        System.err.println("Values cannot be inserted");

                        abort();
                        return false;
                    }

                }

                else if(filepath.equals("markets.csv")){
                    isCommitable=insertMarket(line);
                    if(!isCommitable){

                            System.err.println("Values cannot be inserted");
                            abort();
                            return false;
                        }


                }
                else if(filepath.equals("products.csv")){
                    isCommitable=insertProduct(line);
                    if(!isCommitable){

                        System.err.println("Values cannot be inserted");
                        abort();
                        return false;
                    }
                }else if(filepath.equals("registers.csv")){
                    isCommitable=insertRegister(line);
                    if(!isCommitable){
                        System.err.println("Values cannot be inserted");

                        abort();
                        return false;
                    }
                }else if(filepath.equals("buys.csv")){
                    isCommitable=insertBuys(line);
                    if(!isCommitable){
                        System.err.println("Values cannot be inserted");

                        abort();
                        return false;
                    }
                }else if(filepath.equals("produces.csv")){
                    isCommitable=insertProduces(line);
                    if(!isCommitable){
                        System.err.println("Values cannot be inserted");

                        abort();
                        return false;
                    }

                }


            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isCommitable;

    }

    public boolean insertFarmer(String data){
        String[] values = data.split(",");
        Farmer farmer = new Farmer(values);

        try {


            insertData(farmer.insertToZiptocity(farmer.getZiptocity()));
            insertData(farmer.insertToAddrtozip(farmer.getAddrtozip()));
            insertData("farmer", farmer.getFarmerTable());
            insertData("phone", farmer.getPhoneTable());
            insertData("email", farmer.getEmailTable());

            return true;

        } catch (SQLException e) {
            return false;


        }

    }
    public boolean insertMarket(String data){

        String[] values = data.split(",");
        Market market=new Market(values);
        try {
            insertData(market.insertToZiptocity(market.getZiptocity()));
            insertData(market.insertToAddrtozip(market.getAddresstozip()));
            insertData("market",market.getMarketTable());

            return true;



        }catch (SQLException e){
           return false;
    }
    }
    public boolean insertProduct(String data){
        String[] values = data.split(",");
        Product product = new Product(values);
        try {
            insertData(product.insertToAltitude(product.getAltitudeTable()));
            insertData(product.insertToPDate(product.getPdateTable()));
            insertData("product",product.getProductTable());
            return true;
        }catch (SQLException e){
            return false;
        }

    }
    public boolean insertRegister(String data){
        String[] values = data.split(",");
        Register register = new Register(values);
        try {
            insertData("registers",register.getRegisterTable());
            return true;
        }catch (SQLException e){
            return false;
        }
    }
    public boolean insertBuys(String data){
        String[] values = data.split(",");
        Buy buy = new Buy(values);
        try {
            insertData("creditmarket", buy.getCreditMarket());
            insertData("buys",buy.getBuysTable());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean insertProduces(String data){
        String[] values = data.split(",");
        Produces produces = new Produces(values);
        try {
            insertData("produces",produces.getProducesTable());

            return true;
        }catch (SQLException e){
            return false;
        }
    }
    public boolean checkZipcode(String zipcode,String city) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT zipcode FROM ziptocity WHERE zipcode=? AND city=?");

            boolean alreadyInDatabase=false;

            ps.setString(1, zipcode);
            ps.setString(1, zipcode);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                alreadyInDatabase=true;
            }
            else{
                alreadyInDatabase=false;
            }
            rs.close();
            return alreadyInDatabase;

    }

}
