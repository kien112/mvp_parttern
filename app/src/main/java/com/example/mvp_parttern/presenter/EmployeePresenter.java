package com.example.mvp_parttern.presenter;

import com.example.mvp_parttern.model.Employee;
import com.example.mvp_parttern.model.EmployeeDAO;
import com.example.mvp_parttern.view.EmployeeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeePresenter {
    EmployeeDAO employeeDAO;
    EmployeeView employeeView;
    Boolean isValidData = true;

    public EmployeePresenter(EmployeeDAO employeeDAO, EmployeeView employeeView) {
        this.employeeDAO = employeeDAO;
        this.employeeView = employeeView;
    }

    public void showEmployeeList(Integer id, String fullName, String hireDate, Double salary){
        List<Employee> employees = employeeDAO.searchEmployee(id, fullName, hireDate, salary);
        employeeView.showEmployees(employees);
    }

    public void addNewEmployee(Employee employee){
        HashMap<String, String> map = validateEmployee(employee);
        if(isValidData){
            String message = "";
            Employee employeeInDB = employeeDAO.findById(employee.getId());
            if(employeeInDB != null){
                message = "Employee Id is existed!";
            }
            else{
                employeeDAO.insertAll(employee);
                employeeView.addEmployeeToList(employee);
                message = "Add New Employee Successfully!";
            }
            employeeView.showMessage(message);
            return;
        }
        isValidData = true;
        employeeView.showMapErrorMessage(map);
    }

    public void editEmployee(Employee employee){
        HashMap<String, String> map = validateEmployee(employee);
        if(isValidData){
            String message = "";
            Employee employeeInDB = employeeDAO.findById(employee.getId());
            if(employeeInDB == null){
                message = "Employee is not exist!";
            }
            else{
                employeeDAO.update(employee);
                showEmployeeList(null, null, null, null);
                message = "Update Employee Successfully!";
            }
            employeeView.showMessage(message);
            return;
        }
        isValidData = true;
        employeeView.showMapErrorMessage(map);
    }

    public void deleteEmployee(Integer id){
        String message = "";
        Employee employeeInDB = employeeDAO.findById(id);
        if(employeeInDB == null){
            message = "Employee is not exist!";
        }
        else{
            employeeDAO.delete(employeeDAO.findById(id));
            showEmployeeList(null, null, null, null);
            message = "Delete Employee Successfully!";
        }
        employeeView.showMessage(message);
    }

    public HashMap<String, String> validateEmployee(Employee employee){
        HashMap<String, String> map = new HashMap<>();
        if(employee.getId() == null){
            map.put("id", "Employee Id must not be null");
        }
        map.put("fullName", employee.validateFullName());
        map.put("hireDate", employee.validateHireDate());
        map.put("salary", employee.validateSalary());

        for (String value: map.values()){
            if(!value.isEmpty()){
                isValidData = false;
                break;
            }
        }
        return map;
    }

    public void getEmployee(String text){

        Pattern pattern = Pattern.compile("id=(\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String idStr = matcher.group(1);
            int id = Integer.parseInt(idStr);
            Employee employee = employeeDAO.findById(id);
            employeeView.showEmployeeDetail(employee);
        }
    }
}
