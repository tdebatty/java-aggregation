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
     *
     * @param other
     */
    Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Evaluate order 2 bernstein polynomial. β0.(1-x)² + β1.2x.(1-x) + β2.x²
     *
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

        double y
                = b0 * (p2x - x) * (p2x - x)
                + b1 * 2.0 * (x - p0x) * (p2x - x)
                + b2 * (x - p0x) * (x - p0x);

        y = y / ((p2x - p0x) * (p2x - p0x));

        return (y);
    }

    /**
     * Compute the angular coefficient between points p1 and p2: Δy/Δx.
     *
     * @param p2
     * @param p1
     * @return
     */
    public static double computeCoef(final Point p1, final Point p2) {
        return (p2.y - p1.y) / (p2.x - p1.x);
    }

    /**
     *
     * @param coef0 angular coef between p0 and p1
     * @param coef1 angular coef between p1 and p2
     * @param p1
     * @param p0
     * @param p2
     * @return
     */
    public static double calculaMi(
            final double coef0,
            final double coef1,
            final Point p1,
            final Point p0,
            final Point p2) {

        assert (coef0 * coef1) >= 0.0;

        if (Math.abs(coef0) > Math.abs(coef1)) {
            double bx = (p2.y - p1.y) / coef0 + p1.x;
            double cx = (bx + p2.x) / 2.0;
            return (p2.y - p1.y) / (cx - p1.x);
        }

        if (Math.abs(coef0) < Math.abs(coef1)) {
            double bx = (p1.x - (p1.y - p0.y) / coef1);
            double cx = (bx + p0.x) / 2.0;
            return (p1.y - p0.y) / (p1.x - cx);
        }

        return coef0;
    }

}
