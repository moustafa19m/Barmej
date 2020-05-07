package edu.brown.cs.student.Barmej.algorithm;

import edu.brown.cs.student.Barmej.dataStructures.Coordinates;

import java.util.List;
import java.util.Objects;

/**
 * Class for representing Stars.
 */
public class Star implements Coordinates {

    private int starId;
    private String name;
    private List<Double> coordinates;

    /**
     * Constructor for the star class.
     * @param starId the id of the star
     * @param name the name of the star
     * @param coordinates the coordinates of the star
     */
    public Star(int starId, String name, List<Double> coordinates) {
        this.starId = starId;
        this.name = name;
        this.coordinates = coordinates;
    }

    /**
     * getter method for the star's id.
     * @return the star's id
     */
    public int getStarId() {
        return this.starId;
    }

    /**
     * getter method for the star's name.
     * @return the star's name
     */
    public String getStarName() {
        return this.name;
    }

    /**
     * getter method for the star's coordinates.
     * @return the star's coordinates
     */
    public List<Double> getCoordinates() {
        return this.coordinates;
    }

    /**
     * getter method for a specific star coordinate.
     * @param i the index of the coordinate to be accessed
     * @return the ith coordinate of the star
     */
    public double getIthCoordinate(int i) {
        return coordinates.get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Star star = (Star) o;
        return starId == star.starId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(starId);
    }
}

