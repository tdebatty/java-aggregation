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

    public Function (
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
            final StraightLine line1,
            final StraightLine line2,
            final Point point1,
            final Point point2) {

        double tip;
        double ti = (line1.b - line2.b) / (line2.a - line1.a);
        double zi = (line2.a * line1.b - line1.a * line2.b) / (line2.a - line1.a);

        if ((point1.x <= ti) && (ti <= point2.x)
                && (point1.y <= zi) && (zi <= point2.y)) {
            tip = ti;
        } else {
            tip = (point1.x + point2.x) / 2.0;
        }

        p1.x = (point1.x + tip) / 2.0;
        p1.y = line1.a * (point1.x + tip) / 2.0 + line1.b;
        p3.x = (point2.x + tip) / 2.0;
        p3.y = line2.a * (point2.x + tip) / 2.0 + line2.b;
        p2.x = tip;

        StraightLine R = StraightLine.fromPoints(p1, p3);
        p2.y = R.eval(tip);

        this.type = Type.doubleBernstein;
        this.p0.x = point1.x;
        this.p0.y = point1.y;
        this.p4.x = point2.x;
        this.p4.y = point2.y;

    }



}
