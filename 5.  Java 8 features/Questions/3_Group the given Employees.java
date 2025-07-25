/*
Problem Description
You are tasked with creating a program that categorizes employees in a company by their department. Each employee is represented using a class named Employee, which contains attributes such as their name, ID, salary, and department. Your goal is to group employees by their department using Java Streams and the Collectors.groupingBy method.

Attributes of the Employee Class:
name: Name of the employee (String)

id: ID of the employee (int)

salary: Salary of the employee (double)

department: Department of the employee (String)

Hint
Grouping Elements: To group elements of a stream, you can use a method that classifies elements based on a given function.

Stream API: Make use of the Java Stream API to process the list of employees.

Grouping Method: Look for a method in the Collectors class that can help in grouping elements based on a specified attribute.

Classifier Function: Use a method reference to specify the attribute you want to group by (in this case, the department of the employee).

Output Format: The result should be a map where the key is the department name and the value is the list of employees in that department.

Input format
The input consists of a list of employees, where each employee is represented by their name, ID, salary, and department.

Output format
Map<String, List<Employee>>, where each key is a group name and each value is a list of employees in that group.

Sample Input 1

[{"John" 101 85000.0 “HR”}, {“Alice” 102 92000.0 “IT”}, {“Bob” 103 85000.0 “HR”}, {“Charlie” 104 78000.0 “IT”}]

Sample Output 1

{

"HR" : [{“John” 101 85000.0}, {“Bob” 103 85000.0}],

"IT" : [{“Alice” 102 92000.0}, {“Charlie” 104 78000.0}]

}

Explanation:
Employees are grouped by their respective departments. John and Bob are in the HR department, while Alice and Charlie are in the IT department.

Sample Input 2

[{"Sarah" 201 88000.0 “Finance”}, {“Tom” 202 88000.0 “IT”}, {“Jerry” 203 95000.0 “Finance”}]

Sample Output 2

{

"Finance" : [{“Sarah” 201 88000.0}, {“Jerry” 203 95000.0}],

"IT" : [{“Tom” 202 88000.0}]

}

Explanation:
Employees are grouped by their respective departments. Sarah and Jerry are in the Finance department, while Tom is in the IT department.



*/




import java.util.*;
import java.util.stream.Collectors;


public class Solution {

    static Map<String, List<Employee>> groupingEmployeeJUnit(List<Employee> employees) {
        // TODO : Implement this function
        Map<String, List<Employee>> groupedEmployees = employees.stream()
                                                               .collect(Collectors.groupingBy(Employee::getDepartment));
        return groupedEmployees;
    }

    public static void main(String args[]) {
        // Sample test case
        List<Employee> employeeList = Arrays.asList(
            new Employee("John", 101, 85000.0, "HR"),
            new Employee("Alice", 102, 92000.0, "IT"),
            new Employee("Bob", 103, 85000.0, "HR"),
            new Employee("Charlie", 104, 78000.0, "IT")
        );

        Map<String, List<Employee>> expected = new HashMap<>();
        expected.put("HR", Arrays.asList(
            new Employee("John", 101, 85000.0, "HR"),
            new Employee("Bob", 103, 85000.0, "HR")
        ));
        expected.put("IT", Arrays.asList(
            new Employee("Alice", 102, 92000.0, "IT"),
            new Employee("Charlie", 104, 78000.0, "IT")
        ));

        assert expected.equals(Solution.groupingEmployeeJUnit(employeeList)) : "TEST FAILED";

        System.out.println("All test cases in main function passed");
    }
}


class Employee {
    String name;
    int id;
    double salary;
    String department;

    Employee(String name, int id, double salary, String department) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.department = department;
    }

    public String getDepartment() {
        return department;
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
               Objects.equals(name, employee.name) &&
               Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, salary, department);
    }
}
