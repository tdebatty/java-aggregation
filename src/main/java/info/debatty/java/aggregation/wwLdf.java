/**
 * Traduccio a java del fitxer quantif.c per Vicenc Torra Traduccio a C del
 * fitxer quantif.adb per David Nettleton Fitxer original quantif.adb per Vicenc
 * Torra Comencat el dia 990721
 */
package info.debatty.java.aggregation;

class wwLdf {

    Point[] d;
    wwLfn f;

    public wwLdf(int num_values) {
        this.initLdf(num_values);
    }

    void put(int num_values) {
        int i;

        for (i = 1; i <= num_values; i++) {
            System.out.println(i + ": p=(" + d[i].x + "," + d[i].y + ")");
            if (f.funcioi[i].t == 1) {
                System.out.println("  y=" + f.funcioi[i].m + "*x+" + f.funcioi[i].n);
            } else {
                System.out.println(i + "DOUBLE BERSTEIN:");
                System.out.println(f.funcioi[i].di.x + ":" + f.funcioi[i].di.y);
                System.out.println(f.funcioi[i].vi.x + ":" + f.funcioi[i].di.y);
                System.out.println(f.funcioi[i].oi.x + ":" + f.funcioi[i].di.y);
                System.out.println(f.funcioi[i].wi.x + ":" + f.funcioi[i].di.y);
                System.out.println(f.funcioi[i].diP1.x + ":" + f.funcioi[i].di.y);
            }
        }
        System.out.print((num_values + 1) + ": p=(" + d[num_values + 1].x + ",");
        System.out.println(d[num_values + 1].y + ")");
    } /* eput */


    public double eval4(double x, int num_values) {
        int last, i;
        boolean trobat;
        double y;

        last = 0;
        trobat = false;
        i = 0;
        y = 0.0;

        //trobat = false; i=1;
        for (trobat = false, i = 1; (!trobat) && (i <= num_values); i++) {
            if ((wwbasics.leq(d[i].x, x))
                    && (wwbasics.leq(x, d[i + 1].x))) {
                trobat = true;
                y = (f.funcioi[i]).eval3(x);
            }
        }

        if (!trobat) {
            if (wwbasics.leq(x, d[1].x)) {
                y = 0.0;
                trobat = true;
            }
            if ((!trobat)
                    && (wwbasics.leq(d[num_values + 1].x, x))) {
                y = 1.0;
                trobat = true;
            }
            if (!trobat) {
                System.out.println("EVLdf.NO TROBAT. x:=" + x);
                System.out.println("MINIM:" + d[1].x);
                System.out.println("MAXIM:" + d[num_values].x);
                System.out.print("LongArray (pensa que a aixo se li suma 1!!):");
                System.out.println(":" + num_values);
                System.out.println("FUNCIO ");
                this.put(num_values);
                if (Double.isNaN(x)) {
                    throw new NullPointerException("wwLdf.eval4: x isNaN");
                } else {
                    throw new NullPointerException("wwLdf.eval4: x no Trobat");
                }
                /* raise bignum; */
            }
        }
        return (y);
    } /* eeval */


    public void initLdf(int num_values) {
        int i;
        // Ldf ff;
        // la funcio eval4 accedeix al d[punt+1]
        d = new Point[num_values + 2];
        f = new wwLfn(num_values + 1);

        for (i = 1; i <= num_values; i++) {
            // System.out.println ("Num_values:"+num_values+"i:"+i);
            d[i] = new Point(0.0, 0.0);
            f.funcioi[i].t = 1;
            f.funcioi[i].m = 0.0;
            f.funcioi[i].n = 0.0;
            f.funcioi[i].di.x = 0.0;
            f.funcioi[i].vi.x = 0.0;
            f.funcioi[i].oi.x = 0.0;
            f.funcioi[i].wi.x = 0.0;
            f.funcioi[i].diP1.x = 0.0;
            f.funcioi[i].di.y = 0.0;
            f.funcioi[i].vi.y = 0.0;
            f.funcioi[i].oi.y = 0.0;
            f.funcioi[i].wi.y = 0.0;
            f.funcioi[i].diP1.y = 0.0;
        }
        //d[num_values].x = 0.0;
        //d[num_values].y = 0.0;
    } /* initLdf */


    /**
     * **********************************************************************
     */
    /* Aquesta es la funcio que fa tota la feina, se li passa una tira de    */
    /* punts, i a partir d'aqui en construeix una funcio que els interpola.  */
    /* ferQ: Lp -> Ldf                                                       */
    /* N'hi ha LongArray + 1                                                 */
    /**
     * **********************************************************************
     */
    public void ferQ(Point[] dd, int num_values) {
        // Afegeixo la definicio de les variables de Ldf
        d = new Point[num_values + 2];
        d[num_values + 1] = new Point(0.0, 0.0);

        f = new wwLfn(num_values + 1);

        wwLr L = new wwLr(num_values + 1); //******* Darrera modificacio
        //Ldf f2;
        // Aixo de sota ho trec.
        // wwLp dd = new wwLp (num_values);
        wwfuncio tempFun = new wwfuncio();
        int i;

        // I si canvio d per dd en la definicio de la funcio, aixo ho puc treure
        // dd = d; /* avoids too many indirection passes */
        for (i = 1; i <= num_values; i++) /* initialize L and f */ {
            this.d[i] = new Point(0.0, 0.0);
            this.f.funcioi[i].t = 1;
            this.f.funcioi[i].m = 0.0;
            this.f.funcioi[i].n = 0.0;

            this.f.funcioi[i].di.x = 0.0;
            this.f.funcioi[i].vi.x = 0.0;
            this.f.funcioi[i].oi.x = 0.0;
            this.f.funcioi[i].wi.x = 0.0;
            this.f.funcioi[i].diP1.x = 0.0;
            this.f.funcioi[i].di.y = 0.0;
            this.f.funcioi[i].vi.y = 0.0;
            this.f.funcioi[i].oi.y = 0.0;
            this.f.funcioi[i].wi.y = 0.0;
            this.f.funcioi[i].diP1.y = 0.0;
        }
        tempFun.initfunc(num_values);
        this.d[num_values].x = 0.0;
        this.d[num_values].y = 0.0;

        L.calculaLi(dd, num_values);

        for (i = 1; i <= num_values; i++) {
            this.d[i].x = dd[i].x;
            this.d[i].y = dd[i].y;
            tempFun.calculaDVOWD(L.rectai[i], L.rectai[i + 1], dd[i],
                    dd[i + 1], num_values);
            this.f.funcioi[i].copia(tempFun);
        }
        this.d[num_values + 1].x = dd[num_values + 1].x;
        this.d[num_values + 1].y = dd[num_values + 1].y;
        //return (f2);
    } /* eferQ */

}
