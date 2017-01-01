package info.debatty.java.aggregation;

//import ww.wwbasics;
import java.io.*;
import java.lang.*;

class wwpunt {

    double x = 0.0;
    double y = 0.0;
    static boolean DEBON = false;
    static boolean SEEVAL = false;

    wwpunt(double a, double b) {
        x = a;
        y = b;
    }

    void escriu() {
        System.out.print("(" + x + "," + y + ")");
    }  /* eput3 */


    /**
     * ***********************************************************
     */
    /* Bernstein es un eval y una funcio oculta 			*/
    /**
     * ***********************************************************
     */
    static double Bernstein(wwpunt di, wwpunt wi, wwpunt oi, double x) {
        double y, xi, mi, b, ti, mbi;
        y = 0.0;
        xi = 0.0;
        mi = 0.0;
        b = 0.0;
        ti = 0.0;
        mbi = 0.0;
        if (DEBON) {
            System.out.println("wwpunt.Bernstein");
        }
        xi = di.x;
        mi = di.y;
        b = wi.y;
        ti = oi.x;
        mbi = oi.y;
        if (((ti - xi) * (ti - xi)) == 0.0) /* Check so as not to divide */ /* by zero 		      */ {
            double epsilon = 0.000001;
            if (wwbasics.abso((ti - x) * (xi - x)) < epsilon) {
                y = (di.y + oi.y) / 2.0;
            } else {
                System.out.println("\nDivisio per zero!");
                System.out.println("Aqui hauria de ser di.x = wi.x = oi.x =x");
                System.out.println("di.x, wi.x, oi.x, x:"
                        + di.x + "," + wi.x + "," + oi.x + "-->" + x);
                System.out.println("di.y, wi.y, oi.y:"
                        + di.y + "," + wi.y + "," + oi.y + "--> ??");
                throw new NullPointerException("wwpunt.Bernstein");
            }
        }
        if (wwbasics.eqq(ti, xi)) {
            y = mi;
        } else {
            y = mi * (ti - x) * (ti - x) + 2.0 * b * (x - xi) * (ti - x);
            y = y + mbi * (x - xi) * (x - xi);
            y = y / ((ti - xi) * (ti - xi));
        }
        return (y);
    } /* eBernstein */


    public static double calculaSi(wwpunt di, wwpunt di_1) {
        double resu;
        resu = 0.0;
        if (DEBON) {
            System.out.println("calculaSi");
        }

        resu = (di.y - di_1.y) / (di.x - di_1.x);
        if (SEEVAL) {
            System.out.println("el resultat es " + resu);
        }
        return (resu);
    }  /* ecalculaSi */


    public static double calculaMi(double si, double siP1, wwpunt di,
            wwpunt di_1, wwpunt diP1) {
        double bx, cx, resu;
        bx = 0.0;
        cx = 0.0;
        resu = 0.0;

        if (DEBON) {
            System.out.println("calculaMi");
        }

        if ((si * siP1) < 0.0) {
            resu = 0.0;
            if (!wwbasics.eqq(si * siP1, 0.0)) {
                System.out.println("QUANTIFIER: Not eq!! ha de ser monoton:");
                System.out.println("(si,siP1)=(" + si + "," + siP1 + ")");
            }
        } else if (wwbasics.abso(si) > wwbasics.abso(siP1)) {
            bx = (diP1.y - di.y) / si + di.x;
            cx = (bx + diP1.x) / 2.0;
            resu = (diP1.y - di.y) / (cx - di.x);
        } else if (wwbasics.abso(si) < wwbasics.abso(siP1)) {
            bx = (di.x - (di.y - di_1.y) / siP1);
            cx = (bx + di_1.x) / 2.0;
            resu = (di.y - di_1.y) / (di.x - cx);
        } else {
            resu = si;
        }
        if (SEEVAL) {
            System.out.println("abs(si)=" + wwbasics.abso(si) + "abs(siP1)=" + wwbasics.abso(siP1));
        }
        return (resu);
    }  /* ecalculaMi */


}
