package edu.brown.cs.student.Barmej.algorithm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.student.Barmej.dataStructures.KdNode;
import edu.brown.cs.student.Barmej.dataStructures.KdTree;

public class KNearestNeighborsTest {

  private KdTree<Star> _tree;
  private ArrayList<KdNode<Star>> nodeList;
  private KNearestNeighbors knn = new KNearestNeighbors();

  @Before
  public void setUpOneNodeTree() {
    nodeList = new ArrayList<>(makeNodeList().subList(10, 11));
    _tree = new KdTree<>(nodeList, 3);
  }

  @Before
  public void setUpTwelveNodeTree() {
    nodeList = makeNodeList();
    _tree = new KdTree<>(nodeList, 3);
  }

  @After
  public void tearDown() {
    nodeList = null;
    _tree = null;
  }

  private ArrayList<KdNode<Star>> makeNodeList() {

    ArrayList<Double> c1 = new ArrayList<>();
    c1.add(6.0);
    c1.add(9.0);
    c1.add(8.0);
    Star s1 = new Star(1, "s1", c1);
    KdNode<Star> n1 = new KdNode<>(s1);

    ArrayList<Double> c2 = new ArrayList<>();
    c2.add(3.0);
    c2.add(6.0);
    c2.add(1.0);
    Star s2 = new Star(2, "s2", c2);
    KdNode<Star> n2 = new KdNode<>(s2);

    ArrayList<Double> c3 = new ArrayList<>();
    c3.add(5.0);
    c3.add(4.0);
    c3.add(2.0);
    Star s3 = new Star(3, "s3", c3);
    KdNode<Star> n3 = new KdNode<>(s3);

    ArrayList<Double> c4 = new ArrayList<>();
    c4.add(3.0);
    c4.add(13.0);
    c4.add(14.0);
    Star s4 = new Star(4, "s4", c4);
    KdNode<Star> n4 = new KdNode<>(s4);

    ArrayList<Double> c5 = new ArrayList<>();
    c5.add(17.0);
    c5.add(10.0);
    c5.add(11.0);
    Star s5 = new Star(5, "s5", c5);
    KdNode<Star> n5 = new KdNode<>(s5);

    ArrayList<Double> c6 = new ArrayList<>();
    c6.add(1.0);
    c6.add(8.0);
    c6.add(5.0);
    Star s6 = new Star(6, "s6", c6);
    KdNode<Star> n6 = new KdNode<>(s6);

    ArrayList<Double> c7 = new ArrayList<>();
    c7.add(5.0);
    c7.add(14.0);
    c7.add(6.0);
    Star s7 = new Star(7, "s7", c7);
    KdNode<Star> n7 = new KdNode<>(s7);

    ArrayList<Double> c8 = new ArrayList<>();
    c8.add(13.0);
    c8.add(14.0);
    c8.add(18.0);
    Star s8 = new Star(8, "s8", c8);
    KdNode<Star> n8 = new KdNode<>(s8);

    ArrayList<Double> c9 = new ArrayList<>();
    c9.add(6.0);
    c9.add(9.0);
    c9.add(8.0);
    Star s9 = new Star(9, "s9", c9);
    KdNode<Star> n9 = new KdNode<>(s9);

    ArrayList<Double> c10 = new ArrayList<>();
    c10.add(6.0);
    c10.add(9.0);
    c10.add(8.0);
    Star s10 = new Star(10, "s10", c10);
    KdNode<Star> n10 = new KdNode<>(s10);

    ArrayList<Double> c11 = new ArrayList<>();
    c11.add(65.7);
    c11.add(0.2);
    c11.add(-8.3);
    Star s11 = new Star(11, "s11", c11);
    KdNode<Star> n11 = new KdNode<>(s11);

    ArrayList<Double> c12 = new ArrayList<>();
    c12.add(-5.5);
    c12.add(-9.06);
    c12.add(-8.0);
    Star s12 = new Star(12, "s12", c12);
    KdNode<Star> n12 = new KdNode<>(s12);

    ArrayList<KdNode<Star>> stars = new ArrayList<>();
    stars.add(n1);
    stars.add(n2);
    stars.add(n3);
    stars.add(n4);
    stars.add(n5);
    stars.add(n6);
    stars.add(n7);
    stars.add(n8);
    stars.add(n9);
    stars.add(n10);
    stars.add(n11);
    stars.add(n12);

    return stars;
  }

  private double euclideanDist(List<Double> n1, List<Double> n2) {
    double squaredDifferenceSum = 0;
    for (int i = 0; i < n1.size(); i++) {
      squaredDifferenceSum += Math.pow(n1.get(i) - n2.get(i), 2);
    }
    return Math.sqrt(squaredDifferenceSum);
  }

  public void sortByDistance(ArrayList<KdNode<Star>> nodes, KdNode<Star> target) {
    int len = nodes.size();
    for (int i = 0; i < len - 1; i++) {
      for (int j = 0; j < len - i - 1; j++) {
        if (euclideanDist(target.getNodeVal().getCoordinates(),
            nodes.get(j).getNodeVal().getCoordinates()) > euclideanDist(
                target.getNodeVal().getCoordinates(),
                nodes.get(j + 1).getNodeVal().getCoordinates())) {
          KdNode<Star> temp = nodes.get(j);
          nodes.set(j, nodes.get(j + 1));
          nodes.set(j + 1, temp);
        }
      }
    }
  }

  public ArrayList<KdNode<Star>> naiveKnn(ArrayList<KdNode<Star>> nodes, KdNode<Star> target,
      int k) {
    sortByDistance(nodes, target);

    ArrayList<KdNode<Star>> result = new ArrayList<>();

    for (KdNode<Star> node : nodes) {
      if (node.getNodeVal().getStarId() != target.getNodeVal().getStarId()) {
        result.add(node);
      }
    }

    if (result.size() <= k) {
      return result;
    } else {
      return new ArrayList<>(result.subList(0, k));
    }
  }

  public ArrayList<Integer> idList(ArrayList<KdNode<Star>> nodes) {
    ArrayList<Integer> idList = new ArrayList<>();
    for (KdNode<Star> node : nodes) {
      idList.add(node.getNodeVal().getStarId());
    }
    return idList;
  }

  public KdNode<Star> makeTarget(double x, double y, double z) {
    ArrayList<Double> targetCoordinates1 = new ArrayList<>();
    targetCoordinates1.add(x);
    targetCoordinates1.add(y);
    targetCoordinates1.add(z);
    Star targetStar = new Star(-1, "", targetCoordinates1);
    return new KdNode(targetStar);
  }

  @Test
  public void testOneNodeTree() {
    setUpOneNodeTree();
    KdNode<Star> t1 = makeTarget(2.0, 2.0, 2.0);
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 1, 3)),
        idList(naiveKnn(nodeList, t1, 1)));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 0, 3)),
        idList(naiveKnn(nodeList, t1, 0)));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 2, 3)),
        idList(naiveKnn(nodeList, t1, 2)));

    // testing when the one node is the target
    assertEquals(idList(knn.kNearestNeighbors(nodeList.get(0), _tree.getRoot(), 1, 3)),
        idList(naiveKnn(nodeList, nodeList.get(0), 1)));
    assertEquals(idList(knn.kNearestNeighbors(nodeList.get(0), _tree.getRoot(), 2, 3)),
        idList(naiveKnn(nodeList, nodeList.get(0), 2)));

    tearDown();
  }

  @Test
  public void testTwelveNodeTree() {
    setUpTwelveNodeTree();
    KdNode<Star> t1 = makeTarget(1.0, 3.0, 10.0);

    ArrayList<Integer> nodes = new ArrayList<>(
        Arrays.asList(6, 1, 10, 9, 3, 2, 4, 7, 5, 8, 12, 11));

    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 12, 3)), nodes);
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 20, 3)), nodes);
//    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 0, 3)),
//        idList(new ArrayList<>()));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 2, 3)),
        idList(naiveKnn(nodeList, t1, 2)));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 1, 3)),
        idList(naiveKnn(nodeList, t1, 1)));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 5, 3)),
        new ArrayList<>(Arrays.asList(6, 1, 10, 9, 3)));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 6, 3)),
        new ArrayList<>(Arrays.asList(6, 1, 10, 9, 3, 2)));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 8, 3)),
        new ArrayList<>(Arrays.asList(6, 1, 10, 9, 3, 2, 4, 7)));
    assertEquals(idList(knn.kNearestNeighbors(t1, _tree.getRoot(), 10, 3)),
        new ArrayList<>(Arrays.asList(6, 1, 10, 9, 3, 2, 4, 7, 5, 8)));

    KdNode<Star> t2 = makeTarget(6.0, 9.0, 8.0);
    assertEquals(idList(knn.kNearestNeighbors(t2, _tree.getRoot(), 6, 3)),
        new ArrayList<>(Arrays.asList(1, 10, 9, 7, 6, 4)));
    assertEquals(idList(knn.kNearestNeighbors(t2, _tree.getRoot(), 8, 3)),
        new ArrayList<>(Arrays.asList(1, 10, 9, 7, 6, 4, 3, 2)));

    KdNode<Star> t3 = makeTarget(-5.5, -9.06, -8.0);
    assertEquals(idList(knn.kNearestNeighbors(t3, _tree.getRoot(), 10, 3)),
        new ArrayList<>(Arrays.asList(12, 2, 3, 6, 1, 10, 9, 7, 4, 5)));

    assertEquals(idList(knn.kNearestNeighbors(_tree.getRoot(), _tree.getRoot(), 2, 3)),
        new ArrayList<>(Arrays.asList(10, 9)));

    tearDown();
  }
}
