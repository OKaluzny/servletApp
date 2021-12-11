package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatements {
    public static PreparedStatement savePrepared;

    static {
        try {
            savePrepared = EmployeeConnection.Connect().prepareStatement("insert into users(name,email,country) values (?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement updatePrepared;

    static {
        try {
            updatePrepared = EmployeeConnection.Connect().prepareStatement("update users set name=?,email=?,country=? where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement deletePrepared;

    static {
        try {
            deletePrepared = EmployeeConnection.Connect().prepareStatement("update from users where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement getEmployeeById;

    static {
        try {
            getEmployeeById = EmployeeConnection.Connect().prepareStatement("select * from users where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement getAll;

    static {
        try {
            getAll = EmployeeConnection.Connect().prepareStatement("select * from users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
