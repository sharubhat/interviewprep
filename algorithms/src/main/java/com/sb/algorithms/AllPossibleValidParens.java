package com.sb.algorithms;

public class AllPossibleValidParens {
  public static void allPossibleValidParens(int n) {
    allPossibleValidParensHelper("", n, n);
  }

  private static void allPossibleValidParensHelper(String parens, int open, int close) {
    // if open == 0, close all and return
    if (open == 0) {
      while(close > 0) {
        parens = parens + ")";
        close--;
      }
      System.out.println(parens);
      return;
    }

    // if close > open, close and recurse
    if (close > open) {
      allPossibleValidParensHelper(parens + ")", open, close - 1);
    }
      allPossibleValidParensHelper(parens + "(", open - 1, close);
  }

  public static void main(String[] args) {
    allPossibleValidParens(3);
  }
}
