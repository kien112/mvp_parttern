package com.example.mvp_parttern.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "employees")
public class Employee {
    @PrimaryKey
    private Integer id;
    @ColumnInfo(name = "full_name")
    private String fullName;
    @ColumnInfo(name = "hire_date")
    private String hireDate;
    private Double salary;

    public Employee() {
    }

    public Employee(Integer id, String fullName, String hireDate, Double salary) {
        this.id = id;
        this.fullName = fullName;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String validateFullName(){
        if(fullName == null || fullName.trim().isEmpty()){
            return "Employee Name cannot be null or empty!";
        }
        return "";
    }

    public String validateHireDate(){
        if(hireDate == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(hireDate);
            return "";
        } catch (ParseException e) {
            return "Hire Date must have format yyyy/MM/dd";
        }
    }

    public String validateSalary(){
        if (salary != null && salary <= 0)
            return "Salary must be positive number";
        return "";
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
