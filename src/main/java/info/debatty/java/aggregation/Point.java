package info.debatty.java.aggregation;

/**
 * Represents a point in R².
 *
 * @author Thibault Debatty
 */
class Point {

    double x = 0.0;
    double y = 0.0;

    Point(double a, double b) {
        x = a;
        y = b;
    }

    void escriu() {
        System.out.print("(" + x + "," + y + ")");
    }


    /**
     * ***********************************************************
     */
    /* Bernstein es un eval y una funcio oculta 			*/
    /**
     * ***********************************************************
     */
    static double Bernstein(Point di, Point wi, Point oi, double x) {
        double y, xi, mi, b, ti, mbi;

        xi = di.x;
        mi = di.y;
        b = wi.y;
        ti = oi.x;
        mbi = oi.y;

        // Check so as not to divide by zero
        if (((ti - xi) * (ti - xi)) == 0.0) {
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


    public static double calculaSi(Point di, Point di_1) {
        double resu = (di.y - di_1.y) / (di.x - di_1.x);
        return (resu);
    }


    public static double calculaMi(double si, double siP1, Point di,
            Point di_1, Point diP1) {
        double bx, cx, resu;
        bx = 0.0;
        cx = 0.0;
        resu = 0.0;

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
        return (resu);
    }  /* ecalculaMi */


}
