package com.example.stas.myapplication;

import java.util.ArrayList;

public class JsonTest {
    public ArrayList<JsonTestInnerArr> Android;

    public JsonTest(ArrayList<JsonTestInnerArr> android) {
        Android = android;
    }

    public JsonTest() {
    }

    public ArrayList<JsonTestInnerArr> getAndroid() {
        return Android;
    }

    public void setAndroid(ArrayList<JsonTestInnerArr> android) {
        Android = android;
    }
}

class JsonTestInnerArr {
    public String name;

    public JsonTestInnerArr() {
    }

    public String number;
    public String date_added;

    public String getName() {
        return name;
    }

    public JsonTestInnerArr(String name, String number, String date_added) {
        this.name = name;
        this.number = number;
        this.date_added = date_added;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}
