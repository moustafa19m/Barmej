package edu.brown.cs.student.Barmej.helper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.databases.Database;

/**
 * A class to populate the course table in the database with dummy courses for
 * the recommender algorithm.
 */
public class PopulateCourseTable {

  private static Connection conn = Database.getConn();

  /**
   * A method to add courses to the database.
   *
   * @throws SQLException - when something goes wrong with/in accessing the
   *                      database
   */
  public static void addCoursesToDB() throws SQLException {

    String[] level = {
        "Introduction to ", "Imtermediate ", "Semi-Advanced ", "Advanced ", "Professional "
    }; // size = 5
    String[] skill = {
        "Java ", "C ", "Javascript ", "Python ", "GoLang ", "Scala ", "Ocamel ", "Julia ",
        "OpenCL ", "Objective-C ", "C++ "
    }; // size = 11
    String[] use = {
        "for Data Analysis", "for Data Visualization", "for Designing Algorithms",
        "for Web Development", "and Data Structues", "for Graphics", "for Security Application",
        "and Cloud Services"
    }; // size = 6

    String[] url = {
        "https://images.app.goo.gl/uS4QxfeJeqemiEC46",
        "https://images.app.goo.gl/sUYx2qX652aVzF8q7",
        "https://images.app.goo.gl/uDwq2jvvVqXJ5ChS6",
        "https://images.app.goo.gl/uDwq2jvvVqXJ5ChS6",
        "https://images.app.goo.gl/DTG86WFgxQaV8TsBA", "https://images.app.goo.gl/3hLf2YYgyk3BistG8"
    }; // size =6

    Database.makeTables();
    PreparedStatement prep = null;
    for (int i = 0; i < 40; i++) {
      int iLevel = (int) (Math.random() * 5);
      String randLevel = level[iLevel];
      int iSkill = (int) (Math.random() * 11);
      String randSkill = skill[iSkill];
      String randUse = use[(int) (Math.random() * 6)];
      String name = randLevel + randSkill + randUse;
      String id = UUID.randomUUID().toString().split("-")[0];

      String stmt = "INSERT INTO course (id, title, num_modules, difficulty, "
          + "coding_language, reg_cost, thumbnail_url, max_students, math, creative, "
          + "advanced, theoretical, practical, problem, num_reviews) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
      prep = conn.prepareStatement(stmt);

      // hard-coded values for testing purposes
      if (i == 0) {
        prep.setString(1, "1");
        prep.setString(2, "Introduction to Data Science");
        prep.setInt(4, 1);
        prep.setString(7, url[0]);
        prep.setInt(8, 90);
        prep.setDouble(9, 1.0);
        prep.setDouble(10, 5.0);
        prep.setDouble(11, 3.0);
        prep.setDouble(12, 5.0);
        prep.setDouble(13, 3.0);
        prep.setDouble(14, 3.0);
      } else if (i == 1) {
        prep.setString(1, "2");
        prep.setString(2, "Introduction to Software Engineering");
        prep.setInt(4, 3);
        prep.setString(7, url[1]);
        prep.setInt(8, 150);
        prep.setDouble(9, 3.0);
        prep.setDouble(10, 5.0);
        prep.setDouble(11, 0.0);
        prep.setDouble(12, 0.0);
        prep.setDouble(13, 0.0);
        prep.setDouble(14, 4.0);
      } else {
        if (i == 2) {
          prep.setString(1, "3");
        } else {
          prep.setString(1, id);
        }

        prep.setString(2, name);
        prep.setInt(4, iLevel);
        prep.setString(7, url[(int) (Math.random() * 6)]);
        prep.setInt(8, (int) (Math.random() * 200 + 50));
        int math = ((iSkill < 3) ? 5 : (int) (Math.random() * 5));
        prep.setDouble(9, math);
        prep.setDouble(10, (int) (Math.random() * 6));
        prep.setDouble(11, iLevel);
        prep.setDouble(12, (int) (Math.random() * 6));
        prep.setDouble(13, (int) (Math.random() * 6));
        prep.setDouble(14, (int) (Math.random() * 6));
      }

      // num modules
      prep.setInt(3, (int) (Math.random() * 8));
      // coding language
      prep.setString(5, randSkill.trim());
      // cost
      prep.setDouble(6, (Math.random() * 80 + 30));

      // num reviews
      prep.setInt(15, 20);
      prep.executeUpdate();
      try {
        File adminObj = new File("src/main/resources/static/json/admin/" + id + ".json");
        adminObj.createNewFile();
        File studentObj = new File("src/main/resources/static/json/student/" + id + ".json");
        studentObj.createNewFile();
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    }
    prep.close();
  }

  /**
   * A method to get some of the Courses from the database.
   *
   * @return - a String representing this difficulty.
   */
  public static List<Course> getCourses() {
    List<Course> courses = new ArrayList<Course>();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT * FROM course WHERE difficulty = ?");
      prep.setString(1, "3");
      ResultSet rs = prep.executeQuery();
      System.out.println(rs.getFetchSize());
      while (rs.next()) {
        Course course = new Course(rs.getString(1));
        courses.add(course);
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      System.err.println("get courses in course SQL malfunction");
    }
    return courses;
  }

}
