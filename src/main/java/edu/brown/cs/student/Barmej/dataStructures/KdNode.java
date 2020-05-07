package edu.brown.cs.student.Barmej.dataStructures;

/**
 * Class representing a node in a KD-tree.
 *
 * @param <T> an object that implements coordinates
 */
public class KdNode<T extends Coordinates> {

  private KdNode<T> leftChild;
  private KdNode<T> rightChild;
  private T nodeVal;

  /**
   * Constructor for a KdNode.
   *
   * @param nodeVal an object that implements coordinates, which is the node's
   *                value
   */
  public KdNode(T nodeVal) {
    this.leftChild = null;
    this.rightChild = null;
    this.nodeVal = nodeVal;
  }

  /**
   * Setter method for the node's left child.
   *
   * @param n the node which will be set to the leftChild field
   */
  public void setLeftChild(KdNode<T> n) {
    this.leftChild = n;
  }

  /**
   * Setter method for the node's right child.
   *
   * @param n the node which will be set to the rightChild field
   */
  public void setRightChild(KdNode<T> n) {
    this.rightChild = n;
  }

  /**
   * Getter method for the node's left child.
   *
   * @return the node's left child
   */
  public KdNode<T> getLeftChild() {
    return leftChild;
  }

  /**
   * Getter method for the node's right child.
   *
   * @return the node's right child
   */
  public KdNode<T> getRightChild() {
    return rightChild;
  }

  /**
   * Getter method for the node's right child.
   *
   * @return the node's right child
   */
  public T getNodeVal() {
    return nodeVal;
  }

}
