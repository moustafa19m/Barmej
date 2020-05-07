package edu.brown.cs.student.Barmej.exceptions;

/**
 * Class for the UserPermissionException exception.
 */
public class UserPermissionException extends Exception {

  /**
   * Constructor for the UserPermissionException class.
   *
   * @param errorMessage - a String representing the error message to be thrown
   *                     when this exception is hit.
   */
  public UserPermissionException(String errorMessage) {
    super(errorMessage);
  }
}
