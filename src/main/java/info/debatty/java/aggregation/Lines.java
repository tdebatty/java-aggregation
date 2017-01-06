/*
 * The MIT License
 *
 * Copyright 2017 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.debatty.java.aggregation;

class Lines {

    StraightLine[] lines;

    static boolean McAllister = false;
    // false = Modificacio meva C & O
    // true  = Alternativa de McAllister i Roulier segons Iqbal

    static double infinit = Double.MAX_VALUE;

    Lines(Point[] points) {

        // points starts at 0 and lines starts at 1 :(
        lines = new StraightLine[points.length + 1];
        for (int i = 1; i < points.length + 1; i++) {
            lines[i] = new StraightLine(0, 0);
        }


        double[] s = new double[points.length + 1];
        double[] m = new double[points.length + 1];

        int i = 0;
        int N = points.length + 1 - 1; //N es la darrera posicio de d, i d es num_values+1

        for (i = 2; i <= N; i++) {
            s[i] = Point.computeCoef(points[i - 2], points[i - 1]);
        }

        for (i = 2; i <= N - 1; i++) {
            m[i] = Point.calculaMi(s[i], s[i + 1],
                    points[i - 1],
                    points[i - 2],
                    points[i]);
        }

        if (McAllister) {
            if ((s[2] * (2 * s[2] - m[2])) > 0.0) {
                m[1] = 2 * s[2] - m[2];
            } else {
                m[1] = 0.0;
            }
        } else {
            if ((m[2] == 0.0) && (s[2] == 0.0)) {
                m[1] = 0.0;
            } else if (m[2] == 0.0) {
                m[1] = infinit;
            } else {
                m[1] = s[2] * s[2] / m[2];
            }
        }

        if (McAllister) {
            if ((s[N] * (2 * s[N] - m[N - 1])) > 0.0) {
                m[N] = 2 * s[N] - m[N - 1];
            } else {
                m[N] = 0.0;
            }
        } else {
            if ((m[N - 1] == 0.0) && (s[N] == 0.0)) {
                m[N] = 0.0;
            } else if (m[N - 1] == 0.0) {
                m[N] = infinit;
            } else {
                m[N] = s[N] * s[N]
                        / m[N - 1];
            }
        }

        for (i = 1; i <= N; i++) {
            this.lines[i].a = m[i];
            this.lines[i].b = points[i - 1].y - m[i] * points[i - 1].x;
        }
    }

    public static void setMcAllister() {
        McAllister = true;
    }
}
