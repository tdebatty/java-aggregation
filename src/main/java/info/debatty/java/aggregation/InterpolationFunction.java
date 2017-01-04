/**
 * Traduccio a java del fitxer quantic per Vicenc Torra Traduccio a C del
 * fitxer quantiadb per David Nettleton Fitxer original quantiadb per Vicenc
 * Torra Comencat el dia 990721
 */
package info.debatty.java.aggregation;

class InterpolationFunction {

    Point[] points;
    Function[] functions;

    public InterpolationFunction(final Point[] points) {

        int num_values = points.length - 2;
        // Afegeixo la definicio de les variables de Ldf
        this.points = new Point[num_values + 2];
        this.points[num_values + 1] = new Point(0.0, 0.0);

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
            this.points[i] = new Point(0.0, 0.0);
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
        this.points[num_values].x = 0.0;
        this.points[num_values].y = 0.0;

        L.calculaLi(points, num_values);

        for (i = 1; i <= num_values; i++) {
            this.points[i].x = points[i].x;
            this.points[i].y = points[i].y;
            
            tempFun.calculaDVOWD(L.rectai[i], L.rectai[i + 1], points[i],
                    points[i + 1], num_values);
            this.functions[i].copia(tempFun);
        }
        this.points[num_values + 1].x = points[num_values + 1].x;
        this.points[num_values + 1].y = points[num_values + 1].y;
        //return (f2);
    } /* eferQ */

    public double eval(double x, int num_values) {

        for (int i = 1; i <= num_values; i++) {
            if ((points[i].x <= x) && (x <= points[i + 1].x)) {
                return functions[i].eval3(x);
            }
        }

        if (x <= points[1].x) {
            return 0.0;
        }

        if (points[num_values + 1].x <= x) {
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


}
