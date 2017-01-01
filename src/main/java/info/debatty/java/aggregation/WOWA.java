/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
                arr2Vector(weights),
                arr2Vector(ordered_weights),
                arr2Vector(values));
    }

    private static Vector arr2Vector(double[] values) {
        Vector v = new Vector();
        for (double value : values) {
            v.add(new Double(value));
        }
        return v;
    }

}
