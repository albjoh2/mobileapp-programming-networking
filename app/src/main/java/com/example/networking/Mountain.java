package com.example.networking;

import androidx.annotation.NonNull;

public class Mountain {
    private final String name;
    private final String location;

    public Mountain(String inName, String inLocation){
        name = inName;
        location = inLocation;
    }

    public String info(){
        return name + " is located in " + location;
    }

    @NonNull
    @Override
    public String toString(){
        return name;
    }
}
