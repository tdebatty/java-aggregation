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
 * Implementation of the WOWA operator as described in: V. Torra, The Weighted
 * OWA operator, Int. J. of Intel. Systems, 12 (1997) 153-166.
 *
 * And using the interpolation method described in: V. Torra, The WOWA operator
 * and the interpolation function W*: Chen and Otto's interpolation method
 * revisited, Fuzzy Sets and Systems, 113:3 (2000) 389-396.
 *
 * Original code by Vicenc Torra.
 * http://www.mdai.cat/ifao/wowa.php
 *
 * @author Thibault Debatty
 */
public class WOWA implements AggregatorInterface {

    private final Vector weights;
    private final Vector ordered_weights;

    /**
     * Initialize with provided weights and ordered weights.
     * @param weights
     * @param ordered_weights
     */
    public WOWA(final double[] weights, final double[] ordered_weights) {
        this.weights = new Vector(weights);
        this.ordered_weights = new Vector(ordered_weights);
    }

    @Override
    public final double aggregate(final double[] values) {
        int size = weights.size();

        Vector values_vector = new Vector(values);
        values_vector.sort(ordered_weights);

        Vector omega = new Vector(size);
        InterpolationFunctions interpolations =
                weights.getInterpolationFunctions();
        omega.set(0, interpolations.eval(ordered_weights.get(0)));

        double acc = ordered_weights.get(0);
        for (int i = 2; i <= size; i++) {
            double temp = acc;
            acc += ordered_weights.get(i - 1);
            omega.set(
                    i - 1,
                    interpolations.eval(acc) - interpolations.eval(temp));
        }

        return values_vector.dotProduct(omega);
    }
}
