package Entities;

public class Farmer {
    private String name,lastname,address,zipcode,city,phones,email;



    public Farmer(String[] values){
        this.name = values[0];
        this.lastname = values[1];
        this.address = values[2];
        this.zipcode = values[3];
        this.city = values[4];
        this.phones = values[5];
        this.email = values[6];

    }

    public String getAddrtozip() {
        return "('"+address+"','"+zipcode+"')";
    }
    public String getFarmerTable(){
        return "('"+name+"',"+"'"+lastname+"',"+"'"+address+"')";
    }
    public String getZiptocity(){
        return "('"+zipcode+"','"+city+"')";

    }
    public String getPhoneTable(){
        String[] splittedPhones;
        if (phones.contains("|")) {
            phones= phones.replace("|",":");
            splittedPhones = phones.split(":");
            int numberOfPhones = splittedPhones.length;
            String insertFormOfPhones = "('";
            for (int ii=0;ii<numberOfPhones;ii++){
                if(ii==numberOfPhones-1){
                    insertFormOfPhones+=splittedPhones[ii]+"','"+name+"','"+lastname+"')";
                }else
                    insertFormOfPhones+=splittedPhones[ii]+"','"+name+"','"+lastname+"')&('";

            }

            return insertFormOfPhones;
        }else
            return "('"+phones+"','"+name+"','"+lastname+"')";

    }
    public String  getEmailTable() {
        String[] splittedEmails;
        if (email.contains("|")){
            email=email.replace("|",":");
            splittedEmails = email.split(":");
            int numberOfEmails = splittedEmails.length;
            String insertFormOfEmails = "('";
            for (int ii=0;ii<numberOfEmails;ii++){
                if(ii==numberOfEmails-1) {
                    insertFormOfEmails += splittedEmails[ii] +"','"+name+"','"+lastname+"')'";
                }else
                    insertFormOfEmails += splittedEmails[ii]+"','"+name+"','"+lastname+"')&('";

            }

            return insertFormOfEmails;
        }else
            return "('"+email+"','"+name+"','"+lastname+"')";
    }

}