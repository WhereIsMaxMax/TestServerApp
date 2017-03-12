package com.whrsmxmx.maxim_adressbook.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Department_ {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Employees")
    @Expose
    private List<Employee_> employees = null;
    @SerializedName("Departments")
    @Expose
    private List<Department__> departments = null;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee_> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee_> employees) {
        this.employees = employees;
    }

    public List<Department__> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department__> departments) {
        this.departments = departments;
    }

}
