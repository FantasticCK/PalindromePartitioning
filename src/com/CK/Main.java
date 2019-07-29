package com.CK;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String s = "aab";
        Solution solution = new Solution();
        System.out.println(solution.partition(s).toString());
    }
}

class Solution {
    List<List<String>> res = new ArrayList<>();
    HashSet<String> palindromeSet = new HashSet<>();

    public List<List<String>> partition(String s) {
        if (s.length() == 0) return res;
        List<String> dfsList = new ArrayList<>();
        helper(s, 0, dfsList);
        return res;
    }

    private void helper(String s, int first, List<String> dfsList) {
        if (first >= s.length()) {
            res.add(new ArrayList<>(dfsList));
            return;
        }

        for (int i = first; i < s.length(); i++) {
            String newS = s.substring(first, i + 1);
            if (isPalindrome(newS)) {
                dfsList.add(newS);
                helper(s, i + 1, dfsList);
                dfsList.remove(dfsList.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s) {
        boolean isPalindrome = true;
        if (!palindromeSet.isEmpty() && palindromeSet.contains(s)) return isPalindrome;
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) isPalindrome = false;
        }
        if (isPalindrome) palindromeSet.add(s);
        return isPalindrome;
    }
}


//DP + DFS 用dp预先记录所有palindrome位置
class Solution2 {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j <= 2 || dp[j + 1][i - 1])) {
                    dp[j][i] = true;
                }
            }
        }
        helper(res, new ArrayList<>(), dp, s, 0);
        return res;
    }

    private void helper(List<List<String>> res, List<String> path, boolean[][] dp, String s, int pos) {
        if (pos == s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = pos; i < s.length(); i++) {
            if (dp[pos][i]) {
                path.add(s.substring(pos, i + 1));
                helper(res, path, dp, s, i + 1);
                path.remove(path.size() - 1);
            }
        }
    }
}