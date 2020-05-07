package edu.brown.cs.student.Barmej.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.databases.Database;
import edu.brown.cs.student.Barmej.helper.PopulateCourseTable;

public class StudentTest {

  Administrator admin_1 = null;
  Administrator admin_2 = null;
  Student student_1 = null;
  String[] username = null;
  List<Course> courses = new ArrayList<Course>();

  /**
   * @throws NoSuchAlgorithmException
   * @throws NoSuchProviderException
   *
   */
  @Before
  public void setUp() {
    try {
      Database.makeTables();
      List<String> inst1 = new ArrayList<String>();
      PopulateCourseTable start = new PopulateCourseTable();
      if (start.getCourses().size() == 0) {
        start.addCoursesToDB();
      }
      courses = start.getCourses();
      inst1.add("Test");
      inst1.add("School");
      inst1.add("500");
      inst1.add("sauce street");
      List<String> admin1 = new ArrayList<String>();
      Random rand = new Random();
      String unique = Integer.toString(((Long) rand.nextLong()).hashCode());
      admin1.add("test112313@admin.com" + unique);
      admin1.add("Test Key Admin");
      admin1.add("1");
      admin1.add("123");
      Institution.institutionSignUp(inst1, admin1);
      admin_1 = new Administrator(User.findIdFromUsername("test112313@admin.com" + unique));
      List<String> admin2 = new ArrayList<String>();
      admin2.add("testsauceeeq232132@admin.com" + unique);
      admin2.add("Test Admin");
      admin2.add("2");
      admin2.add("123");
      admin2.add(admin_1.getInstitution().getId());
      User.signUp(admin2, "administrator");
      List<String> student1 = new ArrayList<String>();
      student1.add("testribosom3433@student.com" + unique);
      student1.add("Test Student");
      student1.add("1");
      student1.add("123");
      student1.add(admin_1.getInstitution().getId());
      User.signUp(student1, "student");
      admin_2 = new Administrator(User.findIdFromUsername("testsauceeeq232132@admin.com" + unique));
      student_1 = new Student(User.findIdFromUsername("testribosom3433@student.com" + unique));
      String[] username1 = {
          "test112313@admin.com" + unique, "testsauceeeq232132@admin.com" + unique,
          "testribosom3433@student.com" + unique
      };
      username = username1;
    } catch (Exception e) {
      System.out.println("Administrator Test failing");
      e.printStackTrace();
    }
  }

  /**
   *
   */
  @After
  public void tearDown() {
    admin_1 = null;
    admin_2 = null;
    student_1 = null;
  }

  @Test
  public void getEnrolledInTest() {
    setUp();
    Course course1 = courses.get(0);
    Set<Course> courses = student_1.getEnrolledIn();
    assertFalse(courses.contains(course1));
    admin_1.addStudentToCourse(student_1, course1);
    Set<Course> course = student_1.getEnrolledIn();
    assertTrue(course.contains(course1));
    tearDown();
  }

  @Test
  public void gettersTest() {
    setUp();
    String first_name = student_1.getFirstName();
    assertTrue(first_name.equals("Test Student"));
    String last_name = student_1.getLastName();
    assertTrue(last_name.equals("1"));
    String user = student_1.getUserName();
    assertTrue(user.equals(username[2]));
    String type = student_1.getType();
    assertTrue(type.equals("student"));
    Institution institute = new Institution(admin_1.getInstitution().getId());
    assertTrue(institute.getId().equals(student_1.getInstitution().getId()));
    String name = student_1.getFullName();
    assertTrue(name.equals(first_name + " " + last_name));
    tearDown();
  }
}
