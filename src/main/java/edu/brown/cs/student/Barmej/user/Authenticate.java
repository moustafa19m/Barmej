package edu.brown.cs.student.Barmej.user;

/**
 * A class to authenticate users and check to see if they are a student, admin,
 * or key-admin of a particular institution.
 */
public final class Authenticate {

  /**
   * a constructor for this class.
   */
  private Authenticate() {

  }

  /**
   * A method to authenticate the admins of an institution.
   *
   * @param user        - the User who is to be authenticated
   * @param institution - the institution for which to check the user as being
   *                    part of
   * @return - a boolean which evaluates to true if the user is an administrator
   *         for the particular institution, and false otherwise
   */
  public static Boolean authenticateAdmin(User user, Institution institution) {
    return user instanceof Administrator && user.getInstitution().equals(institution);
  }

  /**
   * A method to authenticate the key-admins of an institution.
   *
   * @param user        - the User who is to be authenticated
   * @param institution - the institution for which to check the user as being
   *                    part of
   * @return - a boolean which evaluates to true if the user is a
   *         key-administrator for the particular institution, and false otherwise
   */
  public static Boolean authenticateKeyAdmin(User user, Institution institution) {
    boolean isAdministrator = (user instanceof Administrator
        && user.getInstitution().getId().equals(institution.getId()));
    System.out.println(isAdministrator);
    return isAdministrator;
  }
}
