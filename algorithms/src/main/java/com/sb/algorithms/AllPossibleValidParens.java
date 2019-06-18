package com.sb.algorithms;

public class AllPossibleValidParens {
  public static void allPossibleValidParenthesis (int n) {
    allPossibleValidParenthesisHelper("", n, n);
  }

  private static void allPossibleValidParenthesisHelper(String parens, int open, int close) {
    // if open == 0, close all and return
    if (open == 0) {
      while(close > 0) {
        parens = parens + ")";
        close--;
      }
      System.out.println(parens);
      return;
    }

    // if you are here, open is non-zero, always start with open and recurse
    allPossibleValidParenthesisHelper(parens + "(", open - 1, close);

    // close and recurse only if close > open; i.e. don't close if open == close
    if (close > open) {
      allPossibleValidParenthesisHelper(parens + ")", open, close - 1);
    }
  }

  public static void main(String[] args) {
    allPossibleValidParenthesis(3);
  }
}
