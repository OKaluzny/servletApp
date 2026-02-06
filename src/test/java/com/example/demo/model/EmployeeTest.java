package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void constructorWithAllFields() {
        Employee employee = new Employee(1, "John", "john@test.com", "USA");

        assertEquals(1, employee.id());
        assertEquals("John", employee.name());
        assertEquals("john@test.com", employee.email());
        assertEquals("USA", employee.country());
    }

    @Test
    void constructorWithoutIdDefaultsToZero() {
        Employee employee = new Employee("Jane", "jane@test.com", "UK");

        assertEquals(0, employee.id());
        assertEquals("Jane", employee.name());
        assertEquals("jane@test.com", employee.email());
        assertEquals("UK", employee.country());
    }

    @Test
    void equalityBasedOnAllFields() {
        Employee e1 = new Employee(1, "John", "john@test.com", "USA");
        Employee e2 = new Employee(1, "John", "john@test.com", "USA");
        Employee e3 = new Employee(2, "John", "john@test.com", "USA");

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
    }

    @Test
    void toStringContainsAllFields() {
        Employee employee = new Employee(1, "John", "john@test.com", "USA");
        String str = employee.toString();

        assertTrue(str.contains("John"));
        assertTrue(str.contains("john@test.com"));
        assertTrue(str.contains("USA"));
    }

    @Test
    void allowsNullCountry() {
        Employee employee = new Employee("John", "john@test.com", null);

        assertNull(employee.country());
    }
}
