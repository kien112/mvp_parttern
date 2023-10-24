package com.example.mvp_parttern.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_parttern.R;
import com.example.mvp_parttern.model.AppDatabase;
import com.example.mvp_parttern.model.Employee;
import com.example.mvp_parttern.model.EmployeeDAO;
import com.example.mvp_parttern.presenter.EmployeePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements EmployeeView{

    AppDatabase db;
    EmployeeDAO dao;
    EmployeePresenter employeePresenter;
    TextView tv_id_message, tv_full_name_message, tv_hire_date_message, tv_salary_message;
    EditText edt_id, edt_full_name, edt_hire_date, edt_salary;
    Button btn_add, btn_search, btn_edit, btn_delete;
    LinearLayout ll_employee_list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dbEmployees").allowMainThreadQueries().build();
        dao = db.employeeDAO();

        tv_id_message = findViewById(R.id.tv_id_message);
        tv_full_name_message = findViewById(R.id.tv_full_name_message);
        tv_hire_date_message = findViewById(R.id.tv_hire_date_message);
        tv_salary_message = findViewById(R.id.tv_salary_message);
        edt_id = findViewById(R.id.edt_id);
        edt_full_name = findViewById(R.id.edt_full_name);
        edt_hire_date = findViewById(R.id.edt_hire_date);
        edt_salary = findViewById(R.id.edt_salary);
        ll_employee_list = findViewById(R.id.employee_list);
        btn_add = findViewById(R.id.btn_add);
        btn_search = findViewById(R.id.btn_search);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);

        employeePresenter = new EmployeePresenter(dao, this);
        employeePresenter.showEmployeeList(null, null, null, null);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmployee();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEmployees();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });

    }

    private void deleteEmployee() {
        try{
            Integer id = Integer.parseInt(edt_id.getText().toString());
            employeePresenter.deleteEmployee(id);
        }catch (Exception ex){
            Toast.makeText(this, "Id cannot be null or empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchEmployees() {
        Integer id = null;
        Double salary = null;
        try{
            id = Integer.parseInt(edt_id.getText().toString());
        }catch (Exception ex){}

        try {
            salary = Double.parseDouble(edt_salary.getText().toString());
        }catch (Exception ex){}

        String fullName = edt_full_name.getText().toString();
        fullName = fullName.trim().isEmpty() ? null : fullName;
        String hireDate = edt_hire_date.getText().toString();
        hireDate = hireDate.trim().isEmpty() ? null : hireDate;
        employeePresenter.showEmployeeList(id, fullName, hireDate, salary);
    }

    private void editEmployee() {
        employeePresenter.editEmployee(getEmployeeInput());
    }

    private void addEmployee() {
        employeePresenter.addNewEmployee(getEmployeeInput());
    }

    private Employee getEmployeeInput(){
        Integer id = null;
        Double salary = null;
        try{
            id = Integer.parseInt(edt_id.getText().toString());
        }catch (Exception ex){}

        try{
            salary = Double.parseDouble(edt_salary.getText().toString());
        }catch (Exception ex){}

        String fullName = edt_full_name.getText().toString();
        String hireDate = edt_hire_date.getText().toString();

        Employee employee = new Employee(id, fullName, hireDate, salary);
        return  employee;
    }

    @Override
    public void showEmployees(List<Employee> employees) {
        ll_employee_list.removeAllViews();
        for (Employee e : employees) {
            TextView tv = new TextView(this);
            tv.setText(e.toString());

            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF);
            border.setStroke(1, 0xFF000000);
            tv.setBackground(border);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);

            tv.setLayoutParams(params);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = ((TextView) v);
                    employeePresenter.getEmployee(textView.getText().toString());
                }
            });
            ll_employee_list.addView(tv);
        }
    }

    @Override
    public void addEmployeeToList(Employee employee) {
        TextView tv = new TextView(this);
        tv.setText(employee.toString());
        ll_employee_list.addView(tv);
    }

    @Override
    public void showEmployeeDetail(Employee employee) {
        if(employee != null){
            edt_id.setText(employee.getId()+"");
            edt_full_name.setText(employee.getFullName());
            edt_hire_date.setText(employee.getHireDate());
            edt_salary.setText(employee.getSalary()+"");
        }else{
            Toast.makeText(this, "No Employee Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMapErrorMessage(HashMap<String, String> map) {
        if(map.containsKey("id")){
            tv_id_message.setText(map.get("id"));
        }
        if(map.containsKey("fullName")){
            tv_full_name_message.setText(map.get("fullName"));
        }
        if(map.containsKey("hireDate")){
            tv_hire_date_message.setText(map.get("hireDate"));
        }
        if(map.containsKey("salary")){
            tv_salary_message.setText(map.get("salary"));
        }
    }
}