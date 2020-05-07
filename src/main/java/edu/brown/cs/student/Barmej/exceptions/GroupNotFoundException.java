package edu.brown.cs.student.Barmej.exceptions;

/**
 * Class for the GroupNotFoundException exception.
 */
public class GroupNotFoundException extends Exception {

  /**
   * Constructor for the GroupNotFoundException class.
   *
   * @param errorMessage - a String representing the error message to be thrown
   *                     when this exception is hit.
   */
  public GroupNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
