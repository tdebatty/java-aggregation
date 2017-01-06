package info.debatty.java.aggregation;

/**
 * Represents a single interpolation function between two straight lines.
 * @author Thibault Debatty
 */
final class Function {

    private enum Type {
        straight,
        doubleBernstein
    }

    private Type type = Type.straight;

    private double m = 0;
    private double n = 0;

    // Used by Bernstein interpolation
    // points are used to store the bernstein coeficients βi and
    // interval bounds :(
    private Point p0 = new Point();
    private Point p1 = new Point();
    private Point p2 = new Point();
    private Point p3 = new Point();
    private Point p4 = new Point();

    Function() {
    }

    Function(
            final StraightLine line1,
            final StraightLine line2,
            final Point point1,
            final Point point2) {


        if ((line1.a == line2.a) && (line1.b == line2.b)) {
            this.type = Type.straight;
            this.m = line1.a;
            this.n = line1.b;

        } else if (line1.a == line2.a) {
            this.calcDVOWDNa(line1, line2, point1, point2);

        } else {
            this.calcDVOWDa(line1, line2, point1, point2);
        }
    }

    public double eval(final double x) {
        double y;

        if (type == Type.straight) {
            y = m * x + n;

        } else {
            if ((p0.x <= x) && (x <= p2.x)) {
                y = Point.bernstein(p0, p1, p2, x);

            } else /* x in [ti, xiP1] */ {
                y = Point.bernstein(p2, p3, p4, x);
            }
        }

        assert y <= 2.0;

        if (y < 0.0) {
            y = 0.0;
        }
        if (y > 1.0) {
            y = 1.0;
        }
        return y;
    }

    /**
     * Compute the interpolation parameters between two lines with same angle
     * (line1.a == line2.a).
     * @param line1
     * @param line2
     * @param point1
     * @param point2
     */
    private void calcDVOWDNa(
            final StraightLine line1,
            final StraightLine line2,
            final Point point1,
            final Point point2) {


        double average_x = (point1.x + point2.x) / 2.0;

        this.p1 = new Point(
                (point1.x + average_x) / 2.0,
                line1.a * (point1.x + average_x) / 2.0 + line1.b);

        this.p3 = new Point(
                (point2.x + average_x) / 2.0,
                line2.a * (point2.x + average_x) / 2.0 + line2.b);

        StraightLine median = StraightLine.fromPoints(p1, p3);
        this.p2 = new Point(average_x, median.eval(average_x));

        assert p3.y <= Math.max(point2.y, point1.y);
        assert p1.y <= Math.max(point2.y, point1.y);
        assert p3.y >= Math.min(point2.y, point1.y);
        assert p1.y >= Math.min(point2.y, point1.y);

        this.type = Type.doubleBernstein;
        this.p0 = new Point(point1);
        this.p4 = new Point(point2);

        //return(f);
    } /* ecalcDVOWDNa */


    private void calcDVOWDa(
            final StraightLine line0,
            final StraightLine line1,
            final Point p0,
            final Point p1) {


        // Compute the coordinates of intersection between line0 and line1
        double x_intersection = (line0.b - line1.b) / (line1.a - line0.a);
        double y_intersection = (line1.a * line0.b - line0.a * line1.b)
                / (line1.a - line0.a);

        // Choose a x coordinate between p0 and p1
        double x_between;
        if ((p0.x <= x_intersection)
                && (x_intersection <= p1.x)
                && (p0.y <= y_intersection)
                && (y_intersection <= p1.y)) {
            x_between = x_intersection;

        } else {
            x_between = (p0.x + p1.x) / 2.0;
        }

        this.type = Type.doubleBernstein;
        this.p0 = new Point(p0);

        this.p1 = new Point(
                (p0.x + x_between) / 2.0,
                line0.a * (p0.x + x_between) / 2.0 + line0.b);

        this.p3 = new Point(
                (p1.x + x_between) / 2.0,
                line1.a * (p1.x + x_between) / 2.0 + line1.b);

        StraightLine straight_line = StraightLine.fromPoints(this.p1, this.p3);
        this.p2 = new Point(
                x_between,
                straight_line.eval(x_between));

        this.p4 = new Point(p1);

    }



}
