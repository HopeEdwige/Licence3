// Grammaire du langage Exp2, poly page 6
// // A. GRAZON - V.MASSON
// grammaire fournie aux étudiants qui doivent y ajouter les appels à Ptgen.pt(k)

// attention l'analyse est poursuivie après erreur si l'on supprime la clause rulecatch

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
// variables globales et méthodes utiles à placer ici
}
// la directive rulecatch permet d'interrompre l'analyse à la première erreur de syntaxe
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
// attention : ANTLR n'autorise pas certains traitements sur les unités lexicales, 
// il est alors nécessaire de passer par un non-terminal intermédiaire 
// exemple : pour l'unité lexicale INT, le non-terminal nbentier a dû être introduit
       
nbentier  :   INT { PtGen2.valNb = Integer.parseInt($INT.text);};
ident : ID  { PtGen2.idLu = $ID.text; } ; 
     
// zone purement lexicale //

INT :   '0'..'9'+ ;
ID  :   ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ; 
WS  :   (' '|'\t' | '\n' |'\r')+ {skip();} ; // définition des "espaces"

	   