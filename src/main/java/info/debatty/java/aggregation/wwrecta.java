
package info.debatty.java.aggregation;

import java.io.*;
import java.lang.*;
//import ww.wwpunt;

class wwrecta {
     double m=0.0;
     double n=0.0;
     boolean DEBON = false;

  wwrecta (double a, double b) {
    if (DEBON) {System.out.println("wwrecta.wwrecta"); }
    m=a; n=b; }

  /**********************************************************************/
  /* buildLineFromPoints builds R(x) = m*x + n 				*/
  /**********************************************************************/
  public void buildLineFromPoints (wwpunt p0, wwpunt p1) {
     m = 0.0;
     n = 0.0;
     if (DEBON) System.out.print("buildLineFromPoints\n");
     if ((p0.x - p1.x) == 0.0)
     {
	 System.out.println("\nDivisio per zero! p0.x="+p0.x+" p1.x="+p1.x);
	 throw new NullPointerException ("wwrecta.buildLineFromPoints");
     }
     m = (p0.y - p1.y) / (p0.x - p1.x);
     if ((p1.x - p0.x) ==0.0)
     {
	 System.out.println ("\nDivisio per zero! p1.x="+p1.x+" p0.x="+p0.x);
	 throw new NullPointerException ("wwrecta.buildLineFromPoints");
     }
     n = (p1.x * p0.y - p0.x * p1.y) / (p1.x - p0.x);
     // return r;
  } /* ebuildLineFromPoints */

  public double eval2 (double x) {
     double resulta;
     resulta = 0.0;

     if (DEBON) System.out.println("eval2\n");
     resulta = m * x + n;
     return (resulta);
  } /* eeval */

}

