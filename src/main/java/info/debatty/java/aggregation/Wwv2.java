package info.debatty.java.aggregation;

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


    public static double owa(Vector w, Vector a, int num_values) {
        Vector b = new Vector(a);
        b.sort();
        return (b.dotProduct(w));
    } /* eowa */


    public static double owa(double[] w, double[] a) {
        Vector wn = new Vector(w);
        Vector an = new Vector(a);
        return owa(wn, an, w.length);
    }

    public static double wm(Vector p, Vector a, int num_values) {
        return (a.dotProduct(p));
    } /* ewm */


    public static double wm(double[] p, double[] a)
            throws Exception {
        Vector pn = new Vector(p);
        Vector an = new Vector(a);
        return wm(pn, an, p.length);
    }
}
