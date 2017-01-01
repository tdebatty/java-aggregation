
package info.debatty.java.aggregation;

//import ww.wwpunt;

class wwLp {
  wwpunt punti [];
  int  lon;

  wwLp (int LARRAY) {
    /* System.out.println ("INICIALITZANT wwLp:"+LARRAY);  */
    lon = LARRAY;
    punti = new wwpunt [LARRAY+1];
    for (int i=0; i<=LARRAY; i++) { punti[i] = new wwpunt (0.0, 0.0); }
  };

}
