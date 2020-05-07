package edu.brown.cs.student.Barmej.dataStructures;

import java.util.Collections;
import java.util.List;

/**
 * Class for KD-Trees.
 *
 * @param <T> the tree's type, which must extend Coordinates
 */
public class KdTree<T extends Coordinates> {
  private KdNode<T> root;
  private int numDimensions;

  /**
   * constructor for a KD-Tree.
   *
   * @param nodes         a list of nodes which will be turned into a tree
   * @param numDimensions integer representing the number of dimensions of the
   *                      tree
   */
  public KdTree(List<KdNode<T>> nodes, int numDimensions) {
    this.numDimensions = numDimensions;
    this.root = buildKdTree(nodes, 0);
  }

  /**
   * getter method for returning the tree's root.
   *
   * @return a node; the tree's root
   */
  public KdNode<T> getRoot() {
    return root;
  }

  /**
   * method for building a KDtree given a list of nodes.
   *
   * @param nodes a list of nodes to be turned into a tree
   * @param depth the depth of recursion. also represents the level of the tree
   *              currently being built
   * @return the tree's root
   */
  private KdNode<T> buildKdTree(List<KdNode<T>> nodes, int depth) {
    if (nodes.isEmpty()) {
      return null;
    }
    int splittingAxis = depth % numDimensions;
    Collections.sort(nodes, new KdNodeComparator(splittingAxis));

    int currIndex = nodes.size() / 2;
    KdNode<T> currRoot = nodes.get(currIndex);

    KdNode<T> leftChild = buildKdTree(nodes.subList(0, currIndex), depth + 1);
    currRoot.setLeftChild(leftChild);

    KdNode<T> rightChild = buildKdTree(nodes.subList(currIndex + 1, nodes.size()), depth + 1);
    currRoot.setRightChild(rightChild);

    return currRoot;
  }

}
