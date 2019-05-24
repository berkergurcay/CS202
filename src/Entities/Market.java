package Entities;

public class Market extends Entity {
    private String mname,address,zip,city,phone,budget;

    public Market(String[] values){
        this.mname=values[0].trim();
        this.address=values[1].trim();
        this.zip = values[2].trim();
        this.city = values[3].trim();
        this.phone = values[4].trim();
        this.budget = values[5].trim();
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
