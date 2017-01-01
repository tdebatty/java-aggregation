/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.debatty.java.aggregation;

import junit.framework.TestCase;

/**
 *
 * @author Thibault Debatty
 */
public class WOWATest extends TestCase {

    public WOWATest(String testName) {
        super(testName);
    }

    /**
     * Test of aggregate method, of class WOWA.
     */
    public void testAggregate() {
        System.out.println("WOWA");

        double[] values = new double[] {0.4, 0.2, 0.3, 0.1, 0.0};
        double[] weights = new double[] {0.1, 0.2, 0.3, 0.4, 0.0};
        double[] ordered_weights = new double[] {0.1, 0.2, 0.3, 0.4, 0.0};

        WOWA wowa = new WOWA(weights, ordered_weights);
        double expResult = 0.194296875;
        double result = wowa.aggregate(values);
        assertEquals(expResult, result, 1E-9);
    }

}
