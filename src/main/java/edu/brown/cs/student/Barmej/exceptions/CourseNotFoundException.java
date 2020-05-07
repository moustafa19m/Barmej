package edu.brown.cs.student.Barmej.exceptions;

/**
 * Class for the CourseNotFoundException exception.
 */
public class CourseNotFoundException extends Exception {

  /**
   * Constructor for the CourseNotFoundException class.
   *
   * @param errorMessage - a String representing the error message to be thrown
   *                     when this exception is hit.
   */
  public CourseNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
