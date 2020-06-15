package org.example.common.exceptions;

/** ValidatorException wraps RuntimeException and is to signal out validating exceptions. */
public class ValidatorException extends RuntimeException {
  public ValidatorException(String message) {
    super(message);
  }

  public ValidatorException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidatorException(Throwable cause) {
    super(cause);
  }
}
