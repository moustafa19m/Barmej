//package edu.brown.cs.student.Barmej.user;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class InstitutionTest {
//
//
//    @Test
//    public void testGetters() throws UserPermissionException {
//        Institution i1 = new Institution("-1574744672");
//        Institution i2 = new Institution("2006293320");
//
//        assertEquals(i1.getId(), "-1574744672");
//        assertEquals(i2.getId(), "2006293320");
//
//        assertEquals(i1.getName(), "Test");
//        assertEquals(i2.getName(), "Test");
//
//        assertEquals(i1.getAdmins(), new ArrayList<>(Arrays.asList(
//                new Administrator("263619665"),
//                new Administrator("-1427407784"))));
//        assertEquals(i2.getAdmins(), new ArrayList<>(Arrays.asList(
//                new Administrator("1254134866"),
//                new Administrator("-1234470692"))));
//
//        assertEquals(i1.getStudents(), new ArrayList<>(Arrays.asList(
//                new Student("89683418"))));
//        assertEquals(i2.getStudents(), new ArrayList<>(Arrays.asList(
//                new Student("2146278610"))));
//
//        assertEquals(i1.getEnrolledStudents("b9d6d52b"), new ArrayList<>());
//        assertEquals(i2.getEnrolledStudents("064b4f78"), new ArrayList<>());
//
//        assertTrue(i1.getNumEnrolled(new Administrator("263619665")) == 1);
//        assertTrue(i2.getNumEnrolled(new Administrator("1254134866")) == 1);
//
//        assertTrue(i1.getSize(new Administrator("263619665")) == 500);
//        assertTrue(i2.getSize(new Administrator("1254134866")) == 500);
//
//        assertEquals(i1.getInstitution(), i1);
//        assertEquals(i2.getInstitution(), i2);
//
//        assertEquals(i1.getType(), "School");
//        assertEquals(i2.getType(), "School");
//    }
//
//    @Test
//    public void testEqualsAndHashCode() {
//        Institution i1 = new Institution("-1574744672");
//        Institution i2 = new Institution("2006293320");
//
//        Institution i3 = new Institution("-1574744672");
//        Institution i4 = new Institution("2006293320");
//
//        assertEquals(i1, i3);
//        assertEquals(i2, i4);
//
//        assertEquals(i1.hashCode(), "-1574744672".hashCode());
//        assertEquals(i2.hashCode(), "2006293320".hashCode());
//    }

//import java.util.ArrayList;
//import java.util.Arrays;
//
//import org.junit.Test;
//
//import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;
//
//public class InstitutionTest {
//
//  @Test
//  public void testGetters() throws UserPermissionException {
//    Institution i1 = new Institution("-1574744672");
//    Institution i2 = new Institution("2006293320");
//
//    assertEquals(i1.getId(), "-1574744672");
//    assertEquals(i2.getId(), "2006293320");
//
//    assertEquals(i1.getName(), "Test");
//    assertEquals(i2.getName(), "Test");
//
//    assertEquals(i1.getAdmins(), new ArrayList<>(
//        Arrays.asList(new Administrator("263619665"), new Administrator("-1427407784"))));
//    assertEquals(i2.getAdmins(), new ArrayList<>(
//        Arrays.asList(new Administrator("1254134866"), new Administrator("-1234470692"))));
//
//    assertEquals(i1.getStudents(), new ArrayList<>(Arrays.asList(new Student("89683418"))));
//    assertEquals(i2.getStudents(), new ArrayList<>(Arrays.asList(new Student("2146278610"))));
//
//    assertEquals(i1.getEnrolledStudents("b9d6d52b"), new ArrayList<>());
//    assertEquals(i2.getEnrolledStudents("064b4f78"), new ArrayList<>());
//
//    assertTrue(i1.getNumEnrolled(new Administrator("263619665")) == 1);
//    assertTrue(i2.getNumEnrolled(new Administrator("1254134866")) == 1);
//
//    assertTrue(i1.getSize(new Administrator("263619665")) == 500);
//    assertTrue(i2.getSize(new Administrator("1254134866")) == 500);
//
//    assertEquals(i1.getInstitution(), i1);
//    assertEquals(i2.getInstitution(), i2);
//
//    assertEquals(i1.getType(), "School");
//    assertEquals(i2.getType(), "School");
//  }
//
//  @Test
//  public void testEqualsAndHashCode() {
//    Institution i1 = new Institution("-1574744672");
//    Institution i2 = new Institution("2006293320");
//
//    Institution i3 = new Institution("-1574744672");
//    Institution i4 = new Institution("2006293320");
//
//    assertEquals(i1, i3);
//    assertEquals(i2, i4);
//
//    assertEquals(i1.hashCode(), "-1574744672".hashCode());
//    assertEquals(i2.hashCode(), "2006293320".hashCode());
//  }
//
//}
