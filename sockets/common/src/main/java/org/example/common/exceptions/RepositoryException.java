package org.example.common.exceptions;

/**
 * RepositoryException wraps RuntimeException and is used for exceptions being thrown in the
 * ro.ubb.repository part of the application
 */
public class RepositoryException extends RuntimeException {
  public RepositoryException(String message) {
    super(message);
  }

  public RepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public RepositoryException(Throwable cause) {
    super(cause);
  }
}
