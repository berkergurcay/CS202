package Entities;



public abstract class Entity {


    public String insertToZiptocity(String values){
        String[] fields = getValues(values).split(",");
        return  "INSERT INTO ziptocity(zipcode,city) VALUES" + values
                + " ON DUPLICATE KEY UPDATE zipcode=" + fields[0] + ",city=" + fields[1];

    }
    private String getValues(String values) {
        int start = values.indexOf("(");
        int end = values.indexOf(")");

        return values.substring(start + 1, end);
    }
    public String insertToAddrtozip(String values) {
        String[] fields = getValues(values).split(",");
        return  "INSERT INTO addrtozip(address,zipcode) VALUES" + values
                + "ON DUPLICATE KEY UPDATE address=" + fields[0] + ",zipcode=" + fields[1];

    }



}
