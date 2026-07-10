package com.cognizant.springlearn.model;

public class Skill {

    private Integer id;
    private String name;

    public Skill() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return "Skill{id=" + id + ", name='" + name + "'}"; }
}
