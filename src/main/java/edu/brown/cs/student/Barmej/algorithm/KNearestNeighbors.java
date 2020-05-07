package edu.brown.cs.student.Barmej.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.brown.cs.student.Barmej.dataStructures.Coordinates;
import edu.brown.cs.student.Barmej.dataStructures.KdNode;

/**
 * A class that implements the k nearest neighbors algorithm for use in the
 * course recommender.
 *
 * @param <T> - a parameter T which extends type Coordinates
 */
public class KNearestNeighbors<T extends Coordinates> {

  /**
   * The constructor for this class.
   */
  public KNearestNeighbors() {

  }

  /**
   * a method for maintaining a list of nodes based on their distance from a given
   * target.
   *
   * @param nodes    the list of nodes that is being maintained
   * @param currNode the node being added to the list of nodes
   * @param target   the target to calculate the distance from
   */
  private void updateList(ArrayList<KdNode<T>> nodes, KdNode<T> currNode, KdNode<T> target) {
    nodes.add(currNode);

    double dist = euclideanDist(target.getNodeVal().getCoordinates(),
        currNode.getNodeVal().getCoordinates());

    int nodeIndex = nodes.size() - 1;
    int i = nodeIndex - 1;
    while (i > -1) {
      KdNode<T> ithNode = nodes.get(i);
      if (dist < euclideanDist(target.getNodeVal().getCoordinates(),
          ithNode.getNodeVal().getCoordinates())) {
        Collections.swap(nodes, nodeIndex, i);
        nodeIndex--;
        i--;
      } else {
        break;
      }
    }
  }

  /**
   * method for calculating the axis distance between two nodes on a given axis.
   *
   * @param target one of the nodes
   * @param node   the other node
   * @param axis   the axis to compare on
   * @return the axis distance
   */
  private double axisDistance(KdNode<T> target, KdNode<T> node, int axis) {
    return target.getNodeVal().getIthCoordinate(axis) - node.getNodeVal().getIthCoordinate(axis);
  }

  /**
   * method for calculating euclidean distance between two sets of coordinates.
   *
   * @param n1 first set of coordinates
   * @param n2 second set of coordinates
   * @return a double representing the euclidean distance between the two points
   */
  private double euclideanDist(List<Double> n1, List<Double> n2) {
    double squaredDifferenceSum = 0;
    for (int i = 0; i < n1.size(); i++) {
      squaredDifferenceSum += Math.pow(n1.get(i) - n2.get(i), 2);
    }
    return Math.sqrt(squaredDifferenceSum);
  }

  /**
   * method for finding the k nearest neighbors of a node.
   *
   * @param target        the node whose nearest neighbors we want to find
   * @param k             the number of nodes to find
   * @param root          root of the tree
   * @param numDimensions number of dimensions
   * @return a list of the k nodes closest to the target node
   */
  public ArrayList<KdNode<T>> kNearestNeighbors(KdNode<T> target, KdNode<T> root, int k,
      int numDimensions) {
    ArrayList<KdNode<T>> kNN = new ArrayList<>();
    return neighborsHelper(target, root, kNN, 0, k, numDimensions);
  }

  /**
   * Recursive helper method for k nearest neighbors.
   *
   * @param target   the node whose nearest neighbors we want to find
   * @param currNode the node currently being evaluated to see if it is one of the
   *                 nearest neighbors
   * @param kNearest a list of the k nearest neighbors
   * @param depth    the depth of recursion
   * @param k        number of nodes to find
   * @return a list of the k nearest neighbors so far
   */
  private ArrayList<KdNode<T>> neighborsHelper(KdNode<T> target, KdNode<T> currNode,
      ArrayList<KdNode<T>> kNearest, int depth, int k, int numDimensions) {
    if (currNode == null || k == 0) {
      return kNearest;
    }

    int axis = depth % numDimensions;

    if (kNearest.size() < k) {

      if (!target.getNodeVal().equals(currNode.getNodeVal())) {
        updateList(kNearest, currNode, target);
      }

      neighborsHelper(target, currNode.getLeftChild(), kNearest, depth + 1, k, numDimensions);
      neighborsHelper(target, currNode.getRightChild(), kNearest, depth + 1, k, numDimensions);
    } else {
      // checking if the current node is closer to the target than the furthest of the
      // k nearest
      // neighbors
      if (euclideanDist(target.getNodeVal().getCoordinates(),
          currNode.getNodeVal().getCoordinates()) < euclideanDist(
              target.getNodeVal().getCoordinates(),
              kNearest.get(kNearest.size() - 1).getNodeVal().getCoordinates())) {
        kNearest.remove(kNearest.get(kNearest.size() - 1));
        if (!target.getNodeVal().equals(currNode.getNodeVal())) {
          updateList(kNearest, currNode, target);
        }
      }
      double axisDistance = axisDistance(target, currNode, axis);
      // checking if the euclidean distance between the target and the furthest of the
      // k nearest
      // neighbors is greater than the axis distance
      if (euclideanDist(target.getNodeVal().getCoordinates(),
          kNearest.get(kNearest.size() - 1).getNodeVal().getCoordinates()) > axisDistance) {
        neighborsHelper(target, currNode.getLeftChild(), kNearest, depth + 1, k, numDimensions);
        neighborsHelper(target, currNode.getRightChild(), kNearest, depth + 1, k, numDimensions);
      } else {
        // checking if the current node's value on the current axis is smaller than the
        // target's
        // value on that same axis
        if (currNode.getNodeVal().getIthCoordinate(axis) < target.getNodeVal()
            .getIthCoordinate(axis)) {
          neighborsHelper(target, currNode.getRightChild(), kNearest, depth + 1, k, numDimensions);
        } else {
          neighborsHelper(target, currNode.getLeftChild(), kNearest, depth + 1, k, numDimensions);
        }
      }
    }
    return kNearest;
  }
}
