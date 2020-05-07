package edu.brown.cs.student.Barmej.dataStructures;

import java.util.List;

/**
 * An interface for representing objects with a field representing coordinates.
 */
public interface Coordinates {
  /**
   * getCoordinates - getter method for the coordinates of an object.
   *
   * @return a list of doubles representing the object's coordinates
   */
  List<Double> getCoordinates();

  /**
   * getIthCoordinate - accessor method for retrieving a specific coordinate from
   * an object's coordinate set.
   *
   * @param i the index of the coordinate to be accessed
   * @return the ith coordinate of the object
   */
  double getIthCoordinate(int i);

  /**
   * equals - an overriden method that checks for equality between two Coordinates
   * objects.
   *
   * @param c - the object to be compared
   * @return - a boolean, true if the two items are equal, false otherwise
   */
  @Override
  boolean equals(Object c);
}
