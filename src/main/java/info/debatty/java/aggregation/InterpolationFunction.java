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
 * Represents a single interpolation function between two pairs of
 * (point + straight line).
 *
 * Uses 5 points for Bernstein interpolation.
 *
 * @author Thibault Debatty
 */
final class InterpolationFunction {

    /**
     * Indicates if this interpolation function is a straight line or uses
     * bernstein interpolation polynomial of order 2.
     */
    private enum Type {
        straight,
        bernstein
    }

    private Type type = Type.straight;

    private double m = 0;
    private double n = 0;

    // Points used by Bernstein interpolation
    private Point p0 = new Point();
    private Point p1 = new Point();
    private Point p2 = new Point();
    private Point p3 = new Point();
    private Point p4 = new Point();

    InterpolationFunction() {
    }

    InterpolationFunction(
            final Line line1,
            final Line line2,
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
                y = Point.bernsteinInterpolation2(p0, p1, p2, x);

            } else /* x in [ti, xiP1] */ {
                y = Point.bernsteinInterpolation2(p2, p3, p4, x);
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
            final Line line1,
            final Line line2,
            final Point point1,
            final Point point2) {


        double average_x = (point1.x + point2.x) / 2.0;

        this.p1 = new Point(
                (point1.x + average_x) / 2.0,
                line1.a * (point1.x + average_x) / 2.0 + line1.b);

        this.p3 = new Point(
                (point2.x + average_x) / 2.0,
                line2.a * (point2.x + average_x) / 2.0 + line2.b);

        Line median = Line.fromPoints(p1, p3);
        this.p2 = new Point(average_x, median.eval(average_x));

        assert p3.y <= Math.max(point2.y, point1.y);
        assert p1.y <= Math.max(point2.y, point1.y);
        assert p3.y >= Math.min(point2.y, point1.y);
        assert p1.y >= Math.min(point2.y, point1.y);

        this.type = Type.bernstein;
        this.p0 = new Point(point1);
        this.p4 = new Point(point2);

        //return(f);
    } /* ecalcDVOWDNa */


    private void calcDVOWDa(
            final Line line0,
            final Line line1,
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

        this.type = Type.bernstein;
        this.p0 = new Point(p0);

        this.p1 = new Point(
                (p0.x + x_between) / 2.0,
                line0.a * (p0.x + x_between) / 2.0 + line0.b);

        this.p3 = new Point(
                (p1.x + x_between) / 2.0,
                line1.a * (p1.x + x_between) / 2.0 + line1.b);

        Line straight_line = Line.fromPoints(this.p1, this.p3);
        this.p2 = new Point(
                x_between,
                straight_line.eval(x_between));

        this.p4 = new Point(p1);

    }
}
