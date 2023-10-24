package com.example.mvp_parttern.view;

import com.example.mvp_parttern.model.Employee;

import java.util.HashMap;
import java.util.List;

public interface EmployeeView {
    void showEmployees(List<Employee> employees);
    void addEmployeeToList(Employee employee);
    void showEmployeeDetail(Employee employee);
    void showMessage(String message);
    void showMapErrorMessage(HashMap<String, String> map);
}
