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
 *
 * @author Thibault Debatty
 */
class Vector {

    private final double[] values;

    Vector(final int size) {
        values = new double[size];
    }

    /**
     * Instantiate this vector creating a copy of of values.
     * @param values
     */
    Vector(final double[] values) {
        this.values = new double[values.length];
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    /**
     * Copy constructor.
     * @param a
     */
    Vector(final Vector a) {
        this(a.values);
    }

    public double get(final int position) {
        return values[position];
    }

    public void set(final int position, final double value) {
        values[position] = value;
    }

    public InterpolationFunctions getInterpolationFunctions() {

        // points now starts at 0
        Point[] points = new Point[values.length + 1];
        points[0] = new Point();
        for (int i = 1; i <= values.length; i++) {
            points[i] = new Point(
                    1.0 * (i) / (values.length),
                    values[i - 1] + points[i - 1].y);
        }

        return new InterpolationFunctions(points);
    }

    /**
     * Order the vector in place.
     * Make a copy if you wish to keep the original vector.
     * @param num_values
     */
    public Vector sort() {

        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] > values[i]) {
                    double temp = values[j];
                    values[j] = values[i];
                    values[i] = temp;
                }
            }
        }

        return this;
    }



    /**
     * Sort both vectors according to the values in this vector.
     * @param other
     */
    public Vector sort(final Vector other) {

        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] > values[i]) {

                    double temp = other.values[j];
                    other.values[j] = other.values[i];
                    other.values[i] = temp;

                    double temp2 = values[j];
                    values[j] = values[i];
                    values[i] = temp2;
                }
            }
        }

        return this;
    }



    /**
     * Compute dot product of vectors.
     * @param other
     * @return
     */
    public double dotProduct(final Vector other) {
        double agg = 0;

        for (int i = 0; i < values.length; i++) {
            agg += other.values[i] * values[i];

        }
        return agg;
    }

    int size() {
        return values.length;
    }
}
