package Entities;

import connection.DBConnection;

import java.sql.SQLException;

public class Farmer extends Entity{
    private String name, lastname, address, zipcode, city, phones, email;


    public Farmer(String[] values) {
        this.name = values[0].trim();
        this.lastname = values[1].trim();
        this.address = values[2].trim();
        this.zipcode = values[3].trim();
        this.city = values[4].trim();
        this.phones = values[5].trim();
        this.email = values[6].trim();
    }

    public String getAddrtozip() {
        return "('" + address + "','" + zipcode + "')";
    }

    public String getFarmerTable() {
        return "('" + name + "'," + "'" + lastname + "'," + "'" + address + "')";
    }

    public String getZiptocity() {
        return "('" + zipcode + "','" + city + "')";

    }

    public String getPhoneTable() {
        String[] splittedPhones;
        phones = phones.replace(" ", "");
        if (phones.contains("|")) {
            phones = phones.replace("|", ":");
            splittedPhones = phones.split(":");
            int numberOfPhones = splittedPhones.length;
            String insertFormOfPhones = "('";
            for (int ii = 0; ii < numberOfPhones; ii++) {
                if (ii == numberOfPhones - 1) {
                    insertFormOfPhones += splittedPhones[ii] + "','" + name + "','" + lastname + "')";
                } else
                    insertFormOfPhones += splittedPhones[ii] + "','" + name + "','" + lastname + "')&('";
            }

            return insertFormOfPhones;
        } else
            return "('" + phones + "','" + name + "','" + lastname + "')";

    }

    public String getEmailTable() {
        email= email.replace(" ", "");
        String[] splittedEmails = null;
       if (email.contains("|")) {
            email = email.replace("|", ":");
            splittedEmails = email.split(":");

            String insertFormOfEmails = "('";
            for (int ii = 0; ii < splittedEmails.length; ii++) {
                if (ii == splittedEmails.length - 1) {
                    insertFormOfEmails += splittedEmails[ii] + "','" + name + "','" + lastname + "')";
                } else
                    insertFormOfEmails += splittedEmails[ii] + "','" + name + "','" + lastname + "')&('";

            }

            return insertFormOfEmails;
        } else
            return "('" + email + "','" + name + "','" + lastname + "')";
    }






}