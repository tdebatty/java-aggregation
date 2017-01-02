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
 * Represents a straight line, and allows to compute y = ax + b.
 * @author Thibault Debatty
 */
final class StraightLine {

    double a = 0.0;
    double b = 0.0;

    StraightLine(final double a, final double b) {
        this.a = a;
        this.b = b;
    }


    /**
     * Build a straight line from 2 points.
     * @param p0
     * @param p1
     * @return
     */
    public static StraightLine fromPoints(final Point p0, final Point p1) {
        double a, b;

        if (p0.x == p1.x) {
            throw new ArithmeticException(
                    "Division by 0! p0.x=" + p0.x + " p1.x=" + p1.x);
        }

        a = (p0.y - p1.y) / (p0.x - p1.x);
        b = (p1.x * p0.y - p0.x * p1.y) / (p1.x - p0.x);

        return new StraightLine(a, b);

    }


    /**
     * Compute y = ax + b.
     * @param x
     * @return
     */
    public double eval(final double x) {
        return a * x + b;
    }

}
