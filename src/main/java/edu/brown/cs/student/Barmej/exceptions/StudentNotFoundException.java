package edu.brown.cs.student.Barmej.exceptions;

/**
 * Class for the StudentNotFoundException exception.
 */
public class StudentNotFoundException extends Exception {

  /**
   * Constructor for the StudentNotFoundException class.
   *
   * @param errorMessage - a String representing the error message to be thrown
   *                     when this exception is hit.
   */
  public StudentNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
