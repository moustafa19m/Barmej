package edu.brown.cs.student.Barmej.dataStructures;

import edu.brown.cs.student.Barmej.course.Course;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KdNodeTest {

    private List<Course> getCourseList() {
        Course c1 = new Course("1");
        Course c2 = new Course("2");
        Course c3 = new Course("3");

        return new ArrayList<>(Arrays.asList(c1, c2, c3));
    }

    @Test
    public void testLeftChild() {
        Course c1 = new Course("1");
        KdNode<Course> n1 = new KdNode<>(c1);

        Course c2 = new Course("2");
        KdNode<Course> n2 = new KdNode<>(c2);

        Course c3 = new Course("3");
        KdNode<Course> n3 = new KdNode<>(c3);

        n1.setLeftChild(n2);
        assertEquals(n1.getLeftChild(), n2);

        n2.setLeftChild(n3);
        assertEquals(n2.getLeftChild(), n3);
    }

    @Test
    public void testRightChild() {
        Course c1 = new Course("1");
        KdNode<Course> n1 = new KdNode<>(c1);

        Course c2 = new Course("2");
        KdNode<Course> n2 = new KdNode<>(c2);

        Course c3 = new Course("3");
        KdNode<Course> n3 = new KdNode<>(c3);

        n1.setRightChild(n2);
        assertEquals(n1.getRightChild(), n2);

        n2.setRightChild(n3);
        assertEquals(n2.getRightChild(), n3);
    }

    @Test
    public void testGetNodeVal() {
        Course c1 = new Course("1");
        KdNode<Course> n1 = new KdNode<>(c1);

        Course c2 = new Course("2");
        KdNode<Course> n2 = new KdNode<>(c2);

        assertEquals(n1.getNodeVal(), c1);
        assertEquals(n2.getNodeVal(), c2);
    }

}
