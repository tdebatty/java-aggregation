package info.debatty.java.aggregation;

/**
 * Represents a single interpolation function between two straight lines.
 * @author Thibault Debatty
 */
final class Function {

    private enum Type {
        straight,
        bernstein
    }

    private Type type = Type.straight;

    private double m = 0;
    private double n = 0;

    // Used by Bernstein interpolation
    private Point di = new Point();
    private Point vi = new Point();
    private Point oi = new Point();
    private Point wi = new Point();
    private Point di_p1 = new Point();

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
            if ((di.x <= x) && (x <= oi.x)) {
                y = Point.Bernstein(di, vi, oi, x);
            } else /* x in [ti, xiP1] */ {
                y = Point.Bernstein(oi, wi, di_p1, x);
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


        double tip = (point1.x + point2.x) / 2.0;

        vi.x = (point1.x + tip) / 2.0;
        vi.y = line1.a * (point1.x + tip) / 2.0 + line1.b;
        wi.x = (point2.x + tip) / 2.0;
        wi.y = line2.a * (point2.x + tip) / 2.0 + line2.b;

        StraightLine R = StraightLine.fromPoints(vi, wi);
        this.oi = new Point(tip, R.eval(tip));

        if (wi.y > Math.max(point2.y, point1.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error1");
        }
        if (vi.y > Math.max(point2.y, point1.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error2");
        }
        if (wi.y < Math.min(point2.y, point1.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error3");
        }
        if (vi.y < Math.min(point2.y, point1.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error4");
        }

        this.type = Type.bernstein;
        this.di = new Point(point1);
        this.di_p1 = new Point(point2);

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

        vi.x = (point1.x + tip) / 2.0;
        vi.y = line1.a * (point1.x + tip) / 2.0 + line1.b;
        wi.x = (point2.x + tip) / 2.0;
        wi.y = line2.a * (point2.x + tip) / 2.0 + line2.b;
        oi.x = tip;

        StraightLine R = StraightLine.fromPoints(vi, wi);
        oi.y = R.eval(tip);
        this.type = Type.bernstein;
        this.di.x = point1.x;
        this.di.y = point1.y;
        this.di_p1.x = point2.x;
        this.di_p1.y = point2.y;

    }



}
