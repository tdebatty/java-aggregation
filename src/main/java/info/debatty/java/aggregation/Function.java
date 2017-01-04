package info.debatty.java.aggregation;

/**
 * Represents a single interpolation function.
 * @author Thibault Debatty
 */
class Function {

    int type = 1;             /* 1: recta, 2: dobleBernstein */

    double m = 0;
    double n = 0;
    Point di = new Point();
    Point vi = new Point();
    Point oi = new Point();
    Point wi = new Point();
    Point diP1 = new Point();

    Function() {
    }

    public void copia(Function f) {
        type = f.type;
        m = f.m;
        n = f.n;
        di = new Point(f.di.x, f.di.y);
        vi = new Point(f.vi.x, f.vi.y);
        oi = new Point(f.oi.x, f.oi.y);
        wi = new Point(f.wi.x, f.wi.y);
        diP1 = new Point(f.diP1.x, f.diP1.y);
    }

    public double eval(double x) {
        double y;

        if (type == 1) {
            y = m * x + n;
        } else {
            if ((di.x <= x) && (x <= oi.x)) {
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

    public void calcDVOWDNa(StraightLine Li, StraightLine LiP1, Point di, Point diP1, int num_values) {


        double tip = (di.x + diP1.x) / 2.0;
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

        this.type = 2;
        this.di.x = di.x;
        this.di.y = di.y;
        this.diP1.x = diP1.x;
        this.diP1.y = diP1.y;

        //return(f);
    } /* ecalcDVOWDNa */


    public void calcDVOWDa(StraightLine Li, StraightLine LiP1, Point di,
            Point diP1, int num_values) {

        double tip;
        double ti = (Li.b - LiP1.b) / (LiP1.a - Li.a);
        double zi = (LiP1.a * Li.b - Li.a * LiP1.b) / (LiP1.a - Li.a);
        
        if ((di.x <= ti) && (ti <= diP1.x)
                && (di.y <= zi) && (zi <= diP1.y)) {
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
        this.type = 2;
        this.di.x = di.x;
        this.di.y = di.y;
        this.diP1.x = diP1.x;
        this.diP1.y = diP1.y;
        //return(f);

    }

    public Function (StraightLine Li, StraightLine LiP1, Point Di,
            Point DiP1, int num_values) {


        if ((Li.a == LiP1.a) && (Li.b == LiP1.b)) {
            this.type = 1;
            this.m = Li.a;
            this.n = Li.b;  /* put ("DVOWD-cas recta"); */

        } else if (Li.a == LiP1.a) {
            this.calcDVOWDNa(Li, LiP1, Di, DiP1, num_values);

        } else {
            this.calcDVOWDa(Li, LiP1, Di, DiP1, num_values);
        }
    }

}
