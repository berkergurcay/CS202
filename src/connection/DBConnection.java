package connection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    private Connection conn = null;


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

    public void insertData(String table, String values) throws SQLException {

        String sql;
        Statement statement = conn.createStatement();

        if (!values.contains("&")) {

            sql = "INSERT INTO " + table + " VALUES" + values;
            System.out.println(sql);

            statement.executeUpdate(sql);

        }else{
            String[] splitOfFields = values.split("&");

            for (String fields:splitOfFields){
                sql = "INSERT INTO " + table + " VALUES" + fields;
                System.out.println(sql);
                statement.executeUpdate(sql);

            }
        }




    }


    /*public String[] csvReader(String filepath){
        String csvfile = filepath;
        BufferedReader br = null;
        String line ="";
        String firstLine="";
        String cvssplitby=";";
        String[] result =null;
        try {
            br = new BufferedReader(new FileReader(csvfile));
            firstLine = br.readLine();
            int numberOfFields = firstLine.split(";").length;

            while ((line=br.readLine())!=null){
                String[] farmers = line.split(cvssplitby);
                for (int i=0;i<numberOfFields;i++) {
                    System.out.print(farmers[i]+" ");
                }
                System.out.println();
                farmers = result;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
   */

}
