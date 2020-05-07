package edu.brown.cs.student.Barmej.user;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import javax.naming.AuthenticationException;

import edu.brown.cs.student.Barmej.constants.Constants;
import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.databases.Database;
import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;
import edu.brown.cs.student.Barmej.recommender.CourseRecommender;
import spark.QueryParamsMap;

/**
 * A class to represent and handle Institutions.
 */
public class Institution {

  /**
   * The fields for this class.
   *
   * id - a String representing the ID of the instituion
   *
   * conn - a Connection object to connect to the database
   *
   * usedIDs - a Set of strings representing all the IDs by which an institution
   * already exists
   */
  private String id;
  private static Connection conn = Database.getConn();
  private static Set<String> usedIDs = new HashSet<>();

  /**
   * A constructor for the Institution class.
   *
   * @param id - a String representing the id of the instituion.
   */
  public Institution(String id) {
    this.id = id;
  }

  /**
   * A getter for the ID of an institution.
   *
   * @return a String representing the id of the instituion.
   */
  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Institution that = (Institution) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  /**
   * A method that gets all the IDs in the institution table, to add to the
   * usedIDs field.
   */
  private static void getIDs() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT id from institution");
      prep.executeUpdate();
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String id = rs.getString(1);
        usedIDs.add(id);
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      System.err.println("get ids in inst");
    }
  }

  /**
   * A method to get the name of an institution.
   *
   * @return - a String representing the name
   */
  public String getName() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT name from institution WHERE id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Name in Institution SQL malfunction");
    }
    return null;
  }

  /**
   * A method that returns the username of the user enrolled in the institution
   * with this.id.
   *
   * @return - a String representing the username
   */
  public String getUserName() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT username from user WHERE id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Username in Institution SQL malfunction");
    }
    return null;
  }

  /**
   * A method that allows an institution to sign up, and inserts its information
   * into the database.
   *
   * @param formOrganizationInputs - a list of strings representing the
   *                               information received from the sign up form for
   *                               an institution on the front end
   * @param formAdminInputs        - a list of strings representing the
   *                               information received from the sign up form for
   *                               an admin on the front end
   * @throws NoSuchProviderException  - when the provider does not exist
   * @throws NoSuchAlgorithmException - when the algorithm does not exist
   */
  public static void institutionSignUp(List<String> formOrganizationInputs,
      List<String> formAdminInputs) throws NoSuchProviderException, NoSuchAlgorithmException {
    try {
      PreparedStatement prep;
      Random rand = new Random();
      String id = Integer.toString(((Long) rand.nextLong()).hashCode());
      getIDs();
      while (usedIDs.contains(id)) {
        id = Integer.toString(((Long) rand.nextLong()).hashCode());
      }
      prep = conn.prepareStatement("INSERT INTO institution VALUES (?, ?, ?, ?, ?)");
      prep.setString(1, id);
      prep.setString(2, formOrganizationInputs.get(0));
      prep.setString(3, formOrganizationInputs.get(1));
      prep.setString(4, formOrganizationInputs.get(2));
      prep.setString(5, formOrganizationInputs.get(3));
      prep.executeUpdate();
      formAdminInputs.add(id);
      User.signUp(formAdminInputs, "key-administrator");
      prep.close();
    } catch (SQLException e) {
      System.err.println("Error during user signup");
      e.printStackTrace();
    }
  }

  /**
   * A method to get a set of all the courses purchased by this institution.
   *
   * @param caller - a User, to ensure a student is not invoking this method
   * @return - a Set of all the courses purchased by this institution
   * @throws AuthenticationException - when the user is then authenticated to
   *                                 invoke this method
   */
  public Set<Course> getPurchasedCourses(User caller) throws AuthenticationException {
    Set<Course> courses = new HashSet<>();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT course_id from purchased_courses " + "WHERE institution_id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String courseId = rs.getString(1);
        Course course = new Course(courseId);
        courses.add(course);
      }
      rs.close();
      prep.close();
      return courses;
    } catch (SQLException e) {
      System.err.println("get Courses in Institution SQL malfunction");
    }
    return null;
  }

  /**
   * A method to get a set of all the administrators of this institution.
   *
   * @return - a Set of all the admins of this institution
   * @throws AuthenticationException - when the user is then authenticated to
   *                                 invoke this method
   */
  public Set<Administrator> getAdmins() {
    Set<Administrator> admins = new HashSet<>();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT administrator_id from institute_admins WHERE institution_id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String adminId = rs.getString(1);
        Administrator admin = new Administrator(adminId);
        admins.add(admin);
      }
      rs.close();
      prep.close();
      return admins;
    } catch (SQLException e) {
      System.err.println("get Courses in Admin SQL malfunction");
    }
    return null;
  }

  /**
   * A method that gets all the students enrolled in/belonging to this
   * institution.
   *
   * @return a Set of all belonging students
   */
  public Set<Student> getStudents() {
    Set<Student> students = new HashSet<>();
    PreparedStatement prep;
    try {
      prep = conn
          .prepareStatement("SELECT student_id from institute_students WHERE institution_id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String iD = rs.getString(1);
        Student student = new Student(iD);
        students.add(student);
      }
      rs.close();
      prep.close();
      return students;
    } catch (SQLException e) {
      System.err.println("get Courses in Admin SQL malfunction");
    }
    return null;
  }

  /**
   * A method that gets all the students enrolled in/belonging to this
   * institution.
   *
   * @pram cID - the id of the course
   *
   * @return a Set of all belonging students
   */
  public Set<Student> getEnrolledStudents(String cID) {
    Set<Student> students = new HashSet<>();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT student_id from enrolled_courses WHERE institution_id = ? AND course_id = ?");
      prep.setString(1, id);
      prep.setString(2, cID);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String iD = rs.getString(1);
        Student student = new Student(iD);
        students.add(student);
      }
      rs.close();
      prep.close();
      return students;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * A method that returns the admin object representing the key-administrator of
   * this institution.
   *
   * @return - an Object corresponding to the key-administrator
   */
  public Administrator getKeyAdmin() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT administrator_id from institute_admins WHERE "
          + "institution_id = ? AND key_admin = ?");
      prep.setString(1, id);
      prep.setInt(2, 1);
      ResultSet rs = prep.executeQuery();
      String keyAdminId = rs.getString(1);
      rs.close();
      prep.close();
      return new Administrator(keyAdminId);
    } catch (SQLException e) {
      System.err.println("get KeyAdmin in Admin SQL malfunction");
    }
    return null;
  }

  /**
   * A method to get a set of all available courses on the site.
   *
   * @param caller - a User who invokes the method, who must be an authenticated
   *               administrator
   * @return - a Set of all available courses
   * @throws UserPermissionException - when the user does not have permission to
   *                                 invoke this method
   */
  public Set<Course> getAllCourses(User caller) throws UserPermissionException {
    if (Authenticate.authenticateAdmin(caller, this)) {
      Set<Course> courses = new HashSet<>();
      PreparedStatement prep;
      try {
        prep = conn.prepareStatement("SELECT * FROM course");
        ResultSet rs1 = prep.executeQuery();
        while (rs1.next()) {
          String courseId = rs1.getString(1);
          Course course = new Course(courseId);
          courses.add(course);
        }
        rs1.close();
        prep.close();
        return courses;
      } catch (SQLException e) {
        System.err.println("purchase course in Institution SQL malfunction");
      }
    } else {
      throw new UserPermissionException(Constants.AUTHENTICATION_ERROR);
    }
    return null;
  }

  /**
   * A method that allows an administrator for this institution to purchase a
   * course for it.
   *
   * @param caller - a User who invokes the method, who must be an authenticated
   *               key-administrator
   * @param course - the course to be purchased
   * @throws UserPermissionException - when the user does not have permission to
   *                                 invoke this method
   */
  public void purchaseCourse(User caller, Course course) throws UserPermissionException {
    if (Authenticate.authenticateKeyAdmin(caller, this)) {
      PreparedStatement prep;
      try {
        prep = conn.prepareStatement(
            "INSERT INTO purchased_courses (institution_id, course_id) " + "VALUES (?,?)");
        prep.setString(1, id);
        prep.setString(2, course.getId());
        prep.executeQuery();
        prep.close();
      } catch (SQLException e) {
        System.err.println("purchase course in Institution SQL malfunction");
      }
    } else {
      throw new UserPermissionException(Constants.AUTHENTICATION_ERROR);
    }
  }

  /**
   * A method to get the number of students enrolled in an institution.
   *
   * @param caller - a User who invokes the method, who must be an authenticated
   *               administrator
   * @return - an integer representing the number of total enrolled students
   * @throws UserPermissionException - when the user does not have permission to
   *                                 invoke this method
   */
  public Integer getNumEnrolled(User caller) throws UserPermissionException {
    if (Authenticate.authenticateAdmin(caller, this)) {
      PreparedStatement prep;
      try {
        prep = conn.prepareStatement(
            "SELECT COUNT(student_id) FROM institute_students WHERE institution_id = ?");
        prep.setString(1, id);
        ResultSet rs = prep.executeQuery();
        int numStudents = 0;

        while (rs.next()) {
          numStudents = rs.getInt(1);
        }
        rs.close();
        prep.close();
        return numStudents;
      } catch (SQLException e) {
        System.err.println("getNumEnrolled in Institution SQL malfunction");
      }
    } else {
      throw new UserPermissionException(Constants.AUTHENTICATION_ERROR);
    }
    return -1;
  }

  /**
   * A method to get the size of the institution.
   *
   * @param caller - a User who invokes the method, who must be an authenticated
   *               administrator
   * @return - an integer representing the size of the institution
   * @throws UserPermissionException - when the user does not have permission to
   *                                 invoke this method
   */
  public Integer getSize(User caller) throws UserPermissionException {
    if (Authenticate.authenticateAdmin(caller, caller.getInstitution())) {
      PreparedStatement prep;
      try {
        prep = conn.prepareStatement("SELECT size FROM institution WHERE id = ?");
        prep.setString(1, id);
        ResultSet rs = prep.executeQuery();
        int numStudents = 0;

        while (rs.next()) {
          numStudents = rs.getInt(1);
        }
        rs.close();
        prep.close();
        return numStudents;
      } catch (SQLException e) {
        System.err.println("getSize in Institution SQL malfunction");
      }
    } else {
      throw new UserPermissionException(Constants.AUTHENTICATION_ERROR);
    }
    return -1;
  }

  /**
   * A getter for the current institution.
   *
   * @return - an object representing this institution
   */
  public Institution getInstitution() {
    return this;
  }

  /**
   * A method to get the type of the institution.
   *
   * @return - a String representing the type of the institution
   */
  public String getType() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT type from institution WHERE id = ?");
      prep.setString(1, this.getId());
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      prep.close();
      rs.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Institution in Admin SQL malfunction");
    }
    return null;
  }

  /**
   * A method to get all the recommended courses for an institution, based on its
   * requirements.
   *
   * @param params - the parameters that define the requirements of this
   *               institution
   * @return - a Set of recommended courses for this institution
   */
  public List<Course> getRecommendedCourses(QueryParamsMap params) {
    CourseRecommender recommender = new CourseRecommender();

    double mathRating = Double.parseDouble(params.value("math"));
    double creativeRating = Double.parseDouble(params.value("creative"));
    double advancedRating = Double.parseDouble(params.value("advanced"));
    double theoreticalRating = Double.parseDouble(params.value("theoretical"));
    double practicalRating = Double.parseDouble(params.value("practical"));
    double problemRating = Double.parseDouble(params.value("problem_solving"));
    int numCourses = Integer.parseInt(params.value("numCourses"));

    List<Double> ratings = new ArrayList<>(Arrays.asList(mathRating, creativeRating, advancedRating,
        theoreticalRating, practicalRating, problemRating));

    return recommender.getRecommendations(ratings, numCourses);
  }

}
