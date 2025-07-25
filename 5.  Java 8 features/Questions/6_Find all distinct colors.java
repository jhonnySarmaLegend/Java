/*
Problem Description
You are given an array of colors where each color is represented by a string. Write a Java program to identify and return a list of unique colors from this array. Use Java Streams to filter out duplicate colors and collect the unique colors into a List<String>.

Note: Colors are case sensitive, i.e, "red" is treated as a different color than “Red” or “RED”.

Hint:
Use Stream.of(colors) to create a stream from the array.

Collect the results into a List<String> using Collectors.toList() after filtering.

Input format
The input consists of a single array of strings, where each string represents a color.

Output format
Return a list of color names with no duplicates. The order of colors in the list does not matter.

Sample Input 1

["Red", “Green”, “Yellow”, “Red”, “Yellow”]

Sample Output 1

["Red", “Green”, “Yellow”]

Explanation:
The list contains multiple entries of "Red" and "Blue". These duplicates are removed, and only unique color names are printed.

Sample Input 2

["Purple", “Purple”, “Orange”, “PURPLE”]

Sample Output 2

["Purple", “Orange”, “PURPLE”]

Explanation:
Here, "Purple" appears twice, so only one instance is printed, along with "Orange" and "PURPLE".



*/



import java.util.*;
import java.util.stream.Collectors;


public class Solution{
    
    public static List<String> distinctColorsJUnit(String[] colors){
        // TODO : Implement this function
        List<String>  ans=Arrays.stream(colors)
                     .distinct()
                     .collect(Collectors.toList());

                     return ans;
    }
    public static void main(String args[]){
        String[] colors = {"Red", "Blue", "Green", "Red", "Blue"};
        List<String> expected = List.of("Red", "Blue", "Green");
        List<String> result = Solution.distinctColorsJUnit(colors);
        assert expected.equals(result) : "TEST FAILED";
        
        
        System.out.println("All test cases in main function passed");
    }
}

