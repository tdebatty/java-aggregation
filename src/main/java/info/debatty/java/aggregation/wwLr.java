package info.debatty.java.aggregation;

//import ww.wwrecta;
//import ww.wwLf;

class wwLr {
    int lon;
    wwrecta rectai[];

    static boolean McAllister = false;
    // false = Modificacio meva C & O
    // true  = Alternativa de McAllister i Roulier segons Iqbal

    static boolean DEBON = false;
    static boolean SEEVAL = false;
    static double infinit = 1e10;

    wwLr (int LARRAY) {
	if (DEBON) {System.out.println ("wwLr.wwLr"); }
	lon = LARRAY;
	rectai = new wwrecta [LARRAY+1];
	for (int i=1; i<=LARRAY; i++) { rectai[i] = new wwrecta(0,0); }
    };

    public static void setMcAllister () { McAllister = true; }

  /************************************************************************/
  /* calculaLi fa servir les funcions:  calculaMi, calculaSi   		*/
  /************************************************************************/
  public void calculaLi (wwLp d, int num_values)
  {
    wwLf s = new wwLf (num_values + 1);
    wwLf m = new wwLf (num_values + 1);
    //wwLr L = new wwLr (num_values);
    int N, i;
    wwLp dd = new wwLp (num_values);
    N = 0; i = 0;

    for (i=1 ;i<=num_values; i++) /* initialize everything that is defined */
      {                             /* in calculaLi 			 */
	s.long_float[i] = 0.0;
	m.long_float[i] = 0.0;
	this.rectai[i].m = 0.0;
	this.rectai[i].n = 0.0;
	dd.punti[i].x = 0.0;
	dd.punti[i].y = 0.0;
      }
  dd = d;  /* ?? */
  if (SEEVAL) {
    System.out.println("\na veure si s'ha assignat be dd....");
    for (i=1 ;i<=num_values+1; i++) {
      System.out.println("dd.punti["+i+"].x="+dd.punti[i].x);
      System.out.println("dd.punti["+i+"].y="+dd.punti[i].y); }
  }
  if (DEBON) System.out.println("calculaLi");

  N = num_values + 1; //N es la darrera posicio de d, i d es num_values+1

  for (i=2 ;i<=N; i++)
  {
    s.long_float[i] = wwpunt.calculaSi(dd.punti[i], dd.punti[i-1]);
  }
  if (SEEVAL)
  {
	System.out.println("\ncontingut de s, calculada per calculaSi");
	for (i=1;i<=num_values;i++)
	  System.out.println("s.long_float["+i+"]="+s.long_float[i]);
  }
  for (i=2; i<=N-1; i++)
  { m.long_float[i] = wwpunt.calculaMi(s.long_float[i], s.long_float[i+1],
				dd.punti[i], dd.punti[i-1], dd.punti[i+1]);
  }
  if (SEEVAL)
  {
    System.out.println("\ncontingut de m, calculada per calculaMi");
	for (i=1;i<=num_values;i++)
	  System.out.println("m.long_float["+i+"d]="+m.long_float[i]);
  }
  if (McAllister) {
      if ((s.long_float[2]*(2*s.long_float[2]-m.long_float[2])) > 0.0) {
  	  m.long_float[1] = 2*s.long_float[2]-m.long_float[2]; }
      else { m.long_float[1] = 0.0; }
  }
  else {
      if  ( (m.long_float[2] == 0.0) && (s.long_float[2] == 0.0) )
	  m.long_float[1] = 0.0;
      else if (m.long_float[2] == 0.0)
	  m.long_float[1] = infinit;
      else
	  m.long_float[1] = s.long_float[2] * s.long_float[2]/m.long_float[2];
  }
  if (McAllister) {
      if ((s.long_float[N]*(2*s.long_float[N]-m.long_float[N-1])) > 0.0) {
      	  m.long_float[N] = 2*s.long_float[N]-m.long_float[N-1]; }
      else { m.long_float[N] = 0.0; }
  }
  else {
      if  ( (m.long_float[N-1] == 0.0) && (wwbasics.eqq(s.long_float[N],0.0)) )
	  m.long_float[N] = 0.0;
      else if (m.long_float[N-1] == 0.0)
	  m.long_float[N] = infinit;
      else
	  m.long_float[N] = s.long_float[N] * s.long_float[N] /
	      m.long_float[N-1];
  }
  for (i=1;i<=N;i++)
      {
	  this.rectai[i].m = m.long_float[i];
	  /*    if (this.rectai[i].m > (infinit/ 10.0) )   	* added */
	  /*	this.rectai[i].n = -infinit;            	* added */
	  /*    else                                     	* added */
	  this.rectai[i].n = d.punti[i].y - m.long_float[i]*d.punti[i].x;
	  /* si m[i] = infinit */
      } /* efor */
  //return(L);

  } /* ecalculaLi */

}
