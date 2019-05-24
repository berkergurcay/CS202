package Entities;

public class Produces {
    private String fname,flastname,pname,amount,year;

    public Produces(String[] values){
        fname=values[0].trim();
        flastname=values[1].trim();
        pname=values[2].trim();
        amount=values[3].trim();
        year=values[4].trim();
    }

    public String getProducesTable(){
        return "('"+fname+"','"+flastname+"','"+pname+"',"+amount+",'"+year+"')";
    }
}
