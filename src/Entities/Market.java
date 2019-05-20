package Entities;

public class Market {
    private String mname,address,zip,city,phone,budget;

    public Market(String[] values){
        this.mname=values[0];
        this.address=values[1];
        this.zip = values[2];
        this.city = values[3];
        this.phone = values[4];
        this.budget = values[5];
    }

    public String getMarketTable() {
        return "('"+mname+"','"+address+"','"+phone+"',"+budget+")";
    }
    public String getZiptocity(){
        return "('"+zip+"','"+city+"')";
    }
    public String getAddresstozip(){
        return "('"+address+"','"+zip+"')";
    }
}
