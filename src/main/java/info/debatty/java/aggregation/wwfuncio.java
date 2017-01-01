package info.debatty.java.aggregation;

import java.io.*;
import java.lang.*;

//import ww.wwrecta;
//import ww.wwpunt;
class wwfuncio {

    int t;             /* 1: recta, 2: dobleBernstein */

    double m, n;
    wwpunt di, vi, oi, wi, diP1;

    static boolean DEBON = false;
    static boolean SEEVAL = false;
    static boolean SEEFUN = false;

    wwfuncio() {
        di = new wwpunt(0.0, 0.0);
        vi = new wwpunt(0.0, 0.0);
        oi = new wwpunt(0.0, 0.0);
        wi = new wwpunt(0.0, 0.0);
        diP1 = new wwpunt(0.0, 0.0);
    }

    ;

  public void copia(wwfuncio f) {
        t = f.t;
        m = f.m;
        n = f.n;
        di = new wwpunt(f.di.x, f.di.y);
        vi = new wwpunt(f.vi.x, f.vi.y);
        oi = new wwpunt(f.oi.x, f.oi.y);
        wi = new wwpunt(f.wi.x, f.wi.y);
        diP1 = new wwpunt(f.diP1.x, f.diP1.y);
    }

    ;


  public double eval3(double x) {
        double y, xi, ti, xiP1;
        y = 0.0;
        xi = 0.0;
        ti = 0.0;
        xiP1 = 0.0;
        if (DEBON) {
            System.out.println("eval3");
        }
        if (SEEVAL) {
            System.out.println("\na eval3 f.t hauria de ser 1 o 2 i es:" + t);
        }
        if (t == 1) {
            y = m * x + n;
        } else {
            xi = di.x;
            ti = oi.x;
            xiP1 = diP1.x;
            if ((xi <= x) && (x <= ti)) {
                y = wwpunt.Bernstein(di, vi, oi, x);
            } else /* x in [ti, xiP1] */ {
                y = wwpunt.Bernstein(oi, wi, diP1, x);
            }
            if (SEEVAL) {
                System.out.println("\nBerstein ha tornat " + y);
            }
        }
        if (y > 2.0) {
            System.out.println("\nBIG ERROR");
            System.out.println("R" + y + "," + m);
            System.out.println(" " + x + "," + n);
            System.out.println("B" + xi + "," + ti + "," + xiP1);
            System.out.println("C" + di.x + "," + vi.x + "," + oi.x);
            System.out.println(" " + di.y + "," + vi.y + "," + oi.y);
            System.out.println("D" + oi.x + "," + wi.x + "," + diP1.x);
            System.out.println(" " + oi.y + "," + wi.y + "," + diP1.y);
            throw new NullPointerException("wwfuncio.eval3");
            /* raise bigNum; */
        }
        if (y < 0.0) {
            y = 0.0;
        }
        if (y > 1.0) {
            y = 1.0;
        }
        return (y);
    } /*eeval */


    public void put4() {
        if (DEBON) {
            System.out.println("put4");
        }
        System.out.println("\nF R/B:" + t);
        if (t != 2) /* if f.t = 2, f.m and f.n are not assigned */ {
            System.out.println("FR:" + m + "," + n);
        }
        System.out.println("FB:" + di.x + "," + vi.x + "," + oi.x);
        System.out.println("   " + di.y + "," + vi.y + "," + oi.y);
        System.out.println("FB:" + oi.x + "," + wi.x + "," + diP1.x);
        System.out.println("   " + oi.y + "," + wi.y + "," + diP1.y);
    } /* eput4 */


    public void initfunc(int num_values) {
        int i = num_values; // EL PARAMETRE D'ENTRADA NO SERVEIX PER A RES
        t = 1;
        m = 0.0;
        n = 0.0;
        di.x = 0.0;
        di.y = 0.0;
        vi.x = 0.0;
        vi.y = 0.0;
        oi.x = 0.0;
        oi.y = 0.0;
        wi.x = 0.0;
        wi.y = 0.0;
        diP1.x = 0.0;
        diP1.y = 0.0;
    } /* initfunc */


    public void calcDVOWDNa(wwrecta Li, wwrecta LiP1, wwpunt di, wwpunt diP1, int num_values) {
        double tip;
        wwpunt vi = new wwpunt(0.0, 0.0);
        wwpunt wi = new wwpunt(0.0, 0.0);
        wwpunt oi = new wwpunt(0.0, 0.0);
        //wwfuncio f;
        wwrecta R = new wwrecta(0.0, 0.0);

        tip = 0.0;
        vi.x = 0.0;
        vi.y = 0.0;
        wi.x = 0.0;
        wi.y = 0.0;
        oi.x = 0.0;
        oi.y = 0.0;
        this.initfunc(num_values); // el parametre no s'usa per a res !!
        R.m = 0.0;
        R.n = 0.0;

        if (DEBON) {
            System.out.println("wwfuncio.calcDVOWDNa");
        }

        tip = (di.x + diP1.x) / 2.0;
        vi.x = (di.x + tip) / 2.0;
        vi.y = Li.m * (di.x + tip) / 2.0 + Li.n;
        wi.x = (diP1.x + tip) / 2.0;
        wi.y = LiP1.m * (diP1.x + tip) / 2.0 + LiP1.n;
        oi.x = tip;
        R.buildLineFromPoints(vi, wi);
        if (SEEVAL) {
            System.out.println("\nbuildLineFromPoints ha tornat (" + R.m + "," + R.n + ")");
        }
        oi.y = R.eval2(tip);
        if (SEEVAL) {
            System.out.println("\neval2 ha tornat " + oi.y);
        }
        if (wi.y > wwbasics.maxx(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error1");
        }
        if (vi.y > wwbasics.maxx(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error2");
        }
        if (wi.y < wwbasics.minn(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error3");
        }
        if (vi.y < wwbasics.minn(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error4");
        }

        this.t = 2;
        this.di.x = di.x;
        this.di.y = di.y;
        this.vi.x = vi.x;
        this.vi.y = vi.y;
        this.oi.x = oi.x;
        this.oi.y = oi.y;
        this.wi.x = wi.x;
        this.wi.y = wi.y;
        this.diP1.x = diP1.x;
        this.diP1.y = diP1.y;

        //return(f);
    } /* ecalcDVOWDNa */


    public void calcDVOWDa(wwrecta Li, wwrecta LiP1, wwpunt di,
            wwpunt diP1, int num_values) {
        double tip, ti, zi;
        //struct funcio f;
        wwpunt vi = new wwpunt(0.0, 0.0);
        wwpunt wi = new wwpunt(0.0, 0.0);
        wwpunt oi = new wwpunt(0.0, 0.0);
        wwpunt t = new wwpunt(0.0, 0.0);
        wwrecta R = new wwrecta(0.0, 0.0);

        tip = 0.0;
        ti = 0.0;
        zi = 0.0;
        vi.x = 0.0;
        vi.y = 0.0;
        wi.x = 0.0;
        wi.y = 0.0;
        oi.x = 0.0;
        oi.y = 0.0;
        t.x = 0.0;
        t.y = 0.0;
        this.initfunc(num_values);
        R.m = 0.0;
        R.n = 0.0;

        if (DEBON) {
            System.out.println("wwfuncio.calcDVOWDa");
        }

        /*  codi no traduit del codi C
         if (Li.m > (infinit / 10.0) ) * start added *
         {
         t.x = di.x + (diP1.x - di.x) * 0.1;
         t.y = LiP1.m * t.x + LiP1.n;
         vi.x = (t.x + di.x) / 2.0;
         R = buildLineFromPoints(di,t);
         vi.y = ( (LiP1.m * vi.x + LiP1.n) + eval2(R,vi.x) ) / 2.0;
         if (SEEVAL==0) printf("\neval2 ha tornat amb %5.2lf\n",eval2(R,vi.x));
         wi.x = (diP1.x + t.x) / 2.0;
         wi.y = LiP1.m * (diP1.x + t.x) / 2.0 + LiP1.n;
         oi.x = t.x;
         R = buildLineFromPoints(vi, wi);
         oi.y = eval2(R, t.x);
         if (SEEVAL==0) printf("\neval2 ha tornat amb %5.2lf\n",oi.y);
         }
         else if (LiP1.m > (infinit / 10.0))
         {
         t.x = diP1.x - ( diP1.x - di.x) * 0.1 ;
         t.y = Li.m * t.x + Li.n;
         vi.x = (di.x + t.x) / 2.0;
         vi.y = Li.m * (di.x + t.x) / 2.0 + Li.n;
         wi.x = (t.x + di.x) / 2.0;
         R = buildLineFromPoints(diP1,t);
         wi.y = (Li.m * wi.x + Li.n + eval2(R,wi.x)) / 2.0;
         if (SEEVAL==0) printf("\neval2 ha tornat amb %5.2lf\n",eval2(R,wi.x));
         oi.x = t.x;
         R = buildLineFromPoints(vi, wi);
         oi.y = eval2(R, t.x);
         if (SEEVAL==0) printf("\neval2 ha tornat amb %5.2lf\n",oi.y);
         }
         else		* end added */
        /* {  */
        ti = (Li.n - LiP1.n) / (LiP1.m - Li.m);
        zi = (LiP1.m * Li.n - Li.m * LiP1.n) / (LiP1.m - Li.m);
        if ((wwbasics.leq(di.x, ti)) && (wwbasics.leq(ti, diP1.x))
                && (wwbasics.leq(di.y, zi)) && (wwbasics.leq(zi, diP1.y))) {
            tip = ti;
        } else {
            tip = (di.x + diP1.x) / 2.0;
        }

        vi.x = (di.x + tip) / 2.0;
        vi.y = Li.m * (di.x + tip) / 2.0 + Li.n;
        wi.x = (diP1.x + tip) / 2.0;
        wi.y = LiP1.m * (diP1.x + tip) / 2.0 + LiP1.n;
        oi.x = tip;

        R.buildLineFromPoints(vi, wi);
        if (SEEVAL) {
            System.out.println("\nbuildLineFromPoints ha tornat (" + R.m + "," + R.n + ")");
        }
        oi.y = R.eval2(tip);
        if (SEEVAL) {
            System.out.println("\neval2 ha tornat " + oi.y);
        }
        /* }  * eif added */
        this.t = 2;
        this.di.x = di.x;
        this.di.y = di.y;
        this.vi.x = vi.x;
        this.vi.y = vi.y;
        this.oi.x = oi.x;
        this.oi.y = oi.y;
        this.wi.x = wi.x;
        this.wi.y = wi.y;
        this.diP1.x = diP1.x;
        this.diP1.y = diP1.y;
        //return(f);

    } /* ecalcDVOWDa */


    public void calculaDVOWD(wwrecta Li, wwrecta LiP1, wwpunt Di,
            wwpunt DiP1, int num_values) {
        //wwfuncio f;
        this.initfunc(num_values);

        if (DEBON) {
            System.out.println("wwfuncio.calculaDVOWD\n");
        }

        if ((Li.m == LiP1.m) && (Li.n == LiP1.n)) {
            this.t = 1;
            this.m = Li.m;
            this.n = Li.n;  /* put ("DVOWD-cas recta"); */

        } else if (Li.m == LiP1.m) {
            this.calcDVOWDNa(Li, LiP1, Di, DiP1, num_values);
            if (SEEFUN) {
                System.out.println("\ncalculaDVOWDNa retorna....");
            }
            if (SEEFUN) {
                this.put4();
            }
        } else {
            this.calcDVOWDa(Li, LiP1, Di, DiP1, num_values);
            if (SEEFUN) {
                System.out.println("\ncalculaDVOWDa retorna ....");
            }
            if (SEEFUN) {
                this.put4();
            }
        }
        //return(f);
    } /* ecalculaDVOWD */

}
