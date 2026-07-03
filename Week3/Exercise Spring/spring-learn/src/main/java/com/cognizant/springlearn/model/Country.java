package com.cognizant.springlearn.model;

// Simple bean whose values are supplied from an XML configuration file.
public class Country {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + "]";
    }
}
