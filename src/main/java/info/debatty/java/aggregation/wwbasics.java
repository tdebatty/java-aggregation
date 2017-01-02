/**
 * Traduccio a java del fitxer quantif.c per Vicenc Torra Traduccio a C del
 * fitxer quantif.adb per David Nettleton Fitxer original quantif.adb per Vicenc
 * Torra Comencat el dia 990721 Fitxer d'els elements basics que han de menester
 * els altres moduls wwbasics.java: WoWa BASICS
 */
package info.debatty.java.aggregation;

class wwbasics {

    static final double epsilon = 0.000000000001;   /* 1.0e-10 */
    static final double infinit = Double.MAX_VALUE;

    public static boolean leq(double x, double y) {
        return ((x - y) <= epsilon);
    } /* eleq */


    public static boolean geq(double x, double y) {
        return ((y - x) >= epsilon);
    } /* egeq */

    public static boolean eqq(double x, double y) {
        double resta;
        resta = x - y;
        return ((Math.abs(resta)) <= epsilon);
    } /* eeq */

}
