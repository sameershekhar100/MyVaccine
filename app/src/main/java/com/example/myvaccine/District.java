package com.example.myvaccine;

public class District {
    String disName;
    String disID;
    public District(String disName, String disID){
        this.disID=disID;
        this.disName=disName;
    }

    public String getDisID() {
        return disID;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisID(String disID) {
        this.disID = disID;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }
}
