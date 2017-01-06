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
 * Represents a set of points with interpolation functions between these points.
 * @author Thibault Debatty
 */
class InterpolationFunctions {

    private final Point[] points;
    private final InterpolationFunction[] functions;

    InterpolationFunctions(final Point[] points) {

        int size = points.length + 1;

        // this.points starts at 1... :(
        this.points = new Point[size];
        this.functions = new InterpolationFunction[size];

        this.functions[0] = new InterpolationFunction();
        this.points[0] = new Point();

        Lines lines = new Lines(points);

        for (int i = 1; i < size - 1; i++) {
            this.points[i] = new Point(points[i - 1]);

            this.functions[i] = new InterpolationFunction(
                    lines.get(i - 1),
                    lines.get(i),
                    points[i - 1],
                    points[i]);
        }
        this.functions[size - 1] = new InterpolationFunction();
        this.points[size - 1] = new Point(points[size - 2]);
    }

    public double eval(final double value, final int num_values) {

        for (int i = 1; i <= num_values; i++) {
            if ((points[i].x <= value) && (value <= points[i + 1].x)) {
                return functions[i].eval(value);
            }
        }

        if (value <= points[1].x) {
            return 0.0;
        }

        if (points[num_values + 1].x <= value) {
            return 1.0;
        }

        throw  new ArithmeticException("Value not found!");
    }
}
