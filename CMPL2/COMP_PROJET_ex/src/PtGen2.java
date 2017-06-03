/*********************************************************************************
 *   squelette de PtGen2 fourni aux �tudiants, � compl�ter pour grammaire  Exp2  *
 *   nom du programme compil�, sans suffixe : String    nomSource                *
 *   ------------------------                                                    *
 *                                                                               *
 *   attributs lexicaux (selon items figurant dans la grammaire):                *
 *   ------------------                                                          *
 *     int PtGen2.valNb = valeur du dernier nombre entier lu (item nbentier)     *
 *     int PtGen2.numId = code du dernier identificateur lu (item ident)         *
 *                                                                               *
 *                                      A. GRAZON - V.MASSON                     *
 *********************************************************************************/


import java.io.*;

public class PtGen2 {


    public static String trinome = "NGUYEN NHON, BOUCHERIE, ANDRIAMILANTO";
    public static int valNb;
    public static String idLu;

    // initialisations  �  compl�ter
    // -------------------------------------


    // code des points de g�n�ration � compl�ter
    // -------------------------------------
    public static void pt(int numGen) {
    	switch (numGen) {
    	case 0 :	break;
    	case 1 : 	System.out.println("empiler " + valNb); break;
    	case 2 : 	System.out.println("contenug " + idLu); break;
    	case 5 : 	System.out.println("mul"); break;
    	case 6 : 	System.out.println("div"); break;
    	case 7 : 	System.out.println("add"); break;
    	case 8 : 	System.out.println("sous"); break;

    	default : System.out.println("Point de g�n�ration non pr�vu dans votre liste");break;

    	}
        }
}
