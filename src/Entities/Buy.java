package Entities;

public class Buy {
    private String fname,flastname,pname,mname,maddress,amount,creditcard;

    public Buy(String[] values){
        fname=values[0].trim();
        flastname=values[1].trim();
        pname=values[2].trim();
        mname=values[3].trim();
        maddress=values[4].trim();
        amount=values[5].trim();
        creditcard=values[6].trim();
    }

    public String getCreditMarket(){
        return "('"+mname+"','"+creditcard+"')";
    }
    public String getBuysTable(){
        return "('"+fname+"','"+flastname+"','"+pname+"','"+maddress+"',"+amount+",'"+creditcard+"')";
    }

}
