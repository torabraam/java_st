package ru.st.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {
    @Test
    // simple positive
    public void distance2Test1() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        assert p1.distance2(p2) == 0;
    }

    @Test
    // with Assert class positive
    public void distance2Test2() {
        Point p1 = new Point(-1.0, 3.0);
        Point p2 = new Point(5.0, 3.0);
        Assert.assertEquals(p1.distance2(p2), 6.0);
    }

    @Test (enabled = false)
    // with Assert class negative
    public void distance2Test3() {
        Point p1 = new Point(15.0, 3.0);
        Point p2 = new Point(5.0, 3.0);
        Assert.assertEquals(p1.distance2(p2), 15.0);
    }
}
