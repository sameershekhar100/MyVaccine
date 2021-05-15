package com.example.myvaccine;

public class State {
private String name;
private String stateID;

public State(String name,String stateID){
    this.name=name;
    this.stateID=stateID;
}
    public void setName(String name) {
        this.name = name;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateID() {
        return stateID;
    }

    public String getName() {
        return name;
    }
}
