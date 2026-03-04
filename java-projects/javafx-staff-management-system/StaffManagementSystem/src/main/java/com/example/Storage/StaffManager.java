package com.example.Storage;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.Models.Staff;

public class StaffManager implements Serializable
{
    public static StaffManager manager = new StaffManager();
    private ArrayList<Staff> staffList;
    private Staff loggedInStaff;

    public Staff authenticate(String email)
    {
        for (Staff staff : staffList)
        {
            if (email.equals(staff.getPerson().getEmail()))
                return staff;
        }
        return null;
    } 

    public boolean exists(Staff staff)
    {
        if (staffList == null)
            return false;
        
        return staffList.stream().anyMatch
        (
            match ->
            match.getPerson().getName().equals(staff.getPerson().getName()) &&
            match.getPerson().getSurname().equals(staff.getPerson().getSurname()) &&
            match.getPerson().getEmail().equals(staff.getPerson().getEmail())
        );
    }

    public void setLoggedInStaff(Staff staff)
    {
        this.loggedInStaff = staff;
    }
    public Staff getLoggedInStaff()
    {
        return loggedInStaff;
    }

    public ArrayList<Staff> getStaffList()
    {
        if (staffList == null)
            return staffList = new ArrayList<>();
        
        return staffList;
    }
    public void setStaffList(ArrayList<Staff> staff) 
    {
        staffList = staff;
    }

    public void addNew(Staff staff)
    {
        if (staffList == null)
            staffList = new ArrayList<>();

        staffList.add(staff);
        
        System.out.println("\n--------------------------------------\n" + 
            "Added User:" + "\n" +
            staff.getPerson().getUuid() + "\n" +
            staff.getPerson().getName() + " " + staff.getPerson().getSurname() + " " + staff.getPerson().getEmail() + "\n" +
            staff.getDepartment().getName() + " " + staff.getDepartment().getRole() + " " + staff.getDepartment().getWage() + "\n" +
            "--------------------------------------\n");
    }

    public void remove(Staff staff)
    {
        staffList.remove(staff);

        System.out.println("\n--------------------------------------\n" + 
            "Removed User:" + "\n" +
            staff.getPerson().getUuid() + "\n" +
            staff.getPerson().getName() + " " + staff.getPerson().getSurname() + " " + staff.getPerson().getEmail() + "\n" +
            "--------------------------------------\n");
    }   
}
