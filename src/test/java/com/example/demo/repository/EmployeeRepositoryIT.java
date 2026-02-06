package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeRepositoryIT {

    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private MockedStatic<EmployeeRepository> repoMock;

    @BeforeAll
    static void startContainer() throws SQLException, IOException {
        postgres.start();
        try (Connection conn = getTestConnection();
             Statement stmt = conn.createStatement()) {
            InputStream is = EmployeeRepositoryIT.class.getClassLoader().getResourceAsStream("initial.sql");
            assertNotNull(is, "initial.sql not found on classpath");
            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            stmt.execute(sql);
        }
    }

    @AfterAll
    static void stopContainer() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        // Clean table before each test
        try (Connection conn = getTestConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE users RESTART IDENTITY");
        }
        // Mock only getConnection to return testcontainers connection, keep all other methods real
        repoMock = mockStatic(EmployeeRepository.class, CALLS_REAL_METHODS);
        repoMock.when(EmployeeRepository::getConnection).thenAnswer(inv -> getTestConnection());
    }

    @AfterEach
    void tearDown() {
        repoMock.close();
    }

    private static Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
    }

    @Test
    void save_returnsOne() {
        Employee emp = new Employee("Alice", "alice@test.com", "USA");
        int status = EmployeeRepository.save(emp);
        assertEquals(1, status);
    }

    @Test
    void save_andGetAll_returnsEmployee() {
        EmployeeRepository.save(new Employee("Bob", "bob@test.com", "UK"));

        List<Employee> all = EmployeeRepository.getAllEmployees();
        assertEquals(1, all.size());
        assertEquals("Bob", all.get(0).name());
        assertEquals("bob@test.com", all.get(0).email());
        assertEquals("UK", all.get(0).country());
    }

    @Test
    void getEmployeeById_existingId_returnsEmployee() {
        EmployeeRepository.save(new Employee("Charlie", "charlie@test.com", "France"));

        List<Employee> all = EmployeeRepository.getAllEmployees();
        int id = all.get(0).id();

        Optional<Employee> found = EmployeeRepository.getEmployeeById(id);
        assertTrue(found.isPresent());
        assertEquals("Charlie", found.get().name());
    }

    @Test
    void getEmployeeById_nonExistingId_returnsEmpty() {
        Optional<Employee> found = EmployeeRepository.getEmployeeById(9999);
        assertTrue(found.isEmpty());
    }

    @Test
    void update_existingEmployee_returnsOne() {
        EmployeeRepository.save(new Employee("Dave", "dave@test.com", "Germany"));
        List<Employee> all = EmployeeRepository.getAllEmployees();
        int id = all.get(0).id();

        Employee updated = new Employee(id, "Dave Updated", "dave.new@test.com", "Spain");
        int status = EmployeeRepository.update(updated);
        assertEquals(1, status);

        Optional<Employee> found = EmployeeRepository.getEmployeeById(id);
        assertTrue(found.isPresent());
        assertEquals("Dave Updated", found.get().name());
        assertEquals("dave.new@test.com", found.get().email());
        assertEquals("Spain", found.get().country());
    }

    @Test
    void update_nonExistingEmployee_returnsZero() {
        Employee emp = new Employee(9999, "Ghost", "ghost@test.com", "Nowhere");
        int status = EmployeeRepository.update(emp);
        assertEquals(0, status);
    }

    @Test
    void delete_existingEmployee_returnsOne() {
        EmployeeRepository.save(new Employee("Eve", "eve@test.com", "Japan"));
        List<Employee> all = EmployeeRepository.getAllEmployees();
        int id = all.get(0).id();

        int status = EmployeeRepository.delete(id);
        assertEquals(1, status);

        Optional<Employee> found = EmployeeRepository.getEmployeeById(id);
        assertTrue(found.isEmpty());
    }

    @Test
    void delete_nonExistingEmployee_returnsZero() {
        int status = EmployeeRepository.delete(9999);
        assertEquals(0, status);
    }

    @Test
    void getAllEmployees_emptyTable_returnsEmptyList() {
        List<Employee> all = EmployeeRepository.getAllEmployees();
        assertTrue(all.isEmpty());
    }

    @Test
    void save_multipleEmployees_allRetrieved() {
        EmployeeRepository.save(new Employee("A", "a@test.com", "X"));
        EmployeeRepository.save(new Employee("B", "b@test.com", "Y"));
        EmployeeRepository.save(new Employee("C", "c@test.com", "Z"));

        List<Employee> all = EmployeeRepository.getAllEmployees();
        assertEquals(3, all.size());
    }
}
