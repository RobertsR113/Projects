package com.example.Models;

import java.io.Serializable;
import java.util.UUID;

public class Staff implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final Person person;
    private final Department department;

    public Staff(Person person, Department department)
    {
        this.person = person;
        this.department = department;
    }

    public Person getPerson() { return person; }

    public Department getDepartment() { return department; }

    public String getName() { return person.getName(); }
    public String getSurname() { return person.getSurname(); }
    public String getPassword() { return person.getPassword(); }
    public String getEmail() { return person.getEmail(); }
    public UUID getUuid() { return person.getUuid(); }

    public String getDepartmentName() { return department.getName(); }
    public String getRole() { return department.getRole(); }
    public String getWage() { return department.getWage(); }
}