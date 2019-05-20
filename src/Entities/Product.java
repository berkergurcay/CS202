package Entities;

public class Product {
    private String pname,pdate,hdate,alt,mintemp,hardness;

    public Product(String[] values){
        this.pname=values[0];
        this.pdate=values[1];
        this.hdate=values[2];
        this.alt=values[3];
        this.mintemp=values[4];
        this.hardness=values[5];
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


}
