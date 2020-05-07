package edu.brown.cs.student.Barmej.recommender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import edu.brown.cs.student.Barmej.algorithm.KNearestNeighbors;
import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.dataStructures.KdNode;
import edu.brown.cs.student.Barmej.dataStructures.KdTree;
import edu.brown.cs.student.Barmej.databases.Database;

/**
 * A course recommender class that deals with extracting courses from the
 * database in accordance to the requirements indicated by an institution.
 */
public class CourseRecommender {

  /**
   * The fields for this class.
   */
  private static Connection conn = Database.getConn();
  private static final int MAX_K = 20;

  /**
   * A method to get a list of KdNodes where each node represents a course, where
   * each course fits the criteria indicated by an institution in the
   * course-recommender feature.
   *
   * @param target - a Course created with the requirements the institution
   *               entered.
   * @return a List of KdNodes where each node is a course
   */
  private List<KdNode<Course>> getAllCourses(Course target) {
    List<KdNode<Course>> courses = new ArrayList<>();
    PreparedStatement prep;

    List<Boolean> categories = target.getCategories();

    try {
      prep = conn.prepareStatement("SELECT id from course");
      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        String id = rs.getString(1);
        Course course = new Course(id);

        if (categories.get(0)) {
          course.addMathRating(course.getMathRating());
        }

        if (categories.get(1)) {
          course.addCreativeRating(course.getCreativeRating());
        }

        if (categories.get(2)) {
          course.addAdvancedRating(course.getAdvancedRating());
        }

        if (categories.get(3)) {
          course.addPracticalRating(course.getTheoreticalRating());
        }

        if (categories.get(4)) {
          course.addPracticalRating(course.getPracticalRating());
        }

        if (categories.get(5)) {
          course.addProblemRating(course.getProblemRating());
        }

        KdNode<Course> node = new KdNode(course);
        courses.add(node);
      }

    } catch (SQLException e) {
      System.err.println("getAllCourses in ContentBasedRecommender SQL Malfunction");
    }

    return courses;
  }

  /**
   * A method to get a list of courses, where each course fits the criteria
   * indicated by an institution in the course-recommender feature.
   *
   * @param ratings - a list of doubles representing course ratings
   * @param k       - the number of courses to be displayed or recommended to the
   *                institution
   * @return a List of courses
   */
  public List<Course> getRecommendations(List<Double> ratings, int k) {

    if (k > MAX_K) {
      k = MAX_K;
    }

    Course target = new Course("");
    int numCategories = 0;
    boolean isEmpty = true;

    if (ratings.get(0) > 0) {
      target.addMathRating(ratings.get(0));
      numCategories += 1;
      isEmpty = false;
    }

    if (ratings.get(1) > 0) {
      target.addCreativeRating(ratings.get(1));
      numCategories += 1;
      isEmpty = false;
    }

    if (ratings.get(2) > 0) {
      target.addAdvancedRating(ratings.get(2));
      numCategories += 1;
      isEmpty = false;
    }

    if (ratings.get(3) > 0) {
      target.addTheoreticalRating(ratings.get(3));
      numCategories += 1;
      isEmpty = false;
    }

    if (ratings.get(4) > 0) {
      target.addPracticalRating(ratings.get(4));
      numCategories += 1;
      isEmpty = false;
    }

    if (ratings.get(5) > 0) {
      target.addProblemRating(ratings.get(5));
      numCategories += 1;
      isEmpty = false;
    }

    KdNode<Course> targetNode = new KdNode(target);
    List<KdNode<Course>> courses = getAllCourses(target);

    System.out.println("course SIZE");
    System.out.println(courses.size());

    List<Course> result = new ArrayList<>();

    if (isEmpty) {
      Random r = new Random();
      Set<Integer> usedIndices = new HashSet<>();
      int index = r.ints(0, courses.size()).findFirst().getAsInt();
      int i = 0;

      while (i < k) {
        while (usedIndices.contains(index)) {
          index = r.ints(0, courses.size()).findFirst().getAsInt();
        }
        usedIndices.add(index);
        result.add(courses.get(index).getNodeVal());
        i++;
      }

      return result;
    }

    KdTree<Course> courseTree = new KdTree<>(courses, numCategories);
    KNearestNeighbors<Course> knn = new KNearestNeighbors();
    List<KdNode<Course>> resultNodes = knn.kNearestNeighbors(targetNode, courseTree.getRoot(), k,
        numCategories);

    for (KdNode<Course> node : resultNodes) {
      result.add(node.getNodeVal());
    }

    return result;
  }

}
