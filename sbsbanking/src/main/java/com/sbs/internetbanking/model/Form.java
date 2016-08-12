package com.sbs.internetbanking.model;

import java.util.HashMap;
import java.util.Map;

public class Form {
    Map<String, String> map = new HashMap<String, String>();


    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
