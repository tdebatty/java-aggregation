package info.debatty.java.aggregation;

import java.util.Vector;
import java.util.Enumeration;

class wwLwav2 {

    double[] values;
    int lon;

    wwLwav2() {
        lon = 10;
        values = new double[10 + 1];
    }

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
}
