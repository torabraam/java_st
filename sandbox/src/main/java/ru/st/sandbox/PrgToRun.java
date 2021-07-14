package ru.st.sandbox;

public class PrgToRun {

    public static void main(String[] args) {

        Point p1 = new Point(15.0, 3.0);
        Point p2 = new Point(5.0, 3.0);

        //for function
        System.out.println("Distance between p1(" + p1.x + ";" + p1.y + ") and p2(" + p2.x + ";" + p2.y + ") is " + distance(p1, p2));

        //for method is in sandbox.Point.java
        System.out.println("Distance between p1 and p2 is " + p1.distance2(p2));

    }

    //function:
    public static double distance(Point p1, Point p2) {
        return ((Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)))));
    }

}
