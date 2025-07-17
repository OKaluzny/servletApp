package com.example.demo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

public final class EmployeeRepository {

    private static final Logger logger = Logger.getLogger(EmployeeRepository.class.getName());

    /*public static void main(String[] args) {
        getConnection();
        Employee employee = new Employee("Takeshi", "takeshi.jp@jmail.jp", "Japan");
        save(employee);
    }*/

    public static Connection getConnection() {

        Connection connection = null;
        // Use environment variables for Docker compatibility, fallback to localhost for local development
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "5432";
        String database = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "employee";
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
        String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "postgres";

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, "Failed to establish database connection", sqlException);
        }
        return connection;
    }

    public static int save(Employee employee) {
        int status = 0;
        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into users(name,email,country) values (?,?,?)");
            ps.setString(1, employee.name());
            ps.setString(2, employee.email());
            ps.setString(3, employee.country());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to save employee", ex);
        }
        return status;
    }

    public static int update(Employee employee) {

        int status = 0;

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("update users set name=?,email=?,country=? where id=?");

            ps.setString(1, employee.name());
            ps.setString(2, employee.email());
            ps.setString(3, employee.country());
            ps.setInt(4, employee.id());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, "Failed to update employee", sqlException);
        }
        return status;
    }

    public static int delete(int id) {

        int status = 0;

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from users where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            connection.close();

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "Failed to delete employee with id: " + id, exception);
        }
        return status;
    }

    //public static List<Employee> getEmployeeById(int id) {
    public static Optional<Object> getEmployeeById(int id) {

        //List<Employee> listEmployee = new ArrayList<>();
        Optional<Object> optionalEmployee = Optional.of(new Object());
        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                optionalEmployee = Optional.of(employee);
                //listEmployee.add(employee);
            }
            connection.close();

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "Failed to get employee by id: " + id, exception);
        }
        //return listEmployee;
        return optionalEmployee;
    }

    public static List<Employee> getAllEmployees() {

        List<Employee> listEmployees = new ArrayList<>();

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Employee employee = new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));

                listEmployees.add(employee);
            }
            connection.close();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get all employees", e);
        }
        return listEmployees;
    }
}
