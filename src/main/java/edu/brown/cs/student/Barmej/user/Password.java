package edu.brown.cs.student.Barmej.user;

/**
 * A Password class to get secure passwords.
 */
public final class Password {

  /**
   * A constructor for this class.
   */
  private Password() {

  }

  /**
   * A method to hash passwords.
   *
   * @param passwordToHash - a String representing the password that is to be
   *                       hashed
   * @return - a String representing the hashed password
   */
  public static String getSecurePassword(String passwordToHash) {
    return Integer.toString(passwordToHash.hashCode());
  }

}
