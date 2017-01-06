/*
 * The MIT License
 *
 * Copyright 2017 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.debatty.java.aggregation;

/**
 * Represents a point in R².
 *
 * @author Thibault Debatty
 */
class Point {

    double x = 0.0;
    double y = 0.0;

    Point(final double x, final double y) {
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
    Point(final Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Bernstein polynomial interpolation of degree 2.
     *
     * As described in Torra "The WOWA operator and the interpolation function
     * W* : Chen and Otto's interpolation method revisited", page 4.
     *
     * @param p0
     * @param p1
     * @param p2
     * @param x
     * @return
     */
    static double bernsteinInterpolation2(
            final Point p0, final Point p1, final Point p2, final double x) {

        double x0 = p0.x;
        double y0 = p0.y;
        double y1 = p1.y;
        double x2 = p2.x;
        double y2 = p2.y;

        if (x2 == x0) {
            return y0;
        }

        double y
                = y0 * (x2 - x) * (x2 - x)
                + y1 * 2.0 * (x - x0) * (x2 - x)
                + y2 * (x - x0) * (x - x0);

        return y / ((x2 - x0) * (x2 - x0));

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
    public static double computeLineCoef(
            final double coef0,
            final double coef1,
            final Point p1,
            final Point p0,
            final Point p2) {

        assert (coef0 * coef1) >= 0.0;

        if (Math.abs(coef0) > Math.abs(coef1)) {
            double delta_y = p2.y - p1.y;
            double bx = delta_y / coef0 + p1.x;
            double cx = (bx + p2.x) / 2.0;
            return delta_y / (cx - p1.x);
        }

        if (Math.abs(coef0) < Math.abs(coef1)) {
            double delta_y = p1.y - p0.y;
            double bx = p1.x - delta_y / coef1;
            double cx = (bx + p0.x) / 2.0;
            return delta_y / (p1.x - cx);
        }

        return coef0;
    }
}
