package info.debatty.java.aggregation;

import java.util.Vector;
import java.util.Enumeration;

class wwLwav2 {

    double uniti[];
    int lon;

    wwLwav2() {
        lon = 10;
        uniti = new double[10 + 1];
    }

    wwLwav2(int LARRAY) {
        lon = LARRAY;
        uniti = new double[LARRAY + 1];
    }

    public wwLwav2(Vector v) {
        lon = v.size();
        uniti = new double[lon + 1];
        Enumeration p = v.elements();
        for (int i = 1; p.hasMoreElements(); i++) {
            uniti[i] = ((Double) p.nextElement()).doubleValue();
        }
    }

    void escriu() {
        for (int i = 1; i <= lon; i++) {
            System.out.print(uniti[i] + " ");
        }
        System.out.print("\n");
    }

    public wwLdf setQ(int num_values) {
        // de vLp se'n necessita un punt de mes perque es va a llo+1
        Points vLp = new Points(num_values + 1);
        wwLdf df = new wwLdf(num_values + 1); /* es lo que se devuelve */

        int i;
        double tempx, llo;

        llo = num_values;
        vLp.punti[1].x = 0.0;
        vLp.punti[1].y = 0.0;
        for (i = 2; i <= llo + 1; i++) {
            tempx = i - 1;
            vLp.punti[i].x = tempx / llo;
            vLp.punti[i].y = uniti[i - 1] + vLp.punti[i - 1].y;
        }
        // df.initLdf(num_values);
        df.ferQ(vLp, num_values);
        return (df);
    }
}
