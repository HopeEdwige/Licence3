public class Autovin {
    public static final int[][] transit =
    	{
    	/* état        BJ   BG IDENT NBENT  ,    ;    /  AUTRES  */
    	/* 0 */      { 6,   6,   1,    6,   6,   0,   7,   6   },
    	/* 1 */      { 2,   2,   4,    3,   6,   0,   6,   6   },
    	/* 2 */      { 6,   6,   4,    6,   6,   0,   6,   6   },
    	/* 3 */      { 2,   2,   4,    6,   6,   0,   6,   6   },
    	/* 4 */      { 6,   6,   6,    5,   3,   0,   6,   6   },
    	/* 5 */      { 6,   6,   4,    6,   6,   0,   6,   6   },
    	/* 6 */      { 6,   6,   6,    6,   6,   0,   6,   6   }
        };
    public static final int FINAL = transit.length , ETATERR = FINAL-1 ;

    public static void main(String[] args) {
	System.out.println("la classe Autovin ne possède pas de 'main'") ;
    } // main
		
} // class Autovin
