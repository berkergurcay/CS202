package Entities;

public class Product {
    private String pname,pdate,hdate,alt,mintemp,hardness;

    public Product(String[] values){
        this.pname=values[0].trim();
        this.pdate=values[1].trim();
        this.hdate=values[2].trim();
        this.alt=values[3].trim();
        this.mintemp=values[4].trim();
        this.hardness=values[5].trim();
    }

    public String getPdateTable(){
        return "('"+pdate+"','"+hdate+"')";
    }
    public String getAltitudeTable(){
        return "('"+alt+"','"+mintemp+"')";
    }
    public String getProductTable(){
        return "('"+pname+"','"+pdate+"','"+alt+"','"+hardness+"')";
    }
    public String insertToAltitude(String values){

        return "INSERT INTO altitude(alt,mintemp) VALUES" + values
                + "ON DUPLICATE KEY UPDATE alt='" + this.alt + "',mintemp='" + this.mintemp+"'";

    }
    public String insertToPDate(String values){

          return "INSERT INTO pdate(pdate,hdate) VALUES" + values
                + "ON DUPLICATE KEY UPDATE pdate='" + this.pdate + "',hdate='" + this.hdate+"'";

    }


}
