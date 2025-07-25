/*
Problem Description
You are tasked with creating a program that manages a list of employees in a company. Each employee is represented using a class named Employee, which contains attributes such as their name, ID, and salary. Your goal is to find the top n employees with the highest salaries.

Attributes of the Employee Class:
name: Name of the employee (String)

id: ID of the employee (int)

salary: Salary of the employee (double)

Hint:
Create a stream from the list of employees.

Use a custom comparator to sort the employees.

Limit the stream to get the top n employees.

Input format
An integer n indicating the number of top employees to find

The details of each employee in the format: name id salary

Output format
Return a list of top n employees

Sorting Rules:
Sort the employees in descending order based on their salary.

If two employees have the same salary, the order in the output does not matter.

Sample Input 1

[{"John" 101 85000.0}, {“Alice” 102 92000.0}, {“Bob” 103 85000.0}, {“Charlie” 104 78000.0}]

3

Sample Output 1

[{"Alice" 102 92000.0}, {“John” 101 85000.0}, {“Bob” 103 85000.0}]

Explanation:
Alice has the highest salary, followed by John and Bob (who have the same salary).

Sample Input 2

[{"Sarah" 201 88000.0}, {“Tom” 202 88000.0}, {“Jerry” 203 95000.0}]

2

Sample Output 2

[{"Jerry" 203 95000.0}, {“Sarah” 201 88000.0}]

Explanation:
Jerry has the highest salary. Sarah and Tom have the same salary, but only the top 2 are selected.



*/




import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    static List<Employee> employeeTopNJUnit(List<Employee> employees, int n) {
        // TODO : Implement this function
       List<Employee> s = employees.stream()
                          .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                          .limit(n)
                          .collect(Collectors.toList());
                            

                            // alternate -->  .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                            return s;

    }

    public static void main(String args[]) {
        // Sample test case
        List<Employee> employees = Arrays.asList(
            new Employee("John", 101, 85000.0),
            new Employee("Alice", 102, 92000.0),
            new Employee("Bob", 103, 85000.0),
            new Employee("Charlie", 104, 78000.0)
        );

        int n = 3;
        List<Employee> expected = Arrays.asList(
            new Employee("Alice", 102, 92000.0),
            new Employee("John", 101, 85000.0),
            new Employee("Bob", 103, 85000.0)
        );

        assert expected.equals(employeeTopNJUnit(employees, n)) : "TEST FAILED";

        System.out.println("All test cases in main function passed");
    }
}


class Employee {
    String name;
    int id;
    double salary;

    Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("{%s %d %.1f}", name, id, salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
               Double.compare(employee.salary, salary) == 0 &&
               Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, salary);
    }
}
