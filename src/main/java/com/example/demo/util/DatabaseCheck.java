package com.example.demo.util;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

/**
 * Manual database connectivity check.
 * Run this class directly to verify that the database connection works
 * and a test record can be saved.
 */
public class DatabaseCheck {

    public static void main(String[] args) {
        EmployeeRepository.getConnection();
        Employee employee = new Employee("Takeshi", "takeshi.jp@jmail.jp", "Japan");
        int result = EmployeeRepository.save(employee);
        if (result > 0) {
            System.out.println("Test record saved successfully.");
        } else {
            System.out.println("Failed to save test record.");
        }
    }
}
