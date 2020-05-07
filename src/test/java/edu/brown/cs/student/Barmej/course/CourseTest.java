package edu.brown.cs.student.Barmej.course;

import static org.junit.Assert.assertEquals;

import edu.brown.cs.student.Barmej.databases.Database;
import edu.brown.cs.student.Barmej.helper.PopulateCourseTable;
import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Institution;
import edu.brown.cs.student.Barmej.user.Student;
import edu.brown.cs.student.Barmej.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A class to test all the public methods in the Course class.
 */
public class CourseTest {

    private static Connection conn = null;
    List<Course> courses = new ArrayList<Course>();

    private void getDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/Barmej/Barmej.sqlite3";
        conn = DriverManager.getConnection(urlToDB);
    }

    private double getNewAverage(double newRating, double currAverage, Course course) {
        int numReviews = course.getNumReviews();
        return ((currAverage * numReviews) + newRating) / (numReviews + 1);
    }

    /**
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     *
     */
    @Before
    public void setUp() {
        try {
            Database.makeTables();
            PopulateCourseTable start = new PopulateCourseTable();
            courses = start.getCourses();

            if (courses.isEmpty()) {
              start.addCoursesToDB();
              courses = start.getCourses();
            }

        } catch (Exception e) {
            System.out.println("Course Test failing");
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        courses = new ArrayList<>();
    }

    @Test
    public void testMathRating() {
        setUp();

        Course c1 = new Course("1");
        Course c2 = new Course("2");

        System.out.println("course id!!!!!");
        System.out.println(c1.getId());

        System.out.println("course id!!!!!");
        System.out.println(c2.getId());

        c1.addMathRating(c1.getMathRating());
        c2.addMathRating(c2.getMathRating());

        assertEquals(c1.getMathRating(), 1.0, 0.1);
        assertEquals(c2.getMathRating(), 3.0, 0.1);

        assert(c1.getCategories().get(0));
        assert(c2.getCategories().get(0));

        Course c4 = new Course("3");

//        double oldRating = c4.getMathRating();
//        c4.updateMathRating(5.0);
//        assertEquals(c4.getMathRating(), getNewAverage(5.0, oldRating, c4), 0.1);
        tearDown();
    }

    @Test
    public void testCreativeRating() {
        setUp();

        Course c1 = new Course("1");
        Course c2 = new Course("2");

        c1.addCreativeRating(c1.getCreativeRating());
        c2.addCreativeRating(c2.getCreativeRating());

        assertEquals(c1.getCreativeRating(), 5.0, 0.1);
        assertEquals(c2.getCreativeRating(), 5.0, 0.1);

        assert(c1.getCategories().get(1));
        assert(c2.getCategories().get(1));

        Course c4 = new Course("3");

//        double oldRating = c4.getCreativeRating();
//        c4.updateCreativeRating(5.0);
//        assertEquals(c4.getCreativeRating(), getNewAverage(5.0, oldRating, c4), 0.1);

        tearDown();
    }

    @Test
    public void testAdvancedRating() {
        setUp();

        Course c1 = new Course("1");
        Course c2 = new Course("2");

        c1.addAdvancedRating(c1.getAdvancedRating());
        c2.addAdvancedRating(c2.getAdvancedRating());

        assertEquals(c1.getAdvancedRating(), 3.0, 0.1);
        assertEquals(c2.getAdvancedRating(), 0.0, 0.1);

        assert(c1.getCategories().get(2));
        assert(c2.getCategories().get(2));

        Course c4 = new Course("3");

//        double oldRating = c4.getAdvancedRating();
//        c4.updateAdvancedRating(5.0);
//        assertEquals(c4.getAdvancedRating(), getNewAverage(5.0, oldRating, c4), 0.1);

        tearDown();
    }

    @Test
    public void testTheoreticalRating() {
        setUp();

        Course c1 = new Course("1");
        Course c2 = new Course("2");

        c1.addTheoreticalRating(c1.getTheoreticalRating());
        c2.addTheoreticalRating(c2.getTheoreticalRating());

        assertEquals(c1.getTheoreticalRating(), 5.0, 0.1);
        assertEquals(c2.getTheoreticalRating(), 0.0, 0.1);

        assert(c1.getCategories().get(3));
        assert(c2.getCategories().get(3));
        assert(c2.getCategories().get(3));

        Course c4 = new Course("3");

//        double oldRating = c4.getTheoreticalRating();
//        c4.updateTheoreticalRating(5.0);
//        assertEquals(c4.getTheoreticalRating(), getNewAverage(5.0, oldRating, c4), 0.1);

        tearDown();
    }

    @Test
    public void testPracticalRating() {
        setUp();

        Course c1 = new Course("1");
        Course c2 = new Course("2");

        c1.addPracticalRating(c1.getPracticalRating());
        c2.addPracticalRating(c2.getPracticalRating());

        assertEquals(c1.getPracticalRating(), 3.0, 0.1);
        assertEquals(c2.getPracticalRating(), 0.0, 0.1);

        assert(c1.getCategories().get(4));
        assert(c2.getCategories().get(4));

        Course c4 = new Course("3");

//        double oldRating = c4.getPracticalRating();
//        c4.updatePracticalRating(5.0);
//        assertEquals(c4.getPracticalRating(), getNewAverage(5.0, oldRating, c4), 0.1);

        tearDown();
    }

    @Test
    public void testProblemRating() {
        setUp();

        Course c1 = new Course("1");
        Course c2 = new Course("2");

        c1.addProblemRating(c1.getProblemRating());
        c2.addProblemRating(c2.getProblemRating());

        assertEquals(c1.getProblemRating(), 3.0, 0.1);
        assertEquals(c2.getProblemRating(), 4.0, 0.1);

        assert(c1.getCategories().get(5));
        assert(c2.getCategories().get(5));

        Course c4 = new Course("3");

//        double oldRating = c4.getProblemRating();
//        c4.updateProblemRating(5.0);
//        assertEquals(c4.getProblemRating(), getNewAverage(5.0, oldRating, c4), 0.1);

        tearDown();
    }

    @Test
    public void testGetNumReviews() {
        setUp();

        Course c1 = new Course("1");

        Course c2 = new Course("2");
        int currNum = c2.getNumReviews();

        c2.updateNumReviews();

        assert(c1.getNumReviews() == 20);
        //assertEquals(java.util.Optional.ofNullable(c2.getNumReviews()), (currNum + 1));

        tearDown();
    }

    @Test
    public void testGetters() {
        Course c1 = new Course("1");
        Course c2 = new Course("2");

        assertEquals(c1.getId(), "1");
        assertEquals(c2.getId(), "2");

        assertEquals(c1.getMaxStudents(), 90);
        assertEquals(c2.getMaxStudents(), 150);

        assertEquals(c1.getStudentsEnrolled(), 0);
        assertEquals(c2.getStudentsEnrolled(), 0);

        assertEquals(c1, new Course("1"));
        assertEquals(c2, new Course("2"));

        assertEquals(c1.hashCode(), "1".hashCode());
        assertEquals(c2.hashCode(), "2".hashCode());

        assertEquals(c1.getThumbnailUrl(), "https://images.app.goo.gl/uS4QxfeJeqemiEC46");
        assertEquals(c2.getThumbnailUrl(), "https://images.app.goo.gl/sUYx2qX652aVzF8q7");

        assertEquals(c1.getName(), "Introduction to Data Science");
        assertEquals(c2.getName(), "Introduction to Software Engineering");

        assertEquals(c1.getDifficulty(), "1");
        assertEquals(c2.getDifficulty(), "3");
    }

    @Test
    public void testCoordinates() {
        Course c1 = new Course("4");

        assertEquals(c1.getCoordinates(), new ArrayList<>());

        c1.addCreativeRating(1.0);
        assertEquals(c1.getCoordinates(), new ArrayList<>(Arrays.asList(1.0)));

        c1.addProblemRating(3.0);
        assertEquals(c1.getCoordinates(), new ArrayList<>(Arrays.asList(1.0, 3.0)));

        assertEquals(c1.getIthCoordinate(0), 1.0, 0.1);
        assertEquals(c1.getIthCoordinate(1), 3.0, 0.1);
    }
}
