package com.example.networking;

public class Mountain {
    private String name;
    private String location;
    private int height;

    public Mountain(String inName, String inLocation, int inHeight){
        name = inName;
        location = inLocation;
        height = inHeight;
    }

    @Override
    public String toString(){
        return name;
    }
}
