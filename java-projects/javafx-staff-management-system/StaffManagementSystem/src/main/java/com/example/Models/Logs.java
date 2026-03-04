package com.example.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final String time;

    private final String name;
    private final String surname;
    private final String departmentName;
    private final String departmentRole;

    private final String message;

    public Logs(Staff staff, String message)
    {
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        this.name = staff.getPerson().getName();
        this.surname = staff.getPerson().getSurname();
        this.departmentName = staff.getDepartment().getName();
        this.departmentRole = staff.getDepartment().getRole();

        this.message = message;
    }

    public String getTime() { return time; }
    
    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getDepartmentName() { return departmentName; }

    public String getDepartmentRole() { return departmentRole; }

    public String getMessage() { return message; }

    

    public String display()
    {
        return time + " " + name + " " + surname + " [" + departmentName + "/" + departmentRole + "] " + message;
    }

    @Override
    public String toString() { return display(); }

}
