package ru.st.sandbox;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    // for method
    public double distance2(Point p2) {
        return ((Math.sqrt(((p2.x - this.x) * (p2.x - this.x)) + ((p2.y - this.y) * (p2.y - this.y)))));
    }
}
