/**
 * Traduccio a java del fitxer quantic per Vicenc Torra Traduccio a C del
 * fitxer quantiadb per David Nettleton Fitxer original quantiadb per Vicenc
 * Torra Comencat el dia 990721
 */
package info.debatty.java.aggregation;

class wwLdf {

    Point[] d;
    Function[] functions;

    public wwLdf(int num_values) {
        // Ldf ff;
        // la funcio eval4 accedeix al d[punt+1]
        d = new Point[num_values + 2];

        functions = new Function[num_values + 2];
        for (int i = 0; i < num_values + 2; i++) {
            functions[i] = new Function();
        }

        for (int i = 1; i <= num_values; i++) {
            // System.out.println ("Num_values:"+num_values+"i:"+i);
            d[i] = new Point(0.0, 0.0);
            functions[i].t = 1;
            functions[i].m = 0.0;
            functions[i].n = 0.0;
            functions[i].di.x = 0.0;
            functions[i].vi.x = 0.0;
            functions[i].oi.x = 0.0;
            functions[i].wi.x = 0.0;
            functions[i].diP1.x = 0.0;
            functions[i].di.y = 0.0;
            functions[i].vi.y = 0.0;
            functions[i].oi.y = 0.0;
            functions[i].wi.y = 0.0;
            functions[i].diP1.y = 0.0;
        }
    }

    public double eval4(double x, int num_values) {

        for (int i = 1; i <= num_values; i++) {
            if ((d[i].x <= x) && (x <= d[i + 1].x)) {
                return functions[i].eval3(x);
            }
        }

        if (x <= d[1].x) {
            return 0.0;
        }

        if (d[num_values + 1].x <= x) {
            return 1.0;
        }

        throw  new ArithmeticException("Value not found!");
    }



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

        functions = new Function[num_values + 2];
        for (int i = 0; i < num_values + 2; i++) {
            functions[i] = new Function();
        }

        wwLr L = new wwLr(num_values + 1); //******* Darrera modificacio
        //Ldf f2;
        // Aixo de sota ho trec.
        // wwLp dd = new wwLp (num_values);
        Function tempFun = new Function();
        int i;

        // I si canvio d per dd en la definicio de la funcio, aixo ho puc treure
        // dd = d; /* avoids too many indirection passes */
        for (i = 1; i <= num_values; i++) /* initialize L and f */ {
            this.d[i] = new Point(0.0, 0.0);
            this.functions[i].t = 1;
            this.functions[i].m = 0.0;
            this.functions[i].n = 0.0;

            this.functions[i].di.x = 0.0;
            this.functions[i].vi.x = 0.0;
            this.functions[i].oi.x = 0.0;
            this.functions[i].wi.x = 0.0;
            this.functions[i].diP1.x = 0.0;
            this.functions[i].di.y = 0.0;
            this.functions[i].vi.y = 0.0;
            this.functions[i].oi.y = 0.0;
            this.functions[i].wi.y = 0.0;
            this.functions[i].diP1.y = 0.0;
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
            this.functions[i].copia(tempFun);
        }
        this.d[num_values + 1].x = dd[num_values + 1].x;
        this.d[num_values + 1].y = dd[num_values + 1].y;
        //return (f2);
    } /* eferQ */

}
