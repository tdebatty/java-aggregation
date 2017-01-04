package info.debatty.java.aggregation;

/**
 *
 * @author Thibault Debatty
 */
class Vector {

    private final double[] values;

    Vector(int size) {
        values = new double[size];
    }

    public Vector(double[] values) {
        this.values = new double[values.length];

        // values[0..] are copied to this.values[0..]...
        for (int i = 0; i < values.length; i++) {
            this.values[i] = values[i];
        }
    }

    Vector(Vector a) {
        this(a.values);
    }

    public double get(final int position) {
        return values[position];
    }

    public void set(final int position, final double value) {
        values[position] = value;
    }

    public InterpolationFunction getInterpolationFunction() {

        // points now starts at 0
        Point[] points = new Point[values.length + 1];
        points[0] = new Point();
        for (int i = 1; i <= values.length; i++) {
            points[i] = new Point(
                    1.0 * (i) / (values.length),
                    values[i - 1] + points[i - 1].y);
        }

        return new InterpolationFunction(points);
    }

    /**
     * Order the vector in place.
     * Make a copy if you wish to keep the original vector.
     * @param num_values
     */
    public void sort() {

        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] > values[i]) {
                    double temp = values[j];
                    values[j] = values[i];
                    values[i] = temp;
                }
            }
        }
    }



    /**
     * Sort both vectors according to the values in this vector.
     * @param other
     */
    public void sort(final Vector other) {

        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] > values[i]) {

                    double temp = other.values[j];
                    other.values[j] = other.values[i];
                    other.values[i] = temp;

                    double temp2 = values[j];
                    values[j] = values[i];
                    values[i] = temp2;
                }
            }
        }
    }



    /**
     * Compute dot product of vectors.
     * @param other
     * @return
     */
    public double dotProduct(final Vector other) {
        double agg = 0;

        for (int i = 0; i < values.length; i++) {
            agg += other.values[i] * values[i];

        }
        return agg;
    }

    int size() {
        return values.length;
    }
}
