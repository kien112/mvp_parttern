package com.example.mvp_parttern.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDAO {
    @Insert
    void insertAll(Employee... employees);

    @Delete
    void delete(Employee employee);

    @Update
    void update(Employee employee);

    @Query("SELECT * FROM employees")
    List<Employee> getAll();
    @Query("SELECT * FROM employees where (:id is null or id = :id) " +
            "and (:fullName is null or full_name like '%'||:fullName||'%' ) " +
            "and (:hireDate is null or hire_date = :hireDate) " +
            "and (:salary is null or salary = :salary)")
    List<Employee> searchEmployee(Integer id,
                              String fullName,
                              String hireDate,
                              Double salary);

    @Query("select * from employees where id = :id")
    Employee findById(Integer id);
}
