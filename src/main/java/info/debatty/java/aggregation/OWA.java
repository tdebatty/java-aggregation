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
 * Ordered Weighted Average aggregation.
 *
 * @author Thibault Debatty
 */
public class OWA implements AggregatorInterface {
    private final Vector weights;

    /**
     * Initialize with provided weights.
     * @param weights
     */
    public OWA(final double[] weights)  {
        double threshold = 0.0000000001;
        if (Math.abs(arraySum(weights) - 1.0) > threshold) {
            throw new IllegalArgumentException("Sum of weights must be equal to 1");
        }
        for (double el : weights) {
            if (el < 0.0 || el > 1.0) {
                throw new IllegalArgumentException("Weights must be between 0 and 1");
            }
        }
        this.weights = new Vector(weights);
    }

    @Override
    public final double aggregate(final double[] values) {
        if (values.length != weights.size()) {
            throw new IllegalArgumentException("Data array size must be equal to weights arrays size");
        }
        for (double v : values) {
            if (v < 0.0 || v > 1.0) {
                throw new IllegalArgumentException("Data values must be between 0 and 1");
            }
        }
        Vector values_vector = new Vector(values);
        return values_vector.sort().dotProduct(weights);
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
