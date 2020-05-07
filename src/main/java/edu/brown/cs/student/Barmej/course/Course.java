package edu.brown.cs.student.Barmej.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.brown.cs.student.Barmej.dataStructures.Coordinates;
import edu.brown.cs.student.Barmej.databases.Database;
import edu.brown.cs.student.Barmej.user.Institution;

/**
 * A class for the construction and handling of Courses.
 *
 */
public class Course implements Coordinates {

  // All the fields for this class.
  private String id;
  private String name;
  private String difficulty;
  private int maxStudents;
  private String thumbnailUrl;
  private List<Double> ratings;
  private List<Boolean> ratingCategories;
  private static Connection conn = Database.getConn();

  // Indices for the rating of courses.
  private static final int MATH_INDEX = 0;
  private static final int CREATIVE_INDEX = 1;
  private static final int ADVANCED_INDEX = 2;
  private static final int THEORETICAL_INDEX = 3;
  private static final int PRACTICAL_INDEX = 4;
  private static final int PROBLEM_INDEX = 5;

  /**
   * A constructor for a Course.
   *
   * @param id - a String representing the ID of the course
   */
  public Course(String id) {
    this.id = id;
    maxStudents = -1;
    this.ratings = new ArrayList<>();
    this.ratingCategories = new ArrayList<>(
        Arrays.asList(false, false, false, false, false, false));
    this.thumbnailUrl = null;
    this.name = null;
  }

  /**
   * A method to add the math rating to a Course.
   *
   * @param rating - a Double representing the math rating of the course
   */
  public void addMathRating(double rating) {
    ratings.add(rating);
    ratingCategories.set(MATH_INDEX, true);
  }

  /**
   * A method to add the creative rating to a Course.
   *
   * @param rating - a Double representing the creative rating of the course
   */
  public void addCreativeRating(double rating) {
    ratings.add(rating);
    ratingCategories.set(CREATIVE_INDEX, true);
  }

  /**
   * A method to add the advanced rating to a Course.
   *
   * @param rating - a Double representing the advanced rating of the course
   */
  public void addAdvancedRating(double rating) {
    ratings.add(rating);
    ratingCategories.set(ADVANCED_INDEX, true);
  }

  /**
   * A method to add the theory rating to a Course.
   *
   * @param rating - a Double representing the theory rating of the course
   */
  public void addTheoreticalRating(double rating) {
    ratings.add(rating);
    ratingCategories.set(THEORETICAL_INDEX, true);
  }

  /**
   * A method to add the practical rating to a Course.
   *
   * @param rating - a Double representing the practical rating of the course
   */

  public void addPracticalRating(double rating) {
    ratings.add(rating);
    ratingCategories.set(PRACTICAL_INDEX, true);
  }

  /**
   * A method to add the problem-solving rating to a Course.
   *
   * @param rating - a Double representing the problem-solving rating of the
   *               course
   */
  public void addProblemRating(double rating) {
    ratings.add(rating);
    ratingCategories.set(PROBLEM_INDEX, true);
  }

  /**
   * Getter for the field ratingCategories.
   *
   * @return - a list of booleans representing the ratingCategories field of the
   *         course
   */
  public List<Boolean> getCategories() {
    return ratingCategories;
  }

  /**
   * A method to get all the institutions that have purchased this course.
   *
   * @return a Set of Institutions that have bought this course
   */
  public Set<Institution> getOwnerInstitutes() {
    Set<Institution> institutes = new HashSet<>();
    PreparedStatement prep;
    try {
      prep = conn
          .prepareStatement("SELECT institute_id from purchased_courses WHERE course_id = ?");
      prep.setString(1, id);
      prep.executeUpdate();
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String institutionId = rs.getString(1);
        institutes.add(new Institution(institutionId));
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      System.err.println("get Courses in Student SQL malfunction");
    }
    return institutes;
  }

  /**
   * Getter for the math rating.
   *
   * @return - a Double representing the math rating
   */
  public Double getMathRating() {
    PreparedStatement prep;

    try {
      Connection conn = Database.getConn();
      prep = conn.prepareStatement("SELECT math from course where id = ?");
      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        return rs.getDouble(1);
      }

    } catch (SQLException e) {
      System.err.println("err in getMathRating");
    }
    return null;
  }

  /**
   * Getter for the creative rating.
   *
   * @return - a Double representing the creative rating
   */
  public Double getCreativeRating() {
    PreparedStatement prep;

    try {
      prep = conn.prepareStatement("SELECT creative from course where id = ?");
      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        return rs.getDouble(1);
      }

    } catch (SQLException e) {
      System.err.println("err in getMathRating");
    }
    return null;
  }

  /**
   * Getter for the advanced rating.
   *
   * @return - a Double representing the advanced rating
   */
  public Double getAdvancedRating() {
    PreparedStatement prep;

    try {
      prep = conn.prepareStatement("SELECT advanced from course where id = ?");
      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        return rs.getDouble(1);
      }

    } catch (SQLException e) {
      System.err.println("err in getMathRating");
    }
    return null;
  }

  /**
   * Getter for the theory rating.
   *
   * @return - a Double representing the theory rating
   */
  public Double getTheoreticalRating() {
    PreparedStatement prep;

    try {
      prep = conn.prepareStatement("SELECT theoretical from course where id = ?");
      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        return rs.getDouble(1);
      }

    } catch (SQLException e) {
      System.err.println("err in getMathRating");
    }
    return null;
  }

  /**
   * Getter for the practical rating.
   *
   * @return - a Double representing the practical rating
   */
  public Double getPracticalRating() {
    PreparedStatement prep;

    try {
      prep = conn.prepareStatement("SELECT practical from course where id = ?");
      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        return rs.getDouble(1);
      }

    } catch (SQLException e) {
      System.err.println("err in getMathRating");
    }
    return null;
  }

  /**
   * Getter for the problem-solving rating.
   *
   * @return - a Double representing the problem-solving rating
   */
  public Double getProblemRating() {
    PreparedStatement prep;

    try {
      prep = conn.prepareStatement("SELECT problem from course where id = ?");
      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        return rs.getDouble(1);
      }

    } catch (SQLException e) {
      System.err.println("err in getMathRating");
    }
    return null;
  }

  /**
   * Method to retrieve the num_reviews value of a course from the SQL database.
   *
   * @return - an integer representing the value of num_reviews for the course
   */
  public Integer getNumReviews() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT num_reviews from course where id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      return rs.getInt(1);
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
    return null;
  }

  /**
   * A method to update a Course's value for num_reviews in the SQL database.
   */
  public void updateNumReviews() {
    PreparedStatement prep;
    try {
      int currNum = getNumReviews();
      prep = conn.prepareStatement("UPDATE course SET num_reviews = ? WHERE id = ?");
      prep.setInt(1, (currNum + 1));
      prep.setString(2, id);
      prep.executeUpdate();
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
  }

  /**
   * A method to update the value of a certain attribute passed in as a string.
   *
   * @param attribute  - the String representing the attribute whose value is to
   *                   be updated
   * @param newRating  - the new value of the attribute
   * @param currRating - the old value of the attribute
   * @return
   */
  private Double updateAttribute(String attribute, Double newRating, Double currRating) {
    PreparedStatement prep;
    try {
      Integer numReviews = getNumReviews();
      double reviewSum = currRating * numReviews + newRating;
      double review = reviewSum / (numReviews + 1);

      updateNumReviews();

      prep = conn.prepareStatement("UPDATE course SET ? = ? WHERE id = ?");
      prep.setString(1, attribute);
      prep.setDouble(2, review);
      prep.setString(3, id);
      prep.executeUpdate();

      return review;
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
    return null;
  }

  /**
   * A method to update the math rating.
   *
   * @param newRating - a double representing the new math rating
   */
  public void updateMathRating(double newRating) {
    PreparedStatement prep;
    try {
      double currRating = getMathRating();
      Integer numReviews = getNumReviews();
      double reviewSum = currRating * numReviews + newRating;
      double review = reviewSum / (numReviews + 1);

      updateNumReviews();

      prep = conn.prepareStatement("UPDATE course SET math = ? WHERE id = ?");
      prep.setDouble(1, review);
      prep.setString(2, id);
      prep.executeUpdate();
      ratings.set(MATH_INDEX, review);
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
  }

  /**
   * A method to update the creative rating.
   *
   * @param newRating - a double representing the new creative rating
   */
  public void updateCreativeRating(double newRating) {
    PreparedStatement prep;
    try {
      double currRating = getCreativeRating();
      Integer numReviews = getNumReviews();
      double reviewSum = currRating * numReviews + newRating;
      double review = reviewSum / (numReviews + 1);
      updateNumReviews();
      prep = conn.prepareStatement("UPDATE course SET creative = ? WHERE id = ?");
      prep.setDouble(1, review);
      prep.setString(2, id);
      prep.executeUpdate();
      ratings.set(CREATIVE_INDEX, review);
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
  }

  /**
   * A method to update the advanced rating.
   *
   * @param newRating - a double representing the new advanced rating
   */
  public void updateAdvancedRating(double newRating) {
    PreparedStatement prep;
    try {
      double currRating = getAdvancedRating();
      Integer numReviews = getNumReviews();
      double reviewSum = currRating * numReviews + newRating;
      double review = reviewSum / (numReviews + 1);

      updateNumReviews();

      prep = conn.prepareStatement("UPDATE course SET advanced = ? WHERE id = ?");
      prep.setDouble(1, review);
      prep.setString(2, id);
      prep.executeUpdate();
      ratings.set(ADVANCED_INDEX, review);
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
  }

  /**
   * A method to update the theory rating.
   *
   * @param newRating - a double representing the new theory rating
   */
  public void updateTheoreticalRating(double newRating) {
    PreparedStatement prep;
    try {
      double currRating = getTheoreticalRating();
      Integer numReviews = getNumReviews();
      double reviewSum = currRating * numReviews + newRating;
      double review = reviewSum / (numReviews + 1);

      updateNumReviews();

      prep = conn.prepareStatement("UPDATE course SET theoretical = ? WHERE id = ?");
      prep.setDouble(1, review);
      prep.setString(2, id);
      prep.executeUpdate();
      ratings.set(THEORETICAL_INDEX, review);
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
  }

  /**
   * A method to update the practical rating.
   *
   * @param newRating - a double representing the new practical rating
   */
  public void updatePracticalRating(double newRating) {
    PreparedStatement prep;
    try {
      double currRating = getPracticalRating();
      Integer numReviews = getNumReviews();
      double reviewSum = currRating * numReviews + newRating;
      double review = reviewSum / (numReviews + 1);

      updateNumReviews();

      prep = conn.prepareStatement("UPDATE course SET practical = ? WHERE id = ?");
      prep.setDouble(1, review);
      prep.setString(2, id);
      prep.executeUpdate();
      ratings.set(PRACTICAL_INDEX, review);
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
  }

  /**
   * A method to update the problem-solving rating.
   *
   * @param newRating - a double representing the new problem-solving rating
   */
  public void updateProblemRating(double newRating) {
    PreparedStatement prep;
    try {
      double currRating = getProblemRating();
      Integer numReviews = getNumReviews();
      double reviewSum = currRating * numReviews + newRating;
      double review = reviewSum / (numReviews + 1);

      updateNumReviews();

      prep = conn.prepareStatement("UPDATE problem SET math = ? WHERE id = ?");
      prep.setDouble(1, review);
      prep.setString(2, id);
      prep.executeUpdate();
      ratings.set(PROBLEM_INDEX, review);
    } catch (SQLException e) {
      System.err.println("get Num Reviews in Student SQL malfunction");
    }
  }

  /**
   * Getter for the id field of a Course.
   *
   * @return a String representing the id of the Course
   */
  public String getId() {
    return id;
  }

  /**
   * Getter for the max_students value of a Course from the SQL database.
   *
   * @return an integer representing the value of max_students
   */
  public int getMaxStudents() {
    if (maxStudents < 0) {
      PreparedStatement prep;
      try {
        prep = conn.prepareStatement("SELECT max_students FROM course WHERE id = ?");
        prep.setString(1, this.id);
        ResultSet rs = prep.executeQuery();
        int value = rs.getInt(1);
        rs.close();
        prep.close();
        maxStudents = value;
      } catch (Exception e) {
        System.err.println("get Name in ADMIN SQL malfunction");
        e.printStackTrace();
        maxStudents = -1;
      }
    }
    return maxStudents;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Course)) {
      return false;
    }

    // compare using IDs because IDs are unique
    Course otherCourse = (Course) obj;
    return otherCourse.getId().equals(this.id);
  }

  @Override
  public int hashCode() {
    System.out.println(id);
    return id.hashCode();
  }

  /**
   * A method to get the number of students enrolled in the course.
   *
   * @return - an integer representing this number
   */
  public int getStudentsEnrolled() {
    PreparedStatement prep;
    try {
      prep = conn
          .prepareStatement("SELECT COUNT(student_id) FROM enrolled_courses WHERE course_id = ?");
      prep.setString(1, this.id);
      ResultSet rs = prep.executeQuery();
      int value = rs.getInt(1);
      rs.close();
      prep.close();
      return value;
    } catch (Exception e) {
      System.err.println("get Name in ADMIN SQL malfunction");
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * A method to get the thumbnail_url of a Course from the database.
   *
   * @return - a String representing this url
   */
  public String getThumbnailUrl() {
    if (this.thumbnailUrl == null) {
      PreparedStatement prep;
      try {
        Connection conn = Database.getConn();
        prep = conn.prepareStatement("SELECT thumbnail_url FROM course WHERE id = ?");
        prep.setString(1, this.id);
        ResultSet rs = prep.executeQuery();
        String value = rs.getString(1);
        rs.close();
        prep.close();
        this.thumbnailUrl = value;
      } catch (SQLException e) {
        System.err.println("get Name in ADMIN SQL malfunction");
        this.thumbnailUrl = null;
      }
    }
    return this.thumbnailUrl;
  }

  /**
   * A method to get the name of a Course from the database.
   *
   * @return - a String representing this name
   */
  public String getName() {
    if (this.name == null) {
      PreparedStatement prep;
      try {
        prep = conn.prepareStatement("SELECT title FROM course WHERE id = ?");
        prep.setString(1, this.id);
        ResultSet rs = prep.executeQuery();
        String value = rs.getString(1);
        rs.close();
        prep.close();
        this.name = value;
      } catch (SQLException e) {
        System.err.println("get Name in ADMIN SQL malfunction");
        this.name = null;
      }
    }
    return this.name;
  }

  /**
   * A method to get the difficulty of a Course from the database.
   *
   * @return - a String representing this difficulty.
   */
  public String getDifficulty() {
    if (this.difficulty == null) {
      PreparedStatement prep;
      try {
        prep = conn.prepareStatement("SELECT difficulty FROM course WHERE id = ?");
        prep.setString(1, this.id);
        ResultSet rs = prep.executeQuery();
        String value = rs.getString(1);
        rs.close();
        prep.close();
        this.difficulty = value;
      } catch (SQLException e) {
        System.err.println("get Name in ADMIN SQL malfunction");
        this.difficulty = null;
      }
    }
    return this.difficulty;
  }

  @Override
  public List<Double> getCoordinates() {
    return ratings;
  }

  @Override
  public double getIthCoordinate(int i) {
    return ratings.get(i);
  }

}
