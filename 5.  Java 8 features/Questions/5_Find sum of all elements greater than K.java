/*
Problem Description
You are given an array of integers and a threshold value k. Your task is to write a Java program that calculates and returns the sum of all numbers from the array that are greater than k.

Hint:
Create a stream from the array.
Input format
The input consists of an integer k and a single array of integers.

Output format
Return a single integer, representing the sum of all numbers from the array that are greater than k. Return 0 if there is no element greater than k.

Sample Input 1

k = 5

array = [2, 8, 5, 3, 10]

Sample Output 1

18

Explanation:
The numbers greater than 555 in the input array are 8 and 10. Therefore, their sum is 8+10=18.

Sample Input 2

k = 6

array = [1, 2, 3, 4, 5, 6]

Sample Output 2

0

Explanation:
No numbers in the input array are greater than 6. Hence, the sum is 0.

Sample Input 3

k = 0

array = [-1, -2, -3, 0, 1, 2, 3]

Sample Output 3

6

Explanation:
The numbers greater than 0 in the input array are 1, 2, and 3. Therefore, their sum is 1+2+3=6.



*/



import java.util.*;


public class Solution{
    
    public static int sumOfAllNumbersGreaterThanKJUnit(int[] numbers, int k){
        // TODO : Implement this function
        int ans=Arrays.stream(numbers) // Create an IntStream from the array
                     .filter(number -> number > k) // Filter numbers greater than k
                     .sum();
        return ans;             
    }
    public static void main(String args[]){
        int[] numbers = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        Integer expected = 4 + 5 + 6 + 7; 
        Integer actual = Solution.sumOfAllNumbersGreaterThanKJUnit(numbers, k);
        assert expected.equals(actual) : "TEST FAILED";
        
        System.out.println("All test cases in main function passed");
    }
}

