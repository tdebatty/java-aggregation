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

import junit.framework.TestCase;

/**
 *
 * @author Thibault Debatty
 */
public class OWATest extends TestCase {

    public OWATest(String testName) {
        super(testName);
    }

    /**
     * Test of aggregate method, of class OWA.
     */
    public void testAggregate() {
        System.out.println("OWA");

        double[] values = new double[] {0.4, 0.2, 0.3, 0.1, 0.0};
        double[] weights = new double[] {0.1, 0.2, 0.3, 0.4, 0.0};

        OWA instance = new OWA(weights);
        double expResult = 0.2;
        double result = instance.aggregate(values);
        assertEquals(expResult, result, 0.0);
    }

}
