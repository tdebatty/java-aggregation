package info.debatty.java.aggregation;

/**
 * Represents a single interpolation function.
 * @author Thibault Debatty
 */
class Function {

    int t;             /* 1: recta, 2: dobleBernstein */

    double m, n;
    Point di, vi, oi, wi, diP1;

    Function() {
        di = new Point(0.0, 0.0);
        vi = new Point(0.0, 0.0);
        oi = new Point(0.0, 0.0);
        wi = new Point(0.0, 0.0);
        diP1 = new Point(0.0, 0.0);
    }

    public void copia(Function f) {
        t = f.t;
        m = f.m;
        n = f.n;
        di = new Point(f.di.x, f.di.y);
        vi = new Point(f.vi.x, f.vi.y);
        oi = new Point(f.oi.x, f.oi.y);
        wi = new Point(f.wi.x, f.wi.y);
        diP1 = new Point(f.diP1.x, f.diP1.y);
    }

    public double eval3(double x) {
        double y, xi, ti, xiP1;
        y = 0.0;
        xi = 0.0;
        ti = 0.0;
        xiP1 = 0.0;
        if (t == 1) {
            y = m * x + n;
        } else {
            xi = di.x;
            ti = oi.x;
            xiP1 = diP1.x;
            if ((xi <= x) && (x <= ti)) {
                y = Point.Bernstein(di, vi, oi, x);
            } else /* x in [ti, xiP1] */ {
                y = Point.Bernstein(oi, wi, diP1, x);
            }
        }

        assert y <= 2.0;

        if (y < 0.0) {
            y = 0.0;
        }
        if (y > 1.0) {
            y = 1.0;
        }
        return (y);
    } /*eeval */


    public void initfunc(int num_values) {
        t = 1;
        m = 0.0;
        n = 0.0;
        di.x = 0.0;
        di.y = 0.0;
        vi.x = 0.0;
        vi.y = 0.0;
        oi.x = 0.0;
        oi.y = 0.0;
        wi.x = 0.0;
        wi.y = 0.0;
        diP1.x = 0.0;
        diP1.y = 0.0;
    }

    public void calcDVOWDNa(StraightLine Li, StraightLine LiP1, Point di, Point diP1, int num_values) {
        double tip;
        Point vi = new Point(0.0, 0.0);
        Point wi = new Point(0.0, 0.0);
        Point oi = new Point(0.0, 0.0);

        tip = 0.0;
        vi.x = 0.0;
        vi.y = 0.0;
        wi.x = 0.0;
        wi.y = 0.0;
        oi.x = 0.0;
        oi.y = 0.0;
        this.initfunc(num_values); // el parametre no s'usa per a res !!

        tip = (di.x + diP1.x) / 2.0;
        vi.x = (di.x + tip) / 2.0;
        vi.y = Li.a * (di.x + tip) / 2.0 + Li.b;
        wi.x = (diP1.x + tip) / 2.0;
        wi.y = LiP1.a * (diP1.x + tip) / 2.0 + LiP1.b;
        oi.x = tip;
        StraightLine R = StraightLine.fromPoints(vi, wi);
        oi.y = R.eval(tip);
        if (wi.y > Math.max(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error1");
        }
        if (vi.y > Math.max(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error2");
        }
        if (wi.y < Math.min(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error3");
        }
        if (vi.y < Math.min(diP1.y, di.y)) {
            System.out.println("wwfuncio.DVOWDNa: Error4");
        }

        this.t = 2;
        this.di.x = di.x;
        this.di.y = di.y;
        this.vi.x = vi.x;
        this.vi.y = vi.y;
        this.oi.x = oi.x;
        this.oi.y = oi.y;
        this.wi.x = wi.x;
        this.wi.y = wi.y;
        this.diP1.x = diP1.x;
        this.diP1.y = diP1.y;

        //return(f);
    } /* ecalcDVOWDNa */


    public void calcDVOWDa(StraightLine Li, StraightLine LiP1, Point di,
            Point diP1, int num_values) {
        double tip, ti, zi;
        //struct funcio f;
        Point vi = new Point(0.0, 0.0);
        Point wi = new Point(0.0, 0.0);
        Point oi = new Point(0.0, 0.0);
        Point t = new Point(0.0, 0.0);

        tip = 0.0;
        ti = 0.0;
        zi = 0.0;
        vi.x = 0.0;
        vi.y = 0.0;
        wi.x = 0.0;
        wi.y = 0.0;
        oi.x = 0.0;
        oi.y = 0.0;
        t.x = 0.0;
        t.y = 0.0;
        this.initfunc(num_values);

        ti = (Li.b - LiP1.b) / (LiP1.a - Li.a);
        zi = (LiP1.a * Li.b - Li.a * LiP1.b) / (LiP1.a - Li.a);
        if ((wwbasics.leq(di.x, ti)) && (wwbasics.leq(ti, diP1.x))
                && (wwbasics.leq(di.y, zi)) && (wwbasics.leq(zi, diP1.y))) {
            tip = ti;
        } else {
            tip = (di.x + diP1.x) / 2.0;
        }

        vi.x = (di.x + tip) / 2.0;
        vi.y = Li.a * (di.x + tip) / 2.0 + Li.b;
        wi.x = (diP1.x + tip) / 2.0;
        wi.y = LiP1.a * (diP1.x + tip) / 2.0 + LiP1.b;
        oi.x = tip;

        StraightLine R = StraightLine.fromPoints(vi, wi);
        oi.y = R.eval(tip);
        this.t = 2;
        this.di.x = di.x;
        this.di.y = di.y;
        this.vi.x = vi.x;
        this.vi.y = vi.y;
        this.oi.x = oi.x;
        this.oi.y = oi.y;
        this.wi.x = wi.x;
        this.wi.y = wi.y;
        this.diP1.x = diP1.x;
        this.diP1.y = diP1.y;
        //return(f);

    }

    public void calculaDVOWD(StraightLine Li, StraightLine LiP1, Point Di,
            Point DiP1, int num_values) {
        //wwfuncio f;
        this.initfunc(num_values);

        if ((Li.a == LiP1.a) && (Li.b == LiP1.b)) {
            this.t = 1;
            this.m = Li.a;
            this.n = Li.b;  /* put ("DVOWD-cas recta"); */

        } else if (Li.a == LiP1.a) {
            this.calcDVOWDNa(Li, LiP1, Di, DiP1, num_values);
            
        } else {
            this.calcDVOWDa(Li, LiP1, Di, DiP1, num_values);
        }
    }

}
