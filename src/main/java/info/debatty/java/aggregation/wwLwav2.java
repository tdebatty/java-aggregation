package info.debatty.java.aggregation;

class wwLwav2 {

    double[] values;
    int lon;

    wwLwav2(int LARRAY) {
        lon = LARRAY;
        values = new double[LARRAY + 1];
    }

    public wwLwav2(double[] values) {
        lon = values.length;
        this.values = new double[lon + 1];

        // values[0..] are copied to this.values[1..] :-(
        for (int i = 1; i <= values.length; i++) {
            this.values[i] = values[i-1];
        }
    }

    wwLwav2(wwLwav2 a) {
        lon = a.lon;
        values = new double[a.lon + 1];
        for (int i = 0; i <= lon; i++) {
            values[i] = a.values[i];
        }
    }

    void escriu() {
        for (int i = 1; i <= lon; i++) {
            System.out.print(values[i] + " ");
        }
        System.out.print("\n");
    }

    public wwLdf setQ(int num_values) {
        // de vLp se'n necessita un punt de mes perque es va a llo+1
        Point[] vLp = new Point[num_values + 2];
        wwLdf df = new wwLdf(num_values + 1); /* es lo que se devuelve */

        int i;
        double tempx, llo;

        llo = num_values;
        vLp[1] = new Point(0.0, 0.0);
        for (i = 2; i <= llo + 1; i++) {
            tempx = i - 1;
            vLp[i] = new Point(tempx / llo, values[i - 1] + vLp[i - 1].y);
        }
        // df.initLdf(num_values);
        df.ferQ(vLp, num_values);
        return (df);
    }

    public void order(int num_values) {
        double aV;
        int i, j;


        for (i = 1; i <= num_values; i++) {
            for (j = i + 1; j <= num_values; j++) {
                if (values[j] > values[i]) {
                    aV = values[j];
                    values[j] = values[i];
                    values[i] = aV;
                }
            }
        }
        // return(a);
    } /* eorder */


    /**
     * ****************************************************
     */
    /* 'orderA' orders the vectors according to the values */
    /**
     * ****************************************************
     */
    public void orderA(wwLwav2 w, int num_values) {
        double aW, aV;
        int i, j;

        for (i = 1; i <= num_values; i++) {
            for (j = i + 1; j <= num_values; j++) {
                if (values[j] > values[i]) {
                    aW = w.values[j];
                    w.values[j] = w.values[i];
                    w.values[i] = aW;
                    aV = values[j];
                    values[j] = values[i];
                    values[i] = aV;
                }
            } /* efor2 */

        } /* efor1 */

    } /* eorderA */


    /**
     * ************************************************************
     */
    /* 'escProd' calculates the escalar product of the two vectors */
    /**
     * ************************************************************
     */
    public double escProd(wwLwav2 w, int num_values) {
        double r;
        int i;

        for (i = 1, r = 0.0; i <= num_values; i++) {
            r = r + (w.values[i] * values[i]);
            /* s'ha de forcar una cohercio T[w]=Unit <> T[a]=value */
        }
        return (r);
    }
}
