package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {


    public static void main(String[] args) throws SQLException {

        EmployeeConnection.Connect();


        /*getConnection();

        Employee employee = new Employee();

        employee.setName("oleg");
        employee.setEmail(" ");
        employee.setCountry(" ");
        save(employee);*/
    }

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/employee";
        String user = "postgres";
        String password = "postgres";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }

    public static int save(Employee employee) throws SQLException {
        int status = 0;
        try {
            PreparedStatement savePrepared = PreparedStatements.savePrepared;
            savePrepared.setString(1, employee.getName());
            savePrepared.setString(2, employee.getEmail());
            savePrepared.setString(3, employee.getCountry());

            status = savePrepared.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            EmployeeConnection.Connect().close();
        }
        return status;
    }

    public static int update(Employee employee) throws SQLException {

        int status = 0;

        try {
            PreparedStatement updatePrepared = PreparedStatements.updatePrepared;
            updatePrepared.setString(1, employee.getName());
            updatePrepared.setString(2, employee.getEmail());
            updatePrepared.setString(3, employee.getCountry());
            updatePrepared.setInt(4, employee.getId());

            status = updatePrepared.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            EmployeeConnection.Connect().close();
        }
        return status;
    }

    public static int delete(int id) throws SQLException {

        int status = 0;

        try {
            PreparedStatement deletePrepared = PreparedStatements.deletePrepared;
            deletePrepared.setInt(1, id);
            status = deletePrepared.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            EmployeeConnection.Connect().close();
        }
        return status;
    }

    public static Employee getEmployeeById(int id) throws SQLException {

        Employee employee = new Employee();

        try {
            PreparedStatement getById = PreparedStatements.getEmployeeById;
            getById.setInt(1, id);
            ResultSet rs = getById.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            EmployeeConnection.Connect().close();
        }
        return employee;
    }

    public static List<Employee> getAllEmployees() throws SQLException {

        List<Employee> listEmployees = new ArrayList<>();

        try {
            PreparedStatement getAll = PreparedStatements.getAll;
            ResultSet rs = getAll.executeQuery();

            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));

                listEmployees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            EmployeeConnection.Connect().close();
        }
        return listEmployees;
    }
}
