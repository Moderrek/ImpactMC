package com.impact.lib;

import com.impact.lib.api.util.SafeParse;

public class ResultTest {

  public static void main(String[] args) {
    int parsed = SafeParse.parseInt("d32").expect("Failed to parse!");
    System.out.println(parsed);
  }

}
