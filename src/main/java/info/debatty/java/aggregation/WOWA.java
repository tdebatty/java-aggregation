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
        if (weights.length != ordered_weights.length) {
            throw new IllegalArgumentException("Weights arrays have different size");
        }
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < 0 || weights[i] > 1 || ordered_weights[i] < 0 || ordered_weights[i] > 1) {
                throw new IllegalArgumentException("Weights must be between 0 and 1");
            }
        }
        if (arraySum(weights) != 1.0 || arraySum(ordered_weights) != 1.0) {
            throw new IllegalArgumentException("Sum of a weights vector must be equal to 1");
        }
        this.weights = new Vector(weights);
        this.ordered_weights = new Vector(ordered_weights);
    }

    @Override
    public final double aggregate(final double[] values) {
        if (values.length != weights.size()) {
            throw new IllegalArgumentException("Data array size must be equal to weights arrays size");
        }
        for (double v : values) {
            if (v < 0 || v > 1) {
                throw new IllegalArgumentException("Data values must be between 0 and 1");
            }
        }
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

    /**
     * Method to sum the elements in an double array.
     * Used to check if the sum of vector's elements is equal to 1.0.
     * @param values
     * @return
     */
    protected final double arraySum(final double[] values) {
        double sum = 0.0;
        for (double el : values) {
            sum = sum + el;
        }
        return sum;
    }
}
