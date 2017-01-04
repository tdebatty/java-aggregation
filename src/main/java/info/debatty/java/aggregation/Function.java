package info.debatty.java.aggregation;

/**
 * Represents a single interpolation function between two straight lines.
 * @author Thibault Debatty
 */
final class Function {

    private int type = 1;             /* 1: recta, 2: dobleBernstein */

    private double m = 0;
    private double n = 0;

    private final Point di = new Point();
    private final Point vi = new Point();
    private final Point oi = new Point();
    private final Point wi = new Point();
    private final Point diP1 = new Point();

    Function() {
    }

    public Function (
            StraightLine line1,
            StraightLine line2,
            Point point1,
            Point point2) {


        if ((line1.a == line2.a) && (line1.b == line2.b)) {
            this.type = 1;
            this.m = line1.a;
            this.n = line1.b;

        } else if (line1.a == line2.a) {
            this.calcDVOWDNa(line1, line2, point1, point2);

        } else {
            this.calcDVOWDa(line1, line2, point1, point2);
        }
    }

    public double eval(double x) {
        double y;

        if (type == 1) {
            y = m * x + n;
        } else {
            if ((di.x <= x) && (x <= oi.x)) {
                y = Point.Bernstein(di, vi, oi, x);
            } else /* x in [ti, xiP1] */ {
                y = Point.Bernstein(oi, wi, diP1, x);
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
    public void calcDVOWDNa(
            StraightLine line1,
            StraightLine line2,
            Point point1,
            Point point2) {


        double tip = (point1.x + point2.x) / 2.0;

        vi.x = (point1.x + tip) / 2.0;
        vi.y = line1.a * (point1.x + tip) / 2.0 + line1.b;
        wi.x = (point2.x + tip) / 2.0;
        wi.y = line2.a * (point2.x + tip) / 2.0 + line2.b;

        StraightLine R = StraightLine.fromPoints(vi, wi);
        oi.x = tip;
        oi.y = R.eval(tip);

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

        this.type = 2;
        this.di.x = point1.x;
        this.di.y = point1.y;
        this.diP1.x = point2.x;
        this.diP1.y = point2.y;

        //return(f);
    } /* ecalcDVOWDNa */


    public void calcDVOWDa(StraightLine Li, StraightLine LiP1, Point di,
            Point diP1) {

        double tip;
        double ti = (Li.b - LiP1.b) / (LiP1.a - Li.a);
        double zi = (LiP1.a * Li.b - Li.a * LiP1.b) / (LiP1.a - Li.a);

        if ((di.x <= ti) && (ti <= diP1.x)
                && (di.y <= zi) && (zi <= diP1.y)) {
            tip = ti;
        } else {
            tip = (di.x + diP1.x) / 2.0;
        }

        vi.x = (di.x + tip) / 2.0;
        vi.y = Li.a * (di.x + tip) / 2.0 + Li.b;
        wi.x = (diP1.x + tip) / 2.0;
        wi.y = LiP1.a * (diP1.x + tip) / 2.0 + LiP1.b;
        oi.x = tip;

        StraightLine R = StraightLine.fromPoints(vi, wi);
        oi.y = R.eval(tip);
        this.type = 2;
        this.di.x = di.x;
        this.di.y = di.y;
        this.diP1.x = diP1.x;
        this.diP1.y = diP1.y;

    }



}
