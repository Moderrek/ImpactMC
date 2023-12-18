package com.impact.lib.api.util;

import static com.impact.lib.api.util.Result.Err;
import static com.impact.lib.api.util.Result.Ok;

public class SafeParse {

  private SafeParse() {}

  public static Result<Integer, NumberFormatException> parseInt(String literal) {
    try {
      return Ok(Integer.parseInt(literal));
    } catch (NumberFormatException exception) {
      return Err(exception);
    }
  }

  public static Result<Double, NumberFormatException> parseDouble(String literal) {
    try {
      return Ok(Double.parseDouble(literal));
    } catch (NumberFormatException e) {
      return Err(e);
    }
  }

  public static Result<Float, NumberFormatException> parseFloat(String literal) {
    try {
      return Ok(Float.parseFloat(literal));
    } catch (NumberFormatException e) {
      return Err(e);
    }
  }

}
