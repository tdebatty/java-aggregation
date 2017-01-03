/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.debatty.java.aggregation;

import java.util.Vector;

/**
 * Traduccio a java del fitxer owawm.c per Vicenc Torra Traduccio a C del fitxer
 * owawm.adb per David Nettleton Fitxer original owawm.adb per Vicenc Torra
 * http://www.iiia.csic.es/~vtorra Traduccio a java comencada el dia 990814
 *
 * Implementation of the WOWA operator as described in: V. Torra, The Weighted
 * OWA operator, Int. J. of Intel. Systems, 12 (1997) 153-166.
 *
 * And using the interpolation method described in: V. Torra, The WOWA operator
 * and the interpolation function W*: Chen and Otto's interpolation method
 * revisited, Fuzzy Sets and Systems, 113:3 (2000) 389-396.
 *
 * @author Thibault Debatty
 */
class Wwv2 {


    public static double owa(wwLwav2 w, wwLwav2 a, int num_values) {
        wwLwav2 b = new wwLwav2(a);
        b.order(num_values);
        return (b.escProd(w, num_values));
    } /* eowa */


    public static double owa(double[] w, double[] a) {
        wwLwav2 wn = new wwLwav2(w);
        wwLwav2 an = new wwLwav2(a);
        return owa(wn, an, w.length);
    }

    public static double wm(wwLwav2 p, wwLwav2 a, int num_values) {
        return (a.escProd(p, num_values));
    } /* ewm */


    public static double wm(double[] p, double[] a)
            throws Exception {
        wwLwav2 pn = new wwLwav2(p);
        wwLwav2 an = new wwLwav2(a);
        return wm(pn, an, p.length);
    }

    public static double wowa(double[] w, double[] p, double[] a) {
        wwLwav2 wn = new wwLwav2(w);
        wwLwav2 pn = new wwLwav2(p);
        wwLwav2 an = new wwLwav2(a);
        return wowa(wn, pn, an, w.length);
    }

    public static double wowa(wwLwav2 w, wwLwav2 p, wwLwav2 a, int num_values) {
        wwLwav2 omega = new wwLwav2(num_values);
        wwLdf fer = new wwLdf(num_values);
        int i;
        double retorna, acc, accv;


        fer.initLdf(num_values);
        fer = w.setQ(num_values);


        a.orderA(p, num_values);
        omega.values[1] = fer.eval4(p.values[1], num_values);
        acc = p.values[1];

        for (i = 2; i <= num_values; i++) {
            accv = acc;
            acc = acc + p.values[i];
            omega.values[i] = fer.eval4(acc, num_values)
                    - fer.eval4(accv, num_values);

        }

        retorna = a.escProd(omega, num_values);
        return (retorna);
    } /* ewowa */
}
