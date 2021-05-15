package com.example.myvaccine;

public class Vaccine {
    String hosName;
    String hosAddress;
    String vaccine;
    String date;
    String amount;
    String slot;

    public  Vaccine(String hosName,String hosAddress, String vaccine,String date,String amount,String slot){
        this.date=date;
        this.amount=amount;
        this.hosAddress=hosAddress;
        this.hosName=hosName;
        this.vaccine=vaccine;
        this.slot=slot;
    }

    public String getHosAddress() {
        return hosAddress;
    }

    public String getHosName() {
        return hosName;
    }

    public String getVaccine() {
        return vaccine;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getSlot() {
        return slot;
    }
}
