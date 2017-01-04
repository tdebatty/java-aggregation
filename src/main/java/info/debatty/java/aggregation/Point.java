package info.debatty.java.aggregation;

/**
 * Represents a point in R².
 *
 * @author Thibault Debatty
 */
class Point {

    double x = 0.0;
    double y = 0.0;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point() {

    }

    /**
     * ***********************************************************
     */
    /* Bernstein es un eval y una funcio oculta 			*/
    /**
     * ***********************************************************
     */
    static double Bernstein(Point di, Point wi, Point oi, double x) {

        double xi = di.x;
        double mi = di.y;
        double b = wi.y;
        double ti = oi.x;
        double mbi = oi.y;

        if (ti == xi) {
            return mi;
        }


        double y = mi * (ti - x) * (ti - x) + 2.0 * b * (x - xi) * (ti - x);
        y = y + mbi * (x - xi) * (x - xi);
        y = y / ((ti - xi) * (ti - xi));

        return (y);
    } /* eBernstein */


    public static double calculaSi(Point di, Point di_1) {
        return (di.y - di_1.y) / (di.x - di_1.x);
    }

    public static double calculaMi(double si, double siP1, Point di,
            Point di_1, Point diP1) {

        double bx, cx, resu;

        if ((si * siP1) < 0.0) {
            resu = 0.0;
            if (si * siP1 != 0.0) {
                System.out.println("QUANTIFIER: Not eq!! ha de ser monoton:");
                System.out.println("(si,siP1)=(" + si + "," + siP1 + ")");
            }
        } else if (Math.abs(si) > Math.abs(siP1)) {
            bx = (diP1.y - di.y) / si + di.x;
            cx = (bx + diP1.x) / 2.0;
            resu = (diP1.y - di.y) / (cx - di.x);
        } else if (Math.abs(si) < Math.abs(siP1)) {
            bx = (di.x - (di.y - di_1.y) / siP1);
            cx = (bx + di_1.x) / 2.0;
            resu = (di.y - di_1.y) / (di.x - cx);
        } else {
            resu = si;
        }
        return (resu);
    }  /* ecalculaMi */


}
