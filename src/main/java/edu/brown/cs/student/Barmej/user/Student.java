package edu.brown.cs.student.Barmej.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.databases.Database;

/**
 * A class for the creation and handling of Student objects.
 */
public class Student extends User {

  /**
   * The fields for this class.
   */
  private static Connection conn = Database.getConn();

  /**
   * A constructor for this class.
   *
   * @param id - a String representing the ID of the student being created
   */
  public Student(String id) {
    super(id);
    this.setId(id);
  }

  /**
   * A method to get a Set of all the Courses the current student is enrolled in.
   *
   * @return - a Set of all the courses this student is enrolled in
   */
  public Set<Course> getEnrolledIn() {
    Set<Course> courses = new HashSet<>();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT course_id from enrolled_courses WHERE student_id = ?");
      prep.setString(1, this.getId());
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String courseId = rs.getString(1);
        Course course = new Course(courseId);
        courses.add(course);
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      System.err.println("get Courses in Admin SQL malfunction");
      e.printStackTrace();
    }
    return courses;
  }

  /**
   * A method to get the first name of the current Student.
   *
   * @return - a String representing the first name of the student
   */
  public String getFirstName() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT first_name from user WHERE id = ?");
      prep.setString(1, this.getId());
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
   * A method to get the last name of the current Student.
   *
   * @return - a String representing the last name of the student
   */
  public String getLastName() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT last_name from user WHERE id = ?");
      prep.setString(1, this.getId());
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
      prep.setString(1, this.getId());
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get Username in Student SQL malfunction");
    }
    return null;
  }

  @Override
  public Institution getInstitution() {
    PreparedStatement prep;
    try {
      prep = conn
          .prepareStatement("SELECT institution_id from institute_students WHERE student_id = ?");
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

  @Override
  public String getFullName() {
    return this.getFirstName() + " " + this.getLastName();
  }

}
