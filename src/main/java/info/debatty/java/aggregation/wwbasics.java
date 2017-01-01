/**
 * Traduccio a java del fitxer quantif.c   per Vicenc Torra
 * Traduccio a C    del fitxer quantif.adb per David Nettleton
 * Fitxer original quantif.adb             per Vicenc Torra
 * Comencat el dia 990721
 * Fitxer d'els elements basics que han de menester els altres moduls
 * wwbasics.java: WoWa BASICS
 */

package info.debatty.java.aggregation;

//import altres;
import java.io.*;
import java.lang.*;

class wwbasics {

  /* #define LARRAY 8   /* Longarray+1 */
  static double epsilon = 0.000000000001;   /* 1.0e-10 */
  static double infinit= 1.0e12;  /* 1.0e12  */
  /* start debug printf flags */
  static boolean DEBON=false;
  //double SEEVAL=0;
  //double SEEFUN=0;
  //double SEELDF=0;

  public static boolean leq (double x, double y)
  {
    if (DEBON) System.out.println("leq");
    return ((x-y) <= epsilon);
  } /* eleq */

  public static boolean geq (double x, double y)
  {
    if (DEBON) System.out.println ("geq");
    return ((y-x) >= epsilon);
  } /* egeq */

  public static double abso (double a) {
    if (a < 0.0) return -a;
    else return a;
  }

  public static boolean eqq  (double x, double y)
  {
    double resta;
    if (DEBON) System.out.println("eq");
    resta = x - y;
    return ((abso(resta)) <= epsilon);
  } /* eeq */

  public static double minn(double a, double b)
  {
    if (DEBON) System.out.println("min");
    if (a < b) return (a);
    else return (b);
  }

  public static double maxx(double a, double b)
  {
    if (DEBON) System.out.println("max");
    if (a > b) return (a);
    else return (b);
  }

  public static double resta (double a, double b) throws Exception
  {
    double r;

    r = a - b;

    if ( abso(r) < epsilon )  r = 0.0;

    if ( r < 0.0 )
      {
	    System.out.println("\n WOWAQ.RESTA.diferencia "+a+"-"+b+"="+(a-b));
	    throw new NullPointerException ("wwbasics.resta");
      }
    return (r);
  } /* eresta */

}


