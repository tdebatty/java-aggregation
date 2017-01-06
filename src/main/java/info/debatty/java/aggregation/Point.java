package info.debatty.java.aggregation;

/**
 * Represents a point in R².
 *
 * @author Thibault Debatty
 */
class Point {

    double x = 0.0;
    double y = 0.0;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point() {

    }

    /**
     * Copy constructor.
     * @param other
     */
    Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }


    /**
     * Evaluate order 2 bernstein polynomial.
     * β0.(1-x)² + β1.2x.(1-x) + β2.x²
     * @param p0
     * @param p1
     * @param p2
     * @param x
     * @return
     */
    static double bernstein(Point p0, Point p1, Point p2, double x) {

        double p0x = p0.x;
        double b0 = p0.y;
        double b1 = p1.y;
        double p2x = p2.x;
        double b2 = p2.y;

        if (p2x == p0x) {
            return b0;
        }

        double y =
                  b0 * (p2x - x) * (p2x - x)
                + b1 * 2.0  * (x - p0x) * (p2x - x)
                + b2 * (x - p0x) * (x - p0x);

        y = y / ((p2x - p0x) * (p2x - p0x));

        return (y);
    }


    /**
     * Compute the angular coefficient between points p1 and p2: Δy/Δx.
     * @param p2
     * @param p1
     * @return
     */
    public static double computeCoef(final Point p1, final Point p2) {
        return (p2.y - p1.y) / (p2.x - p1.x);
    }

    public static double calculaMi(double si, double siP1, Point di,
            Point di_1, Point diP1) {

        double bx, cx, resu;

        if ((si * siP1) < 0.0) {
            resu = 0.0;
            if (si * siP1 != 0.0) {
                System.out.println("QUANTIFIER: Not eq!! ha de ser monoton:");
                System.out.println("(si,siP1)=(" + si + "," + siP1 + ")");
            }
        } else if (Math.abs(si) > Math.abs(siP1)) {
            bx = (diP1.y - di.y) / si + di.x;
            cx = (bx + diP1.x) / 2.0;
            resu = (diP1.y - di.y) / (cx - di.x);
        } else if (Math.abs(si) < Math.abs(siP1)) {
            bx = (di.x - (di.y - di_1.y) / siP1);
            cx = (bx + di_1.x) / 2.0;
            resu = (di.y - di_1.y) / (di.x - cx);
        } else {
            resu = si;
        }
        return (resu);
    }  /* ecalculaMi */


}
