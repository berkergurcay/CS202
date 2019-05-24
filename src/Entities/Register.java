package Entities;

public class Register {
    private String fname,flastname,pname,amount,price,IBAN;

    public Register(String[] values){
        fname=values[0].trim();
        flastname=values[1].trim();
        pname=values[2].trim();
        amount=values[3].trim();
        price=values.length==7? values[4].trim()+","+values[5]:values[4].trim();
        IBAN =values.length==7? values[6].trim():values[5].trim();
    }

    public String getRegisterTable(){
        return "('"+fname+"','"+flastname+"','"+pname+"',"+amount+",'"+price+"','"+IBAN+"')";
    }

}
