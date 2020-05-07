package edu.brown.cs.student.Barmej.dataStructures;

import edu.brown.cs.student.Barmej.algorithm.Star;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KdTreeTest {

    private KdTree<Star> _tree;
    private ArrayList<KdNode<Star>> nodeList;


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

    @Before
    public void setUpOneNodeTree() {
        nodeList = new ArrayList<>(makeNodeList().subList(10, 11));
        _tree = new KdTree<>(nodeList, 3);
    }

    @Before
    public void setUpTwoNodeTree() {
        nodeList = new ArrayList<>(makeNodeList().subList(4, 6));
        _tree = new KdTree<>(nodeList, 3);
    }

    @Before
    public void setUpTwelveNodeTree() {
        nodeList = makeNodeList();
        _tree = new KdTree<>(nodeList, 3);
    }

    public boolean isValidTree(KdNode<Star> node, int depth) {
        if (node == null) {
            return true;
        }

        int axis = depth % node.getNodeVal().getCoordinates().size();

        if (node.getLeftChild() != null &&
                node.getLeftChild().getNodeVal().getIthCoordinate(axis)
                        > node.getNodeVal().getIthCoordinate(axis)) {
            return false;
        }

        if (node.getRightChild() != null &&
                node.getRightChild().getNodeVal().getIthCoordinate(axis)
                        < node.getNodeVal().getIthCoordinate(axis)) {
            return false;
        }

        if (!isValidTree(node.getLeftChild(), depth + 1) ||
                !isValidTree(node.getRightChild(), depth + 1)) {
            return false;
        }

        return true;
    }

    @Test
    public void testOneNodeTree() {
        setUpOneNodeTree();
        assertTrue(isValidTree(_tree.getRoot(), 0));

        ArrayList<Double> c11 = new ArrayList<>();
        c11.add(65.7);
        c11.add(0.2);
        c11.add(-8.3);
        Star s11 = new Star(11, "s11", c11);
        KdNode<Star> n11 = new KdNode<>(s11);

        assertEquals(_tree.getRoot().getNodeVal().getStarId(), n11.getNodeVal().getStarId());
    }

    @Test
    public void testTwoNodeTree() {
        setUpTwoNodeTree();
        assertTrue(isValidTree(_tree.getRoot(), 0));
    }

    @Test
    public void testTwelveNodeTree() {
        setUpTwelveNodeTree();
        assertTrue(isValidTree(_tree.getRoot(), 0));
    }
}
