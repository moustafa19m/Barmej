package edu.brown.cs.student.Barmej.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class Database, creates the connection, and sets the queries.
 */

public final class Database {

  /**
   * Constructor for this class.
   */
  private Database() {
  };

  /**
   * The fields for this class.
   */
  private static Connection conn;

  /**
   * Getter for the connection.
   *
   * @return a Connection object
   */
  public static Connection getConn() {
    return conn;
  }

  /**
   * Fetches the data for the database connections, makes all the required
   * database tables.
   */
  public static void makeTables() {

    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:data/Barmej/Barmej.sqlite3";
      conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
      PreparedStatement prep;
      prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS user(id TEXT, username TEXT UNIQUE,"
          + "  first_name TEXT NOT NULL, last_name TEXT NOT NULL, "
          + "password_hash TEXT NOT NULL, user_type TEXT NOT NULL, PRIMARY KEY(id));");
      prep.executeUpdate();
      prep = conn
          .prepareStatement("CREATE TABLE IF NOT EXISTS institution(id TEXT, name TEXT NOT NULL, "
              + "type TEXT NOT NULL, size TEXT NOT NULL, address TEXT, PRIMARY KEY(id));");
      prep.executeUpdate();
      prep = conn
          .prepareStatement("CREATE TABLE IF NOT EXISTS course(id TEXT, title TEXT NOT NULL, "
              + "num_modules INT NOT NULL, difficulty TEXT, coding_language TEXT, "
              + "reg_cost DOUBLE, thumbnail_url TEXT, max_students INT NOT NULL, "
              + "math DOUBLE, creative DOUBLE, advanced DOUBLE, theoretical DOUBLE, "
              + "practical DOUBLE, problem DOUBLE, num_reviews INT, PRIMARY KEY(id));");
      prep.executeUpdate();
      prep = conn.prepareStatement(
          "CREATE TABLE IF NOT EXISTS course_page(id TEXT, course_id TEXT, page_url TEXT, "
              + "PRIMARY KEY(id), FOREIGN KEY (course_id) REFERENCES course(id));");
      prep.executeUpdate();
      prep = conn.prepareStatement(
          "CREATE TABLE IF NOT EXISTS enrolled_courses(student_id TEXT, course_id TEXT, "
              + "institution_id TEXT, FOREIGN KEY (course_id) REFERENCES course(id), "
              + "FOREIGN KEY (student_id) REFERENCES user(id), "
              + "FOREIGN KEY (institution_id) REFERENCES institution(id));");
      prep.executeUpdate();
      prep = conn
          .prepareStatement("CREATE TABLE IF NOT EXISTS purchased_courses(institution_id TEXT, "
              + "course_id TEXT, FOREIGN KEY (course_id) REFERENCES course(id), "
              + "FOREIGN KEY (institution_id) REFERENCES institution(id));");
      prep.executeUpdate();
      prep = conn
          .prepareStatement("CREATE TABLE IF NOT EXISTS institute_admins(institution_id TEXT, "
              + "administrator_id TEXT, key_admin INT NOT NULL, "
              + "FOREIGN KEY (institution_id) REFERENCES institution(id), "
              + "FOREIGN KEY (administrator_id) REFERENCES user(id));");
      prep.executeUpdate();
      prep = conn
          .prepareStatement("CREATE TABLE IF NOT EXISTS institute_students(institution_id TEXT, "
              + "student_id TEXT, FOREIGN KEY (institution_id) REFERENCES institution(id),"
              + " FOREIGN KEY (student_id) REFERENCES user(id));");
      prep.executeUpdate();
      prep.close();
    } catch (SQLException e) {
      String output = "ERROR: SQL incorrect";
      e.printStackTrace();
      System.err.println(output);
    } catch (ClassNotFoundException e) {
      String output = "ERROR: Class not found";
      System.err.println(output);
    }
  }

}
