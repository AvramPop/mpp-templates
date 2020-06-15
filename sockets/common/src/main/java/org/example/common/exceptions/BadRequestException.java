package org.example.common.exceptions;

/**
 * ClassReflectionException wraps RuntimeException and is used for exceptions being thrown when
 * using Reflection.
 */
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(Throwable cause) {
    super(cause);
  }
}
