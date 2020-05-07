package edu.brown.cs.student.Barmej.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;

import edu.brown.cs.student.Barmej.constants.Constants;
import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.databases.Database;
import edu.brown.cs.student.Barmej.exceptions.FullCourseException;
import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;

/**
 * A class representing an Administrator for an Institution.
 *
 */
public class Administrator extends User {

  /**
   * The fields for this class.
   */
  private String id;
  private static Connection conn = Database.getConn();

  /**
   * Constructor for an Administrator.
   *
   * @param id - a String representing the id of the administrator
   */
  public Administrator(String id) {
    super(id);
    this.id = id;
  }

  @Override
  public String getId() {
    return this.id;
  }

  /**
   * A method that returns a list of all courses purchased by the institution of
   * this administrator.
   *
   * @return - a Set of all purchased courses
   */
  public Set<Course> getCourses() {
    Set<Course> courses = new HashSet<>();
    PreparedStatement prep;
    try {
      prep = conn
          .prepareStatement("SELECT course_id from  purchased_courses WHERE institution_id = ?");
      prep.setString(1, this.getInstitution().getId());
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String courseId = rs.getString(1);
        System.out.println(courseId);
        Course course = new Course(courseId);
        courses.add(course);
      }
      rs.close();
      prep.close();
      return courses;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * A method that allows an authenticated Administrator to send an email invite
   * to a student.
   *
   * @param caller - an object of type User to authenticate to ensure it is an
   *               admin sending out the invites and not a student
   * @param email  - the email address of the student to which the invite must be
   *               sent
   * @throws AuthenticationException - if a User who is not an administrator tries
   *                                 to call this method
   * @throws IOException
   */
  public void inviteStudent(User caller, String email) throws AuthenticationException, IOException {
    if (Authenticate.authenticateAdmin(caller, this.getInstitution())) {
      Cookie cookies = new Cookie("hash", Integer.toString(email.hashCode()));
      Map<String, String> emails = User.getEmailMap();
      emails.put(cookies.getValue(), email);
      User.setEmailMap(emails);
      SendMailBySendGrid.setTo(email);
      SendMailBySendGrid
          .setSubject("Invitation to join Barmej Student: Senstive login information enclosed");
      StringBuilder builder = new StringBuilder(
          "Please follow the link below to create your account today!");
      builder.append("\n");
      StringBuilder build = new StringBuilder("https://barmej.herokuapp.com/studentSignup/");
      build.append(cookies.getValue());
      builder.append(build.toString());
      builder.append("\n");
      builder.append("Your Private Organization ID is: ");
      builder.append(this.getInstitution().getId());
      SendMailBySendGrid.setTextMessage(builder.toString());
      SendMailBySendGrid email1 = new SendMailBySendGrid();
      String[] array = {};
      System.out.println(email);
      email1.main(array);
      System.out.println("invite sent");
      SendMailBySendGrid.setTo(null);
      SendMailBySendGrid.setSubject(null);
      SendMailBySendGrid.setTextMessage(null);
    } else {
      throw new AuthenticationException(Constants.AUTHENTICATION_ERROR);
    }
  }

  /**
   * A method that allows an admin to send an email invite to another admin.
   *
   * @param caller - an object of type User to authenticate to ensure it is an
   *               admin sending out the invites and not a student
   * @param email  - the email address of the student to which the invite must be
   *               sent
   * @throws AuthenticationException - if a User who is not an administrator tries
   *                                 to call this method
   * @throws IOException
   */
  public void inviteAdministrator(User caller, String email)
      throws AuthenticationException, IOException {
    if (Authenticate.authenticateAdmin(caller, this.getInstitution())) {
      Cookie cookies = new Cookie("hash", Integer.toString(email.hashCode()));
      Map<String, String> emails = User.getEmailMap();
      System.out.println(cookies.getValue());
      emails.put(cookies.getValue(), email);
      User.setEmailMap(emails);
      System.out.println("invite admin hit");
      System.out.println(User.getEmailMap().size());
      SendMailBySendGrid.setTo(email);
      SendMailBySendGrid.setSubject(
          "Invitation to join Barmej Administrator: Senstive login information enclosed");
      StringBuilder builder = new StringBuilder(
          "Please follow the link below to create your account today!");
      builder.append("\n");
      StringBuilder build = new StringBuilder("https://barmej.herokuapp.com/adminSignup/");
      build.append(cookies.getValue());
      builder.append(build.toString());
      builder.append("\n");
      builder.append("Your Private Organization ID is: ");
      builder.append(this.getInstitution().getId());
      SendMailBySendGrid.setTextMessage(builder.toString());
      System.out.println("invite sending final");
      SendMailBySendGrid email1 = new SendMailBySendGrid();
      String[] array = {};
      System.out.println(email);
      try {
        email1.main(array);
      } catch (IOException e) {
        System.err.println("io exception in invite admin in Admin");
      }
      System.out.println("invite sent");
      SendMailBySendGrid.setTo(null);
      SendMailBySendGrid.setSubject(null);
      SendMailBySendGrid.setTextMessage(null);
    } else {
      throw new AuthenticationException(Constants.AUTHENTICATION_ERROR);
    }
  }

  /**
   * A method to allow an admin to add a student or enroll a student in a Course.
   *
   * @param student - the student to be added
   * @param course  - the course to be added to
   * @throws UserPermissionException - if a user other than an admin tries to
   *                                 invoke this method
   */
  public void addStudentToCourse(Student student, Course course) {
    PreparedStatement prep;
    try {
      int max = course.getMaxStudents();
      int enrolled = course.getStudentsEnrolled();
      if (enrolled < 0) {
        System.out.println("sauce9999");
        return;
      }
      if (enrolled < max) {
        prep = conn.prepareStatement("INSERT INTO enrolled_courses VALUES (?, ?, ?)");
        prep.setString(1, student.getId());
        prep.setString(2, course.getId());
        prep.setString(3, this.getInstitution().getId());
        prep.executeUpdate();
        prep.close();
        System.out.println("added");
        System.out.println(course.getStudentsEnrolled());
      } else {
        throw new FullCourseException(Constants.FULL_COURSE_ERROR);
      }
    } catch (SQLException | FullCourseException e) {
      System.err.println("add Courses in Students in Admin SQL malfunction");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Administrator that = (Administrator) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  /**
   * A method to get the first name of an admin from the database.
   *
   * @return - a String representing the first name of the admin
   */
  public String getFirstName() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT first_name from user WHERE id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Name in ADMIN SQL malfunction");
    }
    return null;
  }

  /**
   * A method to get the last name of an admin from the database.
   *
   * @return - a String representing the last name of the admin
   */
  public String getLastName() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT last_name from user WHERE id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Name in ADMIN SQL malfunction");
    }
    return null;
  }

  /**
   * A method to get the email address of the admin from the database.
   *
   * @return - a String representing the email address of the admin
   */
  public String getEmail() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT email_address from user WHERE id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Name in ADMIN SQL malfunction");
    }
    return null;
  }

  @Override
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

  @Override
  public Institution getInstitution() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT institution_id from institute_admins WHERE administrator_id = ?");
      prep.setString(1, this.getId());
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return new Institution(value);
    } catch (SQLException e) {
      System.err.println("get Institution in Admin SQL malfunction");
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String getType() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT user_type from user WHERE id = ?");
      prep.setString(1, this.getId());
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Institution in Admin SQL malfunction");
    }
    return null;
  }

  /**
   * A method to get all the students not enrolled in a particular course.
   *
   * @param cID - a String representing the id of the course
   * @return - a List of all the students not enrolled in the course
   */
  public List<Student> getStudentsNotIn(String cID) {
    PreparedStatement prep;
    try {
      String query = "SELECT student.id FROM (SELECT * FROM "
          + "( user JOIN institute_students ON user.id = institute_students.student_id) "
          + "WHERE institute_students.institution_id = ?) AS student "
          + "WHERE student.id NOT IN (SELECT student_id FROM  enrolled_courses WHERE course_id = ? );";
      prep = conn.prepareStatement(query);
      prep.setString(1, this.getInstitution().getId());
      prep.setString(2, cID);
      ResultSet rs = prep.executeQuery();
      List<Student> students = new ArrayList<Student>();
      while (rs.next()) {
        String sID = rs.getString(1);
        students.add(new Student(sID));
      }
      rs.close();
      prep.close();
      return students;
    } catch (SQLException e) {
      System.err.println("get Institution in Admin SQL malfunction");
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String getFullName() {
    return this.getFirstName() + " " + this.getLastName();
  }

  /**
   * A method to allow an administrator to purchase a course for their
   * institution.
   *
   * @param cID - a String representing the ID of the course to be purchased
   */
  public boolean buyCourse(String cID) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT * FROM purchased_courses WHERE institution_id = ? AND course_id = ?");
      prep.setString(1, this.getInstitution().getId());
      prep.setString(2, cID);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        return false;
      }
      prep = conn.prepareStatement("INSERT INTO purchased_courses VALUES (?, ?)");
      prep.setString(1, this.getInstitution().getId());
      prep.setString(2, cID);
      prep.executeUpdate();
      prep.close();
      return true;
    } catch (SQLException e) {
      System.err.println("get Institution in Admin SQL malfunction");
      return false;
    }
  }

}
