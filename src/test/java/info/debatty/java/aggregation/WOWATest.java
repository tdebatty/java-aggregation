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


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Thibault Debatty
 */
public class WOWATest {

    /**
     * Test of aggregate method, of class WOWA.
     */
    @Test
    public void testAggregate() {
        double[] values = new double[] {0.4, 0.2, 0.3, 0.1, 0.0};
        double[] weights = new double[] {0.1, 0.2, 0.3, 0.4, 0.0};
        double[] ordered_weights = new double[] {0.1, 0.2, 0.3, 0.4, 0.0};

        WOWA wowa = new WOWA(weights, ordered_weights);
        double exp = 0.194296875;
        double result = wowa.aggregate(values);
        assertEquals(exp, result, 1E-9);
    }
    @Test
    public void testAggregate2() {
        double[] values = new double[] {0.4, 0.2, 0.3, 0.1, 0.0};
        double[] weights = new double[] {0.2, 0.2, 0.2, 0.2, 0.2};
        double[] ordered_weights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0};

        WOWA wowa = new WOWA(weights, ordered_weights);
        //double exp = 0.3;
        //Value checked with php-aggregation package
        double exp = 0.2;
        double result = wowa.aggregate(values);
        assertEquals(exp, result, 1E-9);
    }
    @Test
    public void testAggregate3() {
        double[] values = new double[] {0.4, 0.2, 0.3, 0.1, 0.0};
        double[] ordered_weights = new double[] {0.2, 0.2, 0.2, 0.2, 0.2};
        double[] weights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0};

        WOWA wowa = new WOWA(weights, ordered_weights);
        //double exp = 0.2;
        //Value checked thanks to php-aggregation
        double exp = 0.3;
        double result = wowa.aggregate(values);
        assertEquals(exp, result, 1E-9);
    }

    /**
     * Test if Exception is triggered during the construction.
     * If at least one vector has a sum different from 1.0.
     */
    @Test
    public void testIllegalArgumentExceptionThrown() {
        final double[] values3 = {0.1, 0.6, 0.2, 0.4};
        final double[] values4 = {0.2, 0.5, 0.2, 0.1};
        assertThrows(IllegalArgumentException.class, () -> {
            new WOWA(values3, values4);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new WOWA(values4, values3);
        });

        final double[] values5 = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        final double[] values6 = {0.2, 0.3, 0.5, 0.3, 0.4, 0.4, 0.1};
        assertThrows(IllegalArgumentException.class, () -> {
            new WOWA(values5, values6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new WOWA(values6, values5);
        });
    }

}
