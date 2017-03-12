package com.whrsmxmx.maxim_adressbook.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Department__ {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Employees")
    @Expose
    private List<Employee__> employees = null;

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

    public List<Employee__> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee__> employees) {
        this.employees = employees;
    }

}
