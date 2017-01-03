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

//import ww.wwrecta;
//import ww.wwLf;
class wwLr {

    int lon;
    StraightLine[] rectai;

    static boolean McAllister = false;
    // false = Modificacio meva C & O
    // true  = Alternativa de McAllister i Roulier segons Iqbal

    static double infinit = Double.MAX_VALUE;

    wwLr(int LARRAY) {
        lon = LARRAY;
        rectai = new StraightLine[LARRAY + 1];
        for (int i = 1; i <= LARRAY; i++) {
            rectai[i] = new StraightLine(0, 0);
        }
    }

    public static void setMcAllister() {
        McAllister = true;
    }

    /**
     * *********************************************************************
     */
    /* calculaLi fa servir les funcions:  calculaMi, calculaSi   		*/
    /**
     * *********************************************************************
     */
    public void calculaLi(Point[] d, int num_values) {
        wwLf s = new wwLf(num_values + 1);
        wwLf m = new wwLf(num_values + 1);
        //wwLr L = new wwLr (num_values);
        int N, i;
        Point[] dd = new Point[num_values + 1];
        N = 0;
        i = 0;

        for (i = 1; i <= num_values; i++) /* initialize everything that is defined */ {                             /* in calculaLi 			 */

            s.long_float[i] = 0.0;
            m.long_float[i] = 0.0;
            dd[i] = new Point(0.0, 0.0);
        }
        dd = d;
        N = num_values + 1; //N es la darrera posicio de d, i d es num_values+1

        for (i = 2; i <= N; i++) {
            s.long_float[i] = Point.calculaSi(dd[i], dd[i - 1]);
        }

        for (i = 2; i <= N - 1; i++) {
            m.long_float[i] = Point.calculaMi(s.long_float[i], s.long_float[i + 1],
                    dd[i], dd[i - 1], dd[i + 1]);
        }

        if (McAllister) {
            if ((s.long_float[2] * (2 * s.long_float[2] - m.long_float[2])) > 0.0) {
                m.long_float[1] = 2 * s.long_float[2] - m.long_float[2];
            } else {
                m.long_float[1] = 0.0;
            }
        } else {
            if ((m.long_float[2] == 0.0) && (s.long_float[2] == 0.0)) {
                m.long_float[1] = 0.0;
            } else if (m.long_float[2] == 0.0) {
                m.long_float[1] = infinit;
            } else {
                m.long_float[1] = s.long_float[2] * s.long_float[2] / m.long_float[2];
            }
        }

        if (McAllister) {
            if ((s.long_float[N] * (2 * s.long_float[N] - m.long_float[N - 1])) > 0.0) {
                m.long_float[N] = 2 * s.long_float[N] - m.long_float[N - 1];
            } else {
                m.long_float[N] = 0.0;
            }
        } else {
            if ((m.long_float[N - 1] == 0.0) && (s.long_float[N] == 0.0)) {
                m.long_float[N] = 0.0;
            } else if (m.long_float[N - 1] == 0.0) {
                m.long_float[N] = infinit;
            } else {
                m.long_float[N] = s.long_float[N] * s.long_float[N]
                        / m.long_float[N - 1];
            }
        }
        for (i = 1; i <= N; i++) {
            this.rectai[i].a = m.long_float[i];
            this.rectai[i].b = d[i].y - m.long_float[i] * d[i].x;
            /* si m[i] = infinit */
        } /* efor */
        //return(L);

    } /* ecalculaLi */
}
