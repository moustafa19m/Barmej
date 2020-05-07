//package edu.brown.cs.student.Barmej.user;
//
//import edu.brown.cs.student.Barmej.databases.Database;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import static edu.brown.cs.student.Barmej.user.Authenticate.authenticateAdmin;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class AuthenticateTest {
//
//  Administrator admin = null;
//  Administrator admin_2 = null;
//  Student student = null;
//  Institution institution1 = null;
//  Institution institution2 = null;
//
//  /**
//   * @throws NoSuchAlgorithmException
//   * @throws NoSuchProviderException
//   */
//  @Before
//  public void setUp() {
//    try {
//      Database.makeTables();
//      List<String> inst1 = new ArrayList<>();
//      inst1.add("Test");
//      inst1.add("School");
//      inst1.add("500");
//      inst1.add("sauce street");
//      List<String> admin1 = new ArrayList<>();
//      Random rand = new Random();
//      String unique = Integer.toString(((Long) rand.nextLong()).hashCode());
//      admin1.add("test666@admin.com" + unique);
//      admin1.add("Test Key Admin");
//      admin1.add("1");
//      admin1.add("123");
//      Institution.institutionSignUp(inst1, admin1);
//      admin = new Administrator(User.findIdFromUsername("test666@admin.com" + unique));
//      institution1 = admin.getInstitution();
//      List<String> inst2 = new ArrayList<>();
//      inst2.add("Test");
//      inst2.add("School");
//      inst2.add("500");
//      inst2.add("sauce street");
//      List<String> admin2 = new ArrayList<>();
//      unique = Integer.toString(((Long) rand.nextLong()).hashCode());
//      admin2.add("testingcrabs2@admin.com" + unique);
//      admin2.add("Test Admin");
//      admin2.add("1");
//      admin2.add("123");
//      Institution.institutionSignUp(inst1, admin2);
//      admin_2 = new Administrator(User.findIdFromUsername("testingcrabs2@admin.com" + unique));
//      institution2 = admin_2.getInstitution();
//      List<String> student1 = new ArrayList<>();
//      student1.add("testassadas44d3@student.com" + unique);
//      student1.add("Test Student");
//      student1.add("1");
//      student1.add("123");
//      student1.add(admin.getInstitution().getId());
//      User.signUp(student1, "student");
//      student = new Student(User.findIdFromUsername("testassadas44d3@student.com" + unique));
//      String[] username1 = {
//          "test1@admin.com" + unique, "testsalsa2@admin.com" + unique,
//          "testsauce3@student.com" + unique
//      };
//    } catch (Exception e) {
//      System.out.println("Administrator Test failing");
//      e.printStackTrace();
//    }
//  }
//
//  /**
//   *
//   */
//  @After
//  public void tearDown() {
//    admin = null;
//    admin_2 = null;
//    student = null;
//    institution1 = null;
//    institution2 = null;
//  }
//
//  @Test
//  public void testAuthenticateAdmin() {
//    setUp();
//    // correct user type, correct institution
//    assertTrue(authenticateAdmin(admin, institution1));
//
//    // correct user type, incorrect institution
//    assertFalse(authenticateAdmin(admin, institution2));
//
//    // incorrect student type
//    assertFalse(authenticateAdmin(student, institution1));
//
//    tearDown();
//  }
//
//}
