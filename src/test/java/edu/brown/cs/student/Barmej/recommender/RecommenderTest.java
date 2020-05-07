//package edu.brown.cs.student.Barmej.recommender;
//
//import static org.junit.Assert.assertEquals;
//
//import edu.brown.cs.student.Barmej.course.Course;
//import edu.brown.cs.student.Barmej.dataStructures.KdNode;
//import org.junit.Test;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class RecommenderTest {
//
//    private double euclideanDist(List<Double> n1, List<Double> n2) {
//        double squaredDifferenceSum = 0;
//        for (int i = 0; i < n1.size(); i++) {
//            squaredDifferenceSum += Math.pow(n1.get(i) - n2.get(i), 2);
//        }
//        return Math.sqrt(squaredDifferenceSum);
//    }
//
//    public void sortByDistance(List<KdNode<Course>> nodes, KdNode<Course> target) {
//        int len = nodes.size();
//        for (int i = 0; i < len - 1; i++) {
//            for (int j = 0; j < len - i - 1; j++) {
//                if (euclideanDist(target.getNodeVal().getCoordinates(),
//                        nodes.get(j).getNodeVal().getCoordinates()) > euclideanDist(
//                        target.getNodeVal().getCoordinates(),
//                        nodes.get(j + 1).getNodeVal().getCoordinates())) {
//                    KdNode<Course> temp = nodes.get(j);
//                    nodes.set(j, nodes.get(j + 1));
//                    nodes.set(j + 1, temp);
//                }
//            }
//        }
//    }
//
//    public ArrayList<KdNode<Course>> naiveKnn(List<KdNode<Course>> nodes, KdNode<Course> target,
//                                            int k) {
//        sortByDistance(nodes, target);
//
//        ArrayList<KdNode<Course>> result = new ArrayList<>();
//
//        for (KdNode<Course> node : nodes) {
//            if (node.getNodeVal().getId() != target.getNodeVal().getId()) {
//                result.add(node);
//            }
//        }
//
//        if (result.size() <= k) {
//            return result;
//        } else {
//            return new ArrayList<>(result.subList(0, k));
//        }
//    }
//
//    private List<Course> extractCourses(List<KdNode> nodes) {
//        List<Course> courses = new ArrayList<>();
//
//        for (KdNode node: nodes) {
//            courses.add((Course) node.getNodeVal());
//        }
//        return courses;
//    }
//
//    private List<KdNode<Course>> getAllCourses(Course target) {
//        List<KdNode<Course>> courses = new ArrayList<>();
//        PreparedStatement prep;
//
//        List<Boolean> categories = target.getCategories();
//
//        try {
//            prep = conn.prepareStatement("SELECT id from course");
//            ResultSet rs = prep.executeQuery();
//
//            while (rs.next()) {
//                String id = rs.getString(1);
//                Course course = new Course(id);
//
//                if (categories.get(0)) {
//                    course.addMathRating(course.getMathRating());
//                }
//
//                if (categories.get(1)) {
//                    course.addCreativeRating(course.getCreativeRating());
//                }
//
//                if (categories.get(2)) {
//                    course.addAdvancedRating(course.getAdvancedRating());
//                }
//
//                if (categories.get(3)) {
//                    course.addPracticalRating(course.getTheoreticalRating());
//                }
//
//                if (categories.get(4)) {
//                    course.addPracticalRating(course.getPracticalRating());
//                }
//
//                if (categories.get(5)) {
//                    course.addProblemRating(course.getProblemRating());
//                }
//
//                KdNode<Course> node = new KdNode(course);
//                courses.add(node);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("getAllCourses in ContentBasedRecommender SQL Malfunction");
//        }
//
//        return courses;
//    }
//
//    @Test
//    public void testRecommender() {
//
//        CourseRecommender r = new CourseRecommender();
//
//        assertEquals(r.getRecommendations(new ArrayList<>(Arrays.asList(1.0, 2.0)), 0),
//                new ArrayList<>());
//
//
//        Course tc1 = new Course("");
//        tc1.addMathRating(1.0);
//        tc1.addCreativeRating(2.0);
//        KdNode<Course> tn1 = new KdNode<>(tc1);
//        List<KdNode<Course>> cl1 = getAllCourses(tc1);
//        assertEquals(r.getRecommendations(new ArrayList<>(Arrays.asList(1.0, 2.0, 0.0, 0.0, 0.0, 0.0)), 1),
//                naiveKnn(cl1, tn1, 1));
//
//        assertEquals(r.getRecommendations(new ArrayList<>(Arrays.asList(1.0, 2.0, 0.0, 0.0, 0.0, 0.0)), 2),
//                naiveKnn(cl1, tn1, 2));
//
//
//        Course tc2 = new Course("");
//        tc2.addTheoreticalRating(3.7);
//        tc2.addProblemRating(4.2);
//        KdNode<Course> tn2 = new KdNode<>(tc2);
//        List<KdNode<Course>> cl2 = getAllCourses(tc2);
//        assertEquals(r.getRecommendations(new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 3.7, 0.0, 4.2)), 3),
//                naiveKnn(cl2, tn2, 3));
//
//        Course tc3 = new Course("");
//        tc3.addMathRating(3.5);
//        tc3.addCreativeRating(2.0);
//        tc3.addAdvancedRating(1.0);
//        tc3.addTheoreticalRating(5.0);
//        tc3.addPracticalRating(4.8);
//        tc3.addPracticalRating(2.9);
//        KdNode<Course> tn3 = new KdNode<>(tc2);
//        List<KdNode<Course>> cl3 = getAllCourses(tc2);
//        assertEquals(r.getRecommendations(new ArrayList<>(Arrays.asList(3.5, 2.0, 1.0, 5.0, 4.8, 2.9)), 6),
//                naiveKnn(cl3, tn3, 6));
//    }
//}
