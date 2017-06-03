// Grammaire du langage Exp2, poly page 6
// // A. GRAZON - V.MASSON
// grammaire fournie aux �tudiants qui doivent y ajouter les appels � Ptgen.pt(k)

// attention l'analyse est poursuivie apr�s erreur si l'on supprime la clause rulecatch

grammar Exp2;

options {
  language=Java;
  k=1;
 }

@header {
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;
}

// partie syntaxique :  description de la grammaire //


@members {
// variables globales et m�thodes utiles � placer ici
}
// la directive rulecatch permet d'interrompre l'analyse � la premi�re erreur de syntaxe
@rulecatch {
catch (RecognitionException e) {reportError (e) ; throw e ; }}


unite  :    exp   EOF
  ;

exp   : (terme)
        ('+' terme {PtGen2.pt(7);}
        |'-' terme {PtGen2.pt(8);}
        )*
  ;

terme  : (primaire)
        ( '*' primaire {PtGen2.pt(5);}
        | 'div' primaire {PtGen2.pt(6);}
        )*
  ;

primaire: nbentier 			{PtGen2.pt(1);}
  | ident 					{PtGen2.pt(2);}
  | '('  exp ')'
  ;

// partie lexicale  //
// attention : ANTLR n'autorise pas certains traitements sur les unit�s lexicales,
// il est alors n�cessaire de passer par un non-terminal interm�diaire
// exemple : pour l'unit� lexicale INT, le non-terminal nbentier a d� �tre introduit

nbentier  :   INT { PtGen2.valNb = Integer.parseInt($INT.text);};
ident : ID  { PtGen2.idLu = $ID.text; } ;

// zone purement lexicale //

INT :   '0'..'9'+ ;
ID  :   ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
WS  :   (' '|'\t' | '\n' |'\r')+ {skip();} ; // d�finition des "espaces"
