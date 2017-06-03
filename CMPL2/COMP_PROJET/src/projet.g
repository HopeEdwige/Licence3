// Grammaire du langage PROJET
// COMP L3
// Anne Grazon, V�ronique Masson

// attention l'analyse est poursuivie apr�s erreur si l'on supprime la clause rulecatch

grammar projet;

options {
  language=Java; k=1;
 }

@header {
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;
}


// partie syntaxique :  description de la grammaire //
// les non-terminaux doivent commencer par une minuscule


@members {


// variables globales et m�thodes utiles � placer ici

}
// la directive rulecatch permet d'interrompre l'analyse � la premi�re erreur de syntaxe
@rulecatch {
catch (RecognitionException e) {reportError (e) ; throw e ; }}


unite  :   unitprog  EOF {PtGen.afftabSymb(); PtGen.pt(123); }
      |    unitmodule  EOF {PtGen.afftabSymb(); PtGen.pt(123); }
  ;

unitprog
  : {PtGen.pt(54);} 'programme' {PtGen.pt(47);} ident ':'
     declarations
     corps { System.out.println("succes, arret de la compilation "); }
  ;

unitmodule
  : {PtGen.pt(54);} 'module' {PtGen.pt(48);} ident ':'
     declarations  {PtGen.pt(52);}
  ;

declarations
  : partiedef? partieref? consts? vars? decprocs?
  ;

partiedef
  : 'def' ident {PtGen.pt(53);} (',' ident {PtGen.pt(53);} )* ptvg
  ;

partieref: 'ref'  specif {PtGen.pt(49);} (',' specif  {PtGen.pt(49);} )* ptvg
  ;

specif  : ident ( 'fixe' '(' type {PtGen.pt(50);} ( ',' type {PtGen.pt(50);} )* ')' )?
                ( 'mod'  '(' type {PtGen.pt(51);} ( ',' type {PtGen.pt(51);} )* ')' )?
  ;

consts  : 'const' ( ident {PtGen.pt(1);} '=' valeur {PtGen.pt(2);} ptvg   )+
  ;

vars  : 'var' ( type ident { PtGen.pt(1); PtGen.pt(9); } ( ','  ident { PtGen.pt(1); PtGen.pt(9); } )* ptvg )+ { PtGen.pt(91); }
  ;

type  : 'ent'  {PtGen.pt(7);}
  |     'bool' {PtGen.pt(8);}
  ;

decprocs: { PtGen.pt(124); } (decproc ptvg)+ { PtGen.pt(125); }
  ;

decproc :  'proc'  ident {PtGen.pt(40);} parfixe? parmod? {PtGen.pt(43);} consts? vars? corps {PtGen.pt(44);}
  ;

ptvg  : ';'
  |
  ;

corps : 'debut' instructions 'fin'
  ;

parfixe: 'fixe' '(' pf ( ';' pf)* ')'
  ;

pf  : type ident {PtGen.pt(41);} ( ',' ident {PtGen.pt(41);} )*
  ;

parmod  : 'mod' '(' pm ( ';' pm)* ')'
  ;

pm  : type ident  {PtGen.pt(42);} ( ',' ident {PtGen.pt(42);} )*
  ;

instructions
  : instruction ( ';' instruction)*
  ;

instruction
  : inssi
  | inscond
  | boucle
  | lecture
  | ecriture
  | affouappel
  |
  ;

inssi : 'si' expression { PtGen.pt(30); }
		'alors' instructions
		('sinon' { PtGen.pt(32); } instructions )?
		{ PtGen.pt(33); } 'fsi'
  ;

inscond : 'cond' { PtGen.pt(36); }  expression  { PtGen.pt(30); } ':' instructions
          (','  { PtGen.pt(37); } expression  { PtGen.pt(30); } ':' instructions )*
          ({ PtGen.pt(38); } 'aut' instructions )?
          { PtGen.pt(39); } 'fcond'
  ;

boucle  : 'ttq' { PtGen.pt(34); } expression { PtGen.pt(30); } 'faire' instructions { PtGen.pt(35); } 'fait'
  ;

lecture: 'lire' '(' ident { PtGen.pt(26); } ( ',' ident { PtGen.pt(26); } )* ')'
  ;

ecriture: 'ecrire' '(' expression { PtGen.pt(27); } ( ',' expression { PtGen.pt(27); } )* ')'
   ;

affouappel
  : ident { PtGen.pt(28); } ( ':=' expression { PtGen.pt(29); }
            |   (effixes (effmods)? )? { PtGen.pt(46); }
           )
  ;

effixes : '(' (expression  (',' expression  )*)? ')'
  ;

effmods :'(' (ident { PtGen.pt(45); } (',' ident { PtGen.pt(45); } )*)? ')'
  ;

expression: (exp1) ('ou' {PtGen.pt(10);}  exp1 {PtGen.pt(10);} {PtGen.pt(11);}  )*
  ;

exp1  : exp2 ('et' {PtGen.pt(10);}  exp2 {PtGen.pt(10);} {PtGen.pt(12);}  )*
  ;

exp2  : 'non' exp2 {PtGen.pt(10);} {PtGen.pt(13);}
  | exp3
  ;

exp3  : exp4
  ( '=' { PtGen.pt(14); } exp4 { PtGen.pt(14); } { PtGen.pt(15); }
  | '<>'{ PtGen.pt(14); }  exp4 { PtGen.pt(14); } { PtGen.pt(16); }
  | '>' { PtGen.pt(14); }  exp4 { PtGen.pt(14); } { PtGen.pt(17); }
  | '>='{ PtGen.pt(14); }  exp4 { PtGen.pt(14); } { PtGen.pt(18); }
  | '<' { PtGen.pt(14); }  exp4 { PtGen.pt(14); } { PtGen.pt(19); }
  | '<='{ PtGen.pt(14); }  exp4 { PtGen.pt(14); } { PtGen.pt(20); }
  ) ?
  ;

exp4  : exp5
        ('+' { PtGen.pt(14); } exp5 { PtGen.pt(14); } { PtGen.pt(21); }
        |'-' { PtGen.pt(14); } exp5 { PtGen.pt(14); } { PtGen.pt(22); }
        )*
  ;

exp5  : primaire
        (    '*' { PtGen.pt(14); } primaire { PtGen.pt(14); } { PtGen.pt(23); }
          | 'div' { PtGen.pt(14); }  primaire { PtGen.pt(14); } { PtGen.pt(24); }
        )*
  ;

primaire: valeur { PtGen.pt(25); }
  | ident  { PtGen.pt(31); }
  | '(' expression ')'
  ;

valeur  : nbentier {PtGen.pt(3);}
  | '+' nbentier {PtGen.pt(3);}
  | '-' nbentier {PtGen.pt(4);}
  | 'vrai' {PtGen.pt(5);}
  | 'faux' {PtGen.pt(6);}
  ;

// partie lexicale  : cette partie ne doit pas �tre modifi�e  //
// les unit�s lexicales de ANTLR doivent commencer par une majuscule
// attention : ANTLR n'autorise pas certains traitements sur les unit�s lexicales,
// il est alors n�cessaire de passer par un non-terminal interm�diaire
// exemple : pour l'unit� lexicale INT, le non-terminal nbentier a d� �tre introduit


nbentier  :   INT { UtilLex.valNb = Integer.parseInt($INT.text);}; // mise � jour de valNb

ident : ID  { UtilLex.traiterId($ID.text, $ID.line); } ; // mise � jour de numId
     // tous les identificateurs seront plac�s dans la table des identificateurs, y compris le nom du programme ou module
     // la table des symboles n'est pas g�r�e au niveau lexical


ID  :   ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;

// zone purement lexicale //

INT :   '0'..'9'+ ;
WS  :   (' '|'\t' | '\n' |'\r')+ {skip();} ; // d�finition des "espaces"


COMMENT
  :  '\{' (.)* '\}' {skip();}   // toute suite de caract�res entour�e d'accolades est un commentaire
  |  '#' ~( '\r' | '\n' )* {skip();}  // tout ce qui suit un caract�re di�se sur une ligne est un commentaire
  ;

// commentaires sur plusieurs lignes
ML_COMMENT    :   '/*' (options {greedy=false;} : .)* '*/' {$channel=HIDDEN;}
    ;
