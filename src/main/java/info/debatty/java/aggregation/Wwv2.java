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

    static boolean DEBON = false;
    static boolean SEELDF = false;
    static boolean SEEVAL = false;
    static boolean SEEOMEGA = false;

    public static void seeomega() {
        SEEOMEGA = true;
    }

    public static double owa(wwLwav2 w, wwLvav2 a, int num_values) {
        wwLvav2 b = new wwLvav2(a);
        int i;
        double retorna;

        if (DEBON) {
            System.out.println("owa\n");
        }

        b.order(num_values);
        return (b.escProd(w, num_values));
    } /* eowa */


    public static double owa(Vector w, Vector a)
            throws Exception {
        wwLwav2 wn = new wwLwav2(w);
        wwLvav2 an = new wwLvav2(a);
        return owa(wn, an, w.size());
    }

    public static double wm(wwLwav2 p, wwLvav2 a, int num_values) {
        return (a.escProd(p, num_values));
    } /* ewm */


    public static double wm(Vector p, Vector a)
            throws Exception {
        wwLwav2 pn = new wwLwav2(p);
        wwLvav2 an = new wwLvav2(a);
        return wm(pn, an, p.size());
    }

    public static double wowa(Vector w, Vector p, Vector a) {
        wwLwav2 wn = new wwLwav2(w);
        wwLwav2 pn = new wwLwav2(p);
        wwLvav2 an = new wwLvav2(a);
        return wowa(wn, pn, an, w.size());
    }

    public static double wowa(wwLwav2 w, wwLwav2 p, wwLvav2 a, int num_values) {
            wwLwav2 omega = new wwLwav2(num_values);
            wwLdf fer = new wwLdf(num_values);
            int i;
            double retorna, acc, accv;

            if (DEBON) {
                System.out.println("wowa\n");
            }

            fer.initLdf(num_values);
            fer = w.setQ(num_values);

            //     double x, y; int pas =100;
            // for (i=0; i<=pas; i++) {
            //   x= ((double) i)/((double) pas);
            //   y= fer.eval4(x, num_values);
            //   System.out.println("("+x+" "+y+")");
            // }
            if (SEELDF) {
                System.out.println("\nsetQ ha tornat la seguient estructura:");
            }
            if (SEELDF) {
                fer.put(num_values);
            }

            a.orderA(p, num_values);
            omega.uniti[1] = fer.eval4(p.uniti[1], num_values);
            acc = p.uniti[1];
            if (SEEVAL) {
                System.out.println("\neval4 torna: omega[1]=" + omega.uniti[1]);
            }
            for (i = 2; i <= num_values; i++) {
                accv = acc;
                acc = acc + p.uniti[i];
                omega.uniti[i] = fer.eval4(acc, num_values)
                        - fer.eval4(accv, num_values);

                if (SEEVAL) {
                    System.out.println("eval4 torna omega[" + i + "]="
                            + omega.uniti[i]);
                }
            }

            /*
             omega.uniti[1] = eval4(fer, p.uniti[1], num_values);
             if (SEEVAL)
             System.out.println("\neval4 torna: omega[1]="+omega.uniti[1]);
             for (i=2; i<=num_values; i++)
             {
             omega.uniti[i] = fer.eval4(p.uniti[i], num_values) -
             omega.uniti[i-1];
             if (SEEVAL)
             System.out.println("eval4 torna omega["+i+"]="+omega.uniti[i]);
             }
             */
            //System.out.println("\nEls pesos intermitjos em donen:\n");
            if (SEEOMEGA) {
                System.out.print("ww =");
                for (int oo = 1; oo <= num_values; oo++) {
                    System.out.print(" " + omega.uniti[oo]);
                }
                System.out.println(" ");
            }

            retorna = a.escProd(omega, num_values);
            return (retorna);
    } /* ewowa */


    public static double wowaQ(wwLdf fer, wwLwav2 p, wwLvav2 a, int num_values) throws Exception {
        wwLwav2 omega = new wwLwav2(num_values);
        double retorna, acc, accv;

        a.orderA(p, num_values);
        omega.uniti[1] = fer.eval4(p.uniti[1], num_values);
        acc = p.uniti[1];
        if (SEEVAL) {
            System.out.println("\neval4 torna: omega[1]=" + omega.uniti[1]);
        }
        for (int i = 2; i <= num_values; i++) {
            accv = acc;
            acc = acc + p.uniti[i];
            omega.uniti[i] = fer.eval4(acc, num_values)
                    - fer.eval4(accv, num_values);
            if (SEEVAL) {
                System.out.println("eval4 ret.omega[" + i + "]=" + omega.uniti[i]);
            }
        }
        retorna = a.escProd(omega, num_values);
        return (retorna);
    }

    public static void main(String args[]) throws Exception {
        Vector pesos = new Vector(10);
        Vector weights = new Vector(10);
        Vector dades = new Vector(10);
        pesos.addElement(new Double(0.1));
        pesos.addElement(new Double(0.2));
        pesos.addElement(new Double(0.3));
        pesos.addElement(new Double(0.4));
        pesos.addElement(new Double(0.0));
        weights.addElement(new Double(0.1));
        weights.addElement(new Double(0.2));
        weights.addElement(new Double(0.3));
        weights.addElement(new Double(0.4));
        weights.addElement(new Double(0.0));
        dades.addElement(new Double(0.4));
        dades.addElement(new Double(0.2));
        dades.addElement(new Double(0.3));
        dades.addElement(new Double(0.1));
        dades.addElement(new Double(0.0));

        System.out.println("Resultat de WM:" + Wwv2.wm(pesos, dades));
        System.out.println("Resultat de OWA:" + Wwv2.owa(weights, dades));
        System.out.println("Resultat de WOWA:" + Wwv2.wowa(weights, pesos, dades));
    }

}
