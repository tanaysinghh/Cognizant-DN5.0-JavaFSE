package com.cognizant.springlearn.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Skill {

    @NotNull
    @Min(1)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

    public Skill() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
