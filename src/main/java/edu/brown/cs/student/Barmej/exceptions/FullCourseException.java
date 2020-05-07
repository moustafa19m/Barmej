package edu.brown.cs.student.Barmej.exceptions;

/**
 * Class for the FullCourseException exception.
 */
public class FullCourseException extends Exception {

  /**
   * Constructor for the FullCourseException class.
   *
   * @param errorMessage - a String representing the error message to be thrown
   *                     when this exception is hit.
   */
  public FullCourseException(String errorMessage) {
    super(errorMessage);
  }
}
