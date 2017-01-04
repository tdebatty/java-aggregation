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

        int size = points.length;

        this.points = new Point[size];
        this.functions = new Function[size];

        for (int i = 0; i < size; i++) {
            this.functions[i] = new Function();
            this.points[i] = new Point(0.0, 0.0);
        }

        wwLr L = new wwLr(size - 1);
        L.calculaLi(points, size - 2);

        Function tempFun = new Function();
        for (int i = 1; i < size - 1; i++) {
            this.points[i].x = points[i].x;
            this.points[i].y = points[i].y;

            this.functions[i] = new Function(L.rectai[i], L.rectai[i + 1], points[i],
                    points[i + 1], size - 2);
        }
        this.points[size - 1].x = points[size - 1].x;
        this.points[size - 1].y = points[size - 1].y;
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
