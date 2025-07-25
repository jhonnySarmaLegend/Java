/*
Problem Description
You are tasked with creating a program that filters employees in a company to find the youngest male employee in the Product Development department. Each employee is represented using a class named Employee, which contains attributes such as their name, age, gender, and department. Your goal is to filter male employees in the Product Development department and find the youngest among them using Java Streams.

Attributes of the Employee Class:
name: Name of the employee (String)

age: Age of the employee (int)

gender: Gender of the employee (String)

department: Department of the employee (String)

Hint
Use min(Comparator.comparingInt(Employee::getAge)) to find the youngest employee among the filtered ones.
Input format
The input consists of a list of employees, where each employee is represented by their name, age, gender, and department.

Output format
Return the details of the youngest male employee in the Product Development department. The details should be returned as a string in the format:


"{name age gender department}"

If there are no such employees, return the message:


"No male employee in Product Development found"

Sample Input 1

[{"John" 28 “Male” “HR”}, {“Alice” 30 “Female” “IT”}, {“Bob” 25 “Male” “Product Development”}, {“Charlie” 22 “Male” “Product Development”}]

Sample Output 1

"{Charlie 22 Male Product Development}"

Explanation:
Among the male employees in the Product Development department, Charlie is the youngest.

Sample Input 2

[{"Sarah" 35 “Female” “Finance”}, {“Tom” 40 “Male” “IT”}, {“Jerry” 29 “Male” “Product Development”}]

Sample Output 2

"{Jerry 29 Male Product Development}"

Explanation:
Jerry is the only male employee in the Product Development department.
*/




import java.util.*;



public class Solution {

    static String youngestMaleJUnit(List<Employee> employeeList) {
        // TODO : Implement this function
        Optional<Employee> youngestMale = employeeList.stream()
            .filter(e -> e.getGender().equalsIgnoreCase("Male") &&
                         e.getDepartment().equalsIgnoreCase("Product Development"))
            .min(Comparator.comparingInt(Employee::getAge)); // Find the youngest male

        // Return employee information or a message if no male employee is found
        return youngestMale.map(Employee::toString)
                           .orElse("No male employee in Product Development found");
   
    }

    public static void main(String args[]) {
        // Sample test case
        List<Employee> employeeList = Arrays.asList(
            new Employee("John", 28, "Male", "HR"),
            new Employee("Alice", 30, "Female", "IT"),
            new Employee("Bob", 25, "Male", "Product Development"),
            new Employee("Charlie", 22, "Male", "Product Development")
        );

        String expected = "{Charlie 22 Male Product Development}";
        assert expected.equals(youngestMaleJUnit(employeeList)): "TEST FAILED";

        System.out.println("All test cases in main function passed");
    }
}

class Employee {
    String name;
    int age;
    String gender;
    String department;

    Employee(String name, int age, String gender, String department) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return String.format("{%s %d %s %s}", name, age, gender, department);
    }
}


