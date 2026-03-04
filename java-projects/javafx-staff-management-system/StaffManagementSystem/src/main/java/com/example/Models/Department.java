package com.example.Models;

import java.io.Serializable;

public class Department implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String role;
    private String wage;

    public Department(String name, String role, String wage)
    {
        this.name = name;
        this.role = role;
        this.wage = wage;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getWage() { return wage; }
    public void setWage(String wage) { this.wage = wage; }
}
