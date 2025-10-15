package com.wipro.task;

import java.util.Arrays;

public class DailyTasks {

    // Method 1: Concatenate two strings
    public String doStringConcat(String s1, String s2) {
        return s1 + " " + s2;
    }

    // Method 2: Sort an integer array
    public int[] sortValues(int arr[]) {
        Arrays.sort(arr);
        return arr;
    }

    // Method 3: Check presence of substring
    public boolean checkPresence(String str, String a) {
        return str.contains(a);
    }
}

