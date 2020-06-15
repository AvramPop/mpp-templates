package org.example.common.exceptions;

/**
 * ClassReflectionException wraps RuntimeException and is used for exceptions being thrown when
 * using Reflection.
 */
public class ClassReflectionException extends RuntimeException {
  public ClassReflectionException(String message) {
    super(message);
  }

  public ClassReflectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClassReflectionException(Throwable cause) {
    super(cause);
  }
}
