package edu.brown.cs.student.Barmej.dataStructures;

import java.util.Comparator;

/**
 * Class for comparing KdNodes on a certain axis.
 */
public class KdNodeComparator implements Comparator<KdNode> {

  private int axis;

  /**
   * Constructor for a KdNode comparator instance.
   *
   * @param axis integer representing the axis to compare on
   */
  public KdNodeComparator(int axis) {
    this.axis = axis;
  }

  /**
   * a method for comparing two nodes on a given axis.
   *
   * @param n1 the first node
   * @param n2 the last node
   * @return 1 if n1 is greater than n2 on the given axis, -1 if it's smaller, and
   *         0 if they're equal
   */
  @Override
  public int compare(KdNode n1, KdNode n2) {
    double diff = n1.getNodeVal().getIthCoordinate(axis % n1.getNodeVal().getCoordinates().size())
        - n2.getNodeVal().getIthCoordinate(axis % n2.getNodeVal().getCoordinates().size());
    if (diff > 0.0) {
      return 1;
    } else if (diff < 0.0) {
      return -1;
    } else {
      return 0;
    }
  }
}
