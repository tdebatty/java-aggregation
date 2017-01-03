package info.debatty.java.aggregation;

import java.util.Vector;
import java.util.Enumeration;

class wwLvav2 {

    double[] values;
    int lon;
    static boolean DEBON = false;

    wwLvav2() {
        lon = 10;
        values = new double[10 + 1];
    }

    wwLvav2(int LARRAY) {
        lon = LARRAY;
        values = new double[LARRAY + 1];
    }

    wwLvav2(wwLvav2 a) {
        lon = a.lon;
        values = new double[a.lon + 1];
        for (int i = 0; i <= lon; i++) {
            values[i] = a.values[i];
        }
    }

    public wwLvav2(Vector v) {
        lon = v.size();
        values = new double[lon + 1];
        Enumeration p = v.elements();
        for (int i = 1; p.hasMoreElements(); i++) {
            values[i] = ((Double) p.nextElement()).doubleValue();
        }
    }

    void escriu() {
        for (int i = 1; i <= lon; i++) {
            System.out.print(values[i] + " ");
        }
        System.out.print("\n");
    }

    public void put2(int num_values) {
        int i;

        for (i = 1; i <= num_values; i++) {
            System.out.print(" " + values[i]);
        }
    } /* eput2 */


    /**
     * **************************
     */
    /* 'order' orders the vector */
    /**
     * **************************
     */
    public void order(int num_values) {
        double aV;
        int i, j;

        if (DEBON) {
            System.out.println("order");
        }

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

        if (DEBON) {
            System.out.println("escProd\n");
        }
        for (i = 1, r = 0.0; i <= num_values; i++) {
            r = r + (w.values[i] * values[i]);
            /* s'ha de forcar una cohercio T[w]=Unit <> T[a]=value */
        }
        return (r);
    } /* eescProd */

}
