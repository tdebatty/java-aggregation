/**
 * Traduccio a java del fitxer quantic per Vicenc Torra Traduccio a C del
 * fitxer quantiadb per David Nettleton Fitxer original quantiadb per Vicenc
 * Torra Comencat el dia 990721
 */
package info.debatty.java.aggregation;

class InterpolationFunction {

    private final Point[] points;
    private final Function[] functions;

    InterpolationFunction(final Point[] points) {

        int size = points.length + 1;

        // this.points starts at 1... :(
        this.points = new Point[size];
        this.functions = new Function[size];

        this.functions[0] = new Function();
        this.points[0] = new Point();

        Lines lines = new Lines(points);

        for (int i = 1; i < size - 1; i++) {
            this.points[i] = new Point(points[i - 1]);

            this.functions[i] = new Function(
                    lines.get(i - 1),
                    lines.get(i),
                    points[i - 1],
                    points[i]);
        }
        this.functions[size - 1] = new Function();
        this.points[size - 1] = new Point(points[size - 2]);
    }

    public double eval(double x, int num_values) {

        for (int i = 1; i <= num_values; i++) {
            if ((points[i].x <= x) && (x <= points[i + 1].x)) {
                return functions[i].eval(x);
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
}
