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

public class AdministratorTest {

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
      admin1.add("test1@admin.com" + unique);
      admin1.add("Test Key Admin");
      admin1.add("1");
      admin1.add("123");
      Institution.institutionSignUp(inst1, admin1);
      admin_1 = new Administrator(User.findIdFromUsername("test1@admin.com" + unique));
      List<String> admin2 = new ArrayList<String>();
      admin2.add("testsalsa2@admin.com" + unique);
      admin2.add("Test Admin");
      admin2.add("2");
      admin2.add("123");
      admin2.add(admin_1.getInstitution().getId());
      User.signUp(admin2, "administrator");
      List<String> student1 = new ArrayList<String>();
      student1.add("testsauce3@student.com" + unique);
      student1.add("Test Student");
      student1.add("1");
      student1.add("123");
      student1.add(admin_1.getInstitution().getId());
      User.signUp(student1, "student");
      admin_2 = new Administrator(User.findIdFromUsername("testsalsa2@admin.com" + unique));
      student_1 = new Student(User.findIdFromUsername("testsauce3@student.com" + unique));
      String[] username1 = {
          "test1@admin.com" + unique, "testsalsa2@admin.com" + unique,
          "testsauce3@student.com" + unique
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
    courses = new ArrayList<Course>();
  }

  @Test
  public void buyAndGetCoursesTest() {
    setUp();
    String courseId = courses.get(0).getId();
    admin_1.buyCourse(courseId);
    Set<Course> purchased = admin_1.getCourses();
    assertTrue(purchased.contains(courses.get(0)));
    purchased.remove(courses.get(0));
    assertTrue(purchased.isEmpty());
    String courseId1 = courses.get(1).getId();
    admin_1.buyCourse(courseId1);
    purchased = admin_1.getCourses();
    assertTrue(purchased.contains(courses.get(0)));
    assertTrue(purchased.contains(courses.get(1)));
    tearDown();
  }

  @Test
  public void gettersTest() {
    setUp();
    String first_name = admin_1.getFirstName();
    assertTrue(first_name.equals("Test Key Admin"));
    String last_name = admin_1.getLastName();
    assertTrue(last_name.equals("1"));
    String user = admin_1.getUserName();
    assertTrue(user.equals(username[0]));
    String type = admin_1.getType();
    assertTrue(type.equals("key-administrator"));
    Institution institute = new Institution(admin_1.getInstitution().getId());
    assertTrue(institute.getId().equals(admin_1.getInstitution().getId()));
    String name = admin_1.getFullName();
    assertTrue(name.equals(first_name + " " + last_name));
    tearDown();
  }

  @Test
  public void addStudentToCourseAndGetStudentsNotInTest() {
    setUp();
    Course course1 = courses.get(0);
    Set<Course> courses = student_1.getEnrolledIn();
    assertFalse(courses.contains(course1));
    admin_1.addStudentToCourse(student_1, course1);
    Set<Course> course = student_1.getEnrolledIn();
    assertTrue(course.contains(course1));
//    assertTrue(admin_1.getStudentsNotIn(courses.get(1).getId()).contains(student_1));
    tearDown();
  }
}
