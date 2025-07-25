/*
Problem Description
You are tasked with creating a program that manages a list of students in a school. Each student is represented using a struct named Student, which contains attributes such as their name, roll number and marks. Your goal is to sort these students based on specific criteria and output the sorted list.

Sorting Rules:

Primary Rule: Sort the students in descending order based on their marks.

Secondary Rule: If two students have the same marks, sort them in ascending order based on their rollNumber.

Attributes of the Student Struct:
name: Name of the student (String)

rollNumber: Roll number of the student (int)

marks: Marks obtained by the student (int)

Note:

The name and marks attributes can be identical for different students.

rollNumber is unique for each student.

Hint:
Create a stream from the array.

Create a custom comparator to sort the students.

Input format
The details of each student in the format: name rollNumber marks.

Output format
Return a sorted list of students

Sample Input 1

[{"John" 101 85},

 {"Alice" 102 92},

 {"Bob" 103 85},

 {"Charlie" 104 78}]

Sample Output 1

[{"Alice" 102 92},

{"John" 101 85},

{"Bob" 103 85},

{"Charlie" 104 78}]

Explanation:
Alice has the highest marks, followed by John and Bob (who have the same marks), and finally Charlie.

John appears before Bob because his roll number is smaller.

Sample Input 2

[{"Sarah" 201 88},

{"Tom" 202 88},

{"Jerry" 203 95}]

Sample Output 2

[{"Jerry" 203 95},

{"Sarah" 201 88},

{"Tom" 202 88}]

Explanation:
Jerry has the highest marks.

Sarah and Tom have the same marks, but Sarah appears first due to a lower roll number.



*/




import java.util.*;
import java.util.stream.Collectors;


public class Solution{
    
    public static List<Student> sortingStudentsJUnit(List<Student> students){
        // TODO : Implement this function
        List<Student> s=students.stream()
                       .sorted(Comparator.comparing(Student::getMarks).reversed() // Primary sort: marks descending
                                         .thenComparing(Student::getRollNumber))   // Secondary sort: rollNumber ascending
                       .collect(Collectors.toList());

      /*  USING LAMBDA EXPRESION

       List<Student> s = students.stream()
    .sorted((s1, s2) -> {
        // Primary sort: marks descending
        int marksCompare = Integer.compare(s2.getMarks(), s1.getMarks());
        if (marksCompare != 0) {
            return marksCompare;
        }
        // Secondary sort: rollNumber ascending
        return Integer.compare(s1.getRollNumber(), s2.getRollNumber());
    })
    .collect(Collectors.toList());
return s;
       
      */
      
    return s;
    }
    public static void main(String args[]){
        List<Student> students = Arrays.asList(
            new Student("Alice", 10, 85),
            new Student("Bob", 5, 90),
            new Student("Charlie", 15, 75),
            new Student("David", 20, 85),
            new Student("Eve", 10, 90)
        );

        List<Student> sortedStudents = sortingStudentsJUnit(students);

        List<Student> expected = Arrays.asList(
            new Student("Bob", 5, 90),
            new Student("Eve", 10, 90),
            new Student("Alice", 10, 85),
            new Student("David", 20, 85),
            new Student("Charlie", 15, 75)
        );

        // Assert using Java Streams' `toString()` for comparison
        
        assert (expected.toString().equals(sortingStudentsJUnit(students).toString())) : "TEST FAILED";
        System.out.println("All test cases in main function passed");
    }
}

class Student {
    
    private String name;
    private int rollNumber;
    private int marks;

    public Student(String name, int rollNumber, int marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public int getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return name + " " + rollNumber + " " + marks + " ";
    }
}

