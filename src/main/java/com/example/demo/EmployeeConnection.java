package com.example.demo;

import java.sql.Connection;


public class EmployeeConnection {
    public static Connection Connect() {
        Connection connect = EmployeeRepository.getConnection();

        return connect;
    }

}
