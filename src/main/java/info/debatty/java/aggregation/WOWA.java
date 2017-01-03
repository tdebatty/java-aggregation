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

import java.util.Vector;

/**
 *
 * @author Thibault Debatty
 */
public class WOWA implements AggregatorInterface {

    private final double[] weights;
    private final double[] ordered_weights;

    public WOWA(double[] weights, double[] ordered_weights) {
        this.weights = weights;
        this.ordered_weights = ordered_weights;
    }

    public double aggregate(double[] values) {
        return Wwv2.wowa(
                weights,
                ordered_weights,
                values);
    }

    private static Vector arr2Vector(double[] values) {
        Vector v = new Vector();
        for (double value : values) {
            v.add(new Double(value));
        }
        return v;
    }

}
